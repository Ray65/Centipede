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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.*;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.*;

import sun.audio.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens = new ArrayList<>();;
    //aliens = new ArrayList<>();
    public ArrayList<Centipede> c;
    public ArrayList<ArrayList<Centipede>> CentiSegments = new ArrayList<ArrayList<Centipede>>();
    Centipede cHead;
    public int CentSpeedCounter = 0;
    public int SpidSpeedCounter = 0;
    int CentShot = 0;
    public Image shroom;
    public JLabel GameInfo;
    
    public boolean isCentipedeDead = false;
    public boolean isSpiderDead = false;
    public boolean allShroomsDead = false;
    
    //public String gunSound = "C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\SpaceInvaders\\shotgun.wav";
    public String gunSound = "shotgun.wav";
    
    
    
    Shroom[] allShrooms_Arr = new Shroom[NUMBER_OF_SHROOMS];
    ArrayList<Shroom> allShrooms = new ArrayList<>();
    public ArrayList<Integer> allShroomsX = new ArrayList<>();
    public ArrayList<Integer> allShroomsY = new ArrayList<>();
    public boolean shroomLocator = false;
    
    private Player player;
    private Shot shot;
    public Spider s;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    //private int direction = -1;
    private int direction = 1;
    public int downFlag = 0;
    //private int deaths = 0;
    public int score = 0;
