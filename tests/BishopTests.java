package tests;

import static org.junit.Assert.*;
import static pieces.Player.*;

import org.junit.Test;

import pieces.Bishop;
import pieces.IllegalPieceException;
import pieces.Player;

public class BishopTests {
    private static Bishop createBishop(int rank, int file, Player player) {
        return new Bishop(rank, file, player);
    }

    public static class BishopCreation {
        @Test(expected = IllegalPieceException.class)
        public void testBishopRankEightThrowsException() {
            createBishop(8, 2, WHITE);
        }
    
        @Test(expected = IllegalPieceException.class)
        public void testBishopRankNegativeOneThrowsException() {
            createBishop(-1, 5, BLACK);
        }
    
        @Test(expected = IllegalPieceException.class)
        public void testBishopFileEightThrowsException() {
            createBishop(5, 8, WHITE);
        }
    
        @Test(expected = IllegalPieceException.class)
        public void testBishopFileNegativeTwoThrowsException() {
            createBishop(-2, 6, BLACK);
        }

        @Test(expected = IllegalPieceException.class)
        public void testBishopUndefinedPlayerThrowsException() {
            createBishop(2, 5, UNDEFINED);
        }
    }

    public static class BishopLocation {
        @Test
        public void testWhiteBishopRankFiveFileFourShouldSucceed() {
            Bishop bishop = createBishop(5, 4, WHITE);
            assertTrue(bishop.getPlayer() == WHITE);
            assertTrue(bishop.getRank() == 5);
            assertTrue(bishop.getFile() == 4);
        }

        @Test
        public void testBlackBishopRankThreeFileSixShouldSucceed() {
            Bishop bishop = createBishop(3, 6, BLACK);
            assertTrue(bishop.getPlayer() == BLACK);
            assertTrue(bishop.getRank() == 3);
            assertTrue(bishop.getFile() == 6);
        }

        @Test(expected = IllegalPieceException.class)
        public void testWhiteBishopSetRankToEightShouldFail() {
            Bishop bishop = createBishop(4, 5, WHITE);
            bishop.setRank(8);
        }

        @Test(expected = IllegalPieceException.class)
        public void testBlackBishopSetRankToNegativeFiveShouldFail() {
            Bishop bishop = createBishop(1, 6, BLACK);
            bishop.setRank(-5);
        }

        @Test(expected = IllegalPieceException.class)
        public void testWhiteBishopSetFileToEighteenShouldFail() {
            Bishop bishop = createBishop(4, 3, WHITE);
            bishop.setFile(18);
        }

        @Test(expected = IllegalPieceException.class)
        public void testBlackBishopSetFileToNegativeOneShouldFail() {
            Bishop bishop = createBishop(7, 0, WHITE);
            bishop.setFile(-1);
        }
    }
}