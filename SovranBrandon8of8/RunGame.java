/*
 * 
 * Name: Brandon Sovran
 * Date: June 2, 2017
 * 
 * Program Description: 
 * This programme is an obstacle avoidance game, 
 * in which you continue to run until you bump into a hazard. 
 * The further you are able to run without coming in contact with a hazard 
 * the higher your score will be. As you go further the speed will increase 
 * making it more difficult to avoid hazards. There are multiple 
 * hazards you have to avoid, the player must decide what actions to take 
 * in order to avoid them (jump or duck). Up and down arrows are used to jump and duck. 
 * The obstacles (walls) will appear at different hights and distaces.
 * A leaderboard will be on display taking in all of the top scores and the player�s names.
 * The player will input their name if a high score is acheived.
 * There is a menue bar on the home screen you will return to after each and evey play.
 * The menue bar has buttons that will show you how to play and the high scores.
 * 
*/

//import packages
import hsa_ufa.Console;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import sun.audio.*;
import java.net.*;

//main class
public class RunGame implements KeyListener, ActionListener{
  
  static Console c; //Assigne Console to Variable c
  
  //-- Fonts --//
  public static Font defaultFont = new Font("Ariel", Font.PLAIN, 12); //create default font
  public static Font titleFont = new Font("TimesRoman", Font.PLAIN, 40); //create font for title
  public static Font scoreFont = new Font("Ariel", Font.PLAIN, 15); //create high score font
  
  
  //---------- Global Variables ----------//
  
  //all global variables are to be initialized here,
  //they may be changed later in the program but are placed in
  //bulk to save room in the file length.
  
  //Radnom Number
  Random rand = new Random();
  //Make Start Game false
  boolean start = false;
  //Make First Run of Program true
  public static boolean firstRun = true;
  //Create Buttons
  public static Button clickStart, clickEnd, clickScore, clickBack, clickHow, clickMusic;
  //Make Playing Game True
  boolean gameIsOver = false;
  //Import Image
  Image runImg = null;
  Image wallImg = null;
  Image bg = null;
  //Set x,y coordinates
  int x = 100;
  int y = 300;
  //Set x moved, y moved 
  int xMoved = 100;
  int yMoved = 350;
  //Set player height
  int h = 40;
  //Jumping Physics
  boolean cooled = true; //Used to detect if player is able to jump again
  long coolTime = 0; //Used to set start time of cooldown
  boolean jumping = false; //Used to detect if player is jumping up
  boolean falling = false; //Used to detect if player is falling after jump
  //Crouching
  boolean firstPress = true; //Used to move player in or out of the
  boolean firstRelease = false; // crouching position
  //Set x,y coordinates for wall
  int xWall[] = {1150, 1450, 1750, 1950};
  int yWall[] = {440, 440, 415, 440};
  //Set speed of walls
  int wallSpeed = 10;
  //Set increase wall speed at score 1000
  int increaseSpeedAt = 1000; 
  //Set score to zero
  long score = 0;
  long startTime = System.currentTimeMillis(); //Check the current time in milliseconds
  String scoreStr = "";
  //High Scores
  String highScore[] = new String[10]; //scores
  String highScoreName[] = new String[10]; //names
  //Sound/Music
  public static InputStream in = null;
  public static AudioStream as = null;  
  
  //-------------------- Main --------------------//
  
  public static void main(String[] args) throws InterruptedException {
    
    RunGame myGame = new  RunGame(); //create new window
    myGame.Home(); //Start the game
    
  }
  
  //this is used every time a new window is created
  public RunGame() {
    
    c = new Console(1000, 600, "Run Game - V8"); //Create new Console
    c.addKeyListener(this); //add the key listener to the console
   
  }
  
  //-------------------- Home Screen For Program --------------------//
  
  //this method is used to create the home screen of the program
  //it includes multiple buttons to preform different action
  //within the program
  
  public void Home() {
    
    //make sure to only play the music on the first run
    if( firstRun == true ){
      music(); //start playing the music
      firstRun = false;
    }
    
    //-- Start Creation of Home Screen Buttons --//
    
    JPanel buttonnPanel = new JPanel(); //create seperate panel
    buttonnPanel.setLayout(null); //set panel to null
    
    //create and add buttons to the new jpanel
    
    //Start Game
    clickStart = new Button("Start Game");
    clickStart.setBounds(75,50,150,50); //set location
    clickStart.addActionListener(this); //link to action listner
    buttonnPanel.add(clickStart); //add to the panel
    
    //High Score
    clickScore = new Button("High Scores");
    clickScore.setBounds(75,140,150,50); //set location
    clickScore.addActionListener(this); //link to action listner
    buttonnPanel.add(clickScore); //add to the panel
    
    //How to Guid
    clickHow = new Button("How to Play");
    clickHow.setBounds(75,230,150,50); //set location
    clickHow.addActionListener(this);  
    buttonnPanel.add(clickHow); //add to the panel
    
    //Close Game
    clickEnd = new Button("Exit");
    clickEnd.setBounds(75,320,150,50); //set location
    clickEnd.addActionListener(this); //link to action listner  
    buttonnPanel.add(clickEnd); //add to the panel
    
    //Back to Home Screen
    clickBack = new Button("< back");
    clickBack.setBounds(75,410,150,50); //set location
    clickBack.addActionListener(this); //link to action listner 
    clickBack.setVisible(false); //make back button invisible
    buttonnPanel.add(clickBack); //add to the panel
    
    c.add(buttonnPanel); //add the second panel to frame
    
    //-- End Button Creation --//
    
    homePicture(); //add some life to the home screen
    
    start = false; //make start false
    
    //start loop to check if game has started or not
    do{
 
      pause(50); //sleep 0.05s
      
      //make sure to only start game once the button is clicked
      if (start == true){
        c.dispose(); //dispose of old frame
        RunGame myGame = new  RunGame(); //create new frame
        myGame.StartGame(); //Start the game
      }
      
      //ReSet x,y coordinates for wall
      xWall[0] = 1100;
      xWall[1] = 1450;
      xWall[2] = 1700;
      
      yWall[0] = 440;
      yWall[1] = 440;
      yWall[2] = 415;
      
      //ReSet Game Over
      gameIsOver = false;
      
    }while(true); //continue this loop
    
  }
  
  //---------- Home Picture ----------//
  
  //this method draws the Picture on the Home Screen, only purpose is to fill in space on home screen
  public void homePicture(){
 
    c.setFont(titleFont); //change the font
    c.drawString("Welcome to...", 150, 150); //Print Run!
    c.drawString("RUN!", 150, 195);
    c.setFont(defaultFont); //change the font back to default
    
    //Print the pretty picutre on the home screen
    
    //import images for home screen
    try{
      File sourceRun = new File("runner.png"); //import image
      runImg = ImageIO.read(sourceRun);
      File sourceWall = new File("wall.png"); //import image
      wallImg = ImageIO.read(sourceWall);
    }
    catch (Exception e){
      System.err.println("file not found"); //error message
    }
    
    c.drawImage(runImg, 150, 325, 60, 60); //Draw Player
    c.drawImage(wallImg, 280, 310, 30, 75); //Draw Wall
    c.drawLine(0, 386, 550, 386); //Draw Ground
    
  }
  
  
  //-------------------- Start Game --------------------//
  
  //-- Main Part of Program that Runs the Game --//
  
  //Once start is clicked this methos will run, it involves the player
  //jumping and ducking to avoid the obstacles, and high score being calculated
  
