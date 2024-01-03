package src.board;

import src.board.pieces.*;

import java.util.List;

public class MoveValidator {

    private final BoardModel board;

    public MoveValidator(BoardModel board) {
        this.board = board;
    }

    public boolean isValidMove(Player player, Move move) {
        Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
        if (pieceToMove == null || pieceToMove.getColor() != player.getColor()) {
            return false;
        }

        MoveStrategy strategy;
        boolean isValid;
        try {
            strategy = MoveStrategyFactory.createMoveStrategy(board, move);
            isValid = strategy.isValidMove(board, move) && !moveWouldCauseCheck(player, move, strategy);
        } catch(UnsupportedOperationException e) {
            isValid = false;
        }

        return isValid;
    }

     boolean moveWouldCauseCheck(Player player, Move move, MoveStrategy strategy) {
        strategy.move(board, move);
        updateKingLocation(player, move.endRank(), move.endFile());
        Location kingLocation = player.getKingLocation();
        boolean kingInCheck = squareIsAttacked(kingLocation.rank(), kingLocation.file(), player.getColor());
        strategy.undoMove(board, move);
        updateKingLocation(player, move.startRank(), move.startFile());
        return kingInCheck;
    }

    public static boolean moveIsWithinBoard(Move move) {
        return move.startRank() >= 1 && move.startRank() <= BoardModel.SIZE
                && move.startFile() >= 1 && move.startFile() <= BoardModel.SIZE
                && move.endRank() >= 1 && move.endRank() <= BoardModel.SIZE
                && move.endFile() >= 1 && move.endFile() <= BoardModel.SIZE;
    }

    void updateKingLocation(Player player, int rank, int file) {
        Piece expectedKing = board.pieceAt(rank, file);
        if (expectedKing != null && expectedKing.getType() == PieceType.KING) {
            player.setKingLocation(rank, file);
        }
    }

    boolean squareIsAttacked(int rank, int file, PieceColor color) {
        for (PieceType type : PieceType.values()) {
            Piece piece = new Piece(type, color);
            MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
            List<Move> captures = generator.getCaptures(rank, file);
            for (Move move : captures) {
                if (MoveValidator.moveIsWithinBoard(move)) {
                    Piece potentialEnemy = board.pieceAt(move.endRank(), move.endFile());
                    if (piece.isEnemyOf(potentialEnemy) && potentialEnemy.getType() == type) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
