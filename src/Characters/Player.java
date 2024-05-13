package Characters;

import GameStates.Play;
import Graphics.Constants;
import Load.Load;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.Player.*;
import static Graphics.Constants.GameCONST;
import static Graphics.Check.*;

public class Player extends Character {

    private Play play;

    private int[][] levelMatrix;
    private static Player playerInstance;
    private List<List<BufferedImage>> animations;
    private int animationTick, animationIndex, animationSpeed = 25;
//    private final int animationJumpSpeed = 18;
    private int playerAction = IDLE;
    private boolean moving = false, attacking1 = false, attacking2 = false, inAir = false;
    private boolean left, right, jump;
    private final float runSpeed = 1.1f * GameCONST.SCALE, jumpSpeed = -2.5f * GameCONST.SCALE, fallSpeed = 0.5f * GameCONST.SCALE;
    private final float gravity = 0.02f * GameCONST.SCALE;
    private float airVelocity = 0.0f;
    private final float xOffset = 50 * GameCONST.SCALE, yOffset = 55 * GameCONST.SCALE;

    private int flipX = 0;
    private int flipW = 1;

    // lifeStatus UI
    private BufferedImage statusBar;

    private BufferedImage fullHeart;
    private BufferedImage emptyHeart;
    private int totalLife = 5; // 5 hearts, 5 chances
    private int actualLife = 2;

    private int hearts_xStart = (int) (110 * GameCONST.SCALE);
    private int hearts_yStart = (int) (50 * GameCONST.SCALE);

    private boolean attackChecked = false;

    // attackBox
    private Rectangle2D.Float attackBox;

    private Player(float x, float y, int width, int height, Play play) {
        super(x, y, width, height);
        this.play = play;

        loadAnimations();
        initCollisionBox(x, y, (int)(23*GameCONST.SCALE), (int)(70*GameCONST.SCALE)); // prin incercari am calculat width-ul si height-ul aproximativ al imaginii caracterului
        initAttackBox();
    }

