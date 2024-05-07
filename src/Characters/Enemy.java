package Characters;

import Graphics.Constants;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.getSpriteAmount;
import static Graphics.Check.*;

public abstract class Enemy extends Character {
    protected int enemyAction = IDLE, enemyType;
    protected int animationTick, animationIndex;
    protected int animationSpeed = 25;
    protected float airVelocity, gravity = 0.03f * Constants.GameCONST.SCALE;
    protected boolean firstUpdate = true, inAir = false;
    protected float walkSpeed = 0.5f * Constants.GameCONST.SCALE;
    protected int walkDirection = -1;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;

        initCollisionBox(x, y, width, height);
    }

    protected void setFirstUpdate(int [][]levelMatrix)
    {
        if (!isOnTheFloor(collisionBox, levelMatrix)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void setInAir(int [][]levelMatrix)
    {
        if(!isCollision(collisionBox.x, collisionBox.y + airVelocity, collisionBox.width, collisionBox.height, levelMatrix)) {
            collisionBox.y += airVelocity;
            airVelocity += gravity;
        }
        else {
            inAir = false;
        }
    }

    public void changeAction(int enemyAction)
    {
        this.enemyAction = enemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    protected void setRunning(int [][]levelMatrix)
    {
        int LEFT = -1;
        float xSpeed = 0;

        if(walkDirection == LEFT) {
            xSpeed = -walkSpeed;
        }
        else{
            xSpeed = walkSpeed;
        }
        if(!isCollision(collisionBox.x + xSpeed, collisionBox.y, collisionBox.width, collisionBox.height, levelMatrix))
        {
            if(isFloor(collisionBox, xSpeed,levelMatrix))
            {
                collisionBox.x += xSpeed;
                return;
            }
        }
        changeDirection();
    }

    protected void changeDirection() {
        if(walkDirection == -1) // is left
        {
            walkDirection = 1; // go right
        }
        else
            walkDirection = -1; // is right, go left
    }

    protected void updateAnimationTick() {
        animationTick += 1;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex += 1;
            if (animationIndex >= getSpriteAmount(enemyType, enemyAction)) {
                animationIndex = 0;
            }
        }
    }


    public int getAnimationIndex()
    {
        return animationIndex;
    }

    public int getEnemyAction () {
        return enemyAction;
    }
}
