package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;

public class GameWindow extends JFrame {
    private static final int DEFAULT_WIDTH = 840;
    private static final int DEFAULT_HEIGHT = 840;
    private static final String DEFAULT_TITLE = "Ultimate Tic Tac Toe";

    private static GameWindow gameWindow_ = null;

    public static GameWindow getInstance() {
        if(GameWindow.gameWindow_ == null) {
            gameWindow_ = new GameWindow();
        }

        return gameWindow_;
    }

    private JPanel cardPanel;

    public JPanel getCardPanel() {
        return this.cardPanel;
    }

    private GameWindow() {
        super(DEFAULT_TITLE);
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.BLACK);

        // Card Panel that allows for showing only 1 of its components at a time. This will be our scene manager
        this.cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());
        cardPanel.setOpaque(false);

        this.getContentPane().add(cardPanel);

        // Create Main Menu Panel
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("background2.png");
        Image background = null;
        try {
            background = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background2.png in resources/");
        }

        MainMenuPanel mainMenuPanel = new MainMenuPanel(background);
        cardPanel.add(mainMenuPanel, "MainMenuPanel");

        // Create Game Panel
        GamePanel gamePanel = new GamePanel();
        cardPanel.add(gamePanel, "GamePanel");

        // Showing the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}