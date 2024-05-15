package Characters;

import GameStates.Play;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.misscellaneous;

public class EnemyManager {

    private Play play;

    public EnemyManager(Play play) {
        this.play = play;
        Worm.loadAnimations();
        CandyZombie.loadAnimations();

        misscellaneous.add(500);
        misscellaneous.add(501);

        createEnemies();

    }

    private void createWorms(int lvlIndex){
        ArrayList<Point2D> coordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(500);
        for (Point2D coordinate : coordinates) {
            play.getLevelHandler().getLevel(lvlIndex).addEnemy(new Worm(32 * (int) coordinate.getX(), 32 * (int) coordinate.getY()));
        }
    }

    private void createZombies(int lvlIndex){
        ArrayList<Point2D> coordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(501);
        for (Point2D coordinate : coordinates) {
            play.getLevelHandler().getLevel(lvlIndex).addEnemy(new CandyZombie(32 * (int) coordinate.getX(), 32 * (int) coordinate.getY()));
        }
    }


    private void createEnemies() {
        // lvl 1
        createWorms(0);
        createZombies(1);
        createWorms(2);
        createZombies(2);
    }

    public void update(int [][]levelMatrix, Player player) {
        boolean isAnyActive = false;

        for (Enemy e : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) { // de modificat in functie de nivel
            if (e.isActive()) {
                e.update(levelMatrix, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive)
        {
            play.setLevelCompleted(true);
        }
    }

    private void drawWorms(Graphics obj, int xLevelOffset) { // de modificat in functie de nivel
        for(Enemy e: play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies())
        {
            if(e.isActive()) {
                if(e instanceof Worm) {
                    BufferedImage image = Worm.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                    obj.drawImage(image, (int) (e.collisionBox.x - worm_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - e.getAttacking() * (e.getHeight() / 2 - 3), e.getWidth() * e.walkDirection, e.getHeight(), null);
                    e.drawCollisionBox(obj, xLevelOffset);
                    e.drawAttackBox(obj, xLevelOffset);
                }
                 if(e instanceof CandyZombie) {
                    BufferedImage image = CandyZombie.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                    obj.drawImage(image, (int) (e.collisionBox.x - zombie_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - 10 - e.getAttacking() * (e.getHeight() / 2 - 3), e.getWidth() * e.walkDirection, e.getHeight(), null);
                    e.drawCollisionBox(obj, xLevelOffset);
                    e.drawAttackBox(obj, xLevelOffset);
                }
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