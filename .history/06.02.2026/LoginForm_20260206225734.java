import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginForm {


       static JPanel content;
       static JLabel userLabel;
       static JTextField  userTextField;
       static JLabel passLabel;
       static JPasswordField  passwordField;
       static JPanel userPane;
       static JPanel passPane;
       static JPanel buttonPane;
       static JButton okButton, cancelButton;




       static void createAndShowGUI(){


           JFrame myFrame=new JFrame("Login Form");
           myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           myFrame.setBounds(new Rectangle(600, 300, 600, 400));
           myFrame.setResizable(false);


           content=  (JPanel) myFrame.getContentPane();
           content.setLayout(new GridLayout(2,1));


           userPane=new JPanel();
           userLabel=new JLabel("username");
           userTextField = new JTextField(20);
           userLabel.setLabelFor(userTextField);


           passPane=new JPanel();
           passLabel=new JLabel("password");
           passwordField=new JPasswordField(20);
           passLabel.setLabelFor(passwordField);


           userPane.add(userLabel);
           userPane.add(userTextField);


           passPane.add(passLabel);
           passPane.add(passwordField);


           content.add(userPane);
           content.add(passPane);


           buttonPane=new JPanel();
           okButton=new JButton("OK");
           cancelButton=new JButton("Cancel");
           buttonPane.add(okButton);
           buttonPane.add(cancelButton);


           content.add(buttonPane);


           myFrame.pack();
           myFrame.setVisible(true);


           toDo();
       }
       static void toDo(){


           okButton.addActionListener(new ActionListener() {
                                          @Override
                                          public void actionPerformed(ActionEvent e) {
                                              String password=new String(passwordField.getPassword());
                                              if(checkPassword(password))
                                                  System.out.println(password);
                                              else{
                                                  System.out.println("Please try again");
                                              }
                                          }
                                      }
           );
       }


       private static boolean checkPassword(String pass){
           if(pass.equals("ACS"))
               return true;
           else
               return false;
       }
       public static void main(String [] args){
           SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                   UIManager.put("swing.boldMetal", Boolean.FALSE);
                   createAndShowGUI();
               }
           });
       }
   }





