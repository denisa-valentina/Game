package Characters;

import Graphics.Constants;
import Main.Game;

import java.awt.geom.Rectangle2D;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.getSpriteAmount;
import static Graphics.Check.*;

public abstract class Enemy extends Character {
    protected int enemyAction = IDLE, enemyType;
    protected int animationTick, animationIndex;
    protected int animationSpeed = 25;
    protected float airVelocity, gravity = 0.03f * Constants.GameCONST.SCALE;
    protected boolean firstUpdate = true, inAir = false;
    protected float walkSpeed = 0.35f * Constants.GameCONST.SCALE;
    protected int walkDirection = 1;
    protected int yTile;
    protected float attackDistance = 15;

    protected boolean active = true;
    protected int maxHealth;
    protected int currentHealth;

    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;

        initCollisionBox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    public void changeAction(int enemyAction)
    {
        this.enemyAction = enemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    protected void updateAnimationTick() {
        animationTick += 1;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex += 1;
            if (animationIndex >= getSpriteAmount(enemyType, enemyAction)) {
                animationIndex = 0;
                switch(enemyAction) {
                    case ATTACK, HURT -> enemyAction = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
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
            yTile = (int)(collisionBox.y / Game.TILE_SIZE);
        }
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
            if(isEnemyOnTheFloor(collisionBox, xSpeed,levelMatrix))
            {
                collisionBox.x += xSpeed;
                return;
            }
        }
        changeDirection();
    }

    protected void turnTowardsPlayer(Player player)
    {
        if(player.collisionBox.x > collisionBox.x) // player is on the right side of the enemy
            walkDirection = 1; // right
        else walkDirection = -1; // left
    }

    protected void changeDirection() {
        if(walkDirection == -1) // is left
        {
            walkDirection = 1; // go right
        }
        else
            walkDirection = -1; // is right, go left
    }

    public void hurt(int value) {
        currentHealth -= value;
        if(currentHealth <= 0) {
            changeAction(DEAD);
            //active = false;
        }
        else
            changeAction(HURT);
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.collisionBox)) // enemy attackBox intersects player's collisionBox
        {
            player.updateHealth(-getEnemyDamage(enemyType));
            //player.changeAction(HURT);
        }
        attackChecked = true;
    }

    // checks if enemy sees the player
    protected boolean spotPlayer(int [][]levelMatrix, Player player) {
        int playerTileY = (int)(player.getCollisionBox().y + player.collisionBox.height) / Game.TILE_SIZE;
        if(playerTileY == yTile) { // player and enemy are on the same Y position (they are not at different heights)
            if(isPlayerInRange(player)) {
                return ClearSight(levelMatrix, collisionBox, player.collisionBox, yTile);
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absoluteDistance = (int)Math.abs(player.collisionBox.x - collisionBox.x);
        return absoluteDistance <= attackDistance * 7;
    }

    protected boolean isPlayerTooClose(Player player){
        int absoluteDistance = (int)Math.abs(player.collisionBox.x - collisionBox.x);
        return absoluteDistance <= attackDistance;
    }

    public int getAnimationIndex()
    {
        return animationIndex;
    }

    public int getEnemyAction () {
        return enemyAction;
    }

    public boolean isActive() {
        return active;
    }

    public void resetEnemy() {
        collisionBox.x = x;
        collisionBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        changeAction(IDLE);
        active = true;
        airVelocity = 0;
    }
}
