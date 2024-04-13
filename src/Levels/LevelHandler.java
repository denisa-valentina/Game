package Levels;

import LoadSave.LoadSave;
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
        //levelMap = LoadSave.getMapSprites(LoadSave.firstMapSprites);
        importMapSprites();
        FirstLevel = new Level(LoadSave.getLevelData());
    }

    private void importMapSprites() {
        BufferedImage image = LoadSave.getImage(LoadSave.firstMapImage);
        levelMap = new BufferedImage[48];
        for(int j = 0; j < 4; ++j) // 9 sprites height
            for(int i=0; i < 12; ++i) { // 10 sprites wide
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

}
