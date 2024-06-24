package Game;

import Utils.BasicBackgroundPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitGamePanel extends BasicBackgroundPanel {
    public QuitGamePanel(String imagePath) {
        super(imagePath);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        // Game Paused (Saving or Quitting)
        JLabel textLabel = new JLabel() {
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
        textLabel.setText("Game Paused");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 54));
        textLabel.setForeground(Color.WHITE);
        textLabel.setPreferredSize(new Dimension(650, 60));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel.revalidate();
        textLabel.repaint();
        this.add(textLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        this.add(contentPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 30, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.setSize(new Dimension(500, 500));
        contentPanel.add(buttonPanel);

        // Setup buttons
        Dimension buttonSize = new Dimension(260, 60);

        JButton resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(buttonSize);

        JButton saveGameButton = new JButton("Save Game");
        saveGameButton.setPreferredSize(buttonSize);

        JButton backToMenuButton = new JButton("Back To Menu");
        backToMenuButton.setPreferredSize(buttonSize);

        resumeButton.setFocusPainted(false);
        saveGameButton.setFocusPainted(false);
        backToMenuButton.setFocusPainted(false);

        buttonPanel.add(resumeButton);
        buttonPanel.add(saveGameButton);
        buttonPanel.add(backToMenuButton);

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLogic.getInstance().resumeGame();
            }
        });

        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveManager.saveGame();
            }
        });

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLogic.getInstance().backToMenu();
            }
        });
    }
}
