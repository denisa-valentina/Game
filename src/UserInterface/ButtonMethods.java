package UserInterface;

import java.awt.*;

public interface ButtonMethods {

    void update();
    void draw(Graphics g);


    boolean isMousePressed();

    void setMouseOver(boolean mouseOver);
    void setMousePressed(boolean mousePressed);

    void resetBooleans();
}
