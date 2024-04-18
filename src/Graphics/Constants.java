package Graphics;

public class Constants {
    public static class GameCONST {
        public final static int UPS = 200; // updates/second
        public final static int FPS = 120; // frames/second
        public final static int TILE_DEFAULT_SIZE = 32;
        public final static float SCALE = 1f;
        public final static int WIDTH_TILES = 50;
        public final static int HEIGHT_TILES = 20;

    }
    public static class UI{
        public static class Buttons
        {
            public static final int DEFAULT_BUTTON_WIDTH = 144; // pixels
            public static final int DEFAULT_BUTTON_HEIGHT = 72;
            public static final int BUTTON_WIDTH = (int)(DEFAULT_BUTTON_WIDTH * GameCONST.SCALE);
            public static final int BUTTON_HEIGHT = (int)(DEFAULT_BUTTON_HEIGHT * GameCONST.SCALE);

        }
    }

    public static class Player {
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int DEAD = 3;
        public static final int ATTACK_1 = 4;
        public static final int ATTACK_2 = 5;

        public static int getSpriteAmount(int action) {

            switch (action) {
                case IDLE, RUN -> { return 8; }
                case JUMP, DEAD, ATTACK_1, ATTACK_2 -> { return 10; }
                default -> { return 0; }
            }
        }
    }
}
