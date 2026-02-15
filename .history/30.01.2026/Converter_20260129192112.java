import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Converter {
    public static JFrame frmMain;
    public static JLabel lblCelsius;
    public static JTextField textCelsius;
    public static JLabel lblFahrenheit;
    public static JTextField textFahrenheit;
    public static JButton btnCalculateCtoF;
    public static JButton btnCalculateFtoC;
    public static void main(String[] args)
    {
        // Set up the frame
        frmMain = new JFrame("Temperature Converter");
        frmMain.setSize(450, 400);
        frmMain.setLayout(new FlowLayout());
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Create GUI Elements
        lblCelsius = new JLabel("Celsius:");
        textCelsius = new JTextField(20);
        lblFahrenheit = new JLabel("Fahrenheit:");
        textFahrenheit = new JTextField(20);
        btnCalculateCtoF = new JButton("Convert C to F");
        // Add ActionListener
        btnCalculateCtoF.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // Convert C to F
                    String cText = textCelsius.getText();
                    double c = Double.parseDouble(cText);
                    double f = (c * 9 / 5) + 32;
                    textFahrenheit.setText(String.valueOf(f));
                }
            }
        );
        
        btnCalculateFtoC = new JButton("Convert F to C");
        // Add ActionListener
        btnCalculateFtoC.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // Convert F to C
                    String fText = textFahrenheit.getText();
                    double f = Double.parseDouble(fText);
                    double c = (f - 32) * 5 / 9;
                    textCelsius.setText(String.valueOf(c)); 
                }
            }
        );
    
        // Add the GUI Elements to the frame
        frmMain.add(lblCelsius);
        frmMain.add(textCelsius);
        frmMain.add(lblFahrenheit);
        frmMain.add(textFahrenheit);
        frmMain.add(btnCalculateCtoF);
        frmMain.add(btnCalculateFtoC);
        
        frmMain.setVisible(true);
    }
}
