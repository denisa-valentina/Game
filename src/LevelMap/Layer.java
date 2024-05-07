package LevelMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Layer {
    private int[][] layerMatrix;
    public Layer(String sourceFile)
    {
        //open the file with the scanner
        File scoreFile = new File(sourceFile);
        try {
            Scanner scanFile = new Scanner(scoreFile);

            int numRows = scanFile.nextInt();
            int numCol = scanFile.nextInt();
            layerMatrix = new int[numRows][numCol];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    layerMatrix[i][j] = scanFile.nextInt();
                }
            }
            scanFile.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    public int[][] getLayerMatrix()
    {
        return layerMatrix;
    }


    public int getTileIndex(int x, int y)
    {
        return layerMatrix[y][x];
    }
}
