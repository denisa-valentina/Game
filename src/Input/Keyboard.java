package Input;

import GameStates.GameState;
import GameWindow.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private GamePanel gamePanel;
    public Keyboard(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        switch (GameState.state)
        {
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
            case PLAY -> gamePanel.getGame().getPlay().keyPressed(e);
        }

//        switch ((e.getKeyCode()))
//        {
//            case KeyEvent.VK_A:
//                gamePanel.getGame().getPlayer().setLeft(true);
//                break;
//            case KeyEvent.VK_W:
//                gamePanel.getGame().getPlayer().setJump(true);
//                break;
//            case KeyEvent.VK_D:
//                gamePanel.getGame().getPlayer().setRight(true);
//                break;
//            case KeyEvent.VK_K:
//            case KeyEvent.VK_L:
//                gamePanel.getGame().getPlayer().setAttacking(true);
//                break;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (GameState.state)
        {
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
            case PLAY -> gamePanel.getGame().getPlay().keyReleased(e);
        }
//        switch ((e.getKeyCode()))
//        {
//            case KeyEvent.VK_A:
//                gamePanel.getGame().getPlayer().setLeft(false);
//                break;
//            case KeyEvent.VK_W:
//                gamePanel.getGame().getPlayer().setJump(false);
//                break;
//            case KeyEvent.VK_D:
//                gamePanel.getGame().getPlayer().setRight(false);
//                break;
//        }
    }
}
