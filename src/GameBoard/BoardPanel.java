package GameBoard;

import Game.GameLogic;
import GameUtils.BoardMarks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    private static BoardPanel boardPanel_ = null;

    public static BoardPanel getInstance() {
        if(BoardPanel.boardPanel_ == null) {
            boardPanel_ = new BoardPanel();
        }

        return boardPanel_;
    }

    private BoardPanel() {
        this.setLayout(new GridLayout(3, 3, 10, 10));
        this.setBackground(Color.BLACK);

        // Fill the board panel with boards
        for(int i = 0; i < 9; i++) {
            Board board = new Board();
            board.setPosition(i);
            board.putClientProperty("parent", this);
            this.add(board);
        }
    }

    // Solely to prevent an issue where there would be thin border-lines
    // If BoardPanel had non-black background and there was resizing done.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int squareSize = (int) (Math.min(panelWidth, panelHeight) / 1.005);

        int offsetX = (panelWidth - squareSize) / 2;
        int offsetY = (panelHeight - squareSize) / 2;

        g.setColor(Color.CYAN); // Color of board panel lines (between boards)
        g.fillRect(offsetX, offsetY, squareSize, squareSize);
    }

    // If MainBoard is
    public boolean validateMainBoard(int boardWonPos) {
        // Check rows
        int row = boardWonPos / 3;
        boolean rowWin = true;
        for (int col = 0; col < 3; col++) {
            if (((Board)this.getComponent(row * 3 + col)).isCompleted() == false) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) {
            return true;
        }

        // Check columns
        int col = boardWonPos % 3;
        boolean colWin = true;
        for (int r = 0; r < 3; r++) {
            if (((Board)this.getComponent(r * 3 + col)).isCompleted() == false) {
                colWin = false;
                break;
            }
        }
        if (colWin) {
            return true;
        }

        // Check 1st diagonal (top left -> bottom right)
        if  (((Board)this.getComponent(0)).isCompleted() &&
            (((Board)this.getComponent(4)).isCompleted() &&
            ((Board)this.getComponent(8)).isCompleted()))
        {
            return true;
        }

        // Check 2nd diagonal (bottom left -> top right)
        if  (((Board)this.getComponent(6)).isCompleted() &&
            (((Board)this.getComponent(4)).isCompleted() &&
            ((Board)this.getComponent(2)).isCompleted()))
        {
            return true;
        }

        return false;
    }
}