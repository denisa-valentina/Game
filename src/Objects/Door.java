package Objects;

import Characters.EnemyManager;
import GameStates.Play;

public class Door extends GameObject{

    boolean open = false;
    public Door(int x, int y, int objectType)
    {
        super(x, y, objectType);
        initCollisionBox(75, 98);
    }

    public void update(int lvlIndex, EnemyManager enemyManager)
    {
        switch(lvlIndex){
            case 0 -> {
                if(collisionBox.intersects(Play.getPlayer().getCollisionBox())){
                    open = true;
                } }
            case 1 -> {
                if(collisionBox.intersects(Play.getPlayer().getCollisionBox()) && enemyManager.getLvl2Defeated()){
                    open = true;
                }
            }
            case 2 -> {
                    if(collisionBox.intersects(Play.getPlayer().getCollisionBox()) && enemyManager.getLvl3Defeated()){
                        open = true;
                    }
            }
        }
        updateAnimationTick();
    }

    protected void updateAnimationTick() {
        if (open) {
            if (animationIndex >= 4) {
                active = false;
                return;
            }
            animationTick += 1;
            if (animationTick >= 25) {
                animationTick = 0;
                animationIndex += 1;
            }
        } else {
            animationIndex = 0;
        }
    }

    public void setOpen(boolean open){
        this.open = open;
    }

}
