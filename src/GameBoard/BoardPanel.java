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

    public ArrayList<Board> getBoards() {
        ArrayList<Board> tempBoardArray = new ArrayList<>(9);

        for(Component c : this.getComponents()) {
            if(c instanceof Board) {
                tempBoardArray.add((Board)c);
            }
        }

        return tempBoardArray;
    }

    public Board getBoard(int position) {
        return (Board)this.getComponent(position);
    }

    public void completeAll() {
        for(Board board : this.getBoards()) {
            board.setCompleted(true);
        }
    }

    public boolean isGameTied() {
        boolean tie = true;

        for(Board board : getBoards()) {
            if(!board.isCompleted()) {
                tie = false;
                break;
            }
        }

        return tie;
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
    public boolean validateMainBoard(int boardWonPos, BoardMarks wonBoardMark) {
        // Check rows
        int row = boardWonPos / 3;
        boolean rowWin = true;
        for (int col = 0; col < 3; col++) {
            if (getBoard(row * 3 + col).getMark() != wonBoardMark) {
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
            if (getBoard(r * 3 + col).getMark() != wonBoardMark) {
                colWin = false;
                break;
            }
        }
        if (colWin) {
            return true;
        }

        // Check 1st diagonal (top left -> bottom right)
        if  ((getBoard(0).getMark() == wonBoardMark) &&
            (getBoard(4).getMark() == wonBoardMark) &&
            (getBoard(8).getMark() == wonBoardMark))
        {
            return true;
        }

        // Check 2nd diagonal (bottom left -> top right)
        if  ((getBoard(6).getMark() == wonBoardMark) &&
            (getBoard(4).getMark() == wonBoardMark) &&
            (getBoard(2).getMark() == wonBoardMark))
        {
            return true;
        }

        return false;
    }
}