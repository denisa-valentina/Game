package Graphics;

import Main.Game;

import java.awt.geom.Rectangle2D;

public class Check {

    // this class will contain some static methods that will check for us different things about our player, map etc.
    private static boolean isSolid(float x, float y, int[][] levelMatrix) {
        int maxWidth = levelMatrix[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth) // in afara mapei pe orizontala
        {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) // in afara mapei pe verticala
        {
            return true;
        }
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;
        int value = levelMatrix[(int) yIndex][(int) xIndex];
        if (value != 0 && value != 500) {
            return true;
        }
        return false;
    }

    public static boolean isCollision(float x, float y, float width, float height, int[][] levelMatrix) {
        int maxWidth = (levelMatrix[0].length-2) * Game.TILE_SIZE;
        if (x < 0 || x + width >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        int leftTile = (int) (x / Game.TILE_SIZE);
        int rightTile = (int) ((x + width) / Game.TILE_SIZE);
        int topTile = (int) (y / Game.TILE_SIZE);
        int bottomTile = (int) ((y + height) / Game.TILE_SIZE);

        // Iterăm prin fiecare tile care se intersectează cu rama entității
        for (int i = leftTile; i <= rightTile; i++) {
            for (int j = topTile; j <= bottomTile; j++) {
                // Verificăm dacă acest tile este solid și dacă se ciocnește cu rama caracterului
                if (isSolid(i * Game.TILE_SIZE, j * Game.TILE_SIZE, levelMatrix)) {
                    return true; // Avem coliziune
                }
            }
        }
        return false;
    }

    public static boolean isOnTheFloor(Rectangle2D.Float collisionBox, int[][] levelMatrix) {
        //bottom_left & bottom_right
        if (!isSolid(collisionBox.x, collisionBox.y + collisionBox.height + 1, levelMatrix))
            if (!isSolid(collisionBox.x + collisionBox.width, collisionBox.y + collisionBox.height + 1, levelMatrix)) {
                return false;
            }
        return true;
    }

    public static boolean isFloor(Rectangle2D.Float collisionBox, float xSpeed, int [][]levelMatrix)
    {
        return isSolid(collisionBox.x + xSpeed, collisionBox.y + collisionBox.height + 1, levelMatrix);
    }
}

