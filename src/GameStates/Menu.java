package GameStates;

import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends State implements StateMethods{
    public Menu(Game game)
    {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics obj) {
        obj.setColor(Color.blue);
        obj.drawString("Hiiii", Game.GAME_WIDTH/2, 200);
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
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            GameState.state = GameState.PLAY;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
