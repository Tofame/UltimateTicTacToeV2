package GameBoard;

import Game.GameLogic;
import GameUtils.BoardMarks;
import GameUtils.Players;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private boolean completed;
    private int position;

    private JPanel buttonsPanel;

    public Board() {
        this.completed = false;

        this.setLayout(new BorderLayout());
        this.setOpaque(true);

        buttonsPanel = new JPanel(){
            // Solely to prevent an issue where there would be thin border lines
            // If BoardButton had non-black background and there was resizing done.
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int buttonWidth = getWidth();
                int buttonHeight = getHeight();
                int squareSize = (int) (Math.min(buttonWidth, buttonHeight) / 1.12);

                int offsetX = (buttonWidth - squareSize) / 2;
                int offsetY = (buttonHeight - squareSize) / 2;

                g.setColor(Color.WHITE); // Color of lines beetween fields
                g.fillRect(offsetX, offsetY, squareSize, squareSize);
            }
        };
        buttonsPanel.setBackground(Color.BLACK); // if this wasn't black then resizing would be bad
        buttonsPanel.setLayout(new GridLayout(3, 3, 5, 5));

        // Fill board's fieldsPanel with buttons
        for(int i = 0; i < 9; i++) {
            BoardButton b = new BoardButton(this);
            b.setPosition(i);
            buttonsPanel.add(b);
        }

        this.add(buttonsPanel, BorderLayout.CENTER);
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean value) {
        this.completed = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMarkOnPosition(int position, BoardMarks mark) {
        ((BoardButton)this.buttonsPanel.getComponent(position)).setMark(mark);
    }

    public BoardMarks getMarkFromPosition(int position) {
        return ((BoardButton)this.buttonsPanel.getComponent(position)).getMark();
    }

    public void onBoardClicked(BoardButton button) {
        // Specifics of a move a player just made:
        int buttonPos = button.getPosition();
        BoardMarks mark = GameLogic.getInstance().getTurnMark();

        button.setMark(mark);

        boolean boardWin = validateBoard(buttonPos, mark);
        if(boardWin) {
            onBoardWin(mark);
            boolean gameWin = BoardPanel.getInstance().validateMainBoard(this.getPosition());
            if(gameWin) {
                //GameLogic.getInstance().onGameWin(mark);
            }
        }
    }

    public void onBoardWin(BoardMarks winningMark) {
        this.setCompleted(true);
        this.buttonsPanel.setVisible(false);
        this.buttonsPanel.removeAll();

        Image img;
        if(winningMark == BoardMarks.MARK_O)
            img = BoardButton.imageMarkO;
        else
            img = BoardButton.imageMarkX;

        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        JLabel markLabel = new JLabel();
        markLabel.setIcon(new ImageIcon(scaledImg));
        markLabel.setHorizontalAlignment(SwingConstants.CENTER);
        markLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(markLabel);
    }

    // Validates all rows, columns and diagonals in a board. Takes parameter 'buttonPos',
    // which is button's position from onBoardClicked().
    // Returns 'true' if board was won.
    public boolean validateBoard(int buttonPos, BoardMarks mark) {
        if (validateRow(buttonPos, mark)) {
            return true;
        }

        if (validateColumn(buttonPos, mark)) {
            return true;
        }

        if (validateDiagonal(buttonPos, mark)) {
            return true;
        }

        return false;
    }

    // 0 1 2
    // 3 4 5
    // 6 7 8
    private boolean validateRow(int buttonPos, BoardMarks mark) {
        int row = buttonPos / 3;
        for (int col = 0; col < 3; col++) {
            // row * 3 <- (3 is amount of columns) gives us the 1st element of row, we add + col and we get correct index
            if (getMarkFromPosition(row * 3 + col) != mark) {
                return false;
            }
        }
        return true;
    }

    // 0 1 2
    // 3 4 5
    // 6 7 8
    private boolean validateColumn(int buttonPos, BoardMarks mark) {
        int col = buttonPos % 3; // e.g. pos = 4, 1 and 7 have the same result and give the same column, same for others
        for (int row = 0; row < 3; row++) {
            if (getMarkFromPosition(row * 3 + col) != mark) {
                return false;
            }
        }
        return true;
    }

    // 0 1 2
    // 3 4 5
    // 6 7 8
    private boolean validateDiagonal(int buttonPos, BoardMarks mark) {
        // Check 1st (top left -> bottom right)
        if (getMarkFromPosition(0) == mark &&
            getMarkFromPosition(4) == mark &&
            getMarkFromPosition(8) == mark)
        {
            return true;
        }

        // Check 2nd (bottom left -> top right)
        if (getMarkFromPosition(2) == mark &&
            getMarkFromPosition(4) == mark &&
            getMarkFromPosition(6) == mark)
        {
            return true;
        }

        return false;
    }
}
