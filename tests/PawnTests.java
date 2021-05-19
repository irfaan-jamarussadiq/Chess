package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import pieces.Pawn;
import pieces.Piece;
import pieces.Player;
import board.BoardModel;

public class PawnTests {
    public static Piece pawn = new Pawn(6, 4, Player.WHITE);
    public static BoardModel model = new BoardModel();

    @Test
    public void testPawnPushUpdatesLocation() {
        assertTrue(model.pieceAt(6, 4) instanceof Pawn);
        assertTrue(model.pieceAt(4, 4).isEmpty());
        model.movePiece(pawn, 4, 4);
        assertTrue(model.pieceAt(4, 4) instanceof Pawn);
    }
}
