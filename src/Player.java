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

    public Player() {
        health = 10;
        hunger = 0;
        x = 0;
        y = 0;
        width = 10;
        height = 10;
        speed = 1;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(KeyHandler keyHandler,
            Set<Color> colorsRight,
            Set<Color> colorsLeft,
            Set<Color> colorsTop,
            Set<Color> colorsBottom) {
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

    public void draw(Graphics g, int width, int height) {
        g.setColor(Color.MAGENTA);
        int screenX = x * width / 400;
        int screenY = y * height / 300;
        int screenWidth = Math.max(1, this.width * width / 400);
        int screenHeight = Math.max(1, this.height * height / 300);
        g.fillRect(screenX, screenY, screenWidth, screenHeight);
    }
}