//    public int score = -1;

    private boolean ingame = true;
    //private final String explImg = "src/images/explosion.png";
    private final String explImg = "expl_Small.png";
    
    
    private String message = "Game Over";

    private Thread animator;

    public Board(JLabel GameInfo) {

        initBoard(GameInfo);
    }

    private void initBoard(JLabel GInfo) {

        this.GameInfo = new JLabel();
        GameInfo = GInfo;
        //addKeyListener(new TAdapter());
        TAdapter tadapt = new TAdapter() ;
        addMouseListener(tadapt);
        addMouseMotionListener(tadapt);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    @Override
    public void addNotify() {

        super.addNotify();
//        gameInit();
    }

    public void gameInit() {
        
        
        //Centipede cArr = new Centipede();
        c = new ArrayList<>();
        CentiSegments.add(c);
        for (int z = 0; z < DOTS; z++) {
            
            //Centipede cSeg = new Centipede(120-z*30, 0);
            Centipede cSeg = new Centipede(((DOTS-1)*30)-z*30, 0);
            c.add(cSeg);
        }
        //CREATING CENTIPEDE HEAD
        cHead = c.get(0);
               
        

        player = new Player();
        shot = new Shot();
        s = new Spider(0,0);

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }
    
    public void drawCent(Graphics g, ArrayList<Centipede> ce) {

//        Iterator it = ce.iterator();
//        System.out.println("Inside drawCent");
        //System.out.println("Size "+ce.size());
//        for (Centipede cent: ce) {
        for (int h=0; h<ce.size(); h++)
        {
            Centipede cent = ce.get(h);
//                    System.out.println("Inside drawCent for");

            if (cent.isVisible()) {
//                        System.out.println("Inside drawCent isVisible");
                g.drawImage(cent.getImage(), cent.getX(), cent.getY(), this);
            }

            if (cent.isDying()) {
                cent.health--;
                if(cent.health == 0){
                                        
                    cent.die(); //Needs to be changed to split till no centipede segments are left
                }
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        /*
        if (player.isDying()) {
            player.lives--;
            //Restoring health of shrooms
            for (Shroom s: allShrooms){
                    if((s.health == 1) || (s.health == 2))
                    {
                       s.health = 3;
                    }
            }
            if(player.lives == 0)
            {
                player.die();
                ingame = false;
            }
        }*/
    }

    public void drawShot(Graphics g) {

        if (shot.isVisible()) {
            
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    public void drawBombing(Graphics g) {
//        System.out.println("In drawBomb");
        int x;
        int y;
        
        int r = (int) (Math.random() * (RAND_POS_X-2)+1);
        x = ((r * DOT_SIZE));
        
        r = (int) (Math.random() * (RAND_POS_Y)+1);
        y = ((r * DOT_SIZE));
        
        
        if (!s.isDestroyed()) {
                
                //g.drawImage(s.getImage(), s.getX(), s.getY(), this);
                g.drawImage(s.getImage(), s.x, s.y, this);
//                System.out.println("After drawing" + s.x + s.y);
            }
    }
    
    public void locateShrooms(Graphics g) {
        if (shroomLocator == false)       
        {
            shroomLocator = true;
        
        for(int i=0; i < NUMBER_OF_SHROOMS; i++ )
        {            
            Shroom mm = new Shroom();
            //int r = (int) (Math.random() * (RAND_POS_X-2)+1);
            int r = (int) (Math.random() * (RAND_POS_X-8)+5);
            mm.ShroomX = ((r * DOT_SIZE));
                        
            r = (int) (Math.random() * (RAND_POS_Y)+1);
            mm.ShroomY = ((r * DOT_SIZE));
                        
            allShrooms_Arr[i] = mm;
            //allShrooms.add(mm);
            
            
        }
        
        //Shroom[] allShrooms_Arr = new Shroom[NUMBER_OF_SHROOMS];
        //Shroom[] allShrooms_Arr = allShrooms.toArray();
        int k;
            for(int j=0; j < NUMBER_OF_SHROOMS; j++ ){              
                
                
                for(k = 0; k < NUMBER_OF_SHROOMS; k++){
                    if(k == j)
                    {
                        continue;
                    }
                    if(allShrooms_Arr[j].ShroomX == allShrooms_Arr[k].ShroomX){
                        if(allShrooms_Arr[j].ShroomY == (allShrooms_Arr[k].ShroomY + DOT_SIZE)){
                            
                            allShrooms_Arr[k].ShroomY = (allShrooms_Arr[k].ShroomY + (3*DOT_SIZE));                           
                    
                        }
                        else if(allShrooms_Arr[j].ShroomY == (allShrooms_Arr[k].ShroomY - DOT_SIZE)){
                            
                            allShrooms_Arr[k].ShroomY = (allShrooms_Arr[k].ShroomY + (3*DOT_SIZE));                           
                    
                        }
                        
                    }
                    
                
                
            }}
            
            for(int b=0; b < NUMBER_OF_SHROOMS; b++){
                allShrooms.add(allShrooms_Arr[b]);
                allShroomsX.add(allShrooms_Arr[b].ShroomX);
                allShroomsY.add(allShrooms_Arr[b].ShroomY);
                
            }
//            System.out.println("allShroomsX:  "+ allShroomsX);
//            System.out.println("allShroomsY:  "+ allShroomsY);
                
        }
        //for(int j=0; j < (NUMBER_OF_SHROOMS-1); j++ ){
        for(Shroom s : allShrooms){
            //g.drawImage(shroom, allShrooms[j].ShroomX, allShrooms[j].ShroomY, this);
            if(s.isVisible()){
             g.drawImage(s.getImage(), s.ShroomX, s.ShroomY, this);
            }
            
            /*
            if (s.isDying()) {
                //s.health--;
                if(s.health == 0){
                    //Score update for spider
                    score = score + 600;
                    s.die();
                }
            }*/
        }
        
        Toolkit.getDefaultToolkit().sync();
    
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
//            for (ArrayList<Centipede> cen: CentiSegments)
            for(int b = 0; b < CentiSegments.size(); b++)
            {
                ArrayList<Centipede> cen = CentiSegments.get(b);
                drawCent(g, cen);
            }
            locateShrooms(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }
    
    public ArrayList<ArrayList<Centipede>> updateCentList(ArrayList<Centipede> ce){
        int idx = CentiSegments.indexOf(ce);
        CentiSegments.remove(idx);
        return CentiSegments;
        
    }
    
    public Player playerDeath(Player p){
        System.out.println("Before life update -- Player lives:  "+p.lives);
        if (p.isDying()) {
            p.lives--;
//            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
            allShrooms = GameReset(allShrooms);
            
            System.out.println("AllShrooms size after reset:  "+allShrooms.size());
            for (int f = 0; f<allShrooms.size(); f++){
                System.out.println("Shroom (after reset) #"+f+" has health --  "+allShrooms.get(f).health);
            }
            
            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
            
            //Restoring health of shrooms
            /*
            for (Shroom s: allShrooms){
                    if((s.health == 1) || (s.health == 2))
                    {
                       s.health = 3;
                    }
            }*/
        }
            if(p.lives == 0)
            {
                GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                p.die();
                ingame = false;
            }
       return p;
    }
    
    public ArrayList<Shroom> GameReset(ArrayList<Shroom> remShrooms){
        //Clearing previous CentiSegments
        System.out.println("GameReset is called");
        CentiSegments.clear();
        
        c = new ArrayList<>();
        CentiSegments.add(c);
        for (int z = 0; z < DOTS; z++) {
            
            //Centipede cSeg = new Centipede(120-z*30, 0);
            Centipede cSeg = new Centipede(((DOTS-1)*30)-z*30, 0);
            c.add(cSeg);
        }
        //CREATING CENTIPEDE HEAD
        cHead = c.get(0);               

        //player = new Player();
        player.setX(player.START_X);
        player.setY(player.START_Y);
        shot = new Shot();
        if (!s.isDestroyed()){
            s = new Spider(0,0);
        }
        
        for (int f = 0; f<remShrooms.size(); f++){
                System.out.println("Shroom (before reset) #"+f+" has health --  "+remShrooms.get(f).health);
        }
        
        for(int j=0; j<remShrooms.size(); j++)
        {
            Shroom currShroom = remShrooms.get(j);
            if((currShroom.health == 1) || (currShroom.health == 2)){
                currShroom.health = 3;
                score = score + 10;
            }
        }
        System.out.println("RemShrooms size:  "+remShrooms.size());
        return remShrooms;
    }
    

    public void animationCycle() {
        
        if ((isSpiderDead == true)&&(isCentipedeDead == true)&&(allShroomsDead == true)) {

            ingame = false;
            message = "Game won!";
        }

        // player
        //player.act();
        player.movePlayer();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();
            
            
            //For killing shrooms
            for(int h = 0; h < allShrooms.size(); h++){
                Shroom ss = allShrooms.get(h);
                int alienShroomX = ss.ShroomX;
                int alienShroomY = ss.ShroomY;
                
                if (ss.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienShroomX)
                            && shotX <= (alienShroomX + SHROOM_WIDTH)
                            && shotY >= (alienShroomY)
                            && shotY <= (alienShroomY + SHROOM_HEIGHT)) {
                        //ImageIcon ii = new ImageIcon("expl_Small.png");
                        //ss.setImage(ii.getImage());
//                        score = score + 1; //player score update
//                        GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                        ss.health--;
                        if((ss.health == 1) || (ss.health == 2)){
                            score = score + 1; //player score update
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                        }
                        if(ss.health == 0){
                            ss.setDying(true);
                            score = score + 5; //player score update
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                            ImageIcon ii = new ImageIcon("expl_Small.png");
                            ss.setImage(ii.getImage());
                            
                            int idx = allShrooms.indexOf(ss);
                            allShrooms.remove(idx);
                            allShroomsX.remove(idx);
                            allShroomsY.remove(idx);
                            
                        }
                        //deaths++;
                        shot.die();
                        if(allShrooms.size() == 0){
                            allShroomsDead = true;
                            System.out.println("ALL SHROOMS ARE DEAD!!");
                        }
                    }
                }
                
            }
            
            //For killing Spider
            int spidX = s.getX();
            int spidY = s.getY();
            int spiderShotCount = 0;
            if (s.isVisible() && shot.isVisible()) {
                    if (shotX >= (spidX)
                            && shotX <= (spidX + SHROOM_WIDTH)
                            && shotY >= (spidY)
                            && shotY <= (spidY + SHROOM_HEIGHT)) 
                        {
//                        if((shotX == spidX) && (shotY == spidY)){
                        spiderShotCount++;
                        System.out.println("SPIDER WAS SHOT!");
                        s.health--;
                        if(s.health == 1)
                        {
                            System.out.println("Spider health :  "+s.health);
                            score = score + 100; //player score update
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                        }
                        
                        
                        if((s.health == 0) || (spiderShotCount == 2)){
//                            System.out.println("Spider is dead.");
                            System.out.println("Spider health:  "+s.health);
                            spiderShotCount = 0;
                            score = score + 600; //update player score
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                            s.setDestroyed(true);
                            s.die();
                            isSpiderDead = true;
                        }
                        shot.die();
                        
                    }
            }
            
            
            
            
            for(int i = 0; i<CentiSegments.size(); i++)
            {
            
                c = CentiSegments.get(i);
                int cSize = c.size();
//                for(Centipede cc: c){
                for (int j = 0; j < c.size(); j++){
                Centipede cc = c.get(j);
                //int cSize = c.size();
                int cSegX = cc.getX();
                int cSegY = cc.getY();
                
                
                if(cc.isVisible() && shot.isVisible()){
                    if (shotX >= (cSegX)
                            && shotX <= (cSegX + DOT_SIZE)
                            && shotY >= (cSegY)
                            && shotY <= (cSegY + DOT_SIZE)){
                       // ImageIcon ii = new ImageIcon("expl_Small.png");
                        //cc.setImage(ii.getImage());
                        CentShot++;
                        if(CentShot == 1)
                        {
                            score = score + 2; //player score update
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                        }
                        System.out.println("CentShot "+CentShot);
                        System.out.println("Shot Coords  x: "+shotX+"   y: "+shotY);
                        if(CentShot == 2)
                        {
                            
                        //Centipede Splitting code here
                        if ((j == 0) || (j == cSize-1)){    //  No split & Check if full centipede is dead
                            score = score + 2; //player score update
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                            if(c.size() == 1){
                                int idx = CentiSegments.indexOf(c);
                                CentiSegments.remove(idx);
                                score = score + 3;
                                GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                                if(CentiSegments.size() == 0)
                                {
                                    System.out.println("Centipede is DEAD!");
//                                    score = score + 600; //player score update
                                    score = score + 595; //player score update
                                    GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                                    isCentipedeDead = true;
                                }
                            }
                        } else {    //  Split
                            score = score + 5; //update player score
                            GameInfo.setText("PLAYER SCORE:  "+Integer.toString(score)+"          PLAYER LIVES:  "+player.lives);
                            CentiSegments.add(new ArrayList<Centipede>(c.subList(j+1,c.size())));
                            CentiSegments.set(i, new ArrayList<Centipede>(c.subList(0, j)));
                        }
                        CentShot = 0;
                    }

                        shot.die();
                    }
                }
               
            }
        }
        //} //IF size check
            
            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

                
        
        //Centipede
        if(CentSpeedCounter++ == 3)
        {
        if(CentiSegments.size() > 0)
        {
            
//            System.out.println("main size: " + CentiSegments.size());
        //for (ArrayList<Centipede> ce: CentiSegments)
         for (int f=0; f<CentiSegments.size(); f++)
        {
            ArrayList<Centipede> ce = CentiSegments.get(f);
//          System.out.println("2nd size: " + ce.size());
            cHead = ce.get(0);
        for (int z = ce.size() - 1; z > 0; z--) {
                        //c.centipedeSegmentX[z] = c.centipedeSegmentX[(z - 1)];
                        //c.centipedeSegmentY[z] = c.centipedeSegmentY[(z - 1)];
//                        System.out.println("In Ctail");
                        ce.get(z).setX(ce.get(z-1).getX());
//                        System.out.println("In seg #"+z+" X cood is   "+ce.get(z).getX());
                        //x[z] = x[(z - 1)];
                        ce.get(z).setY(ce.get(z-1).getY());
//                        System.out.println("In seg #"+z+" Y cood is   "+ce.get(z).getY());
                    }
        
        //Collision with wall
//        if (cHead.getX() >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
        if (cHead.getX() >= BOARD_WIDTH - BORDER_RIGHT) {
                    direction = -1;
                    downFlag = 1;                   
                    
                    
                    cHead.setY(cHead.getY() + GO_DOWN);
                    //cHead.setX(cHead.getX() + GO_LEFT);   //INCLUDE LATER??
                }
        
//        if (cHead.getX() <= BORDER_LEFT && direction != 1) {
        if (cHead.getX() <= BORDER_LEFT) {

            direction = 1;
            downFlag = 1;           
            cHead.setY(cHead.getY() + GO_DOWN);
            //cHead.setX(cHead.getX() + GO_RIGHT);  //INCLUDE LATER??
            
        }
        
        //Collision with Shroom
        int collcHeadX = (cHead.getX() - DOT_SIZE);
        int collcHeadX2 = (cHead.getX() + DOT_SIZE);
        
        if(allShroomsX.contains((collcHeadX))){
        
            //int idx = allShroomsX.indexOf(cHead.getX());
            int idx = allShroomsX.indexOf(collcHeadX);
            
            if(cHead.getY() == allShroomsY.get(idx)){
                System.out.println("Collision!!");
                downFlag = 1;
                cHead.setY(cHead.getY() + GO_DOWN);
            }
        }
        
        if(allShroomsX.contains((collcHeadX2))){
        
            //int idx = allShroomsX.indexOf(cHead.getX());
            int idx = allShroomsX.indexOf(collcHeadX2);
            
            if(cHead.getY() == allShroomsY.get(idx)){
                System.out.println("Collision!!");
                downFlag = 1;
                cHead.setY(cHead.getY() + GO_DOWN);
            }
        }
        /*
        for(int d=0; d<allShrooms.size(); d++)
        {
            //Rectangle r1 = allShrooms.get(d).getBounds();
            Shroom currShroom = allShrooms.get(d);
            Rectangle r1 = currShroom.getBounds();
            if(currShroom.isVisible()){
                Rectangle r2 = cHead.getBounds();
                if(r1.intersects(r2)){
                    System.out.println("Collision!!");
                    downFlag = 1;
                    cHead.setY(cHead.getY() + GO_DOWN);
                }
            }
        }*/
        
        //Ground
        if(cHead.getY() >= (GROUND - DOT_SIZE)){
            cHead.setY(GROUND - DOT_SIZE);
        }
        
        //collision with centipede
        int playerX = player.getX();
        int playerY = player.getY();
        for (int k = 0; k < ce.size(); k++)
        {
            int ceSegX = ce.get(k).getX();
            int ceSegY = ce.get(k).getY();
            if  ( ceSegX >= (playerX)
                        && ceSegX <= (playerX + PLAYER_WIDTH)
                        && ceSegY >= (playerY)
                        && ceSegY <= (playerY + PLAYER_HEIGHT)) {
            
                System.out.println("PLAYER COLLIDES WITH CENT!");
                player.setDying(true);
                player = playerDeath(player);
            }
        
        }
        downFlag = cHead.act(direction, downFlag);
        CentSpeedCounter = 0;
        
    }// first
    }// IF size check
    }
     
        // Spider
        Random generator = new Random();
        
//        for (Shroom ss: allShrooms) {
//          for (int x = 0; x < 5; x++) {

            int shot = generator.nextInt(15);
            if(SpidSpeedCounter++ == 5)
            {
                s.getNextPos();
                SpidSpeedCounter = 0;
            }
            //Shroom.Bomb b = ss.getBomb();

//            if (shot == CHANCE && ss.isVisible && s.isDestroyed()) {
//            if (shot == CHANCE && s.isDestroyed()) {

//                s.setDestroyed(false);
                //s.setX(ss.getX());
                //s.setY(ss.getY());
//            }

            int bombX = s.getX();
            int bombY = s.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !s.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    //ImageIcon ii  = new ImageIcon(explImg);
                    //player.setImage(ii.getImage());
                    System.out.println("PLAYER COLLIDES WITH SPIDER!");
                    player.setDying(true);
                    player = playerDeath(player);
                    /*s.health--;
                    if (s.health ==0){
                        s.setDestroyed(true);
                    }*/
                }
            }

            if (!s.isDestroyed()) {
                
                s.setY(s.getY() + 1);
                /*
                if (s.getY() >= GROUND - BOMB_HEIGHT) {
                    s.setDestroyed(true);
                }*/
            }
//        }//FOR
    }
    


    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            
            beforeTime = System.currentTimeMillis();
        }

        gameOver();
    }

    private class TAdapter extends MouseInputAdapter {
        
        @Override
        public void mouseMoved(MouseEvent e) {

            //player.keyReleased(e);
            player.setX(e.getX());
            player.setY(e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {

            //player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            //int key = e.getKeyCode();
            if(e.getButton() == MouseEvent.BUTTON1){
                if (ingame) {
                                      
                    
                    if (!shot.isVisible()) {

                        shot = new Shot(x, y);
                        String gunSoundName = "shot_sound.wav";
                        File gunFile = new File(gunSoundName);
                        String gunFullPath = gunFile.getAbsolutePath();
                        try{
                            
                            InputStream in;
                            in = new FileInputStream(new File("shot_sound.wav"));
                            AudioStream gunShotSound = new AudioStream(in);
                            AudioPlayer.player.start(gunShotSound);
                            
                        }catch(Exception ex){
                            ex.printStackTrace();
                            System.out.println("Audio error.");
                        }
                        
                        
                    }
                }
            }
            
        }
    }
}
