package Characters;

import Graphics.Constants.GameCONST;

import java.awt.geom.Rectangle2D;
import java.awt.*;


public abstract class Character {

    protected final float x, y;
    protected int width, height;

    protected Rectangle2D.Float collisionBox;
    protected Rectangle2D.Float attackBox;

    protected int action;
    protected int animationSpeed = 25;
    protected int animationTick, animationIndex;

    protected float runSpeed;
    protected float airVelocity = 0.0f;
    protected boolean inAir = false;

    protected int maxHealth;
    protected int currentHealth;


    public Character(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initCollisionBox(int width, int height)
    {
        collisionBox = new Rectangle2D.Float(x, y, (int)(width * GameCONST.SCALE), (int)(height * GameCONST.SCALE));
    }

    public Rectangle2D.Float getCollisionBox()
    {
        return collisionBox;
    }

    protected void drawCollisionBox(Graphics obj, int xLevelOffset)
    {   // debugging
        obj.setColor(Color.green);
        obj.drawRect((int)collisionBox.x - xLevelOffset, (int)collisionBox.y, (int)collisionBox.width, (int)collisionBox.height);
    }

    protected int getAction(){
        return action;
    }

    public int getAnimationIndex()
    {
        return animationIndex;
    }


    public abstract void drawAttackBox(Graphics obj, int xLevelOffset);


    //    public void setX(float x)
//    {
//        this.x = x;
//    }
//    public void setY(float y)
//    {
//        this.y = y;
//    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }

}
