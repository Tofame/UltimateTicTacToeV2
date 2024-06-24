package Game;

import GameBoard.Board;
import GameBoard.BoardButton;
import GameBoard.BoardPanel;
import GameUtils.BoardMarks;
import GameUtils.Players;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// https://www.geeksforgeeks.org/java-swing-jfilechooser/
// https://stackoverflow.com/questions/46876382/read-string-from-text-file-jfilechooser-and-display-in-textarea
public class SaveManager {
    private static String EMPTY_sign = "0";
    private static String X_sign = "1";
    private static String O_sign = "2";

    public static void saveGame() {
        JFileChooser jf = new JFileChooser();
        int actionDone = jf.showSaveDialog(null);

        String filePath;
        if (actionDone == JFileChooser.APPROVE_OPTION)
        {
            filePath = jf.getSelectedFile().getAbsolutePath();
            //System.out.println(filePath);

            File file = jf.getSelectedFile();
            //System.out.println(inFile.getName());

            try {
                BufferedWriter saveWriter = new BufferedWriter(new FileWriter(file));

                // Saving the allowed move && highlighted board && AIMode
                saveWriter.write(String.valueOf(BoardPanel.getInstance().getBoardPosWithMove()));
                saveWriter.write(String.valueOf(BoardPanel.getInstance().boardHighlighted));
                if(GameAI.getInstance().isAIModeEnabled())
                    saveWriter.write("1");
                else
                    saveWriter.write("0");

                saveWriter.newLine();

                // Saving the turn
                if(GameLogic.getInstance().getTurn() == Players.PLAYER_X)
                    saveWriter.write(SaveManager.X_sign);
                else
                    saveWriter.write(SaveManager.O_sign);
                saveWriter.newLine();

                // Saving each board
                for(Board board : BoardPanel.getInstance().getBoards()) {
                    if(board.isCompleted()) {
                        if(board.getMark() == BoardMarks.MARK_X) {
                            saveWriter.write(SaveManager.X_sign);
                        } else {
                            saveWriter.write(SaveManager.O_sign);
                        }
                    } else { // Board is not completed
                        for(BoardButton boardButton : board.getBoardButtons()) {
                            if(boardButton.getMark() == BoardMarks.MARK_X) {
                                saveWriter.write(SaveManager.X_sign);
                            } else if(boardButton.getMark() == BoardMarks.MARK_O) {
                                saveWriter.write(SaveManager.O_sign);
                            } else {
                                saveWriter.write(SaveManager.EMPTY_sign);
                            }
                        }
                    }
                    saveWriter.newLine();
                }

                saveWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("Error in SaveManager::saveGame()\n" + e);
            }
        }
    }



    public static void loadGame() {
        JFileChooser jf = new JFileChooser();
        int actionDone = jf.showOpenDialog(null);

        if (actionDone == JFileChooser.APPROVE_OPTION) {
            File file = jf.getSelectedFile();

            try {
                BufferedReader saveReader = new BufferedReader(new FileReader(file));
                String infoLine = saveReader.readLine();
                BoardPanel.getInstance().setBoardPosWithMove(Character.getNumericValue(infoLine.charAt(0)));
                BoardPanel.getInstance().highlightBoard(Character.getNumericValue(infoLine.charAt(1)));
                boolean isAIMode = Character.getNumericValue(infoLine.charAt(1)) == 1;

                int intPlayer = Character.getNumericValue(saveReader.readLine().charAt(0));
                Players startingPlayer;
                if(intPlayer == 1)
                    startingPlayer = Players.PLAYER_X;
                else
                    startingPlayer = Players.PLAYER_O;

                // Board
                for(int i = 0; i < 9; i++) {
                    String boardLine = saveReader.readLine();
                    Board board = BoardPanel.getInstance().getBoard(i);

                    if(boardLine.length() == 1) { // Board completed, set a mark
                        int boardMark = Character.getNumericValue(boardLine.charAt(0));
                        if(boardMark == 1) {
                            board.onBoardWin(BoardMarks.MARK_X);
                            board.setCompleted(true);
                        } else if(boardMark == 2) {
                            board.onBoardWin(BoardMarks.MARK_O);
                            board.setCompleted(true);
                        } else {
                            board.setMark(BoardMarks.MARK_EMPTY);
                            board.setCompleted(false);
                        }
                    } else { // Board not completed, set buttons
                        ArrayList<BoardButton> boardButtons = board.getBoardButtons();
                        int countFilled = 0;
                        for(int j = 0; j < boardButtons.size(); j++) {
                            int buttonMark = Character.getNumericValue(boardLine.charAt(j));
                            if(buttonMark == 1) {
                                boardButtons.get(j).setMark(BoardMarks.MARK_X);
                                countFilled++;
                            } else if(buttonMark == 2) {
                                boardButtons.get(j).setMark(BoardMarks.MARK_O);
                                countFilled++;
                            } else {
                                boardButtons.get(j).setMark(BoardMarks.MARK_EMPTY);
                            }
                        }

                        if(countFilled == boardButtons.size()) {
                            board.setCompleted(true);
                        } else {
                            board.setCompleted(false);
                        }
                    }
                }

                // Start game
                GameLogic.getInstance().startLoadedGame(isAIMode, startingPlayer);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
