import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Receives keyboard input and makes it available to the rest of the game. */
public class KeyHandler implements KeyListener {

    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();
    private volatile int lastKeyCode = KeyEvent.VK_UNDEFINED;
    private volatile char lastKeyChar = KeyEvent.CHAR_UNDEFINED;

    @Override
    public void keyPressed(KeyEvent event) {
        lastKeyCode = event.getKeyCode();
        lastKeyChar = event.getKeyChar();
        pressedKeys.add(lastKeyCode);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        pressedKeys.remove(event.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent event) {
        lastKeyChar = event.getKeyChar();
    }

    /** Returns true while the specified key is held down. */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /** Returns the key code from the most recent key press. */
    public int getLastKeyCode() {
        return lastKeyCode;
    }

    /** Returns the character from the most recent keyboard input. */
    public char getLastKeyChar() {
        return lastKeyChar;
    }
}
