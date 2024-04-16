package Levels;

import LoadSave.Load;
import Main.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LevelHandler {

    private Game game;
    private BufferedImage []levelMap;
    private Level FirstLevel;
    public LevelHandler(Game game)
    {
        this.game = game;
        importMapSprites();
        FirstLevel = new Level(Load.getLevelMatrix());
    }

    private void importMapSprites() {
        BufferedImage image = Load.getImage(Load.mapTiles);
        levelMap = new BufferedImage[48];
        for(int j = 0; j < 4; ++j)
            for(int i=0; i < 12; ++i) {
                int index = j*12 + i;
                levelMap[index] = image.getSubimage(i*32, j*32, 32, 32);
            }
    }

    public void draw(Graphics obj)
    {
        for(int j=0;j<Game.HEIGHT_TILES;++j)
        {
            for(int i=0;i<Game.WIDTH_TILES;++i)
            {
                int index = FirstLevel.getTileIndex(i, j);
                obj.drawImage(levelMap[index], i*Game.TILE_SIZE, j*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, null);
            }
        }
    }

    public void update()
    {

    }

    public Level getLevel()
    {
        return FirstLevel;
    }

}
