package Characters;

import GameStates.Play;
import Graphics.Constants;
import Load.Load;
import Graphics.Constants.Enemy.Images;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.Enemy.worm_xOffset;
import static Graphics.Constants.misscellaneous;

public class EnemyManager {

    private Play play;
    private List<List<BufferedImage>> animationsGreen;
    private java.util.List<BufferedImage> animationsYellow;

    public EnemyManager(Play play) {
        this.play = play;
        loadAnimations();

        misscellaneous.add(500);

        createEnemies();

    }

    private void createEnemies() {
        ArrayList<Point2D> wormCoordinates = play.getLevelHandler().getLevel(0).getCoordinates(500); // lvl 1 worms
        for (int i=0;i<wormCoordinates.size(); ++i)
        {
            play.getLevelHandler().getLevel(0).addEnemy(new Worm(32*(int)wormCoordinates.get(i).getX(), 32*(int)wormCoordinates.get(i).getY()));
        }
        wormCoordinates = play.getLevelHandler().getLevel(2).getCoordinates(500); // lvl 3 worms
        for (int i=0;i<wormCoordinates.size(); ++i)
        {
            play.getLevelHandler().getLevel(2).addEnemy(new Worm(32*(int)wormCoordinates.get(i).getX(), 32*(int)wormCoordinates.get(i).getY()));
        }
    }

    private void loadAnimations() {
        java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        // 4 animations: Idle, Run, Attack, Dead (Hurt)
        images.add(Load.getImage(Images.worm_idle));
        images.add(Load.getImage(Images.worm_run));
        images.add(Load.getImage(Images.worm_attack));
        images.add(Load.getImage(Images.worm_dead));
        animationsGreen = new java.util.ArrayList<>();

        for (BufferedImage image : images) {
            java.util.List<int[]> imageRegions = Load.getImagesCoords(image);
            java.util.List<BufferedImage> imagess = new java.util.ArrayList<>();
            for (int[] imageRegion : imageRegions) {
                imagess.add(image.getSubimage(imageRegion[0],
                        imageRegion[1],
                        imageRegion[2],
                        imageRegion[3]));
            }
            animationsGreen.add(imagess);
        }
    }

    public void update(int [][]levelMatrix, Player player) {
        for (Enemy e : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) { // de modificat in functie de nivel
            if (e.isActive()) {
                e.update(levelMatrix, player);
            }
        }
    }

    private void drawWorms(Graphics obj, int xLevelOffset) { // de modificat in functie de nivel
        for(Enemy e: play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies())
        {
            if(e.isActive()) {
                BufferedImage image = animationsGreen.get(e.getEnemyAction()).get(e.getAnimationIndex());
                obj.drawImage(image, (int)(e.collisionBox.x - worm_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - e.getAttacking() * (e.getHeight()/2 - 3), e.getWidth() * e.walkDirection, e.getHeight(), null);
                e.drawCollisionBox(obj, xLevelOffset);
                //e.drawAttackBox(obj, xLevelOffset);
            }
        }
    }

    public void draw(Graphics obj, int xLevelOffset) { // de modificat in functie de nivel
        drawWorms(obj, xLevelOffset);
        //draw idk
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Enemy e: play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) {
            if (e.isActive()) {
                if (attackBox.intersects(e.getCollisionBox())) {
                    e.hurt(1);
                    return;
                }
            }
        }
    }

    public void resetAll() {
        for(Enemy e: play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) {
            e.resetEnemy();
            e.resetValues();
        }
    }
}