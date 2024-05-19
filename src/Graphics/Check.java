package Graphics;

import Main.Game;

import java.awt.geom.Rectangle2D;

import static Graphics.Constants.misscellaneous;

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

        return isTileSolid((int)xIndex, (int)yIndex, levelMatrix);
    }

    public static boolean isTileSolid(int xIndex, int yIndex, int [][]levelMatrix) {
        int value = levelMatrix[yIndex][xIndex];
        return value != 0 && !misscellaneous.contains(value);
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
            return isSolid(collisionBox.x + collisionBox.width, collisionBox.y + collisionBox.height + 1, levelMatrix);
        return true;
    }

    public static boolean isEnemyOnTheFloor(Rectangle2D.Float collisionBox, float xSpeed, int [][]levelMatrix)
    {
        if(xSpeed > 0)
            return isSolid(collisionBox.x + collisionBox.width + xSpeed, collisionBox.y + collisionBox.height + 1, levelMatrix);
        else
            return isSolid(collisionBox.x + xSpeed, collisionBox.y + collisionBox.height + 1, levelMatrix);
    }

    // checks if there are obstacles between 2 objects
    public static boolean ClearSight(int[][] levelMatrix, Rectangle2D.Float collisionBox1, Rectangle2D.Float collisionBox2, int yTile) {
        int xTile1 = (int) collisionBox1.x / Game.TILE_SIZE;
        int xTile2 = (int) collisionBox2.x / Game.TILE_SIZE;

        if (xTile1 > xTile2) {
            for (int i = xTile2; i < xTile1; ++i) {
                if (isTileSolid(i, yTile, levelMatrix)) {
                    return false;
                }
                if (levelMatrix[yTile + 1][i] == 0) // daca obstacolul dintre player si enemy este o prapastie/groapa
                {
                    return false;
                }
            }
        } else {
            for (int i = xTile1; i < xTile2; ++i) {
                if (isTileSolid(i, yTile, levelMatrix)) {
                    return false;
                }
                if (levelMatrix[yTile + 1][i] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILE_SIZE;
    }
}