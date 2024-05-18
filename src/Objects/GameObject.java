package Objects;

import Graphics.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Graphics.Constants.ANIMATION_SPEED_OBJECTS;

public class GameObject {

    protected int x, y, objectType;
    protected Rectangle2D.Float collisionBox;
    protected boolean active = true;
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
        collisionBox = new Rectangle2D.Float(x, y, (int)(width * Constants.GameCONST.SCALE), (int)(height * Constants.GameCONST.SCALE));
    }

    public void drawCollisionBox(Graphics g, int xLevelOffset)
    {   // debugging
        g.setColor(Color.green);
        g.drawRect((int)collisionBox.x + xOffset - xLevelOffset, (int)collisionBox.y + yOffset, (int)collisionBox.width, (int)collisionBox.height);
    }

    protected void updateAnimationTick()
    {
        animationTick++;
        if(animationTick >= ANIMATION_SPEED_OBJECTS)
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


    public int getxOffset() {
        return xOffset;
    }


    public int getAnimationIndex()
    {
        return animationIndex;
    }

    public void reset(){
        animationTick = 0;
        animationIndex = 0;
        active = true;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}

