import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Displays the imported image used as the testing level. */
public class LevelPanel extends JPanel {
    private Image levelImage;
    private final KeyHandler keyHandler;
    private final Player player = new Player();

    public LevelPanel(KeyHandler keyHandler, String level, int x, int y) {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(keyHandler);
        loadLevelImage(level, x, y);
        this.keyHandler = keyHandler;

        // Update movement and repaint roughly 60 times per second.
        new Timer(16, event -> {
            player.update(keyHandler);
            repaint();
        }).start();

        // Update movement and repaint roughly 60 times per second.
        new Timer(16, event -> {
            player.update(keyHandler);
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
}
