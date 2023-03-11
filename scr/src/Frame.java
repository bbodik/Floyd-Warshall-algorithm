import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

class Grapisc {
    int cordX, cordY, leng;
    static boolean isEnded = false;

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
        this.leng = -1;
    }

    public static Grapisc[][] addTop(Grapisc[][] graph, Grapisc hiu, Grapisc kaka, int ling) {
        int i = containsPoint(graph[0], hiu), j = containsPoint(graph[0], kaka);
        System.out.println(i + " " + j);
        if (!Grapisc.isEnded) {
            if (i == -1) {
                for (int h = 0; h < graph[0].length; h++) {
                    if (graph[0][h].cordX == -1) {
                        graph[0][h] = hiu;
                        i = h;
                        break;
                    }
                }
            }

            if (j == -1) {
                for (int h = 0; h < graph[0].length; h++) {
                    if (graph[0][h].cordX == -1) {
                        graph[0][h] = kaka;
                        j = h;
                        break;
                    }
                }

            }
        }
        if (j == -1 || i == -1) {
            Grapisc.isEnded = true;
        } else {
            graph[j + 1][i] = new Grapisc(ling);
        }
        return graph;
    }

    private static int containsPoint(Grapisc[] graph, Grapisc point) {
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].cordX == point.cordX && graph[i].cordY == point.cordY) {
                return i;
            }
        }
        return -1;
    }

    public static void Floid(Grapisc[][] graph) {
        int len = graph[0].length;
        int[][] arr = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                arr[i][j] = graph[i + 1][j].leng;
                if (i == j) arr[i][j] = -1;
            }
        }
        arr = makeSymmetric(arr);
        System.out.println("-------------------");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    public static Grapisc[][] generateTwoDArray(Grapisc[][] graph) {
        int len = graph.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1; j++) {
                graph[i][j] = new Grapisc();
            }
        }
        return graph;
    }

    public static int[][] makeSymmetric(int[][] arr) {
        int n = arr.length;
        int[][] symmArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] != -1) {
                    if (i == j) symmArr[i][j] = 0;
                    else {
                        symmArr[i][j] = symmArr[j][i] = arr[i][j];
                        if(symmArr[i][j]==0)symmArr[i][j]=-1;
                    }
                }

            }
        }
        return symmArr;
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
        AtomicReference<Grapisc[][]> graphicRef = new AtomicReference<>(Grapisc.generateTwoDArray(new Grapisc[tops + 1][tops]));
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
                            if (!Grapisc.isEnded) {
                                g.drawLine(X + 262, Y + 62, button.getX() + 262, button.getY() + 62);
                                isCheck = false;
                                graphicRef.set(Grapisc.addTop(graphicRef.get(), new Grapisc(X, Y), new Grapisc(button.getX(), button.getY()), Integer.parseInt(JOptionPane.showInputDialog(panel, "Введіть довжину", "Заповнення значення", JOptionPane.QUESTION_MESSAGE))));
                            } else {
                                Grapisc.Floid(graphicRef.get());
                            }
                        } else {
                            if (Grapisc.isEnded) {
                                Grapisc.Floid(graphicRef.get());
                            } else {
                                X = button.getX();
                                Y = button.getY();
                                isCheck = true;
                            }
                        }
                    }
                });
                panel.add(butt);
            }
        }

        this.add(panel);
    }

}