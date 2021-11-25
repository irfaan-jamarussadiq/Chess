package pieces;

import java.util.ArrayList;
import board.BoardModel;

public abstract class Piece {
    
    protected int rank;
    protected int file;
    protected Player player;

    public Piece(int rank, int file, Player player) {
        setRank(rank);
        setFile(file);
        setPlayer(player);
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public Player getPlayer() {
        return player;
    }

	public void setRank(int newRank) {
        if (rank < 0 || rank >= BoardModel.SIZE) {
            throw new IllegalPieceException("Invalid rank: " + rank);
        }

        this.rank = newRank;
    }

	public void setFile(int newFile) {
        if (file < 0 || file >= BoardModel.SIZE) {
            throw new IllegalPieceException("Invalid file: " + file);
        }

        this.file = newFile;
    }

    private void setPlayer(Player player) {
        if (getClass() != Empty.class && player == Player.UNDEFINED) {
            throw new IllegalPieceException("Invalid piece: " + player.name());
        }

        this.player = player;
    }

    @Override
    public String toString() {
        String pieceName = getClass().getSimpleName();
        String format = "%s %s at (%d, %d)";
        return String.format(format, player.name(), pieceName, rank, file);
    }

    /*
        Validates whether a move to the square specified by newRank and
        newFile is valid according to how the piece moves.
    */
    public abstract boolean isValidMove(int newRank, int newFile);
    public abstract ArrayList<Move> getMoveList(BoardModel model);
    public abstract boolean isEmpty();
    public abstract Piece deepCopy();
}