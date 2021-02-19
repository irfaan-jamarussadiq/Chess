package pieces;

import java.util.Objects;

public class Move {
    private Piece piece;
    private int destinationRank;
    private int destinationFile;

    public Move(int destinationRank, int destinationFile, Piece piece) {
        this.destinationRank = destinationRank;
        this.destinationFile = destinationFile;
        this.piece = piece;
    }

    public int getRank() {
        return destinationRank;
    }

    public int getFile() {
        return destinationFile;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object x) {
        if (this == x) return true;
    
        if (x == null || this.getClass() != x.getClass()) return false;
            
        Move c = (Move) x;
        return this.destinationRank == c.destinationRank 
            && this.destinationFile == c.destinationFile
            && this.piece == c.piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationRank, destinationFile);
    }

    @Override
    public String toString() {
        return piece.toString() + 
            " moves to (" + destinationRank + ", " + destinationFile + ")";
    }
}
