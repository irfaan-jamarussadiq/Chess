package pieces;

public enum Player {
    
    WHITE, BLACK, UNDEFINED;

    public boolean isOppositePlayer(Player player) {
        return (player == WHITE && this == BLACK) ||
            (player == BLACK && this == WHITE);
    }

    public int getDirection() {
        return (this == WHITE) ? -1 : 1;
    }
}