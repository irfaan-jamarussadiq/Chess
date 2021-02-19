package tests;

import static org.junit.Assert.*;
import static pieces.Player.*;

import org.junit.Test;
import board.BoardModel;
import java.util.ArrayList;
import pieces.*;

public class BishopMoveListTests {

    private static BoardModel model = new BoardModel();

    private static void addPiecesToBoard(BoardModel model, Piece... pieces) {
        for (Piece piece : pieces) {
            model.addPiece(piece);
        }
    }

    public static class BishopPositionMoves {
        @Test
        public void testOpenBoard() {        
            Bishop bishop = new Bishop(4, 3, WHITE);
            Knight blockingPiece = new Knight(3, 4, WHITE);
            model.clear();
            addPiecesToBoard(model, bishop, blockingPiece);
    
            String expectedMoves = 
                "(5, 4), (6, 5), (7, 6), (3, 2), (2, 1), " 
              + "(1, 0), (5, 2), (6, 1), (7, 0)";
    
            compareMoveLists(expectedMoves, bishop);
        }
    
        private void compareMoveLists(String expected, Bishop bishop) {
            ArrayList<Move> moveList = bishop.getMoveList(model);
            ArrayList<Move> expectedList = stringToMoveList(bishop, expected);
            assertTrue(moveList.equals(expectedList));
        }
    
        @Test
        public void testBishopImmobileAtStart() {
            model.setUp();
            Bishop bishop = (Bishop) model.pieceAt(7, 2);
            assertTrue(bishop.getMoveList(model).size() == 0);
        }

        private static Move stringToMove(Bishop bishop, String s) {
            String[] result = s.split("");
            int x = Integer.parseInt(result[1]);
            int y = Integer.parseInt(result[4]);
            return new Move(x, y, bishop);
        }

        private static ArrayList<Move> stringToMoveList(Bishop b, String s) {
            ArrayList<Move> expected = new ArrayList<>(); 
            String[] arr = s.split("\\), ");
            for (String coord : arr) {
                expected.add(stringToMove(b, coord));
            }

            return expected;
        }
    }

    public static class BishopCaptures {
        @Test
        public void testBishopShouldCaptureEnemyPawn() {
            model.clear();
            Bishop bishop = new Bishop(6, 6, BLACK);
            Pawn pawn = new Pawn(4, 4, WHITE);
            addPiecesToBoard(model, bishop, pawn);
            Move move = new Move(4, 4, bishop);
            assertTrue(bishop.getMoveList(model).contains(move));
        }

        @Test
        public void testBishopShouldNotCaptureFriendlyPawn() {
            model.clear();
            Bishop bishop = new Bishop(6, 6, WHITE);
            Pawn pawn = new Pawn(4, 4, WHITE);
            addPiecesToBoard(model, bishop, pawn);
            Move move = new Move(4, 4, bishop);
            assertFalse(bishop.getMoveList(model).contains(move));  
        }
    }
}