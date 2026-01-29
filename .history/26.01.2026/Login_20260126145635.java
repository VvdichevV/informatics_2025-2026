import javax.swing.*;
import java.awt.*;
public class Login{
static JPanel content; static JLabel userLabel; static JTextField userTextField; static void createAndShowGUI(){
JFrame myFrame=new JFrame("Login Form"); myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); myFrame.setBounds(new Rectangle(250, 200, 600, 400)); myFrame.setPreferredSize(new Dimension(600,400)); myFrame.setResizable(false); // myFrame.setIconImage(new ImageIcon(new Image("rose.jpg")));

content= (JPanel) myFrame.getContentPane(); content.setLayout(new FlowLayout()); userLabel=new JLabel("Password"); userTextField = new JTextField(20); userLabel.setLabelFor(userTextField); content.add(userLabel,FlowLayout.LEADING); content.add(userTextField); myFrame.pack(); myFrame.setVisible(true); }


public static void main(String [] args){
SwingUtilities.invokeLater(new Runnable() {
public void run() {
//Turn off metal's use of bold fonts
UIManager.put("swing.boldMetal", Boolean.FALSE); createAndShowGUI(); }
}); }
}