package Main;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)
 */

import GameStates.GameState;
import GameStates.Play;
import GameStates.Menu;
import GameWindow.GameWindow;
import GameWindow.GamePanel;
import Graphics.Constants.GameCONST;

import java.awt.*;

public class Game implements Runnable {

    public final static int TILE_SIZE = (int) (GameCONST.TILE_DEFAULT_SIZE * GameCONST.SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * GameCONST.WIDTH_TILES;
    public final static int GAME_HEIGHT = TILE_SIZE * GameCONST.HEIGHT_TILES;
    //private final GameWindow gameWindow;  // Fereastra de joc
    private final GamePanel gamePanel;
    //private Thread gameThread; // referinta catre thread-ul de game loop
    private Play play;
    private Menu menu;

    public Game() {

        init();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); // gets input focus
        start();

    }

    private void init() {
        menu = new Menu(this);
        play = new Play(this);
    }

    private void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU -> menu.update();
            case PLAY -> play.update();
            case OPTIONS, QUIT -> System.exit(0);
        }
    }

    public void draw(Graphics g) {
        switch (GameState.state) {
            case MENU -> menu.draw(g);
            case PLAY -> play.draw(g);
        }
    }

    // game loop running
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / GameCONST.FPS; // nanosecunde
        double timePerUpdate = 1000000000.0 / GameCONST.UPS; // intervalul de timp intre doua update-uri

        long lastTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();


        double dUpdates = 0; // delta updates
        double dFrames = 0; // delta frames

        while (true) // infinite loop
        {
            long currentTime = System.nanoTime();

            dUpdates = dUpdates + (currentTime - lastTime) / timePerUpdate;
            dFrames = dFrames + (currentTime - lastTime) / timePerFrame;
            lastTime = currentTime;

            if (dUpdates >= 1) {
                // update the game
                update();
                updates += 1;
                dUpdates -= 1;
            }

            if (dFrames >= 1) {
                gamePanel.repaint();
                dFrames -= 1;
                frames += 1;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("fps = " + frames + " ups = " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.state == GameState.PLAY) {
            Play.getPlayer().resetDirection();
        }
    }

    public Menu getMenu() {
        return menu;
    }


    public Play getPlay() {
        return play;
    }
}
