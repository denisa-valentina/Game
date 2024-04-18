package LevelMap;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import Graphics.Constants.GameCONST;

public class Level {

    private Layer groundLayer;
    private Layer ground2Layer;
    private Layer backGroundLayer;

    public Level(Layer groundLayer, Layer ground2Layer, Layer backGroundLayer) {
        this.groundLayer = groundLayer;
        this.ground2Layer = ground2Layer;
        this.backGroundLayer = backGroundLayer;
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

    public void draw(Graphics obj, BufferedImage[]levelMap, Layer layer, int xLevelOffset)
    {
        for(int j = 0; j< GameCONST.HEIGHT_TILES; ++j)
        {
            for(int i=0;i<layer.getLayerMatrix()[0].length;++i)
            {
                int index = layer.getTileIndex(i, j);
                if(index != 0) {
                    obj.drawImage(levelMap[index-1], i * Game.TILE_SIZE - xLevelOffset, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, null);
                }
            }
        }
    }
}
