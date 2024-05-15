package Objects;

import Graphics.Constants;

public class Fruit extends GameObject{

    public Fruit(int x, int y, int objectType)
    {
        super(x, y, objectType);
        initCollisionBox(32, 32);
        xOffset = (int)(3*Constants.GameCONST.SCALE);
        yOffset = (int)(2*Constants.GameCONST.SCALE);
    }

    public void update()
    {
        updateAnimationTick();
    }
}
