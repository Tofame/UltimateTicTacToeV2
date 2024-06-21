import Game.GameLogic;
import Game.GameWindow;
import GameUtils.Players;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    GameWindow gameWindow = GameWindow.getInstance();
                }
        );
    }
}