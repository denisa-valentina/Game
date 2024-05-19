package Characters;

import Load.Load;

import java.awt.image.BufferedImage;
import java.util.List;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.Type.ICE_KING;
import static Graphics.Constants.GameCONST.SCALE;

public class IceKing extends Enemy{

    private static List<List<BufferedImage>> iceKingAnimations;

    public IceKing(float x, float y) {

        super(x, y, IceKing_WIDTH, IceKing_HEIGHT, ICE_KING);
        this.runSpeed = 0.45f * SCALE;
        initCollisionBox(40, 29); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
    }

    public static void loadAnimations() {
        java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        // 5 animations: Idle, Run, Attack, Dead, Hurt
        images.add(Load.getImage(Images.iceKing_idle));
        images.add(Load.getImage(Images.iceKing_run));
        images.add(Load.getImage(Images.iceKing_attack));
        images.add(Load.getImage(Images.iceKing_dead));
        images.add(Load.getImage(Images.iceKing_hurt));

        iceKingAnimations = Load.getAnimations(images);
    }

    public static List<List<BufferedImage>> getEnemyAnimations() {
        return iceKingAnimations;
    }
}
