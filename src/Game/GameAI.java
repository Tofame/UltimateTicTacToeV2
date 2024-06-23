package Game;

import GameBoard.Board;
import GameBoard.BoardButton;
import GameBoard.BoardPanel;
import GameUtils.BoardMarks;
import GameUtils.Players;
import Utils.MyPair;

import java.util.ArrayList;

public class GameAI {
    private static GameAI gameAI_ = null;

    public static GameAI getInstance() {
        if (GameAI.gameAI_ == null) {
            gameAI_ = new GameAI();
        }
        return gameAI_;
    }

    private GameAI() {}

    private boolean enabledAI = false;

    public void enableAIMode(boolean value) {
        this.enabledAI = value;
    }

    public boolean isAIModeEnabled() {
        return enabledAI;
    }

    public boolean isAITurn() {
        return GameLogic.getInstance().getTurn() == Players.PLAYER_O;
    }

    public void makeBestMove() {
        MyPair<Board, BoardButton> bestMove = findBestMove();
        Board board = bestMove.getFirst();
        BoardButton button = bestMove.getSecond();

        if (board != null && button != null) {
            board.onBoardClicked(button);
        }
    }

    public MyPair<Board, BoardButton> findBestMove() {
        Board boardToMoveIn = BoardPanel.getInstance().getBoardWithMove();
        MyPair<Board, BoardButton> bestMovePair = new MyPair<>(boardToMoveIn, null);

        ArrayList<Board> boardsToCheck = new ArrayList<>();
        if (boardToMoveIn == null) {
            var boards = BoardPanel.getInstance().getBoards();
            for (Board board : boards) {
                if (!board.isCompleted()) {
                    boardsToCheck.add(board);
                }
            }
        } else {
            boardsToCheck.add(boardToMoveIn);
        }

        bestMovePair = findBestMoveFromBoards(boardsToCheck);
        return bestMovePair;
    }

    public MyPair<Board, BoardButton> findBestMoveFromBoards(ArrayList<Board> boards) {
        for (Board board : boards) {
            for (BoardButton button : board.getBoardButtons()) {
                if (button.getMark() == BoardMarks.MARK_EMPTY) {
                    return new MyPair<>(board, button);
                }
            }
        }
        return new MyPair<>(null, null);
    }
}