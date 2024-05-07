package LevelMap;

import Main.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Graphics.Constants.GameCONST;

public class Level {

    private final Layer ground1Layer;
    private final Layer ground2Layer;
    private final Layer backGround1Layer;
    private final Layer backGround2Layer;

    public Level(Layer ground1Layer, Layer ground2Layer, Layer backGround1Layer, Layer backGround2Layer) {
        this.ground1Layer = ground1Layer;
        this.ground2Layer = ground2Layer;
        this.backGround1Layer = backGround1Layer;
        this.backGround2Layer = backGround2Layer;
    }

    public Layer getGround2Layer() {
        return ground2Layer;
    }

    public Layer getGround1Layer() { return ground1Layer; }

    public Layer getBackGround2Layer() { return backGround2Layer; }

    public Layer getBackGround1Layer()
    {
        return backGround1Layer;
    }

    public void draw(Graphics obj, BufferedImage[]levelMap, Layer layer, int xLevelOffset)
    {
        for(int j = 0; j< GameCONST.HEIGHT_TILES; ++j)
        {
            for(int i=0;i<layer.getLayerMatrix()[0].length;++i)
            {
                int index = layer.getTileIndex(i, j);
                if(index != 0 && index != 500) {
                    obj.drawImage(levelMap[index-1], i * Game.TILE_SIZE - xLevelOffset, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, null);
                }
            }
        }
    }


    // aceasta metoda va parcurge matricea layer-ului principal si va extrage coordonatele
    // la care am decis eu (notand pe matrice) locatiile unde se vor afla inamicii la initializarea
    // nivelului
    public ArrayList<Point2D> getEnemyCoordinates() {
        ArrayList<Point2D> enemyCoordinates = new ArrayList<Point2D>();

        for (int j = 0; j < GameCONST.HEIGHT_TILES; ++j) {
            for (int i = 0; i < ground1Layer.getLayerMatrix()[0].length; ++i) {
                int index = ground1Layer.getTileIndex(i, j);
                if (index == 500) {
                    enemyCoordinates.add(new Point2D.Double(i, j));
                }
            }
        }
        return enemyCoordinates;
    }
}
