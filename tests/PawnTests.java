package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import board.BoardModel;
import board.Game;
import board.GameState;
import pieces.*;

public class PawnTests {
    public static Piece pawn = Pawn(6, 4, Player.WHITE);
    public static BoardModel model = new BoardModel();

    @Test
    public testPawnPush() {
        assertTrue(model.pieceAt(6, 4) instanceof Pawn);
        model.movePiece(pawn, 4, 4);
        assertTrue(model.pieceAt(4, 4) instanceof Pawn);
    }
}
