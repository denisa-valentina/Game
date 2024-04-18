package LoadSave;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Load {

    public static final String mapTiles = "/level_map/TileMap.png";

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
}