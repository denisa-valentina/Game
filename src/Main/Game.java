package Main;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)
 */

import GameWindow.GameWindow;
import GameWindow.GamePanel;
import Levels.LevelHandler;
import Objects.Player;

import java.awt.*;

public class Game implements Runnable{

    private final int UPS = 200; // updates/second
    private final int FPS = 120; // frames/second
    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float SCALE = 1f;
    public final static int WIDTH_TILES = 60;
    public final static int HEIGHT_TILES = 20;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * WIDTH_TILES;
    public final static int GAME_HEIGHT = TILE_SIZE * HEIGHT_TILES;
    private GameWindow gameWindow;  // Fereastra de joc
    private GamePanel gamePanel;
    private Thread gameThread; // referinta catre thread-ul de game loop
    private LevelHandler levelHandler;
    private static Player player;


    public Game() {
        init();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); // gets input focus
        start();

    }
    public Player getPlayer()
    {
        return player;
    }

    private void init() {
        levelHandler = new LevelHandler(this);
        // x:200, y:200 - pozitia initiala
        player = Player.getInstance(200*SCALE, 170*SCALE, (int)(SCALE*128), (int)(SCALE*128));
        player.loadLevelData(levelHandler.getLevel().getLevelMatrix());
    }

    private void start()
    {
        gameThread= new Thread(this);
        gameThread.start();
    }

    public void update()
    {
        levelHandler.update();
        player.update();

    }

    public void render(Graphics obj)
    {
        levelHandler.draw(obj);
        player.render(obj);
    }

    // game loop running
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS; // nanosecunde
        double timePerUpdate = 1000000000.0 / UPS; // intervalul de timp intre doua update-uri

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
    public void windowFocusLost()
    {
        player.resetDirection();
    }
}
