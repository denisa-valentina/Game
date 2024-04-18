package GameStates;

import LoadSave.Load;
import Main.Game;
import UserInterface.MenuButtons;
import Graphics.Constants.GameCONST;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{
    private MenuButtons[] menuButtons = new MenuButtons[3];
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
        menuBackGround = Load.getImage(Load.menuBackGround);
    }

    private void loadMenuImage() {
        menuImage = Load.getImage(Load.menuImage);

        menuWidth = (int)(menuImage.getWidth() * GameCONST.SCALE);
        menuHeight = (int)(menuImage.getHeight() * GameCONST.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int)(95*GameCONST.SCALE);

    }

    private void loadButtons() {
        menuButtons[0] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(250*GameCONST.SCALE), 0, GameState.PLAY);
        menuButtons[1] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(320*GameCONST.SCALE), 1, GameState.OPTIONS);
        menuButtons[2] = new MenuButtons(Game.GAME_WIDTH / 2, (int)(390*GameCONST.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButtons i : menuButtons) {
            i.update();
        }
    }

    @Override
    public void draw(Graphics obj) {

        obj.drawImage(menuBackGround, 0, 0, (int)(GameCONST.SCALE*menuBackGround.getWidth()),(int)(GameCONST.SCALE*menuBackGround.getHeight()), null);
        obj.drawImage(menuImage, menuX, menuY, menuWidth, menuHeight, null);
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
