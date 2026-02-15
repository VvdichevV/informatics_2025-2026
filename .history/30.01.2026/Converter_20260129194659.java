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
    public static JLabel nameLabel;

    public static void main(String[] args) {
        mainFrame = new JFrame("Temperature Converter");
        mainFrame.setSize(450, 400);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        celsiusLabel = new JLabel("Celsius:");
        textCelsius = new JTextField(10);
        fahrenheitLabel = new JLabel("Fahrenheit:");
        textFahrenheit = new JTextField(10);
        celsiusToFahrenheitButton = new JButton("Convert C to F");
        nameLabel = new JLabel("Made by Victor Dichev");

        celsiusToFahrenheitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String cText = textCelsius.getText();
                        double c = Double.parseDouble(cText);
                        double f = (c * 9 / 5) + 32;
                        textFahrenheit.setText(String.valueOf(f));
                    }
                });

        fahrenheitToCelsiusButton = new JButton("Convert F to C");

        fahrenheitToCelsiusButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String fText = textFahrenheit.getText();
                        double f = Double.parseDouble(fText);
                        double c = (f - 32) * 5 / 9;
                        textCelsius.setText(String.valueOf(c));
                    }
                });

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(celsiusLabel);
        centerPanel.add(textCelsius);
        centerPanel.add(fahrenheitLabel);
        centerPanel.add(textFahrenheit);
        centerPanel.add(celsiusToFahrenheitButton);
        centerPanel.add(fahrenheitToCelsiusButton);

        // Bottom right panel with styled name label
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nameLabel.setForeground(new Color(0, 100, 255)); // Blue
        nameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        bottomPanel.add(nameLabel);

        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }
}
