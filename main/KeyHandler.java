package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed; 
    // DEBUG
    boolean cekDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp; // instance game panel
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // fungsi tombol di tekan
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // PLAY STATE
        if (gp.gameState == gp.playState) {
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
            } if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState; // jika game di pause
            } if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // DEBUG
            if (code == KeyEvent.VK_T) {
                if (cekDrawTime == false) {
                    cekDrawTime = true;
                } else if (cekDrawTime == true) {
                    cekDrawTime = false;
                }
            }
        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState; // jika game di play
            }
        }
        
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
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
