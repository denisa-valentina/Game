package GameWindow;

import Input.Keyboard;
import Input.Mouse;
import Main.Game;

import javax.swing.*;
import java.awt.*;

/*
    Se va comporta precum un container (toate elementele
    grafice vor fi desenate aici).
    In timp ce GameWindow va fi "Rama" ferestrei de joc, GamePanel
    va fi continutul ferestrei de joc
*/
public class GamePanel extends JPanel {

    private Mouse mouse;
    private Keyboard keyBoard;
    private Game game;

    public GamePanel(Game game) {
        mouse = new Mouse(this);
        keyBoard = new Keyboard(this);
        this.game = game;

        setPanelSize();
        addKeyListener(keyBoard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // 40 tiles x 25 tiles
        setPreferredSize(size);
    }


    public void updateGame() {

        /*updateSpriteTick();
        setAnimation();
        //updatePosition();*/
    }

    public void paintComponent(Graphics obj) {
        /* apeleaza metoda paintComponent a clasei JComponent (parinte al clasei JPanel, care
           a fost extinsa de clasa noastra. E foarte utila atunci cand dorim sa "curatam" zona de posibile
           glitch-uri si desene anterioare inainte de a desena noi insine. Actioneaza precum un burete pe tabla */
        super.paintComponent(obj);

        game.render(obj);
    }

    public Game getGame() {
        return game;
    }
}