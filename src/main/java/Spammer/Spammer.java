package Spammer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Spammer extends JFrame {

    private JButton startButton;
    private JTextField delayToStart;
    private JPanel mainPanel;
    private JTextField numberOfSpamms;
    private JTextField delayBetweenSpamms;
    private JTextField messageField;


    public Spammer() {
        Window();
        startButton.addActionListener(e -> {
            if (numberOfSpamms.getText().isEmpty() || delayBetweenSpamms.getText().isEmpty() || delayToStart.getText().isEmpty() || messageField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields");
            } else {
                if (numberOfSpamms.getText().matches("[0-9]+") && delayBetweenSpamms.getText().matches("[0-9]+") && delayToStart.getText().matches("[0-9]+")) {
                    if (Integer.parseInt(delayBetweenSpamms.getText()) < 1)
                        try {
                            Robot robot = new Robot();
                            String message = messageField.getText();
                            int times = Integer.parseInt(numberOfSpamms.getText());
                            int delay = Integer.parseInt(delayBetweenSpamms.getText());
                            int delayStart = Integer.parseInt(delayToStart.getText());
                            StringSelection stringSelection = new StringSelection(message);
                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                            Thread.sleep(delayStart);
                            for (int x = 0; x < times; x++) {
                                robot.keyPress(KeyEvent.VK_CONTROL);
                                robot.keyPress(KeyEvent.VK_V);
                                robot.keyRelease(KeyEvent.VK_V);
                                robot.keyRelease(KeyEvent.VK_CONTROL);
                                robot.keyPress(KeyEvent.VK_DELETE);
                                robot.keyRelease(KeyEvent.VK_DELETE);
                                robot.keyPress(KeyEvent.VK_ENTER);
                                robot.keyRelease(KeyEvent.VK_ENTER);
                                Thread.sleep((delay + 1) * 1000L);
                            }
                        } catch (AWTException | InterruptedException e2) {
                            throw new RuntimeException(e2);
                        }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });
    }

    public void Window() {
        JFrame frame = new JFrame("Spammer");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        setTitle("Spammer");
    }
}