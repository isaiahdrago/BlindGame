import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    private static JFrame frame;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            frame = new Screen("testing-level.png");
            frame.setVisible(true);
        });
    }
}
