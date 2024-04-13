package LoadSave;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String firstMapImage = "/level_map/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "/level_map/level_one_data.png";

    public static BufferedImage getImage(String sourceName) {

        BufferedImage image = null;
        InputStream input = LoadSave.class.getResourceAsStream(sourceName);

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

    public static int[][] getLevelData()
    {
        int [][] levelData = new int[Game.HEIGHT_TILES][Game.WIDTH_TILES];
        BufferedImage image = getImage(LEVEL_ONE_DATA);

        for(int j=0;j<image.getHeight();++j)
        {
            for(int i=0;i<image.getWidth();++i)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                levelData[j][i] = value;
            }
        }
        return levelData;
    }
}
