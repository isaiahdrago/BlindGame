import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Set;

public class Player {

    private int health;
    private int hunger;
    private int speed;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean iFrames;
    private int iFramesCount = 0;
    private int runDirection = 0;
    private boolean dead;

    public Player() {
        health = 10;
        hunger = 0;
        x = 0;
        y = 0;
        width = 10;
        height = 10;
        speed = 2;
        dead = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(KeyHandler keyHandler,
            Set<Color> contacted,
            Set<Color> colorsRight,
            Set<Color> colorsLeft,
            Set<Color> colorsTop,
            Set<Color> colorsBottom) {
        if (dead == true) {
            return;
        }
        if (contacted.contains(new Color(237, 28, 36))) {
            health -= 5;
            if (health == 0) {
                dead = true;
                return;
            }
            iFrames = true;
            iFramesCount = 30;
            if (colorsRight.contains(new Color(237, 28, 36))) runDirection = 1;
            if (colorsLeft.contains(new Color(237, 28, 36))) runDirection = 2;
            if (colorsBottom.contains(new Color(237, 28, 36))) runDirection = 3;
            if (colorsTop.contains(new Color(237, 28, 36))) runDirection = 4;
        }
        if (iFrames == true) {
            iFramesCount--;
            if (runDirection <= 2) {
                if (runDirection == 1) {
                    x -= speed * 2;
                } else {
                    x += speed * 2;
                }
            } else {
                if (runDirection == 3) {
                    y -= speed * 2;
                } else {
                    y += speed * 2;
                }
            }
            if (iFramesCount == 0) {
                iFrames = false;
            }
        } else {
            if (keyHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
                if (!colorsLeft.contains(new Color(127, 127, 127))) {
                    x -= speed;
                } else {
                    x += speed;
                }
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
                if (!colorsRight.contains(new Color(127, 127, 127))) {
                    x += speed;
                } else {
                    x -= speed;
                }
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_UP)) {
                if (!colorsTop.contains(new Color(127, 127, 127))) {
                    y -= speed;
                } else {
                    y += speed;
                }
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
                if (!colorsBottom.contains(new Color(127, 127, 127))) {
                    y += speed;
                } else {
                    y -= speed;
                }
            }
        }
    }

    public void draw(Graphics g, int width, int height) {
        g.setColor(Color.MAGENTA);
        int screenX = x * width / 400;
        int screenY = y * height / 300;
        int screenWidth = Math.max(1, this.width * width / 400);
        int screenHeight = Math.max(1, this.height * height / 300);
        g.fillRect(screenX, screenY, screenWidth, screenHeight);
    }
}
