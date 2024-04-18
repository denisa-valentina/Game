package Input;

import GameStates.GameState;
import GameWindow.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public Mouse(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.state)
        {
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            case PLAY -> gamePanel.getGame().getPlay().mouseClicked(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
