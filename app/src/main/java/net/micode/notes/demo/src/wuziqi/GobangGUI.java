package wuziqi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GobangGUI extends JFrame {
    private GobangBoardPanel boardPanel;

    public GobangGUI() {
        setTitle("Gobang Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new GobangBoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GobangGUI();
            }
        });
    }
}

class GobangBoardPanel extends JPanel {
    private char[][] board;
    private char currentPlayer;

    public GobangBoardPanel() {
        setLayout(new GridLayout(15, 15));
        initializeBoard();
        currentPlayer = 'X';
        addButtons();
    }

    private void initializeBoard() {
        board = new char[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void addButtons() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton("");
                button.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
                final int row = i;
                final int col = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        makeMove(row, col);
                    }
                });
                add(button);
            }
        }
    }

    private void makeMove(int row, int col) {
        if (board[row][col] == '-') {
            board[row][col] = currentPlayer;
            ((JButton) getComponent(row * 15 + col)).setText(String.valueOf(currentPlayer));

            if (checkWin(row, col)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard();
            } else {
                switchPlayer();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Cell already occupied.");
        }
    }

    private boolean checkWin(int row, int col) {
        // TODO: Implement the logic to check for a win
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetBoard() {
        initializeBoard();
        for (Component component : getComponents()) {
            ((JButton) component).setText("");
        }
        currentPlayer = 'X';
    }
}
