package seriousgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean leftPressed;
    public boolean rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_LEFT) leftPressed = true;
        if(code == KeyEvent.VK_RIGHT) rightPressed = true;
        if(code == KeyEvent.VK_SPACE) {
            if(gp.gameState == gp.playState) gp.gameState = gp.pauseState;
            else if(gp.gameState == gp.pauseState) gp.gameState = gp.playState;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_LEFT) leftPressed = false;
        if(code == KeyEvent.VK_RIGHT) rightPressed = false;

    }
}
