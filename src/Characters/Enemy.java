package Characters;

import Graphics.Constants;
import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.getSpriteAmount;
import static Graphics.Check.*;
import static Graphics.Constants.GRAVITY;

public abstract class Enemy extends Character {

    protected int enemyType;

    protected boolean firstUpdate = true;
    protected int walkDirection = 1;
    protected int yTile;
    protected float attackDistance = 15;

    protected boolean active = true;

    protected int attacking = 0;

    protected int attackBoxOffsetX;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);

        this.enemyType = enemyType;
        this.action = IDLE;
        this.runSpeed = 0.35f * Constants.GameCONST.SCALE;

        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;

    }

    protected void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(65* Constants.GameCONST.SCALE), (int)(28* Constants.GameCONST.SCALE));
        attackBoxOffsetX = (int)(10 * Constants.GameCONST.SCALE);
    }

    protected void updateAttackBox() {
        attackBox.x = collisionBox.x - attackBoxOffsetX;
        attackBox.y = collisionBox.y;
    }

    public void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x - xLevelOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    public void changeAction(int enemyAction)
    {
        this.action = enemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    protected void updateAnimationTick() {
        animationTick += 1;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex += 1;
            if (animationIndex >= getSpriteAmount(enemyType, action)) {
                animationIndex = 0;
                switch(action) {
                    case ATTACK, HURT -> action = IDLE;
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
            airVelocity += GRAVITY;
        }
        else {
            inAir = false;
            collisionBox.y = GetEntityYPosUnderRoofOrAboveFloor(collisionBox, airVelocity);
            yTile = (int)(collisionBox.y / Game.TILE_SIZE);
        }
    }

    protected void setRunning(int [][]levelMatrix)
    {
        int LEFT = -1;
        float xSpeed = 0;

        if(walkDirection == LEFT) {
            xSpeed = -runSpeed;
        }
        else{
            xSpeed = runSpeed;
        }
        if(!isCollision(collisionBox.x + xSpeed, collisionBox.y, collisionBox.width, collisionBox.height, levelMatrix))
        {
            if(isEnemyOnTheFloor(collisionBox, xSpeed, levelMatrix))
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



    public boolean isActive() {
        return active;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    protected int flipX()
    {
        if(walkDirection == 1)
        {
            return 0;
        }
        else return width;
    }

    protected int getAttacking(){
        return attacking;
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

    public abstract void update(int [][]levelMatrix, Player player);
    protected abstract void updateBehavior(int [][]levelMatrix, Player player);
    public abstract void resetValues();
}
