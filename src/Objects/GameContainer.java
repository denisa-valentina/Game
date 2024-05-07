//package Objects;
//
//import Graphics.Constants;
//
//public class GameContainer extends GameObject{
//
//    public GameContainer(int x, int y, int objectType)
//    {
//        super(x, y, objectType);
//        createCollisionBox();
//    }
//
//    private void createCollisionBox()
//    {
//        initCollisionBox(32, 32);
//        xOffset = (int)(8* Constants.GameCONST.SCALE);
//        yOffset = (int)(5* Constants.GameCONST.SCALE);
//    }
//
//    public void update()
//    {
//        if(doAnimation)
//            updateAnimationTick();
//    }
//}
