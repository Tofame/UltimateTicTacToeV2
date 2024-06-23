package GameBoard;

import Game.GameLogic;
import GameUtils.BoardMarks;

import javax.swing.*;
import javax.swing.border.LineBorder;
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

    // Position of board with allowed move. -1 -> any board, 0-8 otherwise.
    public int boardMovePos = -1;
    // Highlighted board position // -1 -> nothing highlighted
    public int boardHighlighted = -1;

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

    // Returns null if move can be made in any board
    public Board getBoardWithMove() {
        if(boardMovePos == -1) {
            return null;
        }

        return (Board)this.getComponent(boardMovePos);
    }

    // Returns null if move can be made in any board
    public int getBoardPosWithMove() {
        return boardMovePos;
    }

    // Sets a board where moves are allowed. '-1' value for any board.
    public void setBoardPosWithMove(int position) {
        boardMovePos = position;
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
        if(position == -1) {
            return null;
        }

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

    public void highlightBoard(int position) {
        if(boardHighlighted != -1) {
            Board boardToRemoveHighlight = getBoard(boardHighlighted);
            if(boardToRemoveHighlight != null && !boardToRemoveHighlight.isCompleted()) {
                for(BoardButton bb : boardToRemoveHighlight.getBoardButtons()) {
                    bb.setBackground(Color.BLACK);
                }
                boardToRemoveHighlight.setBackground(Color.WHITE);
                boardToRemoveHighlight.setBorder(new LineBorder(Color.BLACK, 10));
            }
        }

        Board boardToHighlight = getBoard(position);
        if(boardToHighlight == null) return;

        // The board meant to be highlighted might be completed
        if(boardToHighlight.isCompleted()) {
            return;
        }

        this.boardHighlighted = position;
        for(BoardButton bb : boardToHighlight.getBoardButtons())
            bb.setBackground(Color.MAGENTA);
        boardToHighlight.setBackground(Color.MAGENTA);
        boardToHighlight.setBorder(new LineBorder(new Color(204, 0, 153), 10));
    }

    // Decide the next board where a move can be made, we take position of button that was clicked
    // and by the rules of tic-tac-toe it's a position of next board
    public void decideNextMove(int nextBoardPosition) {
        Board nextBoard = BoardPanel.getInstance().getBoard(nextBoardPosition);

        BoardPanel.getInstance().setBoardPosWithMove(nextBoardPosition);

        if(BoardPanel.getInstance().canMoveBeMadeInBoard(nextBoard)) {
            BoardPanel.getInstance().setBoardPosWithMove(nextBoardPosition);
        } else {
            // Move can be made anywhere as rules of Ultimate Tic Tac Toe say so.
            BoardPanel.getInstance().setBoardPosWithMove(-1);
        }

        BoardPanel.getInstance().highlightBoard(nextBoardPosition);
    }

    // Returns true if a move can be made in the board that is passed in parameter.
    public boolean canMoveBeMadeInBoard(Board boardChecked) {
        if(boardChecked.isCompleted()) {
            return false;
        }

        if(boardMovePos != -1 && boardMovePos != boardChecked.getPosition()) {
            return false;
        }

        return true;
    }

    public void resetBoardPanel() {
        highlightBoard(-1);
        setBoardPosWithMove(-1);
        var boards = this.getBoards();
        for(Board board : boards) {
            board.setCompleted(false);
            for(BoardButton boardButton : board.getBoardButtons()) {
                boardButton.setMark(BoardMarks.MARK_EMPTY);
                boardButton.repaint();
            }
        }
    }
}