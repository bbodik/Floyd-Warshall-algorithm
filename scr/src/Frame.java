import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    boolean isCheck=false;
    int X;
    int Y;
    Frame() {
        super("Bohdan_Tarasyshyn");
        setBounds(50, 15, 1300, 720);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addCheckingButtons() {
        JPanel panel = new JPanel(null);
        panel.setBounds(250, 25, 20*50, 13*50);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 20; j++) {
                JButton butt = new JButton();
                butt.setBounds(j*50, i*50, 10, 10);
                butt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (isCheck) {
                            Graphics g = getGraphics();
                            g.drawLine(X + 5+7+250, Y + 5+32+25, button.getX() + 5+7+250, button.getY() + 5+32+25);
                            isCheck = false;
                        } else {
                            X = button.getX();
                            Y = button.getY();
                            isCheck = true;
                        }
                    }
                });
                panel.add(butt);
            }
        }
        this.add(panel);
    }
}