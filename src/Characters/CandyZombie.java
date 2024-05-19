package Characters;

import Load.Load;

import java.awt.image.BufferedImage;
import java.util.List;

import static Graphics.Constants.Enemy.Type.CANDY_ZOMBIE;
import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.GameCONST.SCALE;

public class CandyZombie extends Enemy {

    private static List<List<BufferedImage>> zombieAnimations;

    public CandyZombie(float x, float y) {
        super(x, y, zombie_WIDTH, zombie_HEIGHT, CANDY_ZOMBIE);
        this.runSpeed = 0.40f * SCALE;

        initCollisionBox(40, 28); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
    }

    public static void loadAnimations() {
        java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        // 4 animations: Idle, Run, Attack, Dead
        images.add(Load.getImage(Images.zombie_idle));
        images.add(Load.getImage(Images.zombie_run));
        images.add(Load.getImage(Images.zombie_attack));
        images.add(Load.getImage(Images.zombie_dead));

        zombieAnimations = Load.getAnimations(images);
    }

    public static List<List<BufferedImage>> getEnemyAnimations() {
        return zombieAnimations;
    }
}