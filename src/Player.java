import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Set;

public class Player {

    private static final int BASE_SPEED = 2;
    private static final int BASE_BREATH = 200;
    private static final int BASE_HEALTH = 10;

    private int health;
    private int breath;
    private int speed;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean iFrames;
    private int iFramesCount = 0;
    private int runDirection = 0;

    public Player() {
        health = BASE_HEALTH;
        breath = BASE_BREATH;
        x = 0;
        y = 0;
        width = 10;
        height = 10;
        speed = BASE_SPEED;
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
        if (health == 0) {
            return;
        }
        if (contacted.contains(new Color(0, 162, 232)))  {
            speed = BASE_SPEED / 2;
            if (breath > 0) {
                breath--;
            } else {
                health--;
            }
        } else {
            speed = BASE_SPEED;
            breath = BASE_BREATH;
        }

        if (contacted.contains(new Color(237, 28, 36))) {
            health -= 5;
            if (health == 0) {
                return;
            }
            iFrames = true;
            iFramesCount = 15;
            if (colorsBottom.contains(new Color(237, 28, 36)))
                runDirection = 3;
            if (colorsTop.contains(new Color(237, 28, 36)))
                runDirection = 4;
            if (colorsRight.contains(new Color(237, 28, 36)))
                runDirection = 1;
            if (colorsLeft.contains(new Color(237, 28, 36)))
                runDirection = 2;
        }

        if (iFrames == true) {
            iFramesCount--;
            if (runDirection <= 2) {
                if (runDirection == 1) {
                    move(-speed * 2, 0, colorsLeft);
                } else {
                    move(speed * 2, 0, colorsRight);
                }
            } else {
                if (runDirection == 3) {
                    move(0, -speed * 2, colorsTop);
                } else {
                    move(0, speed * 2, colorsBottom);
                }
            }
            if (iFramesCount == 0) {
                iFrames = false;
            }
        } else {
            if (keyHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
                move(-speed, 0, colorsLeft);
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
                move(speed, 0, colorsRight);
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_UP)) {
                move(0, -speed, colorsTop);
            }
            if (keyHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
                move(0, speed, colorsBottom);
            }
        }
    }

    private void move(int dx, int dy, Set<Color> colors) {
        if (!colors.contains(new Color(127, 127, 127))) {
            x += dx;
            y += dy;
        } else {
            x -= dx;
            y -= dy;
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
