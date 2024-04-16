package Levels;

public class Level {
    private int[][] levelMatrix;

    public Level(int[][] levelMatrix)
    {
        this.levelMatrix = levelMatrix;
        for(int j=0;j<levelMatrix.length;++j)
        {
            for(int i=0;i<levelMatrix[j].length;++i)
            {
                System.out.print(getTileIndex(i, j) + " ");
            }
            System.out.println();
        }
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