  public void StartGame() {
    
    
    //-- Import the Image Files --//
    //Runner and Wall
    try{
      File sourceRun = new File("runner.png"); //import image
      runImg = ImageIO.read(sourceRun);
      File sourceWall = new File("wall.png"); //import image
      wallImg = ImageIO.read(sourceWall);
    }
    catch (Exception e){
      System.err.println("file not found"); //error message
    }
    //Backgrounds (randomly picked)
    try{
      Random rand = new Random(); //random num
      int  n = rand.nextInt(3) + 1;
      String randBg = "bg" + n + ".png"; //import background n
      File sourceBg = new File(randBg);
      bg = ImageIO.read(sourceBg); //make bg the background
    }
    catch (Exception e){
      System.err.println("file not found"); //error message
    }
    
    //enter into the game loop
     do{
     
      c.drawImage(bg, 0, 0, 1100, 600); //Draw BackGround 
      c.setColor(Color.BLACK);
      c.drawImage(runImg, x, y, 40, h); //Draw Player
      drawWall(); //Draw the Walls
      c.drawLine(0, 491, 1100, 491); //ReDraw Ground
      c.drawRect(850, 20, 100, 40); // ReDraw Score Box
            
      score(); //get score
      gameOver(); //Check if game is over
      physics(); //create physics
      
      pause(50); //sleep 0.05s
      
      c.clearRect(870, 25, 70, 30); //Clear the Score
      c.clearRect(x, y, 40, h); //Delete original player
      
      x = xMoved; //used to change position of player
      y = yMoved;
      
      c.clearRect(xWall[0], yWall[0], 20, 50); //Delete original wall
      c.clearRect(xWall[1], yWall[1], 20, 50); //Delete original wall
      c.clearRect(xWall[2], yWall[2], 20, 50); //Delete original wall
      

    }while(gameIsOver == false); //continue in the loop until the game is declared over 
     
     c.clear(); 
     c.drawString("GAME OVER...", 450, 300); //Print Game Over
     c.drawString("Score: "+scoreStr, 450, 330); //Print the Score
     c.drawString("HIGH SCORES", 450, 360); //Print High Score
     highScore(); //Check for a high score
     pause(3000); //sleep 3.0s
     c.dispose(); //dispose of old frame
     RunGame myGame = new  RunGame(); //Create new frame
     myGame.Home(); //go home after game over
    
  }
  
  
  //                                                             //
  //-------------------- Methods for Program --------------------//
  //                                                             //
  
  
  //-------------------- High Scores --------------------//
  
