package GameBoard;

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
}