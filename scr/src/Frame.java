import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.GraphicAttribute;
import java.util.concurrent.atomic.AtomicReference;

class Grapisc {
    int cordX, cordY, leng;
    static int conting;

    public Grapisc(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.leng = -1;
    }

    public Grapisc(int leng) {
        this.leng = leng;
        this.cordX = -1;
        this.cordY = -1;
    }

    public Grapisc() {
        this.cordX = -1;
        this.cordY = -1;
    }

    public static Grapisc[][] addTop(Grapisc[][] graph, Grapisc hiu, Grapisc kaka, int ling) {
        int i = containsPoint(graph[0], hiu), j = containsPoint(graph[0], kaka);
        if (i == -1) {
            for (int h = 0; h < graph.length; h++) {
                if (graph[0][h].cordX == -1) {
                    graph[0][h] = hiu;
                    i = h;
                    break;
                }
            }
            Grapisc.conting++;
        }

        if (j == -1) {
            for (int h = 0; h < graph.length; h++) {
                if (graph[0][h].cordX == -1) {
                    graph[0][h] = kaka;
                    j = h;
                    break;
                }
            }
            Grapisc.conting++;
        }

        graph[i][j] = new Grapisc(ling);
        return graph;
    }

    private static int containsPoint(Grapisc[] graph, Grapisc point) {
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].equals(point)) {
                return i;
            }
        }
        return -1;
    }

    public static void Floid(Grapisc[][] graph) {
        int len = graph.length;
        int[][] arr = new int[len - 1][len - 1];
        for (int i = 1; i < len; i++) {
            for (int j = 1; j < len; j++) {
                arr[i - 1][j - 1] = graph[i][j].leng;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Grapisc[][] generateTwoDArray(Grapisc[][] graph) {
        int len = graph.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                graph[i][j] = new Grapisc();
            }
        }
        return graph;
    }
}

public class Frame extends JFrame {
    boolean isCheck = false;
    int X;
    int Y;

    Frame() {
        super("Bohdan_Tarasyshyn");
        setBounds(50, 15, 1300, 720);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addCheckingButtons(int x, int y, int tops) {
        JPanel panel = new JPanel(null);
        AtomicReference<Grapisc[][]> graphicRef = new AtomicReference<>(Grapisc.generateTwoDArray(new Grapisc[tops + 1][tops + 1]));
        panel.setBounds(250, 25, x * 50, y * 50);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                JButton butt = new JButton();
                butt.setBounds(j * 50, i * 50, 10, 10);
                butt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (isCheck) {
                            Graphics g = getGraphics();
                            g.drawLine(X + 262, Y + 62, button.getX() + 262, button.getY() + 62);
                            isCheck = false;
                            graphicRef.set(Grapisc.addTop(graphicRef.get(), new Grapisc(X, Y), new Grapisc(button.getX(), button.getY()), Integer.parseInt(JOptionPane.showInputDialog(panel, "Введіть ваги", "Заповнення значення", JOptionPane.QUESTION_MESSAGE))));
                            if (Grapisc.conting == tops+1) {
                                panel.setVisible(false);
                                Grapisc.Floid(graphicRef.get());
                            }
                        } else {
                            if (Grapisc.conting == tops+1) {
                                panel.setVisible(false);
                                Grapisc.Floid(graphicRef.get());
                            }
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