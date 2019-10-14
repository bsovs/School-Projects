/*
 * Name: Brandon Sovran
 * Date: March 27, 2018
 * 
 * Description: This program is used to create new objects
 * of type Node. Each node has a string value, number of 
 * occurances, and a left and right branching node.
 * 
 */

public class Node {
  
  String data; //data stored in the node
  int count = 1; //occurances
  Node left, right; //branches
  
  public Node(String str){ //Constructor to initialize the string value
    data = str;
  } 
  
}