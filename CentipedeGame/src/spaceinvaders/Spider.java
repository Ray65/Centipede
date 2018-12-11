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
import javax.swing.ImageIcon;
public class Spider extends Sprite implements Commons {
    private final String bombImg = "spider.png";
    public int health;
    public int SpeedCounter;
    //public int x;
    //public int y;
        private boolean destroyed;

        public Spider(int x, int y) {

            initBomb(x,y);
        }

        private void initBomb(int x, int y) {

            //setDestroyed(true);
            setDestroyed(false);
            health = 2;
            this.x = x;
            this.y = y;
            SpeedCounter = 0;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }
        
        public void getNextPos(){
            
//            if(SpeedCounter++ == 2000)

            if(SpeedCounter++ == 5)
            {
                int r = (int) (Math.random() * (RAND_POS_X-2)+1);
                this.x = ((r * DOT_SIZE));
        
                r = (int) (Math.random() * (RAND_POS_Y)+1);
                this.y = ((r * DOT_SIZE));
            
                SpeedCounter = 0;
            
            }
            
        }
        
        public void setDestroyed(boolean destroyed) {
        
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
        
            return destroyed;
        }
    
}
