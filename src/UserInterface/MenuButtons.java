package UserInterface;

import GameStates.GameState;
import LoadSave.Load;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Graphics.Constants.UI.Buttons.*;
import static Graphics.Constants.GameCONST;

public class MenuButtons {
    private BufferedImage[] images;
    private GameState gameState;
    private boolean mouseOver, mousePressed;
    private int x, y, rowIndex, index;
    private int xCenter = BUTTON_WIDTH / 2;
    private Rectangle bounds;

    public MenuButtons(int x, int y, int rowIndex, GameState gameState) {
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.gameState = gameState;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(x - xCenter, y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage buttons = Load.getImage(Load.menuButtons);
        for (int i = 0; i < images.length; ++i) {
            images[i] = buttons.getSubimage(i * DEFAULT_BUTTON_WIDTH, rowIndex * DEFAULT_BUTTON_HEIGHT, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);
        }
    }

    public void draw(Graphics obj) {
        obj.drawImage(images[index], (x - xCenter), y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setGameState()
    {
        GameState.state = gameState;
    }

    public void resetBooleans()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }



}