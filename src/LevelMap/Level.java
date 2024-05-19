package LevelMap;

import Characters.Enemy;
import Load.Load;
import Main.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Graphics.Constants.GameCONST;

import static Graphics.Constants.*;

public class Level {

    private int levelScore;

    private final BufferedImage backGround;

    private BufferedImage[] levelTileMap;
    private final Layer groundLayer;
    private final Layer backGroundLayer;

    private final List<Enemy> enemies;

    public Level(String backGround, String tileMap, int rows, int cols, String groundLayer, String backGroundLayer) {
        levelScore = 0;

        this.backGround = Load.getImage(backGround);
        this.groundLayer = new Layer(groundLayer);
        this.backGroundLayer = new Layer(backGroundLayer);

        enemies = new ArrayList<>();

        importMapSprites(tileMap, rows, cols);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    private void importMapSprites(String tileMap, int rows, int cols) {
        BufferedImage image = Load.getImage(tileMap);

        levelTileMap = new BufferedImage[rows*cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int index = i * cols + j;
                levelTileMap[index] = image.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, BufferedImage[]levelMap, Layer layer, int xLevelOffset)
    {
        for(int j = 0; j< GameCONST.HEIGHT_TILES; ++j)
        {
            for(int i=0;i<layer.getLayerMatrix()[0].length;++i)
            {
                int index = layer.getTileIndex(i, j);
                if(index != 0 && !misscellaneous.contains(index)) {
                    g.drawImage(levelMap[index-1], i * Game.TILE_SIZE - xLevelOffset, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, null);
                }
            }
        }
    }

    public void drawLevel(Graphics g, int xLevelOffset){
        draw(g, levelTileMap, groundLayer, xLevelOffset);
        draw(g, levelTileMap, backGroundLayer, xLevelOffset);
    }


    // aceasta metoda va parcurge matricea layer-ului principal si va extrage coordonatele
    // la care am decis eu (notand pe matrice) locatiile unde se vor afla inamicii (si nu numai)
    // la initializarea nivelului
    public ArrayList<Point2D> getCoordinates(int value) {
        ArrayList<Point2D> elementCoordinates = new ArrayList<>();

        for (int j = 0; j < GameCONST.HEIGHT_TILES; ++j) {
            for (int i = 0; i <groundLayer.getLayerMatrix()[0].length; ++i) {
                int index = groundLayer.getTileIndex(i, j);
                if (index == value) {
                    elementCoordinates.add(new Point2D.Double(i, j));
                }
            }
        }
        return elementCoordinates;
    }

    public Layer getGroundLayer() {
        return groundLayer;
    }

    public BufferedImage getBackGround(){
        return backGround;
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }

    public int getLevelScore(){
        return levelScore;
    }

    public void setLevelScore(int levelScore){
        this.levelScore += levelScore;
    }

    public void resetScore(){
        this.levelScore = 0;
    }
}
