package LevelMap;

import Load.Load;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Graphics.Constants.UI.Images.*;

public class LevelHandler {

    //private final Game game;
    private BufferedImage []levelMap;
    private final Level FirstLevel;

    // public LevelHandler(Game game)
    public LevelHandler() {
        //this.game = game;
        importMapSprites();
        Layer ground1Layer = new Layer("resources/level_map/ground1.txt");
        Layer ground2Layer = new Layer("resources/level_map/ground2.txt");
        Layer backGround1Layer = new Layer("resources/level_map/background1.txt");
        Layer backGround2Layer = new Layer("resources/level_map/background2.txt");

        FirstLevel = new Level(ground1Layer, ground2Layer, backGround1Layer, backGround2Layer);
    }

    private void importMapSprites() {
        BufferedImage image = Load.getImage(mapTiles);

        levelMap = new BufferedImage[180];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 20; ++j) {
                int index = i * 20 + j;
                levelMap[index] = image.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics obj, int xLevelOffset)
    {
        FirstLevel.draw(obj, levelMap, FirstLevel.getBackGround1Layer(), (int)(0.97*xLevelOffset));
        FirstLevel.draw(obj, levelMap, FirstLevel.getBackGround2Layer(), xLevelOffset);
        FirstLevel.draw(obj, levelMap, FirstLevel.getGround1Layer(), xLevelOffset);
        FirstLevel.draw(obj, levelMap, FirstLevel.getGround2Layer(), (int)(1.2*xLevelOffset));
    }

    public Level getLevel()
    {
        return FirstLevel;
    }

    //public void update() {}
}
