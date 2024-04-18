package GameStates;

import LevelMap.LevelHandler;
import Main.Game;
import Objects.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Play extends State implements StateMethods {

    private LevelHandler levelHandler;
    private static Player player;

    public Play(Game game)
    {
        super(game);
        init();
    }
    private void init() {
        levelHandler = new LevelHandler(getGame());
        // x:200, y:200 - pozitia initiala
        player = Player.getInstance(200*Game.SCALE, 170*Game.SCALE, (int)(Game.SCALE*128), (int)(Game.SCALE*128));
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

    }

    @Override
    public void draw(Graphics obj) {
        levelHandler.draw(obj);
        player.render(obj);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

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

    public void windowFocusLost()
    {
        player.resetDirection();
    }
}
