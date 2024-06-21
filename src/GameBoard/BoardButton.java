package GameBoard;

import Game.GameLogic;
import GameUtils.BoardMarks;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class BoardButton extends JButton {
    public static Image imageMarkX = null;
    public static Image imageMarkO = null;

    private BoardMarks mark = BoardMarks.MARK_EMPTY;
    private int position;

    public BoardButton() {
        // Disable the 'pressed' state <- how the button looks when its clicked
        setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                // Do not paint the pressed state to prevent color change
            }
        });

        this.setPreferredSize(new Dimension(50, 50));

        // Set better appearance of JButton
        this.setBorder(new LineBorder(Color.YELLOW, 0));
        this.setBackground(Color.BLACK);
        setFocusPainted(false);

        // My own hover effect
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                Board parent = (Board)BoardButton.this.getClientProperty("parent");
                if(parent != null && parent.isCompleted()) return;

                BoardButton.this.setBorder(new LineBorder(Color.YELLOW, 3));
            }
            @Override
            public void mouseExited(MouseEvent event) {
                Board parent = (Board)BoardButton.this.getClientProperty("parent");
                if(parent != null && parent.isCompleted()) return;

                BoardButton.this.setBorder(new LineBorder(Color.YELLOW, 0));
            }
            @Override
            public void mousePressed(MouseEvent event) {
                if(BoardButton.this.mark != BoardMarks.MARK_EMPTY) return;

                Board parent = (Board)BoardButton.this.getClientProperty("parent");
                if(parent != null && parent.isCompleted()) return;

                this.mouseExited(event);
                GameLogic.getInstance().onBoardClicked(parent,BoardButton.this);
            }
        });
    }

    public BoardMarks getMark() {
        return mark;
    }

    public void setMark(BoardMarks mark) {
        this.mark = mark;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static void initializeBoardButtonIcons() {
        String path1 = "boardMarks/mark_X.png";
        String path2 = "boardMarks/mark_O.png";

        InputStream inputStream1 = BoardButton.class.getClassLoader().getResourceAsStream(path1);
        InputStream inputStream2 = BoardButton.class.getClassLoader().getResourceAsStream(path2);
        try {
            BoardButton.imageMarkX = ImageIO.read(inputStream1);
            BoardButton.imageMarkO = ImageIO.read(inputStream2);
        } catch (IOException e) {
            throw new RuntimeException("BoardButton::initializeBoardButtonIcons() -> Couldn't find mark_X or mark_O in " + path1);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        BoardMarks mark = BoardButton.this.getMark();
        if(mark != BoardMarks.MARK_EMPTY) {
            if(mark == BoardMarks.MARK_O)
                g.drawImage(BoardButton.imageMarkX, 0, 0, getWidth(), getHeight(), this);
            else
                g.drawImage(BoardButton.imageMarkO, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
