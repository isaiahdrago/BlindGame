import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Displays the imported image used as the testing level. */
public class LevelPanel extends JPanel {
    private BufferedImage levelImage;
    private final KeyHandler keyHandler;
    private final Player player = new Player();
    public Set<Color> colorsRight = new HashSet<>();
    public Set<Color> colorsLeft = new HashSet<>();
    public Set<Color> colorsTop = new HashSet<>();
    public Set<Color> colorsBottom = new HashSet<>();

    public LevelPanel(KeyHandler keyHandler, String level, int x, int y) {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(keyHandler);
        loadLevelImage(level, x, y);
        this.keyHandler = keyHandler;

        // Update movement and repaint roughly 60 times per second.
        new Timer(16, event -> {
            // Read the colors at the player's current position before moving.
            getPlayerContactedColors();
            player.update(keyHandler, colorsRight, colorsLeft, colorsTop, colorsBottom);
            repaint();
        }).start();
    }

    private void loadLevelImage(String level, int x, int y) {
        try {
            // Works when the image is packaged as a classpath resource.
            var resource = getClass().getResourceAsStream(level);
            if (resource != null) {
                player.setPosition(x, y);
                levelImage = ImageIO.read(resource);
                resource.close();
                return;
            }

            levelImage = ImageIO.read(new File("src/assets/testing-level.png"));
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not load " + level, exception);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (levelImage != null) {
            // Scale the level image to the current panel size.
            g.drawImage(levelImage, 0, 0, getWidth(), getHeight(), this);
            player.draw(g, getWidth(), getHeight());
        }
    }

    public Set<Color> getPlayerContactedColors() {
        Set<Color> playerContacted = new HashSet<>();
        // Player coordinates are logical coordinates in a 400 x 300 level.
        int playerX = player.x * getWidth() / 400;
        int playerY = player.y * getHeight() / 300;
        int playerWidth = Math.max(1, player.width * getWidth() / 400);
        int playerHeight = Math.max(1, player.height * getHeight() / 300);
        colorsBottom.clear();
        colorsLeft.clear();
        colorsRight.clear();
        colorsTop.clear();

        // Check every pixel on the top and bottom edges.
        for (int x = playerX + 10; x < playerX + playerWidth - 10; x++) {
            addColorAtScreenPosition(colorsTop, x, playerY);
            addColorAtScreenPosition(colorsBottom, x, playerY + playerHeight - 1);
        }


        // Check every pixel on the left and right edges.
        for (int y = playerY + 10; y < playerY + playerHeight - 10; y++) {
            addColorAtScreenPosition(colorsLeft, playerX, y);
            addColorAtScreenPosition(colorsRight, playerX + playerWidth - 1, y);
        }

        playerContacted.addAll(colorsBottom);
        playerContacted.addAll(colorsRight);
        playerContacted.addAll(colorsTop);
        playerContacted.addAll(colorsLeft);

        return playerContacted;
    }

    private void addColorAtScreenPosition(Set<Color> colors, int screenX, int screenY) {
        if (screenX < 0 || screenX >= getWidth()
                || screenY < 0 || screenY >= getHeight()) {
            return;
        }

        int imageX = screenX * levelImage.getWidth() / getWidth();
        int imageY = screenY * levelImage.getHeight() / getHeight();
        colors.add(new Color(levelImage.getRGB(imageX, imageY), true));
    }
}
