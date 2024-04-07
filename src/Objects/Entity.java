package Objects;

public abstract class Entity {

    private float x, y; // doar clasele care extind aceasta clasa pot utiliza aceste proprietati
    private int width, height, sprites;

    public Entity(float x, float y, int width, int height, int sprites){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprites = sprites;
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
    public int getSprites()
    {
        return sprites;
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
    public void setSprites(int sprites)
    {
        this.sprites = sprites;
    }


}
