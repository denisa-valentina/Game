package Characters;

import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.Enemy.*;

public class Worm extends Enemy {

    public Worm(float x, float y) {
        super(x, y, Worm_WIDTH_DEFAULT, Worm_HEIGHT_DEFAULT, WORM);
        initCollisionBox(x, y, 30, 28); // width-ul si height-ul cutiei de coliziune
    }

    public void update(int [][]levelMatrix)
    {
        updateMove(levelMatrix);
        updateAnimationTick();
    }

    private void updateMove(int [][]levelMatrix) {

        if (firstUpdate) {
            setFirstUpdate(levelMatrix);
        }
        if (inAir) {
            setInAir(levelMatrix);
        }
        else {
            switch(enemyAction)
            {
                case IDLE -> changeAction(IDLE);
                case RUN -> setRunning(levelMatrix);
            }
        }
    }
}
