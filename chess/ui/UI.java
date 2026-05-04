import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
//import types.*;

public class UI extends JFrame {
    // ChessGame logic;

    private JButton[][] squares = new JButton[8][8];

    public UI() {
        // logic = new GameLogic();

        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 1024;
        int frameHeight = 1024;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("dada");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        squares = new JButton[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JButton();
                squares[i][j].setBounds(i * frameWidth / 8, frameHeight - (j + 1) * frameHeight / 8, frameWidth / 8,
                        frameHeight / 8);
                squares[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        squareClicked(evt);
                    }
                });

                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(new Color(0x7A6543));
                }
                cp.add(squares[i][j]);
            }
        }

        setVisible(true);

        // updateBoard();
    }

    public static void main(String[] args) {
        new UI();
    }

    private void squareClicked(ActionEvent evt) {
    }

    // private void updateBoard() {
    // logic.getBoard();
    // }
}