    public static Player getInstance(float x, float y, int width, int height, Play play)
    {
        if(playerInstance == null)
        {
            playerInstance = new Player(x, y, width, height, play);
        }
        return playerInstance;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x + 7*collisionBox.width/8, y, (int)(60*GameCONST.SCALE), (int)(40*GameCONST.SCALE));
    }

    public void update() {
        if(actualLife <= 0)
        {
            play.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePosition();

        if(attacking1 || attacking2)
        {
            checkAttack();
        }

        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || animationIndex != 6)
        {
            return;
        }
        else{
            attackChecked = true;
            play.checkEnemyHit(attackBox);
        }
    }

    private void updateAttackBox() {
        if(right) {
            attackBox.x = collisionBox.x + 7*collisionBox.width/8; // vezi daca ar fi necesar si un offset suplimentar
        }
        else if(left) {
            attackBox.x = collisionBox.x - 2 * collisionBox.width;
        }
        attackBox.y = collisionBox.y + collisionBox.height/2;
    }

    void updateHealth(int value) {
        actualLife += value;
        actualLife = Math.max(Math.min(actualLife, totalLife), 0);

//        if(actualLife <= 0) {
//            actualLife = 0;
//            // game over
//        }
//        else if(actualLife >= totalLife) {
//            actualLife = totalLife;
//        }
    }

    public void draw(Graphics obj, int xLevelOffset) {
        BufferedImage image = animations.get(playerAction).get(animationIndex);
        obj.drawImage(image, (int)(collisionBox.x - xOffset) - xLevelOffset + flipX, (int)(collisionBox.y - yOffset), width * flipW, height, null);
        drawCollisionBox(obj, xLevelOffset);
        drawAttackBox(obj, xLevelOffset);
        drawUI(obj);
    }

    @Override
    public void drawAttackBox(Graphics obj, int xLevelOffset) {
        obj.setColor(Color.green);
        obj.drawRect((int)attackBox.x - xLevelOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void drawUI(Graphics obj) {
        obj.drawImage(statusBar, (int)(60*GameCONST.SCALE), (int)(40*GameCONST.SCALE), 270, 150, null);
        for(int i=0;i<actualLife;++i)
        {
            obj.drawImage(fullHeart, hearts_xStart + i * 40, hearts_yStart, 35, 32, null);
        }
        for(int i=actualLife;i<totalLife;++i)
        {
            obj.drawImage(emptyHeart, hearts_xStart + i * 40, hearts_yStart, 35, 32, null);
        }
    }

    private void loadAnimations() {
        List<BufferedImage> images = new ArrayList<>();
        // 6 animations: Idle, Run, Jump, Attack1, Attack2, Dead
        images.add(Load.getImage(Images.player_idle));
        images.add(Load.getImage(Images.player_run));
        images.add(Load.getImage(Images.player_jump));
        images.add(Load.getImage(Images.player_dead));
        images.add(Load.getImage(Images.player_attack1));
        images.add(Load.getImage(Images.player_attack2));
        images.add(Load.getImage(Images.player_hurt));
        animations = new ArrayList<>();

        for (int i = 0; i < 2; ++i) {
            List<BufferedImage> image = new ArrayList<>();
            for (int j = 0; j < 8; ++j) {
                image.add(images.get(i).getSubimage(j * 128, 0, 128, 128));
            }
            animations.add(image);
        }

        for (int i = 2; i < images.size() - 1; ++i) {
            List<BufferedImage> image = new ArrayList<>();
            for (int j = 0; j < 10; ++j) {
                image.add(images.get(i).getSubimage(j * 128, 0, 128, 128));
            }
            animations.add(image);
        }

        List<BufferedImage> image = new ArrayList<>();
        image.add(images.get(images.size()-1).getSubimage(0, 0, 128, 128));
        image.add(images.get(images.size()-1).getSubimage(128, 0, 128, 128));

        animations.add(image);

        fullHeart = Load.getImage(Constants.LifeStatus.fullHeart);
        emptyHeart = Load.getImage(Constants.LifeStatus.emptyHeart);
        statusBar = Load.getImage(Constants.LifeStatus.staturBar);
    }

    public void loadLevelMatrix(int[][] levelMatrix)
    {
        this.levelMatrix = levelMatrix;
        if(!isOnTheFloor(collisionBox, levelMatrix))
            inAir = true;
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUN;
        }
        else {
            playerAction = IDLE;
        }

        if(inAir) {
            playerAction = JUMP;
        }

        if (attacking1) {
            playerAction = ATTACK_1;
//            if(startAnimation != ATTACK_1){ // so that the player's attack have effect sooner
//                animationIndex = 3;
//                animationTick = 0;
//                return;
//            }
        }
        if (attacking2) {
            playerAction = ATTACK_2;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }
    private void updateAnimationTick() {
        if(jump) { animationSpeed = 18; }
        else { animationSpeed = 25; }

        animationTick += 1;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex += 1;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking1 = false;
                attacking2 = false;
                attackChecked = false;
            }
        }
    }

    private void resetAnimationTick() {
        animationIndex = 0;
        animationTick = 0;
    }

    private void updatePosition() {
        float xSpeed = 0;
        moving = false;

        if (jump) { jumping(); }

        if(!inAir) {
            if ((!left && !right) || (left && right)) {
                return;
            }
        }

        if (left && !right) {
            xSpeed -= runSpeed;
            moving = true;
            flipX = width;
            flipW = -1;
        } else if (right && !left) {
            xSpeed += runSpeed;
            moving = true;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!isOnTheFloor(collisionBox, levelMatrix)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (!isCollision(collisionBox.x, collisionBox.y + airVelocity, collisionBox.width, collisionBox.height, levelMatrix)) {
                collisionBox.y += airVelocity;
                airVelocity += gravity; // coordonatele "in sus" sunt negative, deci "gravitatia" va incetini viteza caracterului
                // daca coboara, viteza va creste (practic il va trage gravitatia inapoi)
                updateXPosition(xSpeed, levelMatrix);
            } else {
                if (airVelocity > 0) // we jump and hit something
                {
                    resetJumping();
                } else {
                    airVelocity = fallSpeed;
                }
                updateXPosition(xSpeed, levelMatrix);
            }
        } else {
            updateXPosition(xSpeed, levelMatrix);
        }
        moving = true;
    }

    private void updateXPosition(float xSpeed, int [][]levelMatrix) {
        if(!isCollision(collisionBox.x+xSpeed, collisionBox.y, collisionBox.width, collisionBox.height, levelMatrix))
        {
            collisionBox.x += xSpeed;
        }
    }

    private void jumping() {
        if(inAir)
            return; // we are already in the air
        inAir = true;
        airVelocity = jumpSpeed;
    }


    public void resetDirection() {
        left = false;
        right = false;
    }

    public void resetJumping() // stopJumping()
    {
        inAir = false;
        airVelocity = 0;
    }

    public void changeAction(int playerAction)
    {
        this.playerAction = playerAction;
    }

    public void setAttacking1(boolean attacking1) {
        this.attacking1 = attacking1;
    }

    public void setAttacking2(boolean attacking2) {
        this.attacking2 = attacking2;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public BufferedImage getFullHeart() {
        return fullHeart;
    }

    public void resetAll() {
        resetDirection();
        inAir = false;
        attacking1 = false; attacking2 = false;
        moving = false;
        playerAction = IDLE;
        actualLife = totalLife;

        collisionBox.x = x;
        collisionBox.y = y;

        if(!isOnTheFloor(collisionBox, levelMatrix))
            inAir = true;
    }
}
