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

    public void draw(Graphics obj){
        obj.setColor(new Color(0, 0, 0, 200));
        obj.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        obj.setColor(Color.white);
        obj.drawString("Game Over", Game.GAME_WIDTH/2, 150);
        obj.drawString("Press ESC to enter Main Menu", Game.GAME_WIDTH/2, 300);
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            play.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
