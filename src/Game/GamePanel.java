package Game;

import GameUtils.Players;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GamePanel extends JPanel {
    public GamePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);

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
                int width = textWidth + 40;
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
                if ("onTurnChanged".equals(evt.getPropertyName())) {
                    String turnInfo = GameLogic.getInstance().getTurnInfo();
                    stateGameLabel.setText(turnInfo);
                }
            }
        });
        this.add(stateGameLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        //contentPanel.setOpaque(false);
        contentPanel.setBackground(Color.yellow);
        this.add(contentPanel, BorderLayout.CENTER);


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