package LevelMap;

import GameStates.GameState;
import GameStates.Play;
import Graphics.Constants.LevelLayers;
import Main.Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class LevelHandler {

    private final Game game;
    private ArrayList<Level> Levels;
    private int levelIndex = 2;

    // public LevelHandler(Game game)
    public LevelHandler(Game game) {
        this.game = game;
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
            System.out.println("Congratulations! You have finished the game!");
            GameState.state = GameState.MENU;
        }

        Level nextLevel = Levels.get(levelIndex);
        Play.getPlayer().loadLevelMatrix(nextLevel.getGroundLayer().getLayerMatrix());
        Play.getPlayer().setScore(getCurrentLevel().getLevelScore()); // setarea noului scor
        //game.getPlay().setLevelOffset(nextLevel.getMaxLevelOffsetX());
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

    public int getLevelsNumber(){
        return Levels.size();
    }

    public Level getCurrentLevel(){
        return Levels.get(levelIndex);
    }

    //public void update() {}
}
