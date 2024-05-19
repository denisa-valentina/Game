package Characters;

import Load.Load;

import java.awt.image.BufferedImage;
import java.util.List;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.Type.LEADER_MARAUDER;
import static Graphics.Constants.GameCONST.SCALE;

public class Marauder extends Enemy{

    private static List<List<BufferedImage>> marauderAnimations;

    public Marauder(float x, float y) {

        super(x, y, LeaderMarauder_WIDTH, LeaderMarauder_HEIGHT, LEADER_MARAUDER);
        this.runSpeed = 0.45f * SCALE;
        initCollisionBox(40, 29); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
    }

    public static void loadAnimations() {
        java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        // 5 animations: Idle, Run, Attack, Dead, Hurt
        images.add(Load.getImage(Images.marauderLeader_idle));
        images.add(Load.getImage(Images.marauderLeader_run));
        images.add(Load.getImage(Images.marauderLeader_attack));
        images.add(Load.getImage(Images.marauderLeader_dead));
        images.add(Load.getImage(Images.marauderLeader_hurt));

        marauderAnimations = Load.getAnimations(images);
    }

    public static List<List<BufferedImage>> getEnemyAnimations() {
        return marauderAnimations;
    }
}
