package Graphics;

import java.util.ArrayList;

public class Constants {
    public static final int ANIMATION_SPEED_OBJECTS = 8;
    public static final float GRAVITY = 0.02f * Constants.GameCONST.SCALE;

    public static final int METAL_SPIKE = 0;

    public static final ArrayList<Integer> misscellaneous = new ArrayList<>();

    public static class Fruits {

        public static class Images {
            public static final String apple = "/fruits/Apple.png";
            public static final String banana = "/fruits/Bananas.png";
            public static final String cherry = "/fruits/Cherries.png";
            public static final String kiwi = "/fruits/Kiwi.png";
            public static final String melon = "/fruits/Melon.png";
            public static final String orange = "/fruits/Orange.png";
            public static final String pineapple = "/fruits/Pineapple.png";
            public static final String strawberry = "/fruits/Strawberry.png";
        }

        public static class FruitsConstants {
            public static final int APPLE = 0;
            public static final int BANANA = 1;
            public static final int CHERRY = 2;
            public static final int KIWI = 3;
            public static final int MELON = 4;
            public static final int ORANGE = 5;
            public static final int PINEAPPLE = 6;
            public static final int STRAWBERRY = 7;
        }

    }

    public static class LifeStatus {
        public static final String staturBar = "/status/bar.png";
        public static final String fullHeart = "/status/heartFull.png";
        public static final String emptyHeart = "/status/heartEmpty.png";

        public static final int HEART_WIDTH_DEFAULT = 53; // deocamdata
        public static final int HEART_HEIGHT_DEFAULT = 45;
        public static final int HEART_WIDTH = (int) (HEART_WIDTH_DEFAULT * GameCONST.SCALE);
        public static final int HEART_HEIGHT = (int) (HEART_HEIGHT_DEFAULT * GameCONST.SCALE);
    }

    public static class Surroundings {

