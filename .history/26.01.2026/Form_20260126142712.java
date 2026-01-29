import javax.swing.*;
import java.awt.*;

public class Form {

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Hello Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel emptyLabel = new JLabel("Hello !");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        JLabel labelEast = new JLabel("East !");
        labelEast.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(labelEast, BorderLayout.EAST);
        
        JLabel labelWest = new JLabel("West !");
        labelWest.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(labelWest, BorderLayout.WEST);
        
        JLabel labelNorth = new JLabel("North !");
        labelNorth.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(labelNorth, BorderLayout.NORTH);
        
        JLabel labelSouth = new JLabel("South !");
        labelSouth.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(labelSouth, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}