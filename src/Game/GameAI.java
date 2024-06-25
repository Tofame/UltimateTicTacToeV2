package Game;

import GameBoard.Board;
import GameBoard.BoardButton;
import GameBoard.BoardPanel;
import GameUtils.BoardMarks;
import GameUtils.Players;
import Utils.EvaluatedMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        // https://www.geeksforgeeks.org/priority-queue-class-in-java/
        // Priority queue is a collection that will store our buttons and sort them in descending order (1st element will be of highest score)
        PriorityQueue<EvaluatedMove<BoardButton, Integer>> evaluatedMoves = new PriorityQueue<>(new Comparator<EvaluatedMove<BoardButton, Integer>>() {
            @Override
            public int compare(EvaluatedMove<BoardButton, Integer> pair1, EvaluatedMove<BoardButton, Integer> pair2) {
                return pair2.getSecond().compareTo(pair1.getSecond());
            }
        });

        for (Board board : boards) {
            for (BoardButton button : board.getUnmarkedButtons()) {
                int moveScore = 0;

                // Check if this move wins the board for AI
                button.setMark(BoardMarks.MARK_O);
                if (board.validateBoard(button.getPosition(), BoardMarks.MARK_O)) {
                    moveScore += AIWeightChoice.AI_WIN.getValue();
                    // This move wins whole game for AI, so we return it instantly.
                    if(BoardPanel.getInstance().validateMainBoard(board.getPosition(), BoardMarks.MARK_O)) {
                        return button;
                    };
                } else {
                    moveScore += AIWeightChoice.AI_NO_WIN.getValue();
                }
                button.setMark(BoardMarks.MARK_EMPTY);

                // Check if this move doesn't lose AI the board in next player's move
                Board nextBoard = BoardPanel.getInstance().getBoard(button.getPosition());
                if(nextBoard.getUnmarkedButtons().isEmpty()) {
                    moveScore += AIWeightChoice.PLAYER_MOVE_ANYWHERE.getValue();
                } else {
                    moveScore += AIWeightChoice.PLAYER_NO_MOVE_ANYWHERE.getValue();

                    boolean playerWinsBoard = false;
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

                    if(playerWinsBoard)
                        moveScore += AIWeightChoice.PLAYER_WIN.getValue();
                    else
                        moveScore += AIWeightChoice.PLAYER_NO_WIN.getValue();
                }

                evaluatedMoves.add(new EvaluatedMove<>(button, moveScore));
            }
        }

        if(evaluatedMoves.isEmpty()) {
            // It is very unlikely (impossible) to happen, but let's have an exception anyway.
            throw new RuntimeException("GameAI::findBestMoveButtonFromBoards() couldn't find even 1 move.");
        }

        // We will not get sorted elements by printing PriorityQueue.
        // System.out.println(evaluatedMoves);
        return evaluatedMoves.element().getFirst();
    }
}