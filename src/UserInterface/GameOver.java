package UserInterface;

import java.awt.*;
import java.awt.event.KeyEvent;

import GameStates.GameState;
import GameStates.Play;
import Main.Game;

public class GameOver {

    private final Play play;
    public GameOver(Play play){
        this.play = play;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", Game.GAME_WIDTH/2, 150);
        g.drawString("Press ESC to enter Main Menu", Game.GAME_WIDTH/2, 300);
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            play.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
