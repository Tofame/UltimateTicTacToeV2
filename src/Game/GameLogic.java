package Game;

import GameUtils.Players;

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
        Players oldTurn = this.turn;
        this.turn = turn;
        this.support.firePropertyChange("onTurnChanged", oldTurn, this.turn);
    }
    public void nextTurn() {
        Players oldTurn = this.turn;
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
        enableAIMode(AIMode);
        setTurn(Players.PLAYER_X);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)  {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
