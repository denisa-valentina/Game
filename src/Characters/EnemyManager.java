package Characters;

import GameStates.Play;
import Load.Load;
import Graphics.Constants.Enemy.Images;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Graphics.Constants.misscellaneous;

public class EnemyManager {

    private Play play;
    private java.util.List<java.util.List<BufferedImage>> animationsGreen;
    private java.util.List<BufferedImage> animationsYellow;
    private ArrayList<Worm> worms;


    public EnemyManager(Play play) {
        this.play = play;
        loadAnimations();

        worms = new ArrayList<>();

        misscellaneous.add(500);

        ArrayList<Point2D> enemyCoordinates = play.getLevelHandler().getLevel().getCoordinates(500);

        for (int i=0;i<enemyCoordinates.size(); ++i)
        {
            worms.add(new Worm(32*(int)enemyCoordinates.get(i).getX(), 32*(int)enemyCoordinates.get(i).getY()));
        }

    }

    public void update(int [][]levelMatrix, Player player) {
        for (Worm worm : worms) {
            if (worm.isActive()) {
                worm.update(levelMatrix, player);
            }
        }
    }

    public void draw(Graphics obj, int xLevelOffset) {
        drawWorms(obj, xLevelOffset);
    }

    private void drawWorms(Graphics obj, int xLevelOffset) {
        for(Worm worm: worms)
        {
            if(worm.isActive()) {
                BufferedImage image = animationsGreen.get(worm.getEnemyAction()).get(worm.getAnimationIndex());
                obj.drawImage(image, (int) (worm.collisionBox.x) - xLevelOffset + worm.flipX(), (int) worm.collisionBox.y - worm.getAttacking() * (worm.getHeight()/2 - 3), worm.getWidth() * worm.walkDirection, worm.getHeight(), null);
                worm.drawCollisionBox(obj, xLevelOffset);
                worm.drawAttackBox(obj, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Worm worm: worms) {
            if (worm.isActive()) {
                if (attackBox.intersects(worm.getCollisionBox())) {
                    worm.hurt(1);
                    return;
                }
            }
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

    public void resetAll() {
        for(Worm worm: worms) {
            worm.resetEnemy();
            worm.resetValues();
        }
    }
}

