

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

public class Screen extends JFrame {

    private static final int BASE_WIDTH = 400;
    private static final int BASE_HEIGHT = 300;

    private final KeyHandler keyHandler = new KeyHandler();
    private final LevelPanel levelPanel;

    public Screen() {
        // Create the initial screen for the game
        setTitle("Initial Screen");

        // Set default values for frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BASE_WIDTH, BASE_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        levelPanel = new LevelPanel(keyHandler, "/assets/testing-level.png", 500, 500);
        add(levelPanel, BorderLayout.CENTER);

        // Ensure the frame receives keyboard input after opening
        SwingUtilities.invokeLater(levelPanel::requestFocusInWindow);
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
}
