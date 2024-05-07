package Objects;

import java.awt.geom.Rectangle2D;

import static Graphics.Constants.ANIMATION_SPEED;

public class GameObject {

    protected int x, y, objectType;
    protected Rectangle2D.Float collisionBox;
    protected boolean doAnimation, active = true;
    protected int animationTick, animationIndex;
    protected int xOffset, yOffset;

    public GameObject(int x, int y, int objectType)
    {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void initCollisionBox(int width, int height)
    {
        collisionBox = new Rectangle2D.Float(x, y, width, height);
    }

//    public void drawCollisionBox(Graphics obj, int xLevelOffset)
//    {   // debugging
//        obj.setColor(Color.green);
//        obj.drawRect((int)collisionBox.x - xLevelOffset, (int)collisionBox.y, (int)collisionBox.width, (int)collisionBox.height);
//    }

    protected void updateAnimationTick()
    {
        animationTick++;
        if(animationTick >= ANIMATION_SPEED)
        {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= 17) // all fruits have 17 sprites
            {
                animationIndex = 0;
            }
        }
    }
//    public void reset() {
//        animationIndex = 0;
//        animationTick = 0;
//        active = true;
//
//        doAnimation = true;
//    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Float getCollisionBox()
    {
        return collisionBox;
    }

//    public int getObjectType() {
//        return objectType;
//    }

    public int getxOffset() {
        return xOffset;
    }

//    public int getyOffset()
//    {
//        return yOffset;
//    }
//    public void setActive(boolean active)
//    {
//        this.active = active;
//    }

    public int getAnimationIndex()
    {
        return animationIndex;
    }


}

