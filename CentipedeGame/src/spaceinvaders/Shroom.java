/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class Shroom extends Sprite{
    public int health;
    public int ShroomX;
    public int ShroomY;
    private Bomb bomb;
    private final String shroomImg = "blueMushroom_Small.png";
    
    Shroom(){
        health = 3;
        ShroomX = 0;
        ShroomY = 0;
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(shroomImg);
        setImage(ii.getImage());
    }
    public Rectangle getBounds()
        {
           return new Rectangle(x, y, 30, 30);
        }
    
    public Bomb getBomb() {
        
        return bomb;
    }

    public class Bomb extends Sprite {

        private final String bombImg = "bomb.png";
        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }

        public void setDestroyed(boolean destroyed) {
        
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
        
            return destroyed;
        }
        
        
    }
}
