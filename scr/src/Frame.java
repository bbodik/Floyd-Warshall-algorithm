import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Grapisc{
    int cordX,cordY;
    static int conting;

    public Grapisc(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
    }
}

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

    public void addCheckingButtons(int x,int y,int tops) {
        JPanel panel = new JPanel(null);
        Grapisc[] graphic=new Grapisc[tops];
        panel.setBounds(250, 25, x*50, y*50);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                JButton butt = new JButton();
                butt.setBounds(j*50, i*50, 10, 10);
                butt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (isCheck) {
                            Graphics g = getGraphics();
                            g.drawLine(X + 262, Y + 62, button.getX() + 262, button.getY() + 62);
                            isCheck = false;
                            JOptionPane.showInputDialog(panel,"Введіть ваги","Заповнення значення",JOptionPane.QUESTION_MESSAGE);
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