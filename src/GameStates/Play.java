package GameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import Characters.EnemyManager;
import LevelMap.LevelHandler;
import Load.Load;
import Main.Game;
import Objects.ObjectManager;
import Characters.Player;
import Graphics.Constants.GameCONST;
import Graphics.Constants.Surroundings;
import UserInterface.CompletedLevel;
import UserInterface.GameOver;
import UserInterface.Pause;
import Load.DataBase;

public class Play extends State implements StateMethods {
    private static Player player;
    private static DataBase dataBase;
    private LevelHandler levelHandler;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private GameOver gameOver;
    private Pause pause;
    private CompletedLevel completedLevel;
    private BufferedImage bigCloud, smallCloud;

    private int xLevelOffset;
    private int[] smallClouds;

    private boolean isGameOver;
    private boolean isPaused = false;
    private boolean islevelCompleted = false;
    private boolean load = false
            ;

    public Play(Game game) {
        super(game);

        init();
        addElements();
    }

    public void initGame(){
        getPlayer().setScore(0);
        getPlayer().resetHealth();
        loadGame(0, 5, 0, 200, 200);
    }

    public void loadNextLevel() {
        getPlayer().increaseScore(getLevelHandler().getCurrentLevel().getLevelScore());
        resetAll();
        levelHandler.loadNextLevel();
    }

    public void loadGame(int levelIndex, int hearts, int score, int xPos, int yPos){
        resetAll();
        levelHandler.loadGame(levelIndex, hearts, score, xPos, yPos);
    }

    private void addElements() {
        Random random = new Random();

        bigCloud = Load.getImage(Surroundings.Images.bigClouds);
        smallCloud = Load.getImage(Surroundings.Images.smallClouds);

        smallClouds = new int[15];
        for (int i = 0; i < smallClouds.length; ++i) {
            smallClouds[i] = (int) (100 * GameCONST.SCALE) + random.nextInt((int) (150 * GameCONST.SCALE));
        }
    }

    private void init() {
        levelHandler = new LevelHandler();
        player = Player.getInstance(200 * GameCONST.SCALE, 170 * GameCONST.SCALE, (int) (GameCONST.SCALE * 128), (int) (GameCONST.SCALE * 128), this);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player.loadLevelMatrix(levelHandler.getLevel(levelHandler.getLevelIndex()).getGroundLayer().getLayerMatrix());

        dataBase = DataBase.getInstance();
        gameOver = new GameOver(this);
        pause = new Pause(this);
        completedLevel = new CompletedLevel(this);
    }

    public static Player getPlayer() {
        return player;
    }

    @Override
    public void update() {

        if (isPaused) {
            pause.update();
        } else if (islevelCompleted) {
            System.out.println("Congratulations! You finished the game!");
            completedLevel.update();
        } else if (isGameOver) {
            gameOver.update();
        } else if (player.getIsDead()) {
            player.update();
        } else {
            if(load) {
                List<Integer> values = dataBase.loadGame();
                loadGame(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                load = false;
            }

            objectManager.update();
            player.update();
            enemyManager.update(levelHandler.getCurrentLevel().getGroundLayer().getLayerMatrix(), player);
            isCloseToBorder();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(levelHandler.getLevel(levelHandler.getLevelIndex()).getBackGround(), 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);

        objectManager.draw(g, xLevelOffset);
        levelHandler.draw(g, xLevelOffset);
        player.draw(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);

        if (isGameOver) {
            gameOver.draw(g);
        }
        if (isPaused) {
            pause.draw(g);
        }
        if (islevelCompleted) {
            completedLevel.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 7; ++i) {
            g.drawImage(bigCloud, i * Surroundings.BIG_CLOUD_WIDTH - (int) (xLevelOffset * 0.3), (int) (70 * GameCONST.SCALE), Surroundings.BIG_CLOUD_WIDTH, Surroundings.BIG_CLOUD_HEIGHT, null);
        }
        for (int i = 0; i < smallClouds.length; ++i) {
            g.drawImage(smallCloud, 4 * i * Surroundings.SMALL_CLOUD_WIDTH - (int) (xLevelOffset * 0.6), (int) (smallClouds[i] * GameCONST.SCALE), Surroundings.SMALL_CLOUD_WIDTH, Surroundings.SMALL_CLOUD_HEIGHT, null);
        }
    }

    private void isCloseToBorder() {
        int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
        int rightBorder = (int) (0.7 * Game.GAME_WIDTH);

        int levelTileWide = levelHandler.getLevel(levelHandler.getLevelIndex()).getGroundLayer().getLayerMatrix()[0].length;
        int maxTilesOffset = levelTileWide - GameCONST.WIDTH_TILES;
        int maxOffset = maxTilesOffset * Game.TILE_SIZE;

        int playerX = (int) player.getCollisionBox().x;
        int delta = playerX - xLevelOffset;

        if (delta > rightBorder) {
            xLevelOffset += delta - rightBorder;
        } else if (delta < leftBorder) {
            xLevelOffset += delta - leftBorder;
        }

        if (xLevelOffset > maxOffset) {
            xLevelOffset = maxOffset;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    // reseteaza tot in cazul in care jucatorul a pierdut. reset: player, enemy etc.
    public void resetAll() {
        isGameOver = false;
        isPaused = false;
        islevelCompleted = false;
        player.setIsDead(false);
        player.resetAll();
        enemyManager.resetAll();

        objectManager.resetAll(levelHandler.getLevelIndex());
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkObjectTouched(Rectangle2D.Float collisionBox) {
        objectManager.checkObjectTouched(collisionBox);
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public EnemyManager getEnemyManager() { return enemyManager; }

    public static DataBase getDataBase() { return dataBase; }

    public void setLoad(boolean load) { this.load = load; }

    public void unpauseGame() { isPaused = false; }

    public void setGameOver(boolean gameOver) { this.isGameOver = gameOver; }

    public void setLevelCompleted(boolean levelCompleted) { this.islevelCompleted = levelCompleted; }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) {
            gameOver.keyPressed(e);
            Play.getPlayer().resetHealth();
            resetAll();
        } else {
            switch ((e.getKeyCode())) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_W -> player.setJump(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_K -> player.setAttacking1(true);
                case KeyEvent.VK_L -> player.setAttacking2(true);
                case KeyEvent.VK_ESCAPE -> GameState.state = GameState.MENU;
                case KeyEvent.VK_P -> isPaused = !isPaused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isGameOver) {

            switch ((e.getKeyCode())) {
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_W -> player.setJump(false);
                case KeyEvent.VK_D -> player.setRight(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isGameOver) {
            if (isPaused) {
                pause.mousePressed(e);
            } else if (islevelCompleted) {
                completedLevel.mousePressed(e);
            }
        } else {
            gameOver.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isGameOver) {
            if (isPaused) {
                pause.mouseReleased(e);
            } else if (islevelCompleted) {
                completedLevel.mouseReleased(e);
            }
        } else {
            gameOver.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!isGameOver) {

            if (isPaused) {
                pause.mouseMoved(e);
            } else if (islevelCompleted) {
                completedLevel.mouseMoved(e);
            }
        } else {
            gameOver.mouseMoved(e);
        }
    }

}
