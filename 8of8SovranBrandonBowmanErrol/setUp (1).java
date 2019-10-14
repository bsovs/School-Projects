/*
 * 
 * This class is used to set up the bowman game and load in
 * all of the images. It initates and resets variables.
 * 
 */

import javax.swing.*; //make sure you import this library!
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

class SetUp extends JFrame{

  String userName = "";
  
  SetUp(String userName){
    this.userName = userName;
  }
  
  void setUp(){
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
    
    Bowman bw = new Bowman();
    bw.canvas.setPreferredSize(new Dimension(800, 600)); 
    
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(bw.canvas, BorderLayout.CENTER);
    
    //set health
    bw.playerHealth = 100;
    bw.cpuHealth = 100;
    
    //variable health levels
    bw.cpuHealthLevel = new Rectangle2D.Double(bw.cpuHead.x+bw.cpuHead.getWidth()/2-bw.cpuHealth/2,  bw.playerHead.y+bw.playerHead.getHeight()+45+25+10, bw.cpuHealth,10);
    bw.playerHealthLevel = new Rectangle2D.Double(bw.playerHead.x+bw.playerHead.getWidth()/2-bw.playerHealth/2,  bw.playerHead.y+bw.playerHead.getHeight()+45+25+10, bw.playerHealth,10);
    
    bw.cpuHealthLevel.x = bw.cpuHead.x+bw.cpuHead.width/2-bw.cpuHealthBar.width/2;
    bw.cpuHealthBar.x = bw.cpuHead.x+bw.cpuHead.width/2-bw.cpuHealthBar.width/2;
    bw.playerHealthLevel.x = bw.playerHead.x+bw.playerHead.width/2-bw.playerHealthBar.width/2;
    bw.playerHealthBar.x = bw.playerHead.x+bw.playerHead.width/2-bw.playerHealthBar.width/2;
    
    //For wall
    double h = Math.random()*150 + 100;
    bw.wall = new Rectangle2D.Double(bw.xDistance + bw.playerHead.x, 450-h,20,h);
    
    //load images
    try
    {
      bw.bodyImage = ImageIO.read( new File("assets/body.png" ));
      bw.armImage = ImageIO.read( new File("assets/arm.png" ));
      bw.arrowImage = ImageIO.read( new File("assets/arrow.png" ));
      bw.groundImage = ImageIO.read( new File("assets/ground.png" ));
      bw.bowTemp = ImageIO.read( new File("assets/Bow.png" ));
      bw.sunImage = ImageIO.read( new File("assets/sun.png" ));
      bw.cloudImage = ImageIO.read( new File("assets/cloud.png" ));
      bw.bgImage = ImageIO.read( new File("assets/bg.png" ));
      
      bw.bowImage[0] = bw.bowTemp.getSubimage(0,0,42,54);
      bw.bowImage[1] = bw.bowTemp.getSubimage(74,0,42,54);
      bw.bowImage[2] = bw.bowTemp.getSubimage(0,75,42,54);
      bw.bowImage[3] = bw.bowTemp.getSubimage(74,75,42,54);
      bw.bowImage[4] = bw.bowTemp.getSubimage(0,150,42,54);
    }
    catch ( IOException exc )
    {
      System.out.println("image not found...");
    }
    
    this.setResizable(false);
    pack();           // pack all the components in the JFrame
    setVisible(true); // show it
    requestFocus();
    
    bw.playGame();

    this.setVisible(false);
    this.dispose();
    menu bow = new menu(false, userName);
    bow.homeScreen(); //start the game
    
  }
  
}