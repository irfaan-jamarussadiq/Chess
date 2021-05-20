package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import pieces.Pawn;
import pieces.Queen;
import pieces.Piece;
import pieces.Player;
import board.BoardModel;

public class PawnTests {
    public static BoardModel model = new BoardModel();

    @Test
    public void testPawnPushUpdatesLocation() {
        Piece pawn = new Pawn(6, 4, Player.WHITE);
        assertTrue(model.pieceAt(6, 4) instanceof Pawn);
        assertTrue(model.pieceAt(4, 4).isEmpty());
        model.movePiece(pawn, 4, 4);
        assertTrue(model.pieceAt(4, 4) instanceof Pawn);
    }

    @Test
    public void testPawnPromotion() {
        Piece pawn = new Pawn(1, 4, Player.WHITE);
        model.movePiece(pawn, 0, 4);
        assertTrue(model.pieceAt(0, 4) instanceof Queen);
    }
}
