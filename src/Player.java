import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player {
    
    private int health;
    private int hunger;
    private int speed;
    private int x;
    private int y;
    public int width;
    public int height;

    public Player() {
        health = 10;
        hunger = 0;
        x = 0;
        y = 0;
        width = 10;
        height = 10;
        speed = 3;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed;
        }
        if (keyHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed;
        }
        if (keyHandler.isKeyPressed(KeyEvent.VK_UP)) {
            y -= speed;
        }
        if (keyHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += speed;
        }
    }

    public void draw(Graphics g, int width, int height) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, this.width * width / 400, this.height * height / 300);
    }

    public Set<Color>
}
