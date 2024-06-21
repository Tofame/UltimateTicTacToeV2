package Game;

import GameUtils.Players;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameLogic {
    private static GameLogic gameLogic_;

    public static GameLogic getInstance() {
        if(gameLogic_ == null) {
            gameLogic_ = new GameLogic();
            return gameLogic_;
        }

        return gameLogic_;
    }

    // Variables
    private Players turn;
    private boolean isAIMode;
    private PropertyChangeSupport support;

    private GameLogic() {
        this.support = new PropertyChangeSupport(this);
    }

    public Players getTurn() {
        return turn;
    };

    public void setTurn(Players turn) {
        // We need this to be null, because if old = new, then property change event won't fire
        Players oldTurn = null;
        this.turn = turn;
        this.support.firePropertyChange("onTurnChanged", oldTurn, this.turn);
    }
    public void nextTurn() {
        // We need this to be null, because if old = new, then property change event won't fire
        Players oldTurn = null;
        if(turn == Players.PLAYER_X) {
            turn = Players.PLAYER_O;
        } else {
            turn = Players.PLAYER_X;
        }
        this.support.firePropertyChange("onTurnChanged", oldTurn, this.turn);
    }

    public String getTurnInfo() {
        String result = "";

        if (isAIModeEnabled()) {
            if (turn == Players.PLAYER_X) {
                result = " Your turn";
            } else {
                result = " AI's turn";
            }
        } else {
            result = " turn";
        }

        return result;
    }

    public boolean isAIModeEnabled() {
        return isAIMode;
    }
    public void enableAIMode(boolean value) {
        this.isAIMode = value;
    }

    public void startGame(boolean AIMode) {
        JPanel cardPanel = GameWindow.getInstance().getCardPanel();
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, "GamePanel");
        enableAIMode(AIMode);
        setTurn(Players.PLAYER_X);
    }

    public void quitGame() {
        JPanel cardPanel = GameWindow.getInstance().getCardPanel();
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, "MainMenuPanel");
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)  {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
