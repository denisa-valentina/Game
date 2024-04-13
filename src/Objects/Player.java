package Objects;

import LoadSave.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Graphics.Constants.Player.*;

public class Player extends Entity {

    private static Player playerInstance;
    private BufferedImage[][] animations;
    private int animationStick, animationIndex, animationSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 1.0f;

    private Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        //loadImage();
        loadAnimations();
    }

    public static Player getInstance(float x, float y, int width, int height)
    {
        if(playerInstance == null)
        {
            playerInstance = new Player(x, y, width, height);
        }
    return playerInstance;
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics obj) {
        obj.drawImage(animations[playerAction][animationIndex], (int)getX(), (int)getY(), getWidth(), getHeight(), null);
    }

    /*void loadImage() {
        InputStream []input = new InputStream[6]; // 6 animatii
        input[0] = getClass().getResourceAsStream("/Idle.png");
        input[1] = getClass().getResourceAsStream("/Run.png");
        input[2] = getClass().getResourceAsStream("/Jump.png");
        input[3] = getClass().getResourceAsStream("/Dead.png");
        input[4] = getClass().getResourceAsStream("/Attack_1.png");
        input[5] = getClass().getResourceAsStream("/Attack_2.png");

        try {
            images = new BufferedImage[6];
            for(int i=0;i<input.length;++i) {
                images[i] = ImageIO.read(input[i]);
            }
            //loadAnimations();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for(int i=0;i<input.length;++i) {
                    input[i].close();  // trebuie sa si inchidem InputStream-ul, dar in cazul in care nu a fost
                    // deschis cu succes vom utiliza un alt bloc de try-catch
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    private void loadAnimations() {
        BufferedImage []images = new BufferedImage[6];
        // 6 animations: Idle, Run, Jump, Attack1, Attack2, Dead
        images[0] = LoadSave.getImage("/player/Idle.png");
        images[1] = LoadSave.getImage("/player/Run.png");
        images[2] = LoadSave.getImage("/player/Jump.png");
        images[3] = LoadSave.getImage("/player/Dead.png");
        images[4] = LoadSave.getImage("/player/Attack_1.png");
        images[5] = LoadSave.getImage("/player/Attack_2.png");
        animations = new BufferedImage[6][10];
        for (int i = 0; i < animations.length-2; ++i) {
            for (int j = 0; j < 8; ++j) {
                animations[i][j] = images[i].getSubimage(j * getWidth(), 0, getWidth(), getHeight());
            }
        }
        for(int i=4;i<animations.length;++i)
        {
            for(int j=0;j<10;++j)
            {
                animations[i][j] = images[i].getSubimage(j*getWidth(), 0, getWidth(), getHeight());
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUN;
            attacking = false;
        } else {
            playerAction = IDLE;
        }

        if(attacking) {
            playerAction = ATTACK_1;
        }

        if(startAnimation != playerAction)
        {
            resetAnimationTick();
        }
    }
    private void updateAnimationTick() {

        animationStick += 1;
        if (animationStick >= animationSpeed) {
            animationStick = 0;
            animationIndex += 1;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void resetAnimationTick() {
        animationIndex = 0;
        animationStick = 0;
    }


    private void updatePosition() {
        moving = false;
        float x = getX();
        float y = getY();
        if (left && !right) {
            x = getX();
            setX(x - playerSpeed);
            moving = true;
        } else if (right && !left) {
            setX(x + playerSpeed);
            moving = true;
        }

        if (up && !down) {
            setY(y - playerSpeed);
            moving = true;
        } else if (down && !up) {
            setY(y + playerSpeed);
            moving = true;
        }
    }

    public void resetDirection() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
