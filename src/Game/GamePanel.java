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
        this.setLayout(new BorderLayout());
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

        stateGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stateGameLabel.setFont(new Font("Arial", Font.BOLD, 54));
        stateGameLabel.setForeground(Color.WHITE);
        stateGameLabel.setPreferredSize(new Dimension(650, 60));
        stateGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stateGameLabel.revalidate();
        stateGameLabel.repaint();
        GameLogic.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("bbibibi");
                if ("turn".equals(evt.getPropertyName())) {
                    stateGameLabel.setText(GameLogic.getInstance().getTurnInfo());
                    System.out.println(">>" + GameLogic.getInstance().getTurnInfo());
                    stateGameLabel.repaint();
                }
            }
        });
        this.add(stateGameLabel, BorderLayout.NORTH);

        // Content Panel
//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new GridBagLayout());
//        contentPanel.setOpaque(false);
//        this.add(contentPanel, BorderLayout.CENTER);
//
//        // Buttons panel
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(4, 1, 30, 0));
//        buttonPanel.setOpaque(false);
//        buttonPanel.setBorder(new LineBorder(Color.BLACK, 7));
//        buttonPanel.setSize(new Dimension(500, 300));
//        contentPanel.add(buttonPanel);

        // Setup buttons
//        Dimension buttonSize = new Dimension(260, 40);
//
//        JButton pveButton = new JButton("Player vs AI");
//        pveButton.setPreferredSize(buttonSize);
//
//        JButton pvpButton = new JButton("Player vs Player");
//        pvpButton.setPreferredSize(buttonSize);
//
//        JButton loadGameButton = new JButton("Load Game");
//        loadGameButton.setPreferredSize(buttonSize);
//
//        JButton exitButton = new JButton("Exit");
//        exitButton.setPreferredSize(buttonSize);
//
//        pveButton.setFocusPainted(false);
//        pvpButton.setFocusPainted(false);
//        loadGameButton.setFocusPainted(false);
//        exitButton.setFocusPainted(false);
//
//        buttonPanel.add(pveButton);
//        buttonPanel.add(pvpButton);
//        buttonPanel.add(loadGameButton);
//        buttonPanel.add(exitButton);

//        exitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
    }
}