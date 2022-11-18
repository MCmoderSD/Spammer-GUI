import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Spammer extends JFrame {
    private JButton bStartButton;
    private JTextField tfDelayToStart;
    private JPanel mainPanel;
    private JTextField tfNumberOfSpamms;
    private JTextField tfDelayBetweenSpamms;
    private JTextField tfMessageField;

    public Spammer() {

        char[] allowedNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] allowedDelay = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', '-'};

        tfNumberOfSpamms.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (contains(allowedNumbers, c)) {
                    e.setKeyChar(c);
                } else {
                    e.consume();
                }
            }
        });
        tfDelayBetweenSpamms.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (contains(allowedNumbers, c)) {
                    e.setKeyChar(c);
                } else {
                    e.consume();
                }
                if (c == ',')
                    e.setKeyChar('.');
            }
        });
        tfDelayToStart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (contains(allowedDelay, c)) {
                    e.setKeyChar(c);
                } else {
                    e.consume();
                }
                if (c == ',')
                    e.setKeyChar('.');
            }
        });


        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                    return false;
                });
        Window();

        bStartButton.addActionListener(e -> {
            if (tfNumberOfSpamms.getText().isEmpty() || tfDelayBetweenSpamms.getText().isEmpty() || tfDelayToStart.getText().isEmpty() || tfMessageField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields");
            } else {
                int numberOfSpamms = Integer.parseInt(tfNumberOfSpamms.getText());
                double delayBetweenSpamms = Double.parseDouble(tfDelayBetweenSpamms.getText());
                double delayToStart = Double.parseDouble(tfDelayToStart.getText());
                if (numberOfSpamms > 0 || delayBetweenSpamms > 0 || delayToStart > 0) {
                    try {
                        Robot robot = new Robot();
                        String message = tfMessageField.getText();
                        int times = Integer.parseInt(tfNumberOfSpamms.getText());
                        StringSelection stringSelection = new StringSelection(message);
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                        new Thread(() -> {
                            try {
                                Thread.sleep((long) (delayToStart * 1000L));
                                for (int x = 0; x < times; x++) {
                                    robot.keyPress(KeyEvent.VK_CONTROL);
                                    tapKey(robot, KeyEvent.VK_V);
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
                                    tapKey(robot, KeyEvent.VK_DELETE);
                                    tapKey(robot, KeyEvent.VK_ENTER);
                                    Thread.sleep((long) (delayBetweenSpamms * 1000L));
                                }
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }).start();
                    } catch (AWTException e2) {
                        throw new RuntimeException(e2);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });
    }

    public static boolean contains(char[] array, char c) {
        for (char value : array) {
            if (value == c) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new Spammer();
    }

    public void Window() {
        JFrame frame = new JFrame("Spammer");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Spammer");
    }

    public void tapKey(Robot robot, int event) {
        robot.keyPress(event);
        robot.keyRelease(event);
    }
}