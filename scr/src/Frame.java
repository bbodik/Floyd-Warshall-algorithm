import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.GenericArrayType;
import java.util.concurrent.atomic.AtomicReference;

class Grapisc {
    static final int INF = Integer.MAX_VALUE;
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
        var len = graph.length;
        int i, j;
        for (i = 0; i < len; i++) {
            if (graph[0][i].cordX == hiu.cordX && graph[0][i].cordY == hiu.cordY&&i<len-1) {
                graph[0][i+1] = hiu;
                conting++;
                break;
            }
        }
        for (j = 0; j < len; j++) {
            if (graph[j][0].cordX == kaka.cordX && graph[j][0].cordY == kaka.cordY&&j<len-1) {
                graph[j+1][0] = kaka;
                conting++;
                break;
            }
        }
        graph[i][j] = new Grapisc(ling);
        return graph;
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
                        Grapisc[][] graphic = graphicRef.get();
                        if (isCheck) {
                            Graphics g = getGraphics();
                            g.drawLine(X + 262, Y + 62, button.getX() + 262, button.getY() + 62);
                            isCheck = false;
                            graphicRef.set(Grapisc.addTop(graphic, new Grapisc(X, Y), new Grapisc(button.getX(), button.getY()), Integer.parseInt(JOptionPane.showInputDialog(panel, "Введіть ваги", "Заповнення значення", JOptionPane.QUESTION_MESSAGE))));
                            if (Grapisc.conting == tops) {
                                panel.setVisible(false);
                                Grapisc.Floid(graphic);
                            }
                        } else {
                            if (Grapisc.conting == tops) {
                                panel.setVisible(false);
                                Grapisc.Floid(graphic);
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