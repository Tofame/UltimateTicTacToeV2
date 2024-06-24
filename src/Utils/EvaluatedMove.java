package Utils;

import GameBoard.BoardButton;

import javax.swing.*;

public class EvaluatedMove<F extends BoardButton, S extends Integer> extends MyPair<F, S> {
    public EvaluatedMove(F v1, S v2) {
        super(v1, v2);
    }

    @Override
    public String toString() {
        F button = getFirst();
        if (button != null) {
            BoardButton boardButton = (BoardButton)button;
            return "\nButton's Board Pos: " + (boardButton.getParentBoard().getPosition()+1) + " Button Pos: " + (boardButton.getPosition()+1)
                    + "\nScore: " + getSecond() + "\n";
        } else {
            return "EvaluatedMove is incorrect - doesn't have BoardButton as first element.";
        }
    }
}