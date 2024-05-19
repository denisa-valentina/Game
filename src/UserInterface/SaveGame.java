//package UserInterface;
//
//import GameStates.GameState;
//import GameStates.Play;
//import Graphics.Constants;
//import Load.Load;
//import Main.Game;
//
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//
//import static Graphics.Constants.GameCONST.SCALE;
//import static Graphics.Constants.UI.Buttons.DEFAULT_BUTTON_HEIGHT;
//import static Graphics.Constants.UI.Buttons.DEFAULT_BUTTON_WIDTH;
//
//public class CompletedLevel {
//
//    private final Play play;
//    private OtherButtons menuButton, nextButton;
//
//    private BufferedImage completedLevelImage;
//    private int x, y, width, height;
//
//    public CompletedLevel(Play play) {
//        this.play = play;
//
//        loadBackground();
//        createButtons();
//    }
//
//    private void loadBackground() {
//        completedLevelImage = Load.getImage(Constants.UI.Images.completedLevelImage);
//        width = (int)(completedLevelImage.getWidth() * SCALE);
//        height = (int)(500 * SCALE);
//        x = Game.GAME_WIDTH / 2 - width / 2;
//        y = (int)(90* SCALE);
//    }
//
//    private void createButtons() {
//        int menuY = (int)(420 * SCALE);
//        int nextY = (int)(350 * SCALE);
//        int buttonX = (int)( 720 * SCALE);
//        menuButton = new OtherButtons(buttonX, menuY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, 2);
//        nextButton = new OtherButtons(buttonX, nextY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, 3);
//    }
//
//    public void update(){
//
//        menuButton.update();
//        nextButton.update();
//
//    }
//
//    public void draw(Graphics g){
//        g.drawImage(completedLevelImage, x, y, width, height, null);
//        nextButton.draw(g);
//        menuButton.draw(g);
//    }
//
//    public void mouseMoved(MouseEvent e){
//        nextButton.setMouseOver(false);
//        menuButton.setMouseOver(false);
//
//        if(isIn(e, menuButton))
//            menuButton.setMouseOver(true);
//        else if(isIn(e, nextButton))
//            nextButton.setMouseOver(true);
//
//
//    }
//
//    public void mouseReleased(MouseEvent e){
//        if(isIn(e, menuButton))
//            if(menuButton.isMousePressed)
//            {
//                play.resetAll();
//                GameState.state = GameState.MENU;
//            }
//        if(isIn(e, nextButton))
//            if(nextButton.isMousePressed){
//                play.loadNextLevel();}
//
//        menuButton.resetBooleans();
//        nextButton.resetBooleans();
//
//    }
//
//    public void mousePressed(MouseEvent e){
//        if(isIn(e, menuButton))
//            menuButton.setMousePressed(true);
//        else if(isIn(e, nextButton))
//            nextButton.setMousePressed(true);
//    }
//
//    private boolean isIn(MouseEvent e, PauseButtons button) {
//        return button.getBounds().contains(e.getX(), e.getY());
//    }
//}