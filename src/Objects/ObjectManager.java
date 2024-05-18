package Objects;

import GameStates.Play;

import static Graphics.Constants.*;
import static Graphics.Constants.Fruits.FruitsConstants.*;
import static Graphics.Constants.Fruits.Images.*;
import static Graphics.Constants.Surroundings.Images.metalSpike;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Load.Load;

public class ObjectManager {
    private final Play play;
    private ArrayList<ArrayList<BufferedImage>> fruitImages;
    private final List<List<Fruit>> fruits;

    public ObjectManager(Play play)
    {
        this.play = play;
        loadImages();

        fruits = new ArrayList<>();

        misscellaneous.add(600);

        misscellaneous.add(700);
        misscellaneous.add(701);
        misscellaneous.add(702);
        misscellaneous.add(703);
        misscellaneous.add(704);
        misscellaneous.add(705);
        misscellaneous.add(706);
        misscellaneous.add(707);

        createFruits(0);
        createFruits(1);
        createFruits(2);
    }


    private void createFruits(int lvlIndex){
        List<Fruit> fruitsLevel = new ArrayList<>();
        for (int i = 700; i <= 707; ++i) {
            ArrayList<Point2D> fruitCoordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(i);
            for (int j = 0; j < fruitCoordinates.size(); ++j) {
                fruitsLevel.add(new Fruit(32 * (int) fruitCoordinates.get(j).getX(), 32 * (int) fruitCoordinates.get(j).getY(), i-700));
            }
        }
        fruits.add(fruitsLevel);
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
        for(Fruit f : fruits.get(play.getLevelHandler().getLevelIndex()))
        {
            if(f.isActive())
                f.update();
        }
    }

    public void draw(Graphics g, int xLevelOffset)
    {
        drawFruit(g, xLevelOffset);
        drawSpikes(g, xLevelOffset);

    }

    private void drawSpikes(Graphics g, int xLevelOffset) {
        for(Spike s : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getSpikes()) {
            if(s.objectType == METAL_SPIKE) {
                g.drawImage(Load.getImage(metalSpike), (int) (s.getCollisionBox().x - xLevelOffset),
                        (int) (s.getCollisionBox().y), 32, 32, null);
                s.drawCollisionBox(g, xLevelOffset);
            }
        }
    }

    private void drawFruit(Graphics g, int xLevelOffset) {
        for(Fruit f : fruits.get(play.getLevelHandler().getLevelIndex()))
        {
            if(f.isActive())
            {
                g.drawImage(fruitImages.get(f.objectType).get(f.getAnimationIndex()), (int)(f.getCollisionBox().x - f.getxOffset() - xLevelOffset),
                        (int)(f.getCollisionBox().y - f.getxOffset()), 55, 55, null);
                f.drawCollisionBox(g, xLevelOffset);
            }
        }
    }

    public void checkObjectTouched(Rectangle.Float collisionBox){
        for(Fruit f: fruits.get(play.getLevelHandler().getLevelIndex())){
            if(f.isActive()){
                if(collisionBox.intersects(f.collisionBox)){
                    f.setActive(false);
                    applyEffect(f);
                }
            }
        }
    }

    public void applyEffect(Fruit f) {
        switch (f.objectType) {
            case APPLE -> play.getLevelHandler().getCurrentLevel().setLevelScore(100);
            case BANANA -> play.getLevelHandler().getCurrentLevel().setLevelScore(150);
            case CHERRY, KIWI -> play.getLevelHandler().getCurrentLevel().setLevelScore(200);
            case ORANGE -> play.getLevelHandler().getCurrentLevel().setLevelScore(250);
            case MELON, PINEAPPLE -> play.getLevelHandler().getCurrentLevel().setLevelScore(300);
            case STRAWBERRY -> play.getLevelHandler().getCurrentLevel().setLevelScore(400);
        }
    }

    public void resetAll(int lvlIndex) {
        for(Fruit f: fruits.get(lvlIndex)){
            f.reset();
        }
        play.getLevelHandler().getCurrentLevel().resetScore();
    }
}
