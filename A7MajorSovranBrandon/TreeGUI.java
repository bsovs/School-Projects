
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
public class TreeGUI extends JFrame {
    
    private JPanel contentPane;
    public BinarySearchTree tree;
    public DrawTree drawer;
    
    /**
     * Create the frame.
     */
    public TreeGUI(BinarySearchTree tree, int winWidth, int winHeight) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, winWidth, winHeight);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        drawer = new DrawTree(tree);
        contentPane.add(drawer);
        
        setContentPane(contentPane);
        this.tree = tree;
        setVisible(true);
    }
    
}
