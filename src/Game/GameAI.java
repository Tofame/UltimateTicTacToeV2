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
        BoardButton bestMoveButton = findBestMoveButton();

        if (bestMoveButton != null) {
            bestMoveButton.getParentBoard().onBoardClicked(bestMoveButton);
        }
    }

    public BoardButton findBestMoveButton() {
        Board boardToMoveIn = BoardPanel.getInstance().getBoardWithMove();

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

        return findBestMoveButtonFromBoards(boardsToCheck);
    }

    public BoardButton findBestMoveButtonFromBoards(ArrayList<Board> boards) {
        // The best move (no-win, no-anywhere, ai-wins) has immediate return, below in the code.
        // ...
        // Enemy won't win a board && won't have 'anywhere' move
        ArrayList<BoardButton> greatMoves = new ArrayList<>();
        // Enemy won't win a board
        ArrayList<BoardButton> goodMoves = new ArrayList<>();
        // AI can win a board
        ArrayList<BoardButton> averageMoves = new ArrayList<>();
        // Any available moves if nothing else from above was fitting
        ArrayList<BoardButton> availableMoves = new ArrayList<>();

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
                    return button;
                }

                if(!playerWinsBoard && !playerCanMoveAnywhere) {
                    greatMoves.add(button);
                    continue;
                }

                if(!playerWinsBoard) {
                    goodMoves.add(button);
                    continue;
                }

                if(AIWinsBoard) {
                    averageMoves.add(button);
                    continue;
                }

                availableMoves.add(button);
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

        throw new RuntimeException("GameAI::findBestMoveButtonFromBoards() couldn't find even 1 move.");
    }
}