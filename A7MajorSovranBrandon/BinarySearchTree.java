/*
 * Name: Brandon Sovran
 * Date: March 27, 2018
 * 
 * Description: This program is meant to create a tree based on user inputs. The user
 * is able to add words to the tree (throught keyboard or text file), clear the tree,
 * search for words in the tree, print out the tree in order, check if the tree is
 * balanced, and rebalance the tree.
 * 
 */

//imports
import java.util.*;
import java.util.Scanner;
import java.io.*;

public class BinarySearchTree {
  
  //This method contains the user interface to select options
  public static void main ( String args[] ){
    
    //create a new search tree and gui
    BinarySearchTree tree = new BinarySearchTree();
    TreeGUI tGUI = new TreeGUI(tree, 500, 500);
    Scanner key = new Scanner( System.in );
    String in = "";
    
    do{
      
      //print main menu
      System.out.println("Menu: \n [1] Insert fron Keyboard \n [2] Search Tree \n [3] Display the Tree \n "
                           + "[4] Clear Tree \n [5] Insert from Text File \n [6] Is Tree Balanced \n "
                           + "[7] Rebalance Tree \n [8] Exit");
      in = key.nextLine();
      String temp = "";
      
      //checks for the option inputed
      
      if(in.equals("1")){
        System.out.println("Enter Text:");
        in = key.nextLine();
        Scanner tempKey = new Scanner(in); 
        tempKey.useDelimiter("[\\W_]+"); //split the text into words
        while (tempKey.hasNext()){
          temp = tempKey.next();
          tree.insert(temp.toLowerCase()); //insert into the tree the next word
        }
        tGUI.repaint();
        System.out.println("Insert Complete");
        System.out.println();
      }
      else if(in.equals("2")){
        System.out.println("Enter Words To Search For:");
        in = key.nextLine();
        Scanner tempKey = new Scanner(in); 
        tempKey.useDelimiter("[\\W_]+"); //split the text into words
        while (tempKey.hasNext()){
          temp = tempKey.next();
          if(tree.contains(temp.toLowerCase())){
            System.out.println("Found"); //return if the tree contains the search
          }
          else{
             System.out.println("Not Found");
          }
        }
        System.out.println();
      }
      else if(in.equals("3")){
        tree.print();
      }
      else if(in.equals("4")){
        tree.clear(); //clear tree
        System.out.println("Tree Cleared");
        System.out.println();
      }
      else if(in.equals("5")){
        System.out.println("Enter File Name:");
        in = key.nextLine(); //insert from text file
        addWords(in, tree);
        System.out.println();
      }
      else if(in.equals("6")){ //check if the tree is balanced
        if(tree.isBalanced()){
          System.out.println("Balanced");
          System.out.println();
        }
        else{
          System.out.println("Not Balanced");
          System.out.println();
        }
      }
      else if(in.equals("7")){
        if(tree.isBalanced()==false){
          tree.rebalanceTree(); //rebalance the tree
          System.out.println("Rebalanced");
          System.out.println();
        }
        else{
          System.out.println("Already Balanced");
          System.out.println();
        }
      }
      else if(in.equals("8")==false){
        System.out.println("Invalid Input!"); //Invalid input was put in the field
        System.out.println();
      }
      
      tGUI.repaint(); //redraw the tree in gui
    
    }while(in.equals("8")==false); //loop while user does not exit
    
    System.out.println("EXIT"); //Exit Print
    
  }
  
  Node root; //start of the tree
  
  //This method inserts a new node into the tree if it does not already exist
  void insert (String newItem){
    root = insert(root, newItem);
  }
  //This method is a helper method
  Node insert (Node node, String data) {
        
        if (node == null) {
            node = new Node(data);
            return node;
        }
        
        if (data.compareTo(node.data)<0){
            node.left = insert(node.left, data); //insert on left
        }
        else if (data.compareTo(node.data)>0){ //insert on right
            node.right = insert(node.right, data);
        }
        else if (data.compareTo(node.data)==0){ //node already exists
          node.count++;
        }
        
        return node;
        
    }
  
