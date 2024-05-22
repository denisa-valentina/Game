package UserInterface;

import GameStates.GameState;
import GameStates.Play;
import Load.Load;
import Graphics.Constants.UI.*;
import Main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Graphics.Constants.GameCONST.SCALE;
import static Graphics.Constants.UI.Buttons.*;

public class Pause {

    private final Play play;
    private SoundButtons soundButton, musicButton;
    private OtherButtons menuButton, replayButton, continueButton, saveButton;

    private BufferedImage pauseImage;

    private int pauseX, pauseY, pauseWidth, pauseHeight;

    public Pause(Play play) {
        this.play = play;
        loadBackground();
        createSoundButtons();
        createButtons();
    }

    private void createButtons() {
        int continueY = (int)(270 * SCALE);
        int replayY = (int)(330 * SCALE);
        int saveY = (int)(390 * SCALE);
        int menuY = (int)(450 * SCALE);
        int buttonX = (int)(720 * SCALE);
        saveButton = new OtherButtons(buttonX, saveY, BUTTON_WIDTH, BUTTON_HEIGHT, 4);
        menuButton = new OtherButtons(buttonX, menuY, BUTTON_WIDTH, BUTTON_HEIGHT, 2);
        replayButton = new OtherButtons(buttonX, replayY, BUTTON_WIDTH, BUTTON_HEIGHT, 1);
        continueButton = new OtherButtons(buttonX, continueY, BUTTON_WIDTH, BUTTON_HEIGHT, 0);
    }

    private void createSoundButtons() {
        int soundX = (int)(690* SCALE);
        int soundY = (int)(200* SCALE);
        int musicX = (int)(830* SCALE);
        int musicY = (int)(200* SCALE);
        soundButton = new SoundButtons(soundX, soundY, 64, 64);
        musicButton = new SoundButtons(musicX, musicY, 64, 64);

        soundButton.loadImages(Images.soundButton, 2,3);
        musicButton.loadImages(Images.musicButton, 2,3);
    }

    private void loadBackground() {
        pauseImage = Load.getImage(Images.pauseImage);
        pauseWidth = (int)(pauseImage.getWidth() * SCALE);
        pauseHeight = (int)(500 * SCALE);
        pauseX = Game.GAME_WIDTH / 2 - pauseWidth / 2;
        pauseY = (int)(90* SCALE);
    }

    public void update(){
        musicButton.update();
        soundButton.update();
        saveButton.update();

        menuButton.update();
        replayButton.update();
        continueButton.update();
    }

    public void draw(Graphics g){
        // Background
        g.drawImage(pauseImage, pauseX, pauseY, pauseWidth, pauseHeight, null);

        // SoundButtons
        musicButton.draw(g);
        soundButton.draw(g);


        //OtherButtons
        menuButton.draw(g);
        replayButton.draw(g);
        continueButton.draw(g);
        saveButton.draw(g);
    }


    public void resetBooleans() {
        musicButton.resetBooleans();
        soundButton.resetBooleans();
        menuButton.resetBooleans();
        replayButton.resetBooleans();
        continueButton.resetBooleans();
        saveButton.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton)){
            musicButton.setMousePressed(true);
        } else if(isIn(e, soundButton))
            soundButton.setMousePressed(true);
        else if(isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if(isIn(e, replayButton))
            replayButton.setMousePressed(true);
        else if(isIn(e, continueButton))
            continueButton.setMousePressed(true);
        else if(isIn(e, saveButton))
            saveButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(musicButton.isMuted());
        } else if (isIn(e, soundButton)) {
            if (soundButton.isMousePressed())
                soundButton.setMuted(soundButton.isMuted());
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                GameState.state = GameState.MENU;
                play.unpauseGame();
            }
        } else if (isIn(e, replayButton)) {
            if (replayButton.isMousePressed()) {
                play.resetAll();
                play.getLevelHandler().getCurrentLevel().resetScore();
                Play.getPlayer().resetHealth();
                play.unpauseGame();
            }
        } else if (isIn(e, continueButton)) {
            if (continueButton.isMousePressed()) {
                play.unpauseGame();
            }
        }
        else if(isIn(e, saveButton)){
            Play.getDataBase().saveGame(play.getLevelHandler().getLevelIndex(),
                    Play.getPlayer().getHealth(),
                    Play.getPlayer().getScore(),
                    (int)Play.getPlayer().getCollisionBox().x,
                    (int)Play.getPlayer().getCollisionBox().y);
        }
        resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        setMouseOver();

        if(isIn(e, musicButton)){
            musicButton.setMouseOver(true);
        } else if(isIn(e, soundButton))
            soundButton.setMouseOver(true);
        else if(isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if(isIn(e, replayButton))
            replayButton.setMouseOver(true);
        else if(isIn(e, continueButton))
            continueButton.setMouseOver(true);
        else if(isIn(e, saveButton))
            saveButton.setMouseOver(true);
    }

    public void setMouseOver() {
        musicButton.setMouseOver(false);
        soundButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        continueButton.setMouseOver(false);
        saveButton.setMouseOver(false);
    }

    private boolean isIn(MouseEvent e, PauseButtons button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

}
