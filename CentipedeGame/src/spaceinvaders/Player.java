/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *
 * @author Lenovo
 */
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {
    public int lives = 3;
    
    public final int START_Y = ((Y_DIM_MAX-1) * DOT_SIZE);
    public final int START_X = ((X_DIM_MAX/2)*DOT_SIZE);

    //private final String playerImg = "src/images/player.png";
    private final String playerImg = "PoopEmoji_Small.png";
    private int width;
    private int height;
    
    private boolean PlayerLeftDirection = false;
    private boolean PlayerRightDirection = false;
    private boolean PlayerUpDirection = false;
    private boolean PlayerDownDirection = false;

    public Player() {

        initPlayer();
    }

    private void initPlayer() {
        
        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }
    
    //'x' & 'dx' comes from Sprite.java
    public void act() {
        
        x += dx;
        //y += dy;
        
        if (x <= 2) {
            x = 2;
        }
        /*
        if (y <= 2) {
            y = 2;
        }*/
        
        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
        /*
        if (y >= BOARD_HEIGHT - 2 * height) {
            y = BOARD_HEIGHT - 2 * height;
        }*/
    }
    
    
    public void movePlayer() {

        

        if (PlayerLeftDirection) {
            x -= dx;
        }

        if (PlayerRightDirection) {
            x += dx;
        }

        if (PlayerUpDirection) {
            y -= dy;
        }

        if (PlayerDownDirection) {
            y += dy;
        }
    }
    
    
    //'x' & 'dx' comes from Sprite.java
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        /*
        if (key == KeyEvent.VK_LEFT) {
        
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 2;
        }
        
        if (key == KeyEvent.VK_DOWN) {
        
            dy = -2;
        }

        if (key == KeyEvent.VK_UP) {
        
            dy = 2;
        }*/
        if (key == KeyEvent.VK_LEFT) {
            //p.PlayerX -= DOT_SIZE;
            //x -= DOT_SIZE;
            PlayerLeftDirection = true;
            dx = 2;
            System.out.println("Left keypress detected.");
            
        }

        if (key == KeyEvent.VK_RIGHT) {
            //x += DOT_SIZE;
            PlayerRightDirection = true;
            dx = 2;
            System.out.println("Right keypress detected.");
        }

        if (key == KeyEvent.VK_UP) {
            //y -= DOT_SIZE;
            PlayerUpDirection = true;
            dy = 2;
            System.out.println("Up keypress detected.");
        }

        if (key == KeyEvent.VK_DOWN) {
            //y += DOT_SIZE;
            PlayerDownDirection = true;
            dy = 2;
            System.out.println("Down keypress detected.");
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
        
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
        
            dy = 0;
        }
    }
}