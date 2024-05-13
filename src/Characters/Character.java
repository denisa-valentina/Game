package Characters;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.*;

import static Graphics.Check.isCollision;

public abstract class Character {
    protected final float x, y;
    protected int width, height;
    private List<int[]> imageRegions;
    protected Rectangle2D.Float collisionBox;

    public Character(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initCollisionBox(float x, float y, int width, int height)
    {
        collisionBox = new Rectangle2D.Float(x, y, width, height);
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
