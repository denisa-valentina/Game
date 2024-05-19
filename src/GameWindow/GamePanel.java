package GameWindow;

import Input.Keyboard;
import Input.Mouse;
import Main.Game;

import javax.swing.*;
import java.awt.*;

import static Main.Game.GAME_HEIGHT;
import static Main.Game.GAME_WIDTH;

/*
    Se va comporta precum un container (toate elementele
    grafice vor fi desenate aici).
    In timp ce GameWindow va fi "Rama" ferestrei de joc, GamePanel
    va fi continutul ferestrei de joc
*/
public class GamePanel extends JPanel {
    private final Game game;

    public GamePanel(Game game) {
        Mouse mouse = new Mouse(this);
        Keyboard keyBoard = new Keyboard(this);
        this.game = game;

        setPanelSize();
        addKeyListener(keyBoard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public Game getGame() {
        return game;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println(size.getWidth() + " " + size.getHeight());
    }

    public void paintComponent(Graphics g) {
        /* apeleaza metoda paintComponent a clasei JComponent (parinte al clasei JPanel, care
           a fost extinsa de clasa noastra. E foarte utila atunci cand dorim sa "curatam" zona de posibile
           glitch-uri si desene anterioare inainte de a desena noi insine. Actioneaza precum un burete pe tabla */
        super.paintComponent(g);

        game.draw(g);
    }
}