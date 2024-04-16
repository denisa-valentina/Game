package LoadSave;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Load {

    public static final String mapTiles = "/level_map/TileMap.png";
    public static final String firstLevel = "/level_map/level_one_data.png";

    public static BufferedImage getImage(String sourceName) {

        BufferedImage image = null;
        InputStream input = Load.class.getResourceAsStream(sourceName);

        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static int[][] getLevelMatrix()
    {
        int [][] levelMatrix = new int[Game.HEIGHT_TILES][Game.WIDTH_TILES];
        BufferedImage image = getImage(firstLevel);

        for(int j=0;j<image.getHeight();++j)
        {
            for(int i=0;i<image.getWidth();++i)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                levelMatrix[j][i] = value;
            }
        }
        return levelMatrix;
    }
}