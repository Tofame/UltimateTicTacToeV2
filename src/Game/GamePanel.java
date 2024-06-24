package Game;

import GameBoard.BoardPanel;
import Utils.BasicBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GamePanel extends BasicBackgroundPanel {
    public GamePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(39,39,39));

        this.setBackgroundImage("background2.png");

        // Turn / Win / Tie Label
        JLabel stateGameLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Calculate position and size for the background
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2 - 20;
                int y = (getHeight() - textHeight) / 2;
                int width = textWidth + 80;
                int height = textHeight;

                // Draw background
                g.setColor(Color.GRAY);
                g.fillRect(x, y, width, height);

                super.paintComponent(g);
            }
        };

        stateGameLabel.setAlignmentX(CENTER_ALIGNMENT);
        stateGameLabel.setFont(new Font("Arial", Font.BOLD, 40));
        stateGameLabel.setForeground(Color.WHITE);
        stateGameLabel.setPreferredSize(new Dimension(650, 60));
        GameLogic.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("gameInfoChanged".equals(evt.getPropertyName())) {
                    stateGameLabel.setText(GameLogic.getInstance().getGameInfo());

                    ImageIcon icon = GameLogic.getInstance().getInfoIcon();
                    stateGameLabel.setIcon(icon);
                }
            }
        });
        this.add(stateGameLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setMinimumSize(new Dimension(500, 500));
        this.add(contentPanel, BorderLayout.CENTER);

        // Moves History
        MovesLog movesLog = MovesLog.getInstance();
        movesLog.setBackground(Color.YELLOW);
        movesLog.setMinimumSize(new Dimension(160, 400));
        movesLog.setPreferredSize(new Dimension(200, 560));

        // Add to contentPanel
        contentPanel.add(movesLog);
        contentPanel.add(BoardPanel.getInstance());

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        quitButton.setFocusPainted(false);
        quitButton.setSize(new Dimension(200, 200));

        this.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLogic.getInstance().quitGame();
            }
        });
        this.add(Box.createVerticalStrut(50));
    }
}