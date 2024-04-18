package Objects;

import LoadSave.Load;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Graphics.Constants.Player.*;
import static Graphics.Check.*;

public class Player extends Character {

    private int[][] levelMatrix;
    private static Player playerInstance;
    private BufferedImage[][] animations;
    private int animationStick, animationIndex, animationSpeed = 25;
    private int animationJumpSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, inAir = false;
    private boolean left, up, right, down, jump;
    private float runSpeed = 1.0f * Game.SCALE, jumpSpeed = -2.0f * Game.SCALE, fallSpeed = 0.5f * Game.SCALE;
    private float gravity = 0.02f * Game.SCALE, airVelocity = 0.0f;
    private float xOffset = 50 * Game.SCALE, yOffset = 55 * Game.SCALE;


    private Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initCollisionBox(x, y, (int)(23*Game.SCALE), (int)(73*Game.SCALE)); // prin incercari am calculat width-ul si height-ul aproximativ al imaginii caracterului
    }

    public static Player getInstance(float x, float y, int width, int height)
    {
        if(playerInstance == null)
        {
            playerInstance = new Player(x, y, width, height);
        }
        return playerInstance;
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics obj) {
        //xOffset = playerInstance.getX()
        obj.drawImage(animations[playerAction][animationIndex], (int)(getCollisionBox().x - xOffset), (int)(getCollisionBox().y - yOffset), getWidth(), getHeight(), null);
        drawCollisionBox(obj);
    }

    private void loadAnimations() {
        BufferedImage []images = new BufferedImage[6];
        // 6 animations: Idle, Run, Jump, Attack1, Attack2, Dead
        images[0] = Load.getImage("/player/Idle.png");
        images[1] = Load.getImage("/player/Run.png");
        images[2] = Load.getImage("/player/Jump.png");
        images[3] = Load.getImage("/player/Dead.png");
        images[4] = Load.getImage("/player/Attack_1.png");
        images[5] = Load.getImage("/player/Attack_2.png");
        animations = new BufferedImage[6][10];

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 8; ++j) {
                animations[i][j] = images[i].getSubimage(j * 128, 0, 128, 128);
            }
        }

        for(int i=2;i<animations.length;++i)
        {
            for(int j=0;j<10;++j)
            {
                animations[i][j] = images[i].getSubimage(j*128, 0, 128, 128);
            }
        }
    }

    public void loadLevelMatrix(int[][] levelMatrix)
    {
        this.levelMatrix = levelMatrix;
        if(!isOnTheFloor(getCollisionBox(), levelMatrix))
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

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }
    private void updateAnimationTick() {
        if(jump) { animationSpeed = animationJumpSpeed; }
        else { animationSpeed = 25; }

        animationStick += 1;
        if (animationStick >= animationSpeed) {
            animationStick = 0;
            animationIndex += 1;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void resetAnimationTick() {
        animationIndex = 0;
        animationStick = 0;
    }

    private void updatePosition() {
        float xSpeed = 0;
        moving = false;

        if (jump) { jumping(); }

        if (!left && !right && !inAir) {
            return;
        }

        if (left && !right) {
            xSpeed -= runSpeed;
            moving = true;
        } else if (right && !left) {
            xSpeed += runSpeed;
            moving = true;
        }

        if (!inAir) {
            if (!isOnTheFloor(getCollisionBox(), levelMatrix)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (!isCollision(getCollisionBox().x, getCollisionBox().y + airVelocity, getCollisionBox().width, getCollisionBox().height, levelMatrix)) {
                getCollisionBox().y += airVelocity;
                airVelocity += gravity; // coordonatele "in sus" sunt negative, deci "gravitatia" va incetini viteza caracterului
                // daca coboara, viteza va creste (practic il va trage gravitatia inapoi)
                updateXPosition(xSpeed);
            } else {
                //getCollisionBox().y = getYPositionRoof_Floor(getCollisionBox(), airVelocity);
                if (airVelocity > 0) // we jump and hit something
                {
                    resetJumping();
                } else {
                    airVelocity = fallSpeed;
                }
                updateXPosition(xSpeed);
            }
        } else {
            updateXPosition(xSpeed);
        }
        moving = true;
    }

    private void jumping() {
        if(inAir)
            return; // we are already in the air
        inAir = true;
        airVelocity = jumpSpeed;
    }


    private void updateXPosition(float xSpeed) {
        if(!isCollision(getCollisionBox().x+xSpeed, getCollisionBox().y, getCollisionBox().width, getCollisionBox().height, levelMatrix))
        {
            getCollisionBox().x += xSpeed;
        }
    }

    public void resetDirection() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void resetJumping() // stopJumping()
    {
        inAir = false;
        airVelocity = 0;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
