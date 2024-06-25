package Game;

// My rules for AI:
/*
 0. Weight of winning not only the board, but whole game
 (^ the highest valued move and it's not weight based, immediate return)
 1. The best move (no-win, no-anywhere, ai-wins)
 2. Enemy won't win a board && won't have 'anywhere' move
 3. Enemy won't win a board
 4. AI can win a board
 5. Any available moves if nothing else from above was fitting

== How I could make it even better: (not done as of now)
 Weight if move is stacking e.g. row, column or diagonal in 2 length
*/

public enum AIWeightChoice {
    PLAYER_NO_WIN(8),
    PLAYER_WIN(-8),
    PLAYER_NO_MOVE_ANYWHERE(5),
    PLAYER_MOVE_ANYWHERE(-5),
    AI_WIN(4),
    AI_NO_WIN(-4);

    private final int value;
    public int getValue() { return value; };

    AIWeightChoice(int value) {
        this.value = value;
    }
}