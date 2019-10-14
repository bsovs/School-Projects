
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class DrawTree extends JPanel{
    
    public BinarySearchTree tree;
    
    public DrawTree(BinarySearchTree tree){
        this.tree = tree;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        g.setFont(new Font("Tahoma", Font.BOLD, 10));
        try{
        DrawTree(g, 0, getWidth(), 0, getHeight() / tree.height(tree.root), tree.root, 0 , 0);
        }
        catch(Exception e){}
    } 
    
    public void DrawTree(Graphics g, int StartWidth, int EndWidth, int StartHeight, int Level, Node node, int oldX, int oldY) {
        String data = String.valueOf(node.data);
        g.setFont(new Font("Tahoma", Font.BOLD, 10));
        FontMetrics fm = g.getFontMetrics();
        int dataWidth = fm.stringWidth(data);
        
        g.drawString(node.data + " " +node.count, (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2);
        
        if (oldX !=0 && oldY !=0){
            g.drawLine( (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2, oldX, oldY );
        }
        if (node.left != null)            
            DrawTree(g, StartWidth, (StartWidth + EndWidth) / 2, StartHeight + Level, Level, node.left, (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2 );
        
        if (node.right != null)
            DrawTree(g, (StartWidth + EndWidth) / 2, EndWidth, StartHeight + Level, Level, node.right, (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2);
    }
    
    
}