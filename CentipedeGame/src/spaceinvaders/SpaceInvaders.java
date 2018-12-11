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
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class SpaceInvaders extends JFrame implements Commons {
    public JLabel GameInfo;
    public SpaceInvaders() {

        initUI();
    }

    private void initUI() {

        //add(new Board(GameInfo));
        //setTitle("Space Invaders");
        GameInfo = new JLabel("PLAYER SCORE:  0          PLAYER LIVES:  3");
        add(new Board(GameInfo));
        setTitle("Centipede");
        add(GameInfo, BorderLayout.NORTH);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}