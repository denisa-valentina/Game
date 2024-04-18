package GameStates;

import Main.Game;
import UserInterface.MenuButtons;

import java.awt.event.MouseEvent;

public class State {

    private Game game;

    // super class for all our gameStates
    public State(Game game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButtons button)
    {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}
