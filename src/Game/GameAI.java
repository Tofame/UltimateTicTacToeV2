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
        // Enemy won't win a board && won't move anywhere
        ArrayList<MyPair<Board, BoardButton>> greatMoves = new ArrayList<>();
        // Enemy won't win a board
        ArrayList<MyPair<Board, BoardButton>> goodMoves = new ArrayList<>();
        // AI can win a board
        ArrayList<MyPair<Board, BoardButton>> averageMoves = new ArrayList<>();
        // Any available moves if nothing else from above was fitting
        ArrayList<MyPair<Board, BoardButton>> availableMoves = new ArrayList<>();

        for (Board board : boards) {
            for (BoardButton button : board.getUnmarkedButtons()) {
                boolean playerWinsBoard = false;
                boolean playerCanMoveAnywhere = false;
                boolean AIWinsBoard = false;

                // Check if this move wins the board for AI
                button.setMark(BoardMarks.MARK_O);
                if (board.validateBoard(button.getPosition(), BoardMarks.MARK_O)) {
                    AIWinsBoard = true;
                }
                button.setMark(BoardMarks.MARK_EMPTY);

                // Check if this move doesn't lose AI the board in next player's move
                Board nextBoard = BoardPanel.getInstance().getBoard(button.getPosition());
                if(nextBoard.getUnmarkedButtons().isEmpty()) {
                    playerCanMoveAnywhere = true;
                } else {
                    for (BoardButton buttonForPlayer : nextBoard.getUnmarkedButtons()) {
                        buttonForPlayer.setMark(BoardMarks.MARK_X);
                        if (nextBoard.validateBoard(buttonForPlayer.getPosition(), BoardMarks.MARK_X)) {
                            playerWinsBoard = true;
                            buttonForPlayer.setMark(BoardMarks.MARK_EMPTY);
                            break;
                        } else {
                            buttonForPlayer.setMark(BoardMarks.MARK_EMPTY);
                        }
                    }
                }

                // It's a best move: Player won't win a board, won't be able to move ANYWHERE && AI can win
                if(!playerWinsBoard && !playerCanMoveAnywhere && AIWinsBoard) {
                    return new MyPair<>(button.getParentBoard(), button);
                }

                if(!playerWinsBoard && !playerCanMoveAnywhere) {
                    greatMoves.add(new MyPair<>(button.getParentBoard(), button));
                    continue;
                }

                if(!playerWinsBoard) {
                    goodMoves.add(new MyPair<>(button.getParentBoard(), button));
                    continue;
                }

                if(AIWinsBoard) {
                    averageMoves.add(new MyPair<>(button.getParentBoard(), button));
                    continue;
                }

                availableMoves.add(new MyPair<>(button.getParentBoard(), button));
            }
        }

        if (!greatMoves.isEmpty()) {
            return greatMoves.get(0);
        }
        if (!goodMoves.isEmpty()) {
            return goodMoves.get(0);
        }
        if (!averageMoves.isEmpty()) {
            return averageMoves.get(0);
        }
        if (!availableMoves.isEmpty()) {
            return availableMoves.get(0);
        }

        System.out.println("Shouldnt happen");
        return new MyPair<>(null, null);
    }
}