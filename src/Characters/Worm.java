package Characters;


import Graphics.Constants.GameCONST;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Graphics.Constants.Enemy.Type.WORM;
import static Graphics.Constants.Enemy.*;

public class Worm extends Enemy {

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Worm(float x, float y) {
        super(x, y, Worm_WIDTH_DEFAULT, Worm_HEIGHT_DEFAULT, WORM);
        initCollisionBox(x, y, (int)(45* GameCONST.SCALE), (int)(28*GameCONST.SCALE)); // width-ul si height-ul cutiei de coliziune
        initAttackBox();
    }


    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(65* GameCONST.SCALE), (int)(28*GameCONST.SCALE));
        attackBoxOffsetX = (int)(10 * GameCONST.SCALE);
    }

    public void update(int [][]levelMatrix, Player player)
    {
        updateBehavior(levelMatrix, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = collisionBox.x - attackBoxOffsetX;
        attackBox.y = collisionBox.y;
    }

    public void drawAttackBox(Graphics obj, int xLevelOffset) {
        obj.setColor(Color.RED);
        obj.drawRect((int)(attackBox.x - xLevelOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void updateBehavior(int [][]levelMatrix, Player player) {

        if (firstUpdate) {
            setFirstUpdate(levelMatrix);
        }
        if (inAir) {
            setInAir(levelMatrix);
        } else {
            switch (enemyAction) {
                case IDLE -> changeAction(RUN);
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
                    if(animationIndex == 0)
                        attackChecked = false;
                    if(animationIndex == 3 && !attackChecked){
                        checkEnemyHit(attackBox, player);
                    }
                }
                    case HURT -> {

                    }
            }
        }
    }




    int flipX()
    {
        if(walkDirection == 1)
        {
            return 0;
        }
        else return width;
    }
}