  /*
   * this method saves the high scores of players long term in a text file,
   * it then goes on to sort the high scores and update it if a player receives a
   * high score, players name input will also be taken if a high score is achieved
  */
  public void highScore(){
    
    /*
     * this part of the method makes sure the proper file exists
     * and will input values to avoid any errors in the program
    */
    try{ 
      //Set High Scores to zero
      File f = new File("Scores.txt"); //create storage file
      if(!f.exists() && !f.isDirectory()) { //make sure it exists
        PrintWriter printWriter = new PrintWriter("Scores.txt");
        //scores to zero
        for(int i = 0; i<10; i++){
          printWriter.println(0); //print in file
        }
        //names to Unknown
        for(int i = 10; i<20; i++){
          printWriter.println("null"); //print in file
        }
        printWriter.close (); //close file
      }
    }
    catch(IOException e){}
    
    /*
     * this part of the method takes all the scores and names from the file, 
     * then puts them into arrays before sorting
    */
    try{ 
      //Check the high scores
      BufferedReader br = new BufferedReader(new FileReader("Scores.txt")); 
      String line;
      int lineNumber = 0;
      //get all the high scores from lines
      while (lineNumber < 10 && (line = br.readLine()) != null) {   
        highScore[lineNumber] = line; //set the score to the one in that position
        ++lineNumber;
      }
      //get all score names
      while ((line = br.readLine()) != null && lineNumber < 20) {   
        highScoreName[lineNumber - 10] = line; //set the score to the one in that position
        ++lineNumber;
      }
    }
    catch(IOException e){}
     
    /*
     * this part of the method takes all the scores and names from the file, 
     * then sorts them into the correct order
    */
    try{ 
      //update the high scores in text file
      PrintWriter printWriter = new PrintWriter("Scores.txt");
      
      //reprint the scores
      for(int i = 0; i<9; i++){
        printWriter.println(highScore[i]); //print in file
      }
      
      //if the score is good enough to make the top 10 high scores
      if(score > Long.valueOf(highScore[9]).longValue()){
       
        printWriter.println(score + ""); //print in file
        highScore[9] = score + ""; //update your high score into the leaderboard
        score = 0; //make score zero again
        
        for(int i=0; i<6; i++){
          c.println(); //print white space
        }
        
        c.drawString("!!! HIGH SCORE !!! ", 20, 50); //draw text
        c.drawString("ENTER YOUR NAME (max 10 character)... ", 20, 75);
        
        //ask for name of player, make sure it is less than 10 characters in length
        boolean nameTooLong = false;
        do{
          if(nameTooLong==true){ //see if the name previously entered was too long
            c.println("Error! Please Input again. (10 character max)");
          }
          c.print("   Enter Name: ");
          highScoreName[9] = c.readLine(); //ask for input for high score name
          nameTooLong = true;
        }while(highScoreName[9].length() > 10);
        
      }
      else{
        printWriter.println(highScore[9]); //print in file
      }
      
      //reprint the names
      for(int i = 0; i<9; i++){
        printWriter.println(highScoreName[i]); //print in file
      }
      
      //
      //this part of the method sorts the array of high scores
      //
      for(int i = 1; i<10; i++){
        //Swap the two scores
        long item = Long.valueOf(highScore[i]).longValue();
        String itemName = highScoreName[i];
        int a = i;
        // shift items to the right if next item is smaller
        // than previous item
        while (a > 0 && item > Long.valueOf(highScore[a-1]).longValue()) 
        {
          highScore[a] = highScore[a-1];
          highScoreName[a] = highScoreName[a-1];
          a--;
        }
        highScore[a] = item + "";
        highScoreName[a] = itemName;
      }
      
      printWriter.close (); //close file
      
    }
    catch(IOException e){}
      
    /*
     * this part of the method takes all the scores and names that have been sorted, 
     * then updates the file with the correct order
    */
    try{ 
      //update the high scores in text file
      PrintWriter printWriter = new PrintWriter("Scores.txt");
      
      for(int i = 0; i<10; i++){
        printWriter.println(highScore[i]); //print in file
      }
      for(int i = 0; i<10; i++){
        printWriter.println(highScoreName[i]); //print in file
      }
      
      printWriter.close (); //close file
    }
    catch(IOException e){} 
    
    
    //  ** Print out on to the screen  ** //
    for(int i = 0; i<10; i++){
      c.drawString((i+1) + ": " + highScore[i] + "  |  " + highScoreName[i], 450, (380+(15*i))); //Print the High Scores
    }
    
    
  }
  
  //-------------------- Print Scores On Home Screen --------------------//
  
  /* this method gets high scores from text file 
   * and prints them onto the home screen
  */
  public void getHighScore(){

    //Save scores in variables
    try{ 
      //Check the high scores
      BufferedReader br = new BufferedReader(new FileReader("Scores.txt")); 
      String line;
      int lineNumber = 0;
      //get all the high scores from lines
      while (lineNumber < 10 && (line = br.readLine()) != null) {   
        highScore[lineNumber] = line; //set the score to the one in that position
        ++lineNumber;
      }
      //get all the names from lines
      while ((line = br.readLine()) != null && lineNumber < 20) {   
        highScoreName[lineNumber-10] = line; //set the name to the one in that position
        ++lineNumber;
      }
      
      br.close(); //close the file reader
    }
    catch(IOException e){}
    
    c.drawRect(95, 130, 360, 350); //draw frame
    
    c.setFont(titleFont); //change the font
    c.drawString("HIGH SCORES", 150, 200); //Print High Score
    c.setFont(defaultFont); //change the font back to default
    
    c.setFont(scoreFont); //change the font
    
     //  ** Print out on to the screen  ** //
    for(int i = 0; i<10; i++){
      c.drawString((i+1) + ": " + highScoreName[i] + "  |  " + highScore[i], 200, (250+(20*i))); //Print the High Scores
    }
    
    c.setFont(defaultFont); //change the font back to default
    
  }
  
  
  //-------------------- How To Play Guide --------------------//
  
