package LevelMap;

import GameStates.GameState;
import GameStates.Play;
import Graphics.Constants.LevelLayers;

import java.awt.Graphics;
import java.util.ArrayList;

public class LevelHandler {

    private final ArrayList<Level> Levels;
    private int levelIndex = 0;

    public LevelHandler() {
        Levels = new ArrayList<>();

        initLevels();
    }

    private void initLevels() {
        Levels.add(new Level(LevelLayers.firstLevelGameBackGround, LevelLayers.firstMapTiles, 9, 20, LevelLayers.lvl1Ground,
                 LevelLayers.lvl1backGround));
        // init enemies
        Levels.add(new Level(LevelLayers.secondLevelGameBackGround, LevelLayers.secondMapTiles, 7, 16, LevelLayers.lvl2Ground,
                 LevelLayers.lvl2backGround));
        Levels.add(new Level(LevelLayers.thirdLevelGameBackGround, LevelLayers.thirdMapTiles, 9, 20, LevelLayers.lvl3Ground,
                 LevelLayers.lvl3backGround));
    }

    public void loadNextLevel(){
        levelIndex++;
        if(levelIndex >= Levels.size()){
            // game completed
            levelIndex = 0;
            Play.getPlayer().increaseScore(0);
            GameState.state = GameState.MENU;
        }

        Level nextLevel = Levels.get(levelIndex);
        Play.getPlayer().loadLevelMatrix(nextLevel.getGroundLayer().getLayerMatrix());
        //Play.getPlayer().setScore(getCurrentLevel().getLevelScore()); // setarea noului scor
    }

    public void loadGame(int levelIndex, int health, int score, int xPos, int yPos){
        this.levelIndex = levelIndex;

        Level level = Levels.get(levelIndex);
        Play.getPlayer().loadLevelMatrix(level.getGroundLayer().getLayerMatrix());

        Play.getPlayer().getCollisionBox().x = xPos;
        Play.getPlayer().getCollisionBox().y = yPos;

        Play.getPlayer().setScore(score);
        Play.getPlayer().setHealth(health);
    }

    public void draw(Graphics g, int xLevelOffset)
    {
            Levels.get(levelIndex).drawLevel(g, xLevelOffset);
    }

    public Level getLevel(int index)
    {
        return Levels.get(index);
    }

    public int getLevelIndex(){
        return levelIndex;
    }

    public Level getCurrentLevel(){
        return Levels.get(levelIndex);
    }
}
