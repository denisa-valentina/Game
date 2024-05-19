package Characters;

import Load.Load;

import java.awt.image.BufferedImage;
import java.util.List;

import static Graphics.Constants.Enemy.*;
import static Graphics.Constants.Enemy.worm_WIDTH;
import static Graphics.Constants.Enemy.worm_HEIGHT;
import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.GameCONST.SCALE;

public class Worm extends Enemy {

    private static List<List<BufferedImage>> wormAnimations;

    public Worm(float x, float y) {
        super(x, y, worm_WIDTH, worm_HEIGHT, WORM);
        this.runSpeed = 0.35f * SCALE;
        initCollisionBox(42, 29); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
    }

    public static void loadAnimations() {
        java.util.List<BufferedImage> images = new java.util.ArrayList<>();
        // 4 animations: Idle, Run, Attack, Dead (Hurt)
        images.add(Load.getImage(Images.worm_idle));
        images.add(Load.getImage(Images.worm_run));
        images.add(Load.getImage(Images.worm_attack));
        images.add(Load.getImage(Images.worm_dead));

        wormAnimations = Load.getAnimations(images);
    }

    @Override
    protected void updateBehavior(int [][]levelMatrix, Player player) {
        if (firstUpdate) {
            setFirstUpdate(levelMatrix);
        }
        if (inAir) {
            setInAir(levelMatrix);
        } else {
            switch (action) {
                case IDLE -> {
                    resetValues();
                    changeAction(RUN); }
                case RUN -> {
                    if (spotPlayer(levelMatrix, player)) {
                        if (isPlayerTooClose(player)) {
                            changeAction(ATTACK);
                        }
                    }
                    setRunning(levelMatrix);
                }
                case ATTACK -> {
                    width = 65; height = 60; attacking = 1;
                    if(animationIndex == 0)
                        attackChecked = false;
                    if(animationIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    }
                    case DEAD -> resetValues();
            }
        }
    }

    @Override
    public void resetValues(){
        attacking = 0;
        width = worm_WIDTH;
        height = worm_HEIGHT;
    }

    public static List<List<BufferedImage>> getEnemyAnimations() {
        return wormAnimations;
    }

}