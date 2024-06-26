package Objects;

import Graphics.Constants;

public class Spike extends GameObject {

    public Spike(int x, int y, int height, int objectType){
        super(x, y, objectType);

        initCollisionBox(15, height);
        xOffset = (int)(Constants.GameCONST.SCALE * 11);
        yOffset = (int)(Constants.GameCONST.SCALE * 5);
    }

    public void update()
    {
        updateAnimationTick();
    }
}
