package Levels;

public class Level {
    private int[][] levelMatrix;

    public Level(int[][] levelMatrix)
    {
        this.levelMatrix = levelMatrix;
    }

    public int[][] getLevelMatrix()
    {
        return levelMatrix;
    }

    public int getTileIndex(int x, int y)
    {
        return levelMatrix[y][x];
    }
}
