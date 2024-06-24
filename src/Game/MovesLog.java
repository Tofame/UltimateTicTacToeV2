package Game;

import GameBoard.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;

public class MovesLog extends JScrollPane {
    private static MovesLog movesLog_ = null;

    public static MovesLog getInstance() {
        if(MovesLog.movesLog_ == null) {
            movesLog_ = new MovesLog();
        }

        return movesLog_;
    }

    private JPanel movesPanel;
    // We are using a 'cheat', as recommended line (similar): this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getHeight());
    // Wouldn't work
    private boolean autoScrollEnabled = true;

    public MovesLog() {
        this.setMinimumSize(new Dimension(160, 400));
        this.setPreferredSize(new Dimension(200, 560));

        this.movesPanel = new JPanel();
        movesPanel.setBackground(Color.LIGHT_GRAY);
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));

        this.getViewport().add(movesPanel);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Changing scrolling speed as it is slow by default
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(26);

        // Scroll to the bottom when new board is added to move log
//         https://stackoverflow.com/questions/5147768/scroll-jscrollpane-to-bottom Author: Peter Saitz
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(autoScrollEnabled) {
                    autoScrollEnabled = false;
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                }
            }
        });
    }

    public void addBoardToMoveHistory() {
        BoardPanel boardPanel = BoardPanel.getInstance();

        int width = boardPanel.getWidth();
        int height = boardPanel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        boardPanel.paint(g2);
        g2.dispose();

        // scaling the image so it fits the moves log
        int size = Math.min(this.getViewport().getWidth(), this.getViewport().getHeight());
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        // add image to movesPanel
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(imageIcon);

        int spacing = 10;
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, spacing, 0));

        this.movesPanel.add(imageLabel);
        this.scrollToBottom();

        this.revalidate();
        this.repaint();
    }

    private void scrollToBottom() {
        autoScrollEnabled = true;
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void clear() {
        this.movesPanel.removeAll();
    }
}
