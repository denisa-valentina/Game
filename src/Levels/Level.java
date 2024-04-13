package Levels;

public class Level {
    private int[][] levelData;

    public Level(int[][] levelData)
    {
        this.levelData = levelData;
    }

    public int getTileIndex(int x, int y)
    {
        return levelData[y][x];
    }
}
