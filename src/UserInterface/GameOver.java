package UserInterface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Load.Load;
import Graphics.Constants.UI.Images;
import GameStates.GameState;
import GameStates.Play;
import Main.Game;

import static Graphics.Constants.GameCONST.SCALE;
import static Graphics.Constants.UI.Buttons.DEFAULT_BUTTON_HEIGHT;
import static Graphics.Constants.UI.Buttons.DEFAULT_BUTTON_WIDTH;

public class GameOver {

    private final Play play;
    private OtherButtons replayButton, menuButton;

    private BufferedImage gameOverImage;
    private int x, y, width, height;

    public GameOver(Play play){

        this.play = play;
        createImage();
        createButtons();
    }

    private void createButtons() {
        int menuY = (int)(500 * SCALE);
        int replayY = (int)(420 * SCALE);
        int buttonX = (int)(725 * SCALE);
        menuButton = new OtherButtons(buttonX, menuY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, 2);
        replayButton = new OtherButtons(buttonX, replayY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, 1);
    }

    private void createImage() {
        gameOverImage = Load.getImage(Images.gameOverImage);
        width = (int)(SCALE * gameOverImage.getWidth());
        height = (int)(SCALE * gameOverImage.getHeight());
        x = Game.GAME_WIDTH/2 - width/2;
        y = (int)(100 * SCALE);
    }

    public void update(){

        menuButton.update();
        replayButton.update();

    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(gameOverImage, x, y, width, height, null);
        menuButton.draw(g);
        replayButton.draw(g);
    }


    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            play.resetAll();
            GameState.state = GameState.MENU;
        }
    }

    public void mouseMoved(MouseEvent e){
        replayButton.setMouseOver(false);
        menuButton.setMouseOver(false);

        if(isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if(isIn(e, replayButton))
            replayButton.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e){
        if(isIn(e, menuButton))
            if(menuButton.isMousePressed)
            {
                play.resetAll();
                GameState.state = GameState.MENU;
            }
        if(isIn(e, replayButton))
            if(replayButton.isMousePressed){
                play.resetAll();
                Play.getPlayer().resetHealth();
            }

        menuButton.resetBooleans();
        replayButton.resetBooleans();

    }

    public void mousePressed(MouseEvent e){
        if(isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if(isIn(e, replayButton))
            replayButton.setMousePressed(true);
    }

    private boolean isIn(MouseEvent e, PauseButtons button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