  //This method checks if the data inputed matches that already in the tree
  boolean contains (String item){
    
    Node current = root;
    Node parent = null;
    boolean goLeft = false;
    
    while (current != null) //loop while current is not null
    {
      if (current.data.compareTo(item)==0)
      {
        return true; //found the item
      }
      else if (current.data.compareTo(item)>0) //move left
      {
        parent = current;
        goLeft = true;
        current = current.left;
      }
      else //move right
      {
        parent = current;
        goLeft = false;
        current = current.right;
      }
    }
    
    return false; //did not find the item
    
  }
  
  //This method prints out the tree inorder
  void print (){
    print(root);
    System.out.println();
  }
  //This method is a helper method
  void print (Node node){
    if (node != null){
      print(node.left); //move left
      System.out.println(node.data + " " + node.count); //print current node
      print(node.right); //move right
    } 
    else if(root == null){
      System.out.println("Tree Is Empty"); //print empty tree
    }
  }
  
  //This method counts all the nodes in the tree
  int count (){
    return count(root);
  }
  //This is a helper method
  int count (Node node){
    if (node == null){
      return 0;
    }
    else{
      return 1 + count(node.left) + count(node.right); //increment for every node that is passed through
    }
  }
  
  //This method clears the tree
  void clear (){
    root = null; //clear root pointer
  }
  
  //This method checks if the tree is balanced
  boolean isBalanced (){
    return isBalanced(root);
  }
  //This method is a helper method
  boolean isBalanced (Node node){
    
    int lh; //left subtree height
    int rh; //right subtree height

    if (node == null){
      return true; //if tree is null it is balanced
    }
    
    lh = height(node.left); //returns the height of the left subtree
    rh = height(node.right); //returns the height of the right subtree
    
    if (Math.abs(lh - rh) <= 1 && isBalanced(node.left) && isBalanced(node.right)){
      return true; //if tree heights are equal or less than 1 it is balanced
    }

    return false; //return false if height is farther apart than 1
  }
  
  //This method return the height of the tree to the furthest node
  public int height (){
    return height(root);
  }
  //This method is a helper method
  public int height (Node node){
    if (node == null){
      return 0;
    }
    
    return 1 + Math.max(height(node.left), height(node.right)); //add for every node passed through
  }
  
  //This method rebalances the tree if it is not already balanced
  void rebalanceTree (){
    if(isBalanced()==false){
      Node[] arr = new Node[count()];
      arr = helperRebalance(arr, root, 0);
      root = insertRebalance(arr, 0, arr.length-1); //set the root to be the new root
    }
  }
  //This method is a helper method that puts the tree into an array inorder
  Node[] helperRebalance (Node[] arr, Node node, int parentIndex){
    if (node == null){
      return null;
    }
    else{
      if(node == root){
        parentIndex = count(node.left); //count left
      }
      else if(node.data.compareTo(arr[parentIndex].data)<0){
        parentIndex -= count(node.right)+1; //count right and move left
      }
      else if(node.data.compareTo(arr[parentIndex].data)>0){
        parentIndex += count(node.left)+1; //count left and move right
      }
    } 
    arr[parentIndex] = node;
    helperRebalance(arr, node.left, parentIndex); //move left
    helperRebalance(arr, node.right, parentIndex); //move right
    return arr;
  }
  //This method is a helper method that places the nodes in the tree inorder
  Node insertRebalance (Node[] arr, int first, int last){
    
    if (first > last){ //moved to the leftmost point
      return null;
    }
    
    int mid = (first + last) / 2; //get mid
    Node node = arr[mid];
    
    node.left = insertRebalance(arr, first, mid - 1); //get left half
    node.right = insertRebalance(arr, mid + 1, last); //get right half
    
    return node;
  }
  
  //This method takes the words from a text file to be added into the tree
  public static void addWords(String fileName, BinarySearchTree tree) 
    {
        Scanner dataScan = null;
        boolean found = true;
        
        try {
            //inputData is just a text file
            dataScan = new Scanner(new File(fileName)); 
            dataScan.useDelimiter("[\\W_]+"); //splits the text into words and numbers respectively
            System.out.println("Insert Complete");
        }
        catch (FileNotFoundException exception) {
            System.out.println("The input file was not found.");
            found = false;
        }
        
        // will run as long as file exists and the exception did not run
        if (found) {
            // Checks to see if there are more tokens read
            while (dataScan.hasNext())   {
              tree.insert(dataScan.next().toLowerCase()); //inserts the next word into the tree
            }
        }
    }
  
}