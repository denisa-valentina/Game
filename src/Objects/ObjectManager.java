package Objects;

import Load.Load;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Graphics.Constants.Fruits.FruitsConstants.*;
import static Graphics.Constants.Fruits.Images.*;


public class ObjectManager {
    //private final Play play;
    private BufferedImage[] fruitImages;
    private final ArrayList<Fruit> fruits;

    public ObjectManager()//(Play play)
    {
        //this.play = play;
        loadImages();

        fruits = new ArrayList<>();
        fruits.add(new Fruit(100, 350, APPLE));
        fruits.add(new Fruit(150, 350, APPLE));
    }

    private void loadImages()
    {
        BufferedImage appleSprite = Load.getImage(apple);
        fruitImages = new BufferedImage[17];
        for(int i=0;i<fruitImages.length;++i)
        {
            fruitImages[i] = appleSprite.getSubimage(32*i, 0, 32, 32);
        }
    }

    public void update()
    {
        for(Fruit f : fruits)
        {
            if(f.isActive())
                f.update();
        }
    }

    public void draw(Graphics obj, int xLevelOffset)
    {
        drawFruit(obj, xLevelOffset);
    }

    private void drawFruit(Graphics obj, int xLevelOffset) {
        for(Fruit f : fruits)
        {
            if(f.isActive())
            {
                //int type = 0;
                if(f.objectType == APPLE)
                {
                    obj.drawImage(fruitImages[f.getAnimationIndex()], (int)(f.getCollisionBox().x - f.getxOffset() - xLevelOffset),
                            (int)(f.getCollisionBox().y - f.getxOffset()), 64, 64, null);
                }

            }
        }
    }
}
