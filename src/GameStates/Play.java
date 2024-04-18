package GameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import LevelMap.LevelHandler;
import Main.Game;
import Objects.Player;
import Graphics.Constants.GameCONST;

public class Play extends State implements StateMethods {
    private LevelHandler levelHandler;
    private static Player player;
    private int xLevelOffset;
    private int leftBorder = (int)(0.3 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.7 * Game.GAME_WIDTH);

    public Play(Game game)
    {
        super(game);
        init();
    }
    private void init() {
        levelHandler = new LevelHandler(getGame());
        player = Player.getInstance(200*GameCONST.SCALE, 170*GameCONST.SCALE, (int)(GameCONST.SCALE*128), (int)(GameCONST.SCALE*128));
        player.loadLevelMatrix(levelHandler.getLevel().getGroundLayer().getLayerMatrix());
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void update() {
        levelHandler.update();
        player.update();
        isCloseToBorder();
    }

    @Override
    public void draw(Graphics obj) {
        levelHandler.draw(obj, xLevelOffset);
        player.render(obj, xLevelOffset);
    }

    private void isCloseToBorder() {
        int levelTileWide = levelHandler.getLevel().getGroundLayer().getLayerMatrix()[0].length;
        int maxTilesOffset = levelTileWide - GameCONST.WIDTH_TILES;
        int maxOffset = maxTilesOffset * Game.TILE_SIZE;

        int playerX = (int) player.getCollisionBox().x;
        int delta = playerX - xLevelOffset;

        if (delta > rightBorder) {
            xLevelOffset += delta - rightBorder;
        } else if (delta < leftBorder) {
            xLevelOffset += delta - leftBorder;
        }

        if (xLevelOffset > maxOffset) {
            xLevelOffset = maxOffset;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch ((e.getKeyCode()))
        {
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_W -> player.setJump(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_K, KeyEvent.VK_L -> player.setAttacking(true);
            case KeyEvent.VK_ESCAPE -> GameState.state = GameState.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch ((e.getKeyCode()))
        {
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_W -> player.setJump(false);
            case KeyEvent.VK_D -> player.setRight(false);
        }
    }

    public void windowFocusLost() { player.resetDirection(); }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
