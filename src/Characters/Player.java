package Characters;

import GameStates.Play;
import Graphics.Constants;
import Load.Load;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.GRAVITY;
import static Graphics.Constants.Player.*;
import static Graphics.Constants.GameCONST;
import static Graphics.Check.*;

public class Player extends Character {

    private final Play play;
    private static Player playerInstance;

    // lifeStatus UI
    private BufferedImage statusBar;
    private BufferedImage fullHeart;
    private BufferedImage emptyHeart;

    // score
    private int score = 0;

    private int[][] levelMatrix;
    private List<List<BufferedImage>> animations;

    private boolean moving = false, attacking1 = false, attacking2 = false;
    private boolean left, right, jump;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked = false;


    private Player(float x, float y, int width, int height, Play play) {
        super(x, y, width, height);
        this.play = play;

        this.action = IDLE;
        this.runSpeed = 1.1f * GameCONST.SCALE;

        this.maxHealth = 5; // 5 hearts, 5 chances
        this.currentHealth = 3; // currentHealth = maxHealth

        loadAnimations();
        initCollisionBox(23, 70); // prin incercari am calculat width-ul si height-ul aproximativ al imaginii caracterului
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
        if(currentHealth <= 0)
        {
            play.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePosition();
        if(moving){

            checkFruitTouched();
            checkSpikeTouched();

        }

        if((attacking1 || attacking2)) // && play.getLevelHandler().getLevelIndex() == 2) || (attacking2 && play.getLevelHandler().getLevelIndex() == 1))
        {
            checkAttack();
        }

        updateAnimationTick();
        setAnimation();
        System.out.println(score + play.getLevelHandler().getCurrentLevel().getLevelScore());
    }

    private void checkFruitTouched() {
        play.checkObjectTouched(collisionBox);
    }
    private void checkSpikeTouched(){
        play.getLevelHandler().getCurrentLevel().checkSpikeTouched(this);
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
            attackBox.x = collisionBox.x + 7 * collisionBox.width/8; // vezi daca ar fi necesar si un offset suplimentar
        }
        else if(left) {
            attackBox.x = collisionBox.x - 2 * collisionBox.width;
        }
        attackBox.y = collisionBox.y + collisionBox.height/2;
    }

    void updateHealth(int value) {
        currentHealth += value;
        currentHealth = Math.max(Math.min(currentHealth, currentHealth), 0);
    }

    public void draw(Graphics g, int xLevelOffset) {
        final float xOffset = 50 * GameCONST.SCALE, yOffset = 55 * GameCONST.SCALE;

        BufferedImage image = animations.get(action).get(animationIndex);
        g.drawImage(image, (int)(collisionBox.x - xOffset) - xLevelOffset + flipX, (int)(collisionBox.y - yOffset), width * flipW, height, null);
        drawCollisionBox(g, xLevelOffset);
        drawAttackBox(g, xLevelOffset);
        drawUI(g);
    }

    @Override
    public void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.green);
        g.drawRect((int)attackBox.x - xLevelOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        int hearts_xStart = (int) (110 * GameCONST.SCALE);
        int hearts_yStart = (int) (50 * GameCONST.SCALE);

        g.drawImage(statusBar, (int)(60*GameCONST.SCALE), (int)(40*GameCONST.SCALE), 270, 150, null);
        for(int i=0;i<currentHealth;++i)
        {
            g.drawImage(fullHeart, hearts_xStart + i * 40, hearts_yStart, 35, 32, null);
        }
        for(int i=currentHealth;i<maxHealth;++i)
        {
            g.drawImage(emptyHeart, hearts_xStart + i * 40, hearts_yStart, 35, 32, null);
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
        image.add(images.getLast().getSubimage(0, 0, 128, 128));
        image.add(images.getLast().getSubimage(128, 0, 128, 128));

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
        int startAnimation = action;

        if (moving) {
            action = RUN;
        } else {
            action = IDLE;
        }
        if (inAir) {
            action = JUMP;
        }

        if (attacking1){ //&& play.getLevelHandler().getLevelIndex() == 1) {
            action = ATTACK_1;
//            if(startAnimation != ATTACK_1){ // so that the player's attack have effect sooner
//                animationIndex = 3;
//                animationTick = 0;
//                return;
//            }
        }
        if (attacking2)// && play.getLevelHandler().getLevelIndex() == 2)
            {
            action = ATTACK_2;
        }

        if (startAnimation != action) {
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
            if (animationIndex >= getSpriteAmount(action)) {
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
        float fallSpeed = 0.5f * GameCONST.SCALE;
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
                airVelocity += GRAVITY; // coordonatele "in sus" sunt negative, deci "gravitatia" va incetini viteza caracterului
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

    public void respawn() {
        updateHealth(-1);
        resetAll();
    }

    private void jumping() {
        float jumpSpeed = -2.5f * GameCONST.SCALE;
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
        this.action = playerAction;
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

    public void resetHeart(){
        currentHealth = maxHealth;
    }

    public void resetAll() {
        resetDirection();
        inAir = false;
        attacking1 = false; attacking2 = false;
        moving = false;
        action = IDLE;

        collisionBox.x = x;
        collisionBox.y = y;

        if(!isOnTheFloor(collisionBox, levelMatrix))
            inAir = true;
    }

    public void setScore(int value){
        score += value;
    }

    public int getScore(){
        return score;
    }
}
