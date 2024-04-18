package Graphics;

public class Constants {

//    public static final int UP = 0;
//    public static final int RIGHT = 1;
//    public static final int LEFT = 2;
//    public static final int DOWN = 3;

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
