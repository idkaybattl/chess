package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import logic.*;

public class UI extends JFrame {
  ChessGame logic;
  
  private JButton[][] squares;
  
  public UI() {
    logic = new GameLogic();     
    
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1024; 
    int frameHeight = 1024;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("dada");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    
    squares = new JButton[8][8];
    
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; i++) {
        squares[i][j] = new JButton();
        squares[i][j].setBounds(i * frameWidth / 8, j * frameHeight / 8, frameWidth / 8, frameHeight / 8);
         jButton1.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent evt) { 
            squareClicked(evt);
          }
         });
      } 
    }
    
    updateBoard(); 
  }
  
  private void updateBoard() {
    logic.getBoard(); 
  }
}

