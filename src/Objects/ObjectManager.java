package Objects;

import GameStates.Play;
import static Graphics.Constants.Fruits.Images.*;
import static Graphics.Constants.Fruits.FruitsConstants.*;
import static Graphics.Constants.misscellaneous;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Load.Load;

public class ObjectManager {
    private final Play play;
    private ArrayList<ArrayList<BufferedImage>> fruitImages;
    private final ArrayList<Fruit> fruits;

    public ObjectManager(Play play)
    {
        this.play = play;
        loadImages();

        fruits = new ArrayList<>();

        misscellaneous.add(700);
        misscellaneous.add(701);
        misscellaneous.add(702);
        misscellaneous.add(703);
        misscellaneous.add(704);
        misscellaneous.add(705);
        misscellaneous.add(706);
        misscellaneous.add(707);

        for (int i = 700; i < 707; ++i) {
            ArrayList<Point2D> fruitCoordinates = play.getLevelHandler().getLevel().getCoordinates(i);
            for (int j = 0; j < fruitCoordinates.size(); ++j) {
                fruits.add(new Fruit(32 * (int) fruitCoordinates.get(j).getX(), 32 * (int) fruitCoordinates.get(j).getY(), i-700));
            }
        }
    }

    private void loadImages()
    {
        ArrayList<BufferedImage> images = new ArrayList<>();
        images.add(Load.getImage(apple));
        images.add(Load.getImage(banana));
        images.add(Load.getImage(cherry));
        images.add(Load.getImage(kiwi));
        images.add(Load.getImage(melon));
        images.add(Load.getImage(orange));
        images.add(Load.getImage(pineapple));
        images.add(Load.getImage(strawberry));
        fruitImages = new ArrayList<>();
        for(int i = 0; i < images.size(); ++i) {
            ArrayList<BufferedImage> fruitImage = new ArrayList<>();
            for (int j = 0; j < 17; ++j) {
                fruitImage.add(images.get(i).getSubimage(32 * j, 0, 32, 32));
            }
            fruitImages.add(fruitImage);
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
                obj.drawImage(fruitImages.get(f.objectType).get(f.getAnimationIndex()), (int)(f.getCollisionBox().x - f.getxOffset() - xLevelOffset),
                        (int)(f.getCollisionBox().y - f.getxOffset()), 55, 55, null);
            }
        }
    }
}