        public static class Images {
            public static final String bigClouds = "/level_map/big_clouds.png";
            public static final String smallClouds = "/level_map/small_clouds.png";
            public static final String metalSpike = "/level_map/metal_spike.png";
            public static final String woodSpike = "/level_map/wood_spike.png";
        }

        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * GameCONST.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * GameCONST.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * GameCONST.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * GameCONST.SCALE);
    }

    public static class GameCONST {
        public final static int UPS = 200; // updates/second
        public final static int FPS = 120; // frames/second
        public final static int TILE_DEFAULT_SIZE = 32;
        public final static float SCALE = 1f;
        public final static int WIDTH_TILES = 50;
        public final static int HEIGHT_TILES = 25;
    }

    public static class UI {

        public static class Images {
            public static final String menuButtons = "/UI/Menu/MenuButtons.png";
            public static final String menuImage = "/UI/Menu/image.png";
            public static final String menuBackGround = "/UI/Menu/menuBackground.png";
            public static final String pauseImage = "/UI/Pause/pause.png";
            public static final String soundButton = "/UI/Pause/soundButton.png";
            public static final String musicButton = "/UI/Pause/musicButton.png";
            public static final String otherButtons = "/UI/otherButtons.png";
            public static final String completedLevelImage = "/UI/CompletedLevel/CompletedLevel1.png";
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 32;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * GameCONST.SCALE);
        }

        public static class Buttons {
            public static final int DEFAULT_BUTTON_WIDTH = 144; // pixels
            public static final int DEFAULT_BUTTON_HEIGHT = 72;
            public static final int BUTTON_WIDTH = (int) (DEFAULT_BUTTON_WIDTH * GameCONST.SCALE);
            public static final int BUTTON_HEIGHT = (int) (DEFAULT_BUTTON_HEIGHT * GameCONST.SCALE);
        }
    }

    public static class LevelLayers {

        public static final String firstMapTiles = "/level_map/level/1/TileMap.png";
        public static final String secondMapTiles = "/level_map/level/2/TiledMap.png";
        public static final String thirdMapTiles = "/level_map/level/3/TileMap.png";

        public static final String firstLevelGameBackGround = "/level_map/level/1/levelWallpaper.jpg";
        public static final String secondLevelGameBackGround = "/level_map/level/2/levelWallpaper.jpg";
        public static final String thirdLevelGameBackGround = "/level_map/level/3/levelWallpaper.jpg";

        public static final String lvl1Ground = "resources/level_map/level/1/ground.txt";
        public static final String lvl1backGround = "resources/level_map/level/1/background.txt";

        public static final String lvl2Ground = "resources/level_map/level/2/ground.txt";
        public static final String lvl2backGround = "resources/level_map/level/2/background.txt";

        public static final String lvl3Ground = "resources/level_map/level/3/ground.txt";
        public static final String lvl3backGround = "resources/level_map/level/3/background.txt";
    }

    public static class Player {

        public static class Images {
            public static final String player_idle = "/player/Idle.png";
            public static final String player_run = "/player/Run.png";
            public static final String player_jump = "/player/Jump.png";
            public static final String player_dead = "/player/Dead.png";
            public static final String player_attack1 = "/player/Attack_1.png";
            public static final String player_attack2 = "/player/Attack_2.png";
            public static final String player_hurt = "/player/Hurt.png";
        }

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int DEAD = 3;
        public static final int ATTACK_1 = 4;
        public static final int ATTACK_2 = 5;
        public static final int HURT = 6;

        public static int getSpriteAmount(int action) {

            switch (action) {
                case IDLE, RUN -> {
                    return 8;
                }
                case JUMP, DEAD, ATTACK_1, ATTACK_2 -> {
                    return 10;
                }
                case HURT -> {
                    return 2;
                }
                default -> {
                    return 0;
                }
            }
        }
    }

    public static class Enemy {
        public static class Images {
            public static final String worm_idle = "/enemies/worm/worm_idle.png";
            public static final String worm_run = "/enemies/worm/worm_run.png";
            public static final String worm_attack = "/enemies/worm/worm_attack.png";
            public static final String worm_dead = "/enemies/worm/worm_hurt.png";

            public static final String zombie_idle = "/enemies/candy_zombie/zombie_idle.png";
            public static final String zombie_run = "/enemies/candy_zombie/zombie_run.png";
            public static final String zombie_attack = "/enemies/candy_zombie/zombie_attack.png";
            public static final String zombie_dead = "/enemies/candy_zombie/zombie_hurt.png";

        }

        public static class Type {
            public static final int WORM = 0;
            public static final int CANDY_ZOMBIE = 1;
        }

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 4;
        public static final int DEAD = 3;

        public static final int Worm_WIDTH_DEFAULT = 40;
        public static final int Worm_HEIGHT_DEFAULT = 33;
        public static final int worm_WIDTH = (int)(Worm_WIDTH_DEFAULT * GameCONST.SCALE);
        public static final int worm_HEIGHT = (int)(Worm_HEIGHT_DEFAULT * GameCONST.SCALE);

        public static final int worm_xOffset = (int)(3 * GameCONST.SCALE);

        public static final int Zombie_WIDTH_DEFAULT = 55;
        public static final int Zombie_HEIGHT_DEFAULT = 45;
        public static final int zombie_WIDTH = (int)(Zombie_WIDTH_DEFAULT * GameCONST.SCALE);
        public static final int zombie_HEIGHT = (int)(Zombie_HEIGHT_DEFAULT * GameCONST.SCALE);

        public static final int zombie_xOffset = (int)(7 * GameCONST.SCALE);


        public static int getSpriteAmount(int enemyType, int action) {
            switch (enemyType) {
                case Type.WORM -> {
                    switch (action) {
                        case IDLE -> {
                            return 8;
                        }
                        case RUN -> {
                            return 10;
                        }
                        case ATTACK -> {
                            return 18;
                        }
                        case DEAD -> {
                            return 6;
                        }
                        default -> {
                            return 0;
                        }
                    }
                }
                case Type.CANDY_ZOMBIE -> {
                    switch (action) {
                        case IDLE -> {
                            return 9;
                        }
                        case RUN -> {
                            return 13;
                        }
                        case ATTACK -> {
                            return 7;
                        }
                        case DEAD -> {
                            return 4;
                        }
                    }
                }
            }
            return 0;
        }

        public static int getMaxHealth(int enemyType){
            switch (enemyType){
                case Type.WORM, Type.CANDY_ZOMBIE -> { return 1; }
                default -> { return 0; }
            }
        }

        public static int getEnemyDamage(int enemyType){
            switch (enemyType){
                case Type.WORM, Type.CANDY_ZOMBIE -> { return 1; }
                default -> { return 0; }
            }
        }
    }
}
