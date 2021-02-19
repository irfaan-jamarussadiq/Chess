package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import board.BoardModel;
import board.Game;
import board.GameState;
import pieces.*;

public class GameTests {
    public static Game game = new Game();

    private static void addMovesHelper(int[][] moves) {
        BoardModel model = game.getModel();
        for (int[] row : moves) {
            Piece piece = model.pieceAt(row[2], row[3]);
            game.addMove(row[0], row[1], piece);
        }
    }

    public static class GameValidation {
        @Test(expected = IllegalPlayerException.class)
        public void testBlackMovingFirstIsInvalid() {
            game.reset();
            int[][] gameMoves = {
                {3, 4, 1, 4}, 
                {4, 6, 6, 6},
                {4, 7, 0, 3},
            };
            addMovesHelper(gameMoves);
        }

        @Test(expected = IllegalPlayerException.class)
        public void testWhiteMovingTwiceIsInvalid() {
            game.reset();
            int[][] gameMoves = {
                {5, 5, 6, 5},
                {4, 6, 6, 6},
                {4, 7, 0, 3},
            };
            addMovesHelper(gameMoves);
        }
    }

    public static class GameSimulation {
        @Test
        public void testFoolsMate() {   
            game.reset();
            int[][] gameMoves = {
                {5, 5, 6, 5},
                {3, 4, 1, 4}, 
                {4, 6, 6, 6},
                {4, 7, 0, 3},
            };
            addMovesHelper(gameMoves);
            assertTrue(game.getGameState() == GameState.BLACK_WON);
        }
    
        @Test
        public void testAlmostFoolsMate() {    
            game.reset();
            int[][] gameMoves = {
                {5, 5, 6, 5},
                {3, 4, 1, 4}, 
                {4, 0, 6, 0},
                {4, 7, 0, 3},
            };
            addMovesHelper(gameMoves);
            assertFalse(game.getGameState() == GameState.BLACK_WON);
            assertTrue(game.getGameState() == GameState.ONGOING);
            assertTrue(game.getModel().kingIsInCheck(Player.WHITE));
        }
    
        @Test
        public void testScholarsMate() {   
            game.reset();
            int[][] gameMoves = {
                {4, 4, 6, 4},
                {3, 4, 1, 4}, 
                {4, 2, 7, 5},
                {2, 2, 0, 1},
                {3, 7, 7, 3}, // check here ... seems fine
                {2, 5, 0, 6},
                {1, 5, 3, 7}, // problem here
            };
            addMovesHelper(gameMoves);
            assertTrue(game.getModel().isCheckMate(Player.BLACK));
            assertTrue(game.getGameState() == GameState.WHITE_WON);
        }
    
        @Test
        public void testAlmostScholarsMate() {
            game.reset();
            int[][] gameMoves = {
                {4, 4, 6, 4},
                {3, 4, 1, 4}, 
                {4, 2, 7, 5},
                {2, 2, 0, 1},
                {3, 7, 7, 3},
                {2, 5, 0, 6},
                {1, 5, 4, 2},
            };
            addMovesHelper(gameMoves);
            assertFalse(game.getGameState() == GameState.WHITE_WON);
            assertTrue(game.getGameState() == GameState.ONGOING);
        }
    }
}