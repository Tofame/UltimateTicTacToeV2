package Game;

import GameBoard.BoardPanel;

import java.awt.*;

public class MovesLog extends ScrollPane {
    private static MovesLog movesLog_ = null;

    public static MovesLog getInstance() {
        if(MovesLog.movesLog_ == null) {
            movesLog_ = new MovesLog();
        }

        return movesLog_;
    }


}
