import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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

    public static Grapisc[][] addTop(Grapisc[][] graph, Grapisc hiu, Grapisc kaka, int ling, Graphics g) {
        int i = containsPoint(graph[0], hiu), j = containsPoint(graph[0], kaka);
        if (i == -1 && !Grapisc.isEnded) {
            for (int h = 0; h < graph[0].length; h++) {
                if (graph[0][h].cordX == -1) {
                    graph[0][h] = hiu;
                    i = h;
                    break;
                }
            }
        }

        if (j == -1 && !Grapisc.isEnded) {
            for (int h = 0; h < graph[0].length; h++) {
                if (graph[0][h].cordX == -1) {
                    graph[0][h] = kaka;
                    j = h;
                    break;
                }
            }

        }
        if (j == -1 || i == -1) {
            Grapisc.isEnded = true;
        } else {
            graph[j + 1][i] = new Grapisc(ling);
        }
        if (!Grapisc.isEnded || (i != -1 && j != -1)) {
            g.drawLine(hiu.cordX + 5, hiu.cordY + 5, kaka.cordX + 5, kaka.cordY + 5);
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

    public static String Floid(Grapisc[][] graph) {
        int len = graph[0].length;
        int[][] arr = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                arr[i][j] = graph[i + 1][j].leng;
                if (i == j) arr[i][j] = -1;
            }
        }
        arr = makeSymmetric(arr);
        String txt="";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                txt+=arr[i][j] + "\t";
            }
            txt+="\n";
        }
        return txt;
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
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i][j] == -1 && arr[j][i] != -1) {
                    arr[i][j] = arr[j][i];
                } else if (arr[i][j] != -1 && arr[j][i] == -1) {
                    arr[j][i] = arr[i][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            arr[i][i] = 0;
        }
        return arr;
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
        JFrame frame=this;
        JPanel panel = new JPanel(null);
        AtomicReference<Grapisc[][]> graphicRef = new AtomicReference<>(Grapisc.generateTwoDArray(new Grapisc[tops + 1][tops]));
        panel.setBounds(250, 25, x * 50, y * 50);
        JButton readyButton = new JButton("Граф закінчено. Розрахувати!");
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Grapisc.isEnded) {
                    JPanel pan=new JPanel();
                    pan.setBounds(10,250,230,frame.getY()-10);
                    pan.setVisible(true);
                    JLabel lbl = new JLabel(Grapisc.Floid(graphicRef.get()));
                    pan.add(lbl);
                    frame.add(pan);
                }
            }
        });
        Thread t = new Thread(() -> {
            readyButton.setBackground(Color.RED);
            readyButton.setText("Розрахувати неможливо");
            while (true) {
                if (Grapisc.isEnded) {
                    readyButton.setBackground(Color.GREEN);
                    readyButton.setText("Розрахувати!");
                    break;
                }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("mistake in Thread");
            }
        }
        });
        readyButton.setBounds(10, 10, 230, 60);
        frame.add(readyButton);
        t.start();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                JButton butt = new JButton();
                butt.setBounds(j * 50, i * 50, 10, 10);
                butt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (isCheck) {

                            isCheck = false;
                            graphicRef.set(Grapisc.addTop(graphicRef.get(), new Grapisc(X, Y), new Grapisc(button.getX(), button.getY()), Integer.parseInt(JOptionPane.showInputDialog(panel, "Введіть довжину", "Заповнення значення", JOptionPane.QUESTION_MESSAGE)), panel.getGraphics()));
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

        frame.add(panel);
    }

}