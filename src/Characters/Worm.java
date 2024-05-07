package Characters;

import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.Enemy.*;

public class Worm extends Enemy {

    public Worm(float x, float y) {
        super(x, y, Worm_WIDTH_DEFAULT, Worm_HEIGHT_DEFAULT, WORM);
        initCollisionBox(x, y, 30, 28); // width-ul si height-ul cutiei de coliziune
    }

    public void update(int [][]levelMatrix, Player player)
    {
        updateMove(levelMatrix, player);
        updateAnimationTick();
    }

    private void updateMove(int [][]levelMatrix, Player player) {

        if (firstUpdate) {
            setFirstUpdate(levelMatrix);
        }
        if (inAir) {
            setInAir(levelMatrix);
        }
        else {
            switch(enemyAction)
            {
                case IDLE -> changeAction(RUN);
                case RUN -> {

                    if(spotPlayer(levelMatrix, player)){
                        turnTowardsPlayer(player);
                        if(isPlayerTooClose(player)){
                            changeAction(ATTACK);
                        }
                    }
                    setRunning(levelMatrix);
                }

                }
        }
    }
}
