package Load;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Load {

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

    public static java.util.List<int[]> getImagesCoords(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        java.util.List<int[]> imageRegions = new java.util.ArrayList<>();
        // Indică dacă suntem în interiorul unei imagini
        boolean inImage = false;

        // Inițializări pentru coordonatele imaginii
        int startX = 0;
        int endX;
        int minHeight = h;  // Înălțimea minimă
        int maxHeight = 0;  // Înălțimea maximă
        int upperLeftX = 0;
        int upperLeftY = 0;
        int imageLenght = 0;
        int imageHeight = 0;
        // Iterează pe lățimea imaginii
        for (int i = 0; i < w; i++) {
            boolean isTransparent = true;
//            int startY = -1;
//            int endY = -1;

            // Verifică transparența pe toată înălțimea imaginii și reține startY și endY
            for (int j = 0; j < h; j++) {
                if ((image.getRGB(i, j) & 0xFF000000) != 0) // Non-transparență
                {
                    isTransparent = false;
                    if (maxHeight < j) {
                        maxHeight = j;
                    }
                    if (minHeight > j) {
                        minHeight = j;
                        upperLeftY = j;
                    }
                }
            }

            if (!inImage && !isTransparent) {
                // Am găsit marginea stângă a unei imagini
                startX = i;
                upperLeftX = startX;
                inImage = true;
            }

            if (inImage && isTransparent) {
                // Am găsit marginea dreaptă a unei imagini
                endX = i;
                imageLenght = endX - startX;
                imageHeight = maxHeight - minHeight;
                // if (upperLeftY < 0) upperLeftY = 0;
                // if (upperLeftX < 0) upperLeftX = 0;
                if (imageLenght < 0) imageLenght = 0;
                if (imageHeight < 0) imageHeight = 0;
                imageRegions.add(new int[]{upperLeftX, upperLeftY, imageLenght, imageHeight});
                inImage = false;
            }
        }

        // Dacă nu a fost un spațiu transparent la sfârșit
        if (inImage) {

            imageRegions.add(new int[]{upperLeftX - 1, upperLeftY, imageLenght, imageHeight});
        }

        return imageRegions;
    }

    public static List<List<BufferedImage>> getAnimations(List<BufferedImage> images) {
        List<List<BufferedImage>> animations = new ArrayList<>();
        for (BufferedImage image : images) {
            java.util.List<int[]> imageRegions = Load.getImagesCoords(image);
            java.util.List<BufferedImage> imagess = new java.util.ArrayList<>();
            for (int[] imageRegion : imageRegions) {
                imagess.add(image.getSubimage(imageRegion[0],
                        imageRegion[1],
                        imageRegion[2],
                        imageRegion[3]));
            }
           animations.add(imagess);
        }
        return animations;
    }
}