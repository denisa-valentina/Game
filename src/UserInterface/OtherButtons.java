package UserInterface;

import Load.Load;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.UI.Buttons.*;
import static Graphics.Constants.UI.Buttons.BUTTON_HEIGHT;
import static Graphics.Constants.UI.Images.pauseButtons;

public class OtherButtons extends PauseButtons implements ButtonMethods {

    private List<List<BufferedImage>> images;
    private int index;

    public OtherButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage buttons = Load.getImage(pauseButtons);
        images = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            List<BufferedImage> image = new ArrayList<>();
            for(int j = 0; j < 3; ++j) {
                image.add(buttons.getSubimage(j * DEFAULT_BUTTON_WIDTH, i * DEFAULT_BUTTON_HEIGHT, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
            }
            images.add(image);
        }
    }

    public void update(){
        index = 0;
        if(isMouseOver)
            index = 1;
        if(isMousePressed)
            index = 2;
    }

    public void draw(Graphics obj){
        obj.drawImage(images.get(rowIndex).get(index), x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void resetBooleans(){
        isMouseOver = false;
        isMousePressed = false;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public void setMouseOver(boolean isMouseOver) {
        this.isMouseOver = isMouseOver;
    }
    public void setMousePressed(boolean isMousePressed) {
        this.isMousePressed = isMousePressed;
    }

    public boolean isMouseOver() {
        return isMouseOver;
    }
}
