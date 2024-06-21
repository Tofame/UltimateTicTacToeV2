package GameBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private boolean completed;
    private int position;

    private JPanel fieldsPanel;

    public Board() {
        this.completed = false;

        this.setLayout(new BorderLayout());
        this.setOpaque(true);

        fieldsPanel = new JPanel(){
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

                g.setColor(Color.CYAN); // Color of lines beetween fields
                g.fillRect(offsetX, offsetY, squareSize, squareSize);
            }
        };
        fieldsPanel.setBackground(Color.BLACK); // if this wasn't black then resizing would be bad
        fieldsPanel.setLayout(new GridLayout(3, 3, 5, 5));

        // Fill board's fieldsPanel with buttons
        for(int i = 0; i < 9; i++) {
            BoardButton b = new BoardButton();
            b.setPosition(i);
            b.putClientProperty("parent", this);
            fieldsPanel.add(b);
        }

        this.add(fieldsPanel, BorderLayout.CENTER);
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
}
