import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardUI  extends JFrame {
    JButton[][] pieces;
    JPanel p = new JPanel();
    ActionListener first = new Mover();
    private int x, y;
    ActionListener sec = new ButtonThing();
    int round = 0;
    ArrayList<String> history = new ArrayList<String>();

    public static void main(String args[]) {
        new BoardUI();

    }

    public BoardUI() {

        //setup window
        pack();
        setSize(625, 650);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //panel grid layout && window placement
        p.setLayout(new GridLayout(8, 8));
        setLocation(400, 10);

        //setup for buttons
        pieces = new JButton[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                pieces[r][c] = new JButton();
                pieces[r][c].setOpaque(true);
                pieces[r][c].setBorderPainted(false);

                //coloring background
                if ((r % 2 == 1 && c % 2 == 1)
                        || (r % 2 == 0 && c % 2 == 0)) {
                    pieces[r][c].setBackground(new Color(255, 170, 100));
                } else {
                    pieces[r][c].setBackground(new Color(150, 70, 5));
                }
                pieces[r][c].addActionListener(first);
                p.add(pieces[r][c]);
            }
        }
        SetUp();

        System.out.println("-");

        add(p);

        setVisible(true);
    }



    private class ButtonThing implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //checks all the boxes if they match the one clicked, or "source"
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    if (source == pieces[r][c]) {
                        processClicker(r, c);

                    }
                }
            }

        }

        /* sends selected's icon (data packet computer) to correct
         piece class, like Knight(selected); ,
         to see if valid move. just returns true for now */
        private boolean isValidMove(int x, int y) {
            return true;
        }

        //action listener extention
        private void processClicker(int r, int c) {

        /* this is where you would send the piece type
        (selected.toString().charAt(62)) to another method
        to check if the move is valid
        */
            if (!isValidMove(r, c)) {
                return;
            }

            System.out.println("####");


            pieces[r][c].setIcon(pieces[getX()][getY()].getIcon());
            pieces[getX()][getY()].setIcon(null);

            int p = (int) (Math.random() * 7);
            int g = (int) (Math.random() * 7);
            pieces[p][g].setIcon(new ImageIcon(getClass().getResource("Evil.png")));
            if (g + 1 < 8) {
                pieces[p][g + 1].setIcon(new ImageIcon(getClass().getResource("Evil.png")));
                history.add(p+","+(g+1));
            }
            if (p - 1 > -1 ) {
                pieces[p - 1][g].setIcon(new ImageIcon(getClass().getResource("Evil.png")));
                history.add(p -1+","+g);
            }
            if (p + 1 < 8) {
                pieces[p + 1][g].setIcon(new ImageIcon(getClass().getResource("Evil.png")));
                history.add(p+ 1+","+g);
            }
            if (g - 1 > -1) {
                pieces[p][g - 1].setIcon(new ImageIcon(getClass().getResource("Evil.png")));
                history.add(p+","+(g-1));
            }
            String temp = "" +r + "," + c;


            history.add("" + p+","+g);
            round++;

            System.out.println("temp: " +temp);
            for(String xNy : history) {

                System.out.println("history: " +xNy);
                if ((r == p && g == c) || (g + 1 == c && r == p) || (p - 1 == r && g == c)
                        || (p + 1 == r && g == c) || (getY() == c && getX() == r) ||
                        (g - 1 == c && r == p)|| (xNy.charAt(0) ==temp.charAt(0) &&
                xNy.charAt(2) == temp.charAt(2))) {
                    JOptionPane.showMessageDialog(null,
                            "You survived: " + round + " rounds.",
                            "GG",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            int q = 0;
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j< 8; j++) {

                    pieces[i][j].removeActionListener(sec);
        //            pieces[i][j].removeActionListener(first);
                }
            }

            return;
        }

    }







    private class Mover implements ActionListener {
        private int x, y;
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //checks all the boxes if they match the one clicked, or "source"
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    //once it works, it sends it to the final class for the selection
                    if (source == pieces[r][c]) {
                        processClick(r, c);
                        return;
                    }
                }
            }

        }

    }

    /*sends selected's icon (data packet computer) to correct
    piece class, like Knight(selected); , to see if valid move.
    just returns true for now*/
    private boolean isValidMove(int x, int y) {
        return true;
    }

    //action listener extention
    private void processClick(int r, int c) {

        /*this is where you would send the piece type
        (selected.toString().charAt(62)) to another method
        to check if the move is valid */
        if (!isValidMove(r, c)) {
            return;
        }

        setX(r);
        setY(c);

        for(int R = 0; R< 8; R++) {
            for(int C = 0; C< 8; C++) {
                pieces[R][C].addActionListener(sec);
            }
        }


        return;

    }




    //board setup
    public void SetUp() {

        int e = (int) (Math.random() * 7);
        int t = (int) (Math.random() * 7);
        pieces[e][t].setIcon(new ImageIcon(getClass().getResource("good.png")));
        history.add(Math.abs(e-5)+","+Math.abs(t-5));


        pieces[Math.abs(e - 5)][Math.abs(t - 5)].setIcon(new ImageIcon(getClass().getResource("Evil.png")));

    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
