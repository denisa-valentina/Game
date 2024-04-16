package Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Character {
    private float x, y;
    private int width, height;
    private Rectangle2D.Float collisionBox;

    public Character(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initCollisionBox(float x, float y, float width, float height)
    {
        collisionBox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void drawCollisionBox(Graphics obj)
    {   // debugging
        obj.setColor(Color.green);
        obj.drawRect((int)collisionBox.x, (int)collisionBox.y, (int)collisionBox.width, (int)collisionBox.height);

    }

    public Rectangle2D.Float getCollisionBox()
    {
        return collisionBox;
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }

}
