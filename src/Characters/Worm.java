package Characters;

import Load.Load;

import java.awt.image.BufferedImage;
import java.util.List;

import static Graphics.Constants.Enemy.worm_WIDTH;
import static Graphics.Constants.Enemy.worm_HEIGHT;
import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.Enemy.*;

public class Worm extends Enemy {

    private static List<List<BufferedImage>> wormAnimations;

    public Worm(float x, float y) {
        super(x, y, worm_WIDTH, worm_HEIGHT, WORM);
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
        wormAnimations = new java.util.ArrayList<>();

        for (BufferedImage image : images) {
            java.util.List<int[]> imageRegions = Load.getImagesCoords(image);
            java.util.List<BufferedImage> imagess = new java.util.ArrayList<>();
            for (int[] imageRegion : imageRegions) {
                imagess.add(image.getSubimage(imageRegion[0],
                        imageRegion[1],
                        imageRegion[2],
                        imageRegion[3]));
            }
            wormAnimations.add(imagess);
        }
    }

    public static List<List<BufferedImage>> getEnemyAnimations() {
        return wormAnimations;
    }

    @Override
    public void update(int [][]levelMatrix, Player player)
    {
        updateBehavior(levelMatrix, player);
        updateAnimationTick();
        updateAttackBox();
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
                    case DEAD -> { resetValues(); }
            }
        }
    }

    @Override
    public void resetValues(){
        attacking = 0;
        width = worm_WIDTH;
        height = worm_HEIGHT;
    }

}