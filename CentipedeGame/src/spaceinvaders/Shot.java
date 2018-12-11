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

public class Shot extends Sprite {

    //private final String shotImg = "src/images/shot.png";
    private final String shotImg = "orangeDot_Small2.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
        x = 727;
        y = 0;
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());
        
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}
