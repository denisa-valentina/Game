package Objects;

import Graphics.Constants;

public class Fruit extends GameObject{

    private float hoverOffset;
    private final int maxHoverOffset;
    private int hoverDirection = 1;


    public Fruit(int x, int y, int objectType)
    {
        super(x, y, objectType);
        initCollisionBox(24, 24);
        xOffset = (int)(8*Constants.GameCONST.SCALE);
        yOffset = (int)(10*Constants.GameCONST.SCALE);

        maxHoverOffset = (int)(10*Constants.GameCONST.SCALE);
    }

    public void update()
    {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.06f * Constants.GameCONST.SCALE * hoverDirection);
        if(hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if(hoverOffset < 0)
            hoverDirection = 1;
        collisionBox.y = y + hoverOffset;
    }
}