  //this method prints out how to play the game in the main window
  public void howToPlay(){
    
    c.setFont(titleFont); //change the font
    c.drawString("How to Play Guide", 70, 105); //Print Run!
    c.drawString("for Run!", 70, 150);
    c.setFont(defaultFont); //change the font back to default
    c.drawString("Objective:  To avoid obstacles for as long as posible by jumping ", 70, 215);
    c.drawString("                    and ducking, racking in a huge high score!", 70, 230);
    c.drawString("Controles: Up Arrow Key to Jump, ", 70, 270);
    c.drawString("                    Down Arrow Key to Duck", 70, 285);
    c.drawString("Scores:    High Scores are calculated based on the length", 70, 325);
    c.drawString("                   you are able to run! Avoid obstacles longer to", 70, 340);
    c.drawString("                   get your self on the leaderboard. It's that simple!", 70, 355);
    //print all the instructions on screen
    
  }
  
  //-------------------- Play Music --------------------//
  
  //this method plays the background music for the game
  public void music(){
    
    try{
      in = new FileInputStream("music.wav"); //import song
      as = new AudioStream(in); //add song to audio stream
    }
    catch(IOException error){ 
      System.err.println("file not found"); //error
    }
    
    AudioPlayer.player.start(as); //start audio player for the song 
    
  }
  
  //this method plays a sound every time you level up (gain 1000 points)
  public static void levelUp(){
    
    InputStream in_LvlUP = null;
    AudioStream as_LvlUP = null;  
    
    try{
      in_LvlUP = new FileInputStream("levelUp.wav"); //import song
      as_LvlUP = new AudioStream(in_LvlUP); //add song to audio stream
    }
    catch(IOException error){ 
      System.err.println("file not found"); //error
    }
    
    AudioPlayer.player.start(as_LvlUP); //start audio player for the song 
    
  }
  
  
  //-------------------- Drawing the Walls --------------------//
  
  //this methos will redraw all of the walls and move them according to speed
  public void drawWall(){
    
    c.drawImage(wallImg, xWall[0], yWall[0], 20, 50); //Draw Wall
    c.drawImage(wallImg, xWall[1], yWall[1], 20, 50); //Draw Wall
    c.drawImage(wallImg, xWall[2], yWall[2], 20, 50); //Draw Wall
    c.drawImage(wallImg, xWall[3], yWall[3], 20, 50); //Draw Wall
    
    //reset all the walls x coordinates
    for(int i=0; i<4; i++){
      if (xWall[i] < -10){
        xWall[i] = 1200 + ((wallSpeed - 10)*50); //Reset the position of the wall
        //random generator for low or high wall
        int  n = rand.nextInt(2) + 1;
        if (n > 1){
          yWall[i] = 415; //high wall
        }
        else{
          yWall[i] = 440; //low wall
        }
      }
      xWall[i] = xWall[i] - wallSpeed; //Move Wall towards player
    }
    
  }
  
  
  //-------------------- In Game Score --------------------//
  
  //this methos sets the players score in the game and speeds the player up
  //once a certain score is reached
  public void score(){
    
    long currentTime = System.currentTimeMillis(); //Check the current time in milliseconds
    score = (currentTime - startTime)/10; //Set score to amount of time spent in game
    scoreStr = Long.toString(score); //Change Long to String
    c.drawString(scoreStr, 890, 50); //Print the Score

    //once score reaches +1000 increase speed
    if(score >= increaseSpeedAt){
      levelUp(); //play level up music
      wallSpeed = wallSpeed + 3; //speed up walls
      increaseSpeedAt = increaseSpeedAt*2; //set speed increase level to double
    }
    
  }
  
  
  //-------------------- Is Game Over --------------------//
  
  //this method checks if the distance between the walls and the player is
  //close enough to consider a collision and end the game
  public void gameOver(){
   
    //check distance from the walls to the player
    for(int i=0; i<3; i++){
      if (distancePointLine(xWall[i]+0.0, yWall[i]+0.0, xWall[i]+0.0, yWall[i]+40.0, x+20.0, y+20.0) <= 20){
        c.drawString("GAME OVER...", 450, 300); //Print Game Over
        pause(2000); //Pause
        gameIsOver = true; //Set Game Over to true
      }
    }
      
  }
  
  
  //-------------------- Collision Detection --------------------//
  
