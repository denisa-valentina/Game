package GameStates;

import Load.Load;
import Main.Game;
import UserInterface.MenuButtons;
import Graphics.Constants.GameCONST;
import Graphics.Constants.UI.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{
    private final MenuButtons[] menuButtons = new MenuButtons[3];
    private BufferedImage menuImage, menuBackGround;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game)
    {
        super(game);
        loadButtons();
        loadMenuImage();
        loadMenuBackGround();
    }

    private void loadMenuBackGround()
    {
        menuBackGround = Load.getImage(Images.menuBackGround);
    }

    private void loadMenuImage() {
        menuImage = Load.getImage(Images.menuImage);

//        menuWidth = (int)(menuImage.getWidth() * GameCONST.SCALE);
//        menuHeight = (int)(menuImage.getHeight() * GameCONST.SCALE);
        menuX = Game.GAME_WIDTH / 2 - 300;
        menuY = (int)(150*GameCONST.SCALE);

    }

    private void loadButtons() {
        menuButtons[0] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(300*GameCONST.SCALE), 0, GameState.PLAY);
        menuButtons[1] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(370*GameCONST.SCALE), 1, GameState.OPTIONS);
        menuButtons[2] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(440*GameCONST.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButtons i : menuButtons) {
            i.update();
        }
    }

    @Override
    public void draw(Graphics obj) {

        obj.drawImage(menuBackGround, 0, 0, (int)(GameCONST.SCALE*Game.GAME_WIDTH),(int)(GameCONST.SCALE*Game.GAME_HEIGHT), null);
        obj.drawImage(menuImage, menuX, menuY, 600, 470, null);
            for (MenuButtons i : menuButtons) {
                i.draw(obj);
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButtons i : menuButtons) {
            if(isIn(e, i))
            {
                i.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButtons i : menuButtons) {
            if (isIn(e, i)) {
                if (i.isMousePressed()) {
                    i.setGameState();
                    break;
                }
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButtons i : menuButtons) {
            i.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButtons i : menuButtons) {
            i.setMouseOver(false);
        }
        for (MenuButtons i : menuButtons) {
            if (isIn(e, i)) {
                i.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            GameState.state = GameState.PLAY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
}
