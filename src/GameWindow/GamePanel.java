package GameWindow;

import Input.Keyboard;
import Input.Mouse;
import Main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Graphics.Constants.Player.*;


/*
    Se va comporta precum un container (toate elementele
    grafice vor fi desenate aici).
    In timp ce GameWindow va fi "Rama" ferestrei de joc, GamePanel
    va fi continutul ferestrei de joc
*/
public class GamePanel extends JPanel {

    private Mouse mouse;
    private Game game;
    /*private float xDelta = 100, yDelta = 100;
    private BufferedImage bufferedImage;
    private BufferedImage[] idleHero;
    private int spriteTick, spriteIndex, spriteSpeed = 25;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;*/

    public GamePanel(Game game) {
        mouse = new Mouse(this);
        this.game = game;

//        /*importImage("/Attack_1.png");
//        loadAnimations();*/

        setPanelSize();
        addKeyListener(new Keyboard(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // 40 tiles x 25 tiles
        setPreferredSize(size);
    }

    /*private void importImage(String path) {
        InputStream input = getClass().getResourceAsStream(path);

        try {
            bufferedImage = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close(); // trebuie sa si inchidem InputStream-ul, dar in cazul in care nu a fost
                // deschis cu succes vom utiliza un alt bloc de try-catch
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }*/

    /*private void loadAnimations() {
        idleHero = new BufferedImage[10]; // width = 128, height = 128
        for (int i = 0; i < idleHero.length; ++i) {
            idleHero[i] = bufferedImage.getSubimage(i * 128, 0, 128, 128);
        }
    }*/

    /*private void updateSpriteTick() {

        spriteTick += 1;
        if (spriteTick >= spriteSpeed) {
            spriteTick = 0;
            spriteIndex += 1;
            if (spriteIndex >= idleHero.length) {
                spriteIndex = 0;
            }
        }
    }

    private void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    private void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void setAnimation() {
        if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }
    }*/

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

        /*obj.setColor(Color.white);
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 40; j++)
                obj.fillRect(i * 20, j * 20, 20, 20);*/

        game.render(obj);

        //subBufferedImage = bufferedImage.getSubimage(128, 0, 128,128);
        //obj.drawImage(idleHero[spriteIndex], (int) xDelta, (int) yDelta, 128, 128, null);

    }

    public Game getGame() {
        return game;
    }
}