package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed; 

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // fungsi tombol di tekan
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // tekan tombol WASD
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    // fungsi tombol di lepas
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // tekan tombol WASD
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
    
}
