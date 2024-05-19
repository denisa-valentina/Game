package Input;

import GameStates.GameState;
import GameWindow.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private final GamePanel gamePanel;
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
            case PLAY, LOAD -> gamePanel.getGame().getPlay().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (GameState.state)
        {
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
            case PLAY, LOAD -> gamePanel.getGame().getPlay().keyReleased(e);
        }
    }
}
