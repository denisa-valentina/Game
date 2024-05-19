package Objects;

import GameStates.Play;

import static Graphics.Constants.*;
import static Graphics.Constants.Fruits.FruitsConstants.*;
import static Graphics.Constants.Fruits.Images.*;
import static Graphics.Constants.LifeStatus.fullHeart;
import static Graphics.Constants.Surroundings.Images.metalSpike;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Load.Load;

public class ObjectManager {
    private final Play play;

    private List<BufferedImage> doorImages;
    private ArrayList<ArrayList<BufferedImage>> fruitImages;
    private BufferedImage heartImage;

    private final List<List<Fruit>> fruits;
    private final List<Door> doors;
    private final List<List<Heart>> hearts;

    public ObjectManager(Play play) {
        this.play = play;
        loadImages();

        fruits = new ArrayList<>();
        doors = new ArrayList<>();
        hearts = new ArrayList<>();

        misscellaneous.add(400); // door

        misscellaneous.add(600); // traps

        misscellaneous.add(700); // fruits
        misscellaneous.add(701);
        misscellaneous.add(702);
        misscellaneous.add(703);
        misscellaneous.add(704);
        misscellaneous.add(705);
        misscellaneous.add(706);
        misscellaneous.add(707);
        misscellaneous.add(708); // heart

        initHeart(0);
        initHeart(1);
        initHeart(2);

        initDoor(0);
        initDoor(1);
        initDoor(2);

        initFruits(0);
        initFruits(1);
        initFruits(2);
    }

    private void initHeart(int lvlIndex) {
        List<Heart> heartsLevel = new ArrayList<>();
        ArrayList<Point2D> heartCoordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(708);
        for (Point2D heartCoordinate : heartCoordinates) {
            heartsLevel.add(new Heart(32 * (int) heartCoordinate.getX(), 32 * (int) heartCoordinate.getY(), 8));
        }
        hearts.add(heartsLevel);
    }


    private void initFruits(int lvlIndex) {
        List<Fruit> fruitsLevel = new ArrayList<>();
        for (int i = 700; i <= 707; ++i) {
            ArrayList<Point2D> fruitCoordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(i);
            for (Point2D fruitCoordinate : fruitCoordinates) {
                fruitsLevel.add(new Fruit(32 * (int) fruitCoordinate.getX(), 32 * (int) fruitCoordinate.getY(), i - 700));
            }
        }
        fruits.add(fruitsLevel);
    }

    private void initDoor(int lvlIndex) {
        ArrayList<Point2D> doorCoordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(400);
        doors.add(new Door(32 * (int) doorCoordinates.getFirst().getX(), 32 * (int) doorCoordinates.getFirst().getY(), 400));
    }

    private void loadImages() {
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
        doorImages = new ArrayList<>();

        for (int i = 0; i < images.size(); ++i) {
            ArrayList<BufferedImage> fruitImage = new ArrayList<>();
            for (int j = 0; j < 17; ++j) {
                fruitImage.add(images.get(i).getSubimage(32 * j, 0, 32, 32));
            }
            fruitImages.add(fruitImage);
        }

        BufferedImage image = Load.getImage(Surroundings.Images.door);

        java.util.List<int[]> imageRegions = Load.getImagesCoords(image);
        for (int[] imageRegion : imageRegions) {
            doorImages.add(image.getSubimage(imageRegion[0],
                    imageRegion[1],
                    imageRegion[2],
                    imageRegion[3]));
        }
        heartImage = Load.getImage(fullHeart);
    }

    public void update() {
        for (Fruit f : fruits.get(play.getLevelHandler().getLevelIndex())) {
            if (f.isActive())
                f.update();
        }
        if (doors.get(play.getLevelHandler().getLevelIndex()).isActive()){
            doors.get(play.getLevelHandler().getLevelIndex()).update(play.getLevelHandler().getLevelIndex(), play.getEnemyManager());
        } else {
            play.setLevelCompleted(true);
        }

        if (!hearts.get(play.getLevelHandler().getLevelIndex()).isEmpty()) {
            for (Heart h : hearts.get(play.getLevelHandler().getLevelIndex())) {
                if (h.isActive()) {
                    h.update();
                }
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset)
    {
        drawFruit(g, xLevelOffset);
        drawSpikes(g, xLevelOffset);
        drawDoor(g, xLevelOffset);
        drawHearts(g, xLevelOffset);
    }

    private void drawHearts(Graphics g, int xLevelOffset) {
        if(play.getLevelHandler().getLevelIndex() > 0) {
            for (Heart h : hearts.get(play.getLevelHandler().getLevelIndex())) {
                if (h.isActive()) {
                    g.drawImage(heartImage, (int) (h.getCollisionBox().x - h.getxOffset() - xLevelOffset),
                            (int) (h.getCollisionBox().y - h.getxOffset()), 32, 32, null);
                    h.drawCollisionBox(g, xLevelOffset);
                }
            }
        }
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

    private void drawDoor(Graphics g, int xLevelOffset) {
        {
            int currentIndex = play.getLevelHandler().getLevelIndex();
            int animIndex = doors.get(currentIndex).isActive() ? doors.get(currentIndex).getAnimationIndex() : 4;
            g.drawImage(doorImages.get(animIndex), (int) (doors.get(currentIndex).getCollisionBox().x - doors.get(currentIndex).getxOffset() - xLevelOffset),
                    (int) (doors.get(currentIndex).getCollisionBox().y - doors.get(currentIndex).getxOffset()), 75, 98, null);
            doors.get(currentIndex).drawCollisionBox(g, xLevelOffset);
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
        for(Heart h: hearts.get(play.getLevelHandler().getLevelIndex())){
            if(h.isActive()){
                if(collisionBox.intersects(h.collisionBox)){
                    h.setActive(false);
                    applyEffect(h);
                }
            }
        }
    }

    public void applyEffect(GameObject f) {
        switch (f.objectType) {
            case APPLE -> play.getLevelHandler().getCurrentLevel().setLevelScore(100);
            case BANANA -> play.getLevelHandler().getCurrentLevel().setLevelScore(150);
            case CHERRY, KIWI -> play.getLevelHandler().getCurrentLevel().setLevelScore(200);
            case ORANGE -> play.getLevelHandler().getCurrentLevel().setLevelScore(250);
            case MELON, PINEAPPLE -> play.getLevelHandler().getCurrentLevel().setLevelScore(300);
            case STRAWBERRY -> play.getLevelHandler().getCurrentLevel().setLevelScore(400);
            case HEART -> Play.getPlayer().updateHealth(1);
        }
    }

    public void resetAll(int lvlIndex) {
        for(Fruit f: fruits.get(lvlIndex)){
            f.reset();
        }

        doors.get(lvlIndex).reset();
        doors.get(lvlIndex).setOpen(false);
        play.getLevelHandler().getCurrentLevel().resetScore();

    }


}
