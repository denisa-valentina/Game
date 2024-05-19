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

    boolean lvl2Defeated = false;
    boolean lvl3Defeated = false;
    private final Play play;

    public EnemyManager(Play play) {
        this.play = play;

        Worm.loadAnimations();
        CandyZombie.loadAnimations();
        IceKing.loadAnimations();
        Marauder.loadAnimations();

        misscellaneous.add(500);
        misscellaneous.add(501);
        misscellaneous.add(1000);

        createEnemies();
        createBosses();
    }

    private void createWorms(int lvlIndex) {
        ArrayList<Point2D> coordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(500);
        for (Point2D coordinate : coordinates) {
            play.getLevelHandler().getLevel(lvlIndex).addEnemy(new Worm(32 * (int) coordinate.getX(), 32 * (int) coordinate.getY()));
        }
    }

    private void createZombies(int lvlIndex) {
        ArrayList<Point2D> coordinates = play.getLevelHandler().getLevel(lvlIndex).getCoordinates(501);
        for (Point2D coordinate : coordinates) {
            play.getLevelHandler().getLevel(lvlIndex).addEnemy(new CandyZombie(32 * (int) coordinate.getX(), 32 * (int) coordinate.getY()));
        }
    }

    private void createBosses() {
        ArrayList<Point2D> marauderLeaderCoordinates = play.getLevelHandler().getLevel(1).getCoordinates(1000);
        ArrayList<Point2D> iceKingCoordinates = play.getLevelHandler().getLevel(2).getCoordinates(1000);

        play.getLevelHandler().getLevel(2).addEnemy(new IceKing(32 * (int) iceKingCoordinates.getFirst().getX(), 32 * (int) iceKingCoordinates.getFirst().getY()));
        play.getLevelHandler().getLevel(1).addEnemy(new Marauder(32 * (int) marauderLeaderCoordinates.getFirst().getX(), 32 * (int) marauderLeaderCoordinates.getFirst().getY()));
    }


    private void createEnemies() {
        createWorms(0);
        createZombies(1);
        createWorms(2);
        createZombies(2);
    }

    public void update(int[][] levelMatrix, Player player) {
        for (Enemy e : play.getLevelHandler().getCurrentLevel().getEnemies()) {
            if (e.isActive()) {
                e.update(levelMatrix, player);
            } else {
                if (e instanceof IceKing)
                    lvl3Defeated = true;
                else if (e instanceof Marauder)
                    lvl2Defeated = true;
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset) { // de modificat in functie de nivel
        for (Enemy e : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) {
            if (e.isActive()) {
                switch (e) {
                    case Worm ignored -> {
                        BufferedImage image = Worm.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                        g.drawImage(image, (int) (e.collisionBox.x - worm_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - e.getAttacking() * (e.getHeight() / 2 - 3), e.getWidth() * e.walkDirection, e.getHeight(), null);
                    }
                    case CandyZombie ignored -> {
                        BufferedImage image = CandyZombie.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                        g.drawImage(image, (int) (e.collisionBox.x - zombie_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - 10 - e.getAttacking() * (e.getHeight() / 2 - 3), e.getWidth() * e.walkDirection, e.getHeight(), null);
                    }
                    case IceKing ignored -> {
                        BufferedImage image = IceKing.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                        g.drawImage(image, (int) (e.collisionBox.x - iceKing_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - iceKing_yOffset, IceKing_WIDTH * e.walkDirection, IceKing_HEIGHT, null);
                    }
                    case Marauder ignored -> {
                        BufferedImage image = Marauder.getEnemyAnimations().get(e.getAction()).get(e.getAnimationIndex());
                        g.drawImage(image, (int) (e.collisionBox.x - leaderMarauder_xOffset) - xLevelOffset + e.flipX(), (int) e.collisionBox.y - leaderMarauder_yOffset, LeaderMarauder_WIDTH * e.walkDirection, LeaderMarauder_HEIGHT, null);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + e);
                }
                e.drawCollisionBox(g, xLevelOffset);
                e.drawAttackBox(g, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy e : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) {
            if (e.isActive()) {
                if (attackBox.intersects(e.getCollisionBox())) {
                    e.hurt(1);
                    return;
                }
            }
        }
    }

    public void resetAll() {
        for (Enemy e : play.getLevelHandler().getLevel(play.getLevelHandler().getLevelIndex()).getEnemies()) {
            e.resetEnemy();
            e.resetValues();
        }
    }

    public boolean getLvl2Defeated() {
        return lvl2Defeated;
    }

    public boolean getLvl3Defeated() {
        return lvl3Defeated;
    }
}