import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Converter {
    public static JFrame mainFrame;
    public static JLabel celsiusLabel;
    public static JTextField textCelsius;
    public static JLabel fahrenheitLabel;
    public static JTextField textFahrenheit;
    public static JButton celsiusToFahrenheitButton;
    public static JButton fahrenheitToCelsiusButton;
    public static void main(String[] args)
    {
        mainFrame = new JFrame("Temperature Converter");
        mainFrame.setSize(450, 400);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Create GUI Elements
        celsiusLabel = new JLabel("Celsius:");
        textCelsius = new JTextField(10);
        fahrenheitLabel = new JLabel("Fahrenheit:");
        textFahrenheit = new JTextField(10);
        celsiusToFahrenheitButton = new JButton("Convert C to F");
        // Add ActionListener
        celsiusToFahrenheitButton.addActionListener
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
        
        fahrenheitToCelsiusButton = new JButton("Convert F to C");
        // Add ActionListener
        fahrenheitToCelsiusButton.addActionListener
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
        mainFrame.add(celsiusLabel);
        mainFrame.add(textCelsius);
        mainFrame.add(fahrenheitLabel);
        mainFrame.add(textFahrenheit);
        mainFrame.add(celsiusToFahrenheitButton);
        mainFrame.add(fahrenheitToCelsiusButton);
        
        mainFrame.setVisible(true);
    }
}
