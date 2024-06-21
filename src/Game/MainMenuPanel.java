package Game;

import GameUtils.Players;
import Utils.BasicBackgroundPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends BasicBackgroundPanel {
    public MainMenuPanel(Image background) {
        super(background);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        // Game Title
        JLabel titleLabel = new JLabel() {
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
        titleLabel.setText("ULTIMATE TIC TAC TOE");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 54));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(650, 60));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.revalidate();
        titleLabel.repaint();
        this.add(titleLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        this.add(contentPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new LineBorder(Color.BLACK, 7));
        buttonPanel.setSize(new Dimension(500, 300));
        contentPanel.add(buttonPanel);

        // Setup buttons
        Dimension buttonSize = new Dimension(260, 40);

        JButton pveButton = new JButton("Player vs AI");
        pveButton.setPreferredSize(buttonSize);

        JButton pvpButton = new JButton("Player vs Player");
        pvpButton.setPreferredSize(buttonSize);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setPreferredSize(buttonSize);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);

        pveButton.setFocusPainted(false);
        pvpButton.setFocusPainted(false);
        loadGameButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        buttonPanel.add(pveButton);
        buttonPanel.add(pvpButton);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(exitButton);

        pveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLogic.getInstance().startGame(true);
            }
        });

        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLogic.getInstance().startGame(false);
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}