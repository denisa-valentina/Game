package Characters;


import Graphics.Constants.GameCONST;

import static Graphics.Constants.Enemy.worm_WIDTH;
import static Graphics.Constants.Enemy.worm_HEIGHT;
import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.Enemy.*;

public class Worm extends Enemy {

    public Worm(float x, float y) {
        super(x, y, worm_WIDTH, worm_HEIGHT, WORM);
        initCollisionBox(x, y, (int)(42* GameCONST.SCALE), (int)(28*GameCONST.SCALE)); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
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
            switch (enemyAction) {
                case IDLE -> {
                    resetValues();
                    changeAction(RUN); }
                case RUN -> {
                    if (spotPlayer(levelMatrix, player)) {
                        turnTowardsPlayer(player);
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
                    case HURT -> {

                    }
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