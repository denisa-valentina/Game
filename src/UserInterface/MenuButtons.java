package UserInterface;

import GameStates.GameState;
import Load.Load;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.UI.Buttons.*;
import static Graphics.Constants.UI.Images.*;

public class MenuButtons{
    private List<List<BufferedImage>> images;
    private final GameState gameState;
    private boolean mouseOver, mousePressed;
    private final int x, y, rowIndex;
    private int index;
    private final int xCenter = BUTTON_WIDTH / 2;
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
        BufferedImage buttons = Load.getImage(menuButtons);
        images = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            List<BufferedImage> image = new ArrayList<>();
            for (int j = 0; j < 3; ++j) {
                image.add(buttons.getSubimage(j * DEFAULT_BUTTON_WIDTH, i * DEFAULT_BUTTON_HEIGHT, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
            }
            images.add(image);
        }
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics obj) {
        obj.drawImage(images.get(rowIndex).get(index), (x - xCenter), y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void resetBooleans()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setGameState()
    {
        GameState.state = gameState;
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
