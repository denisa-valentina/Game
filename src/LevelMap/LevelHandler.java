package LevelMap;

import LoadSave.Load;
import Main.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LevelHandler {

    private Game game;
    private BufferedImage []levelMap;
    private Level FirstLevel;

    public LevelHandler(Game game) {
        this.game = game;
        importMapSprites();
        Layer groundLayer = new Layer("resources/level_map/firstLevelGround.txt");
        Layer ground2Layer = new Layer("resources/level_map/firstLevelGround2.txt");
        Layer backGroundLayer = new Layer("resources/level_map/firstLevelBackground.txt");

        FirstLevel = new Level(groundLayer, ground2Layer, backGroundLayer);
    }

    private void importMapSprites() {
        BufferedImage image = Load.getImage(Load.mapTiles);

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
        FirstLevel.draw(obj, levelMap, FirstLevel.getGroundLayer(), xLevelOffset);
        FirstLevel.draw(obj, levelMap, FirstLevel.getGround2Layer(), xLevelOffset);
        FirstLevel.draw(obj, levelMap, FirstLevel.getBackGroundLayer(), xLevelOffset);
    }

    public Level getLevel()
    {
        return FirstLevel;
    }

    public void update() {}
}