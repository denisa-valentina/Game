package Main;


/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)
 */

import GameWindow.GameWindow;
import GameWindow.GamePanel;
import Objects.Player;

import java.awt.*;

public class Game implements Runnable{

    private GameWindow gameWindow;  // Fereastra de joc
    private GamePanel gamePanel;
    private Thread gameThread; // referinta catre thread-ul de game loop
    private final int UPS = 200; // updates/second
    private final int FPS = 120; // frames/second
    private Player player;


    public Game() {
        initAll();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); // gets input focus
        start();

    }
    public Player getPlayer()
    {
        return player;
    }

    private void initAll() {

        // x:200, y:200 - pozitia initiala
        player = new Player(200, 200, 128, 128, 8);
    }

    private void start()
    {
        gameThread= new Thread(this);
        gameThread.start();
    }

    public void update()
    {
        //gamePanel.updateGame();
        player.update();
    }

    public void render(Graphics obj)
    {
        player.render(obj);
    }

    // game loop running
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS; // nanosecunde
        double timePerUpdate = 1000000000.0 / UPS; // intervalul de timp intre doua update-uri

        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        int updates = 0;
        long lastTime = System.nanoTime();

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
