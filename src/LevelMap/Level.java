package LevelMap;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level {

    private Layer groundLayer;
    private Layer ground2Layer;
    private Layer backGroundLayer;

    public Level(Layer groundLayer, Layer ground2Layer, Layer backGroundLayer) {
        this.groundLayer = groundLayer;
        this.ground2Layer = ground2Layer;
        this.backGroundLayer = backGroundLayer;

//        for(int j=0;j<levelMatrix.length;++j)
//        {
//            for(int i=0;i<levelMatrix[j].length;++i)
//            {
//                System.out.print(getTileIndex(i, j) + " ");
//            }
//            System.out.println();
//        }
    }

    public Layer getGround2Layer() {
        return ground2Layer;
    }

    public Layer getGroundLayer()
    {
        return groundLayer;
    }

    public Layer getBackGroundLayer()
    {
        return backGroundLayer;
    }

    public void draw(Graphics obj, BufferedImage[]levelMap, Layer layer)
    {
        for(int j = 0; j< Game.HEIGHT_TILES; ++j)
        {
            for(int i=0;i<Game.WIDTH_TILES;++i)
            {
                int index = layer.getTileIndex(i, j);
                if(index != 0) {
                    obj.drawImage(levelMap[index-1], i * Game.TILE_SIZE, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, null);
                }
            }
        }
    }
}