  //Check Distance between Two Points
  //this code has been taken from Mr. Corea
   public static double distancePointLine(double x1, double y1, double x2, double y2, double px, double py){    
    double A = px - x1;
    double B = py - y1;
    double C = x2 - x1;
    double D = y2 - y1;
    
    double dot = A * C + B * D;
    double len_sq = C * C + D * D;
    double param = -1;
    if (len_sq != 0) //in case of 0 length line
      param = dot / len_sq;
    
    double xx, yy;
    
    if (param < 0) {
      xx = x1;
      yy = y1;
    }
    else if (param > 1) {
      xx = x2;
      yy = y2;
    }
    else {
      xx = x1 + param * C;
      yy = y1 + param * D;
    }
    
    double dx = px - xx;
    double dy = py - yy;
    return Math.sqrt(dx * dx + dy * dy);    
  }
  
   
  //-------------------- Timer Method --------------------// 
   
  //Create a pause in the program
  public static void pause(int time){
    try {
      Thread.sleep(time); //sleep x seconds
    }
    catch (Exception e){}    
  }
  
  
  //-------------------- Jumping Method --------------------//
  
  //physics for jumping
  public void physics() {
    
    //Detect if player is jumping or falling
    if (jumping == true){
      yMoved = yMoved - 10; //move up
    }
    else if (falling == true){
      yMoved = yMoved + 10; //move down
    }
    
    //Check if jump or fall is over
    if (yMoved <= 350){
      jumping = false;
      falling = true;
    }
    else if (yMoved >= 450){
      falling = false;
    }
    
  }
  
  
  //-------------------- Detect Key Press --------------------//
  
  public void keyPressed(KeyEvent e) { 
    
    int key = e.getKeyCode();
    
    long currentTime = System.currentTimeMillis(); //Check the current time in milliseconds
    
    if (currentTime >= coolTime+1050){
      cooled = true; //Detect if jump has cooled down
    }
    
    if (key == KeyEvent.VK_UP) {
      if (cooled == true){
        cooled = false; //make cooldown false
        coolTime = currentTime; //set the time for cooldown
        jumping = true; //set player jumping up to true
      }  
    }
    
    if (key == KeyEvent.VK_DOWN && jumping == false && falling == false) {
      h = 20; //Make player smaller
      if (firstPress == true){
        c.clearRect(x, y, 40, h); //Delete original player
        yMoved = yMoved + 20; //Move player into position
        firstPress = false;
        firstRelease = true;
      }
    }
    
  }
  public void keyReleased(KeyEvent e) { 
  
    int key = e.getKeyCode();
    
     if (key == KeyEvent.VK_DOWN) {
      h = 40; //Make player original size
      if (firstRelease == true){
        yMoved = yMoved - 20; //Move player into original position
        firstPress = true;
        firstRelease = false;
      }
     }
    
  }
  public void keyTyped(KeyEvent e) {}
  
  
  
  //-------------------- Check for Click on Buttons --------------------//
  
  public void actionPerformed(ActionEvent e){
    
    //---------- Home Screen Buttons ----------//
    
    //click start game
    if (e.getSource() == clickStart){
      start = true; //set game start to true
    }
    
    //click get high scores
    if (e.getSource() == clickScore){
      c.clearRect(0, 0, 550, 700); //clear space for high scores
      clickBack.setVisible(true); //make back button visible
      getHighScore(); //Check for a high score
    }
    
    //click how to play
    if (e.getSource() == clickHow){
      c.clearRect(0, 0, 550, 700); //clear space for how to guid
      clickBack.setVisible(true); //make back button visible
      howToPlay(); //create home picture
    }

    //click end game
    if (e.getSource() == clickEnd){
      //Close the Window
      c.dispose();
      System.exit(0); //exit the program
    }
    
    //click back button
    if (e.getSource() == clickBack){
      clickBack.setVisible(false);
      c.clearRect(0, 0, 550, 700); //clear space for home picture
      homePicture(); //create home picture
    }
    
  }
  
  
  //---------- End Program ----------//
} 
