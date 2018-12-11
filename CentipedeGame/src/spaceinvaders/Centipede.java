/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Rectangle;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author Lenovo
 */
public class Centipede extends Sprite implements Commons{
    
    public int health;
    //public final Point2D centipedeSegment[] = new Point2D[ALL_DOTS];
    //public final int centipedeSegmentX[] = new int[ALL_DOTS];
    //public final int centipedeSegmentY[] = new int[ALL_DOTS];
    
    public int centSegmentX;
    public int centSegmentY;
    //public Centipede cHead;
    
    
    private final String centImgDot = "purpleDot_Small.png";
    
    Centipede(int x, int y){
        health = 2;
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(centImgDot);
        setImage(ii.getImage());
    }
     
    public int act(int direction, int downFlag) {
        if (downFlag == 1){
            downFlag = 0;
//            this.y += 1;
            this.x += (direction)*DOT_SIZE;
        }
        else{
        this.x += direction*DOT_SIZE;
        }
        return downFlag;
       // move(c);
    }
    
    public void move(ArrayList<Centipede> c) {
        
        for(int i= c.size()-1; i > 0; i--){
                //for (int z = dots; z > 0; z--) {
                c.get(i).setX(c.get(i-1).getX());
                //x[z] = x[(z - 1)];
                c.get(i).setY(c.get(i-1).getY());
                //y[z] = y[(z - 1)];
            }
    }
    
    public Rectangle getBounds()
    {
           return new Rectangle(x, y, 30, 30);
    }
    
    
    
    
    
}
