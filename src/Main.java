import Game.GameLogic;
import Game.GameWindow;
import GameBoard.BoardButton;
import GameUtils.Players;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    BoardButton.initializeBoardButtonIcons();

                    GameWindow gameWindow = GameWindow.getInstance();
                }
        );
    }
}