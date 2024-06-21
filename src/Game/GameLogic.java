package Game;

import GameBoard.Board;
import GameBoard.BoardButton;
import GameUtils.GameState;
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

    private GameState gameState = GameState.DEFAULT;

    private GameLogic() {
        this.support = new PropertyChangeSupport(this);
    }

    public Players getTurn() {
        return turn;
    };

    public void setTurn(Players turn) {
        Players oldTurn = this.turn;
        this.turn = turn;
        // We put null oldValue as we sometimes need to trigger gameInfoChanged even when old=new
        this.support.firePropertyChange("gameInfoChanged", null, this.turn);
    }
    public void nextTurn() {
        Players oldTurn = this.turn;
        if(turn == Players.PLAYER_X) {
            turn = Players.PLAYER_O;
        } else {
            turn = Players.PLAYER_X;
        }
        // We put null oldValue as we sometimes need to trigger gameInfoChanged even when old=new
        this.support.firePropertyChange("gameInfoChanged", null, this.turn);
    }

    public String getTurnInfo() {
        String result = "";

        if (isAIModeEnabled()) {
            if (turn == Players.PLAYER_X) {
                result = "your turn";
            } else {
                result = "AI's turn";
            }
        } else {
            result = "turn";
        }

        return result;
    }

    public String getGameInfo() {
        switch (gameState) {
            case DEFAULT:
                return getTurnInfo();
            case TIE:
                return "Draaaw!";
            case X_WIN:
                return "wins!";
            case O_WIN:
                return "wins!";
            default:
                return getTurnInfo();
        }
    }

    public ImageIcon getInfoIcon() {
        switch (gameState) {
            case TIE:
                return null;
            case X_WIN:
                return new ImageIcon(BoardButton.imageMarkX);
            case O_WIN:
                return new ImageIcon(BoardButton.imageMarkO);
            default:
            case DEFAULT:
                if(getTurn() == Players.PLAYER_X) {
                    return new ImageIcon(BoardButton.imageMarkX);
                } else {
                    return new ImageIcon(BoardButton.imageMarkO);
                }
        }
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }
    public GameState getGameState() {
        return this.gameState;
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

    public void onBoardClicked(Board board, BoardButton button) {

    }
}
