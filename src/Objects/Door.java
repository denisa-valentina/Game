package Objects;

import Characters.EnemyManager;
import Characters.Player;
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
                if(spotPlayer(Play.getPlayer())){
                    System.out.println("Lvl 1");
                    open = true;
                } }
            case 1 -> {
                if(spotPlayer(Play.getPlayer()) && enemyManager.getLvl2Defeated()){
                    System.out.println("Lvl 2");
                    open = true;
                }
            }
            case 2 -> {
                    if(spotPlayer(Play.getPlayer()) && enemyManager.getLvl3Defeated()){
                        System.out.println("Lvl 3");
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

    protected boolean spotPlayer(Player player) {
        return isPlayerTooClose(player);
    }

    protected boolean isPlayerTooClose(Player player){
        int xAbsoluteDistance = (int)Math.abs(player.getCollisionBox().x - collisionBox.x);
        int yAbsoluteDistance = (int)Math.abs(player.getCollisionBox().y - collisionBox.y);
        return (xAbsoluteDistance <= 15 && yAbsoluteDistance <= 30);
    }

    public void setOpen(boolean open){
        this.open = open;
    }

}
