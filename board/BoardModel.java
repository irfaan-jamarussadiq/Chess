package board;

import pieces.*;
import static pieces.Player.*;

import java.util.ArrayList;

public class BoardModel {
    
    public static final int SIZE = 8;
    private Piece[][] board;

    public BoardModel() {
        board = new Piece[SIZE][SIZE];
        setUp();
    }

    public void movePieceIfValid(Piece piece, int newRank, int newFile) {
        if (isValidMove(piece, newRank, newFile)) {
            movePiece(piece, newRank, newFile);
        }
    }

    public boolean isValidMove(Piece piece, int newRank, int newFile) {
        boolean validPieceMove = piece.isValidMove(newRank, newFile);
        Player squarePlayer = pieceAt(newRank, newFile).getPlayer();
        boolean notFriendly = squarePlayer != piece.getPlayer();
        boolean castlingMove = isCastlingMove(piece, newRank, newFile);
        
        if (castlingMove && notFriendly) {
            return true;
        }

        Move move = new Move(newRank, newFile, piece);
        boolean inMoveList = piece.getMoveList(this).contains(move);
        return validPieceMove && notFriendly && inMoveList;
    }

    public void castle(Piece piece, int newRank, int newFile) {
        if (newRank != piece.getRank() || !(piece instanceof King)
        || kingIsInCheck(piece.getPlayer())) {
            return;
        }

        King king = (King) piece;
        if (newFile == king.getFile() + 2 && kingCanCastleOO(king)) {
            movePiece(king, newRank, newFile);
            Rook rook = (Rook) pieceAt(newRank, 7);
            movePiece(rook, newRank, newFile - 1);
        }
    }

    public boolean isCastlingMove(Piece piece, int newRank, int newFile) {
        if (!(piece instanceof King)) {
            return false;
        }

        King king = (King) piece;
        return isCastlingOOMove(king, newRank, newFile)
            || isCastlingOOOMove(king, newRank, newFile);
    }

    public boolean isCastlingOOMove(King king, int newRank, int newFile) {
        int castlingRank = (king.getPlayer() == WHITE) ? 7 : 0;
        if (king.getRank() != castlingRank || king.getRank() != newRank) {
            return false;
        }

        return newFile == king.getFile() + 2 && kingCanCastleOO(king);
    }

    public boolean isCastlingOOOMove(King king, int newRank, int newFile) {
        int castlingRank = (king.getPlayer() == WHITE) ? 7 : 0;
        if (king.getRank() != castlingRank || king.getRank() != newRank) {
            return false;
        }

        return newFile == king.getFile() - 2 && kingCanCastleOOO(king);
    }

    public boolean playerCanCastle(Player player) {
        King king = findKing(player);
        return kingCanCastleOO(king) || kingCanCastleOOO(king);
    }

    public boolean kingCanCastle(boolean isShortCastle, King king) {
        Player player = king.getPlayer();
        int rank = king.getRank();
        int file = king.getFile();
        int castlingRank = (player == WHITE) ? 7 : 0;
        int castlingFile = isShortCastle ? 7 : 0;
        Piece rook = pieceAt(castlingRank, castlingFile);
        boolean isRook = rook instanceof Rook && rook.getPlayer() == player;
        boolean empty = castlingSquaresAreEmpty(isShortCastle, rank, file);
        return isRook && rank == castlingRank && file == 4 && empty;
    }

    private boolean castlingSquaresAreEmpty(boolean isShortCastle, int kingRank,
        int kingFile) {
        
        int firstEmptySquareFile = isShortCastle ? kingFile + 1 : kingFile - 1;
        int secondEmptySquareFile = isShortCastle ? kingFile + 2 : kingFile - 2;
        if (!isShortCastle && !pieceAt(kingRank, kingFile - 3).isEmpty()) {
            return false;
        }

        return pieceAt(kingRank, firstEmptySquareFile).isEmpty()
            && pieceAt(kingRank, secondEmptySquareFile).isEmpty();
    }

    public boolean kingCanCastleOO(King king) {
        return kingCanCastle(true, king);
    }

    public boolean kingCanCastleOOO(King king) {
        return kingCanCastle(false, king);
    }

    public void movePiece(Piece piece, int newRank, int newFile) {
        removePiece(piece.getRank(), piece.getFile());
        piece.setRank(newRank);
        piece.setFile(newFile);
        addPiece(piece);

        if (piece instanceof Pawn && (piece.getPlayer() == WHITE && newRank == 0)
            || (piece.getPlayer() == BLACK && newRank == 7)) {
                removePiece(newRank, newFile);
                addPiece(new Queen(newRank, newFile, piece.getPlayer()));
        }
    }

    public Piece pieceAt(int rank, int file) {
        return board[rank][file];
    }

    public boolean isWithinBoard(int rank, int file) {
        boolean rankInBoard = rank >= 0 && rank < SIZE;
        boolean fileInBoard = file >= 0 && file < SIZE;
        return rankInBoard && fileInBoard;
    }

    public void addPiece(Piece piece) {
        board[piece.getRank()][piece.getFile()] = piece;
    }

    public void removePiece(int rank, int file) {
        board[rank][file] = new Empty(rank, file, UNDEFINED);
    }

    public void setUp() {
        setUpPieces(0);
        setUpPawns(1);
        clearRanks(2, 5);
        setUpPawns(6);
        setUpPieces(7);
    }

    private void setUpPieces(int rank) {
        Player player = (rank == 0) ? BLACK : WHITE;
        addPiece(new Rook(rank, 0, player));
        addPiece(new Knight(rank, 1, player));
        addPiece(new Bishop(rank, 2, player));
        addPiece(new Queen(rank, 3, player));
        addPiece(new King(rank, 4, player));
        addPiece(new Bishop(rank, 5, player));
        addPiece(new Knight(rank, 6, player));
        addPiece(new Rook(rank, 7, player));
    }

    private void setUpPawns(int rank) {
        Player player = (rank == 1) ? BLACK : WHITE;
        for (int file = 0; file < SIZE; file++) {
            addPiece(new Pawn(rank, file, player));
        }
    }

    private void clearRanks(int startRank, int endRank) {
        for (int rank = startRank; rank <= endRank; rank++) {
            for (int file = 0; file < SIZE; file++) {
                removePiece(rank, file);
            }
        }
    }
    
    public King findKing(Player player) {
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece instanceof King && player == piece.getPlayer()) {
                    return (King) piece;
                }
            }
        }

        return null;
    }

    public boolean noMovesAvailable(Player player) {
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece.getPlayer() == player) {
                    for (Move move : piece.getMoveList(this)) {
                        if (!moveWouldCauseCheck(move, player)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean moveWouldCauseCheck(Move move, Player player) {
        BoardModel copy = deepCopy();
        int rank = move.getRank();
        int file = move.getFile();
        Piece piece = copy.pieceAt(move.getPiece().getRank(), move.getPiece().getFile());
        copy.movePieceIfValid(piece, rank, file);
        boolean isCheck = copy.kingIsInCheck(player);
        if (isCheck) {
            piece.getMoveList(this).remove(move);
        }
        return isCheck;
    }

    public boolean kingIsInCheck(Player player) {
        King king = findKing(player);
        return squareAttacked(player, king.getRank(), king.getFile());
    }

    public boolean squareAttacked(Player player, int rank, int file) {
        Piece piece = pieceAt(rank, file);
        Bishop fakeBishop = new Bishop(rank, file, player);
        Rook fakeRook = new Rook(rank, file, player);
        Knight fakeKnight = new Knight(rank, file, player);
        return fakePieceMoveListHasPiece(piece, fakeKnight)
            || fakePieceMoveListHasPiece(piece, fakeBishop)
            || fakePieceMoveListHasPiece(piece, fakeRook);
    }

    private boolean fakePieceMoveListHasPiece(Piece piece, Piece fake) {
        for (Move move : fake.getMoveList(this)) {
            Piece attacker = pieceAt(move.getRank(), move.getFile());
            ArrayList<Move> attackList = attacker.getMoveList(this);
            for (Move m : attackList) {
                if (m.getRank() == piece.getRank()
                && m.getFile() == piece.getFile()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isStaleMate() {
        return isStaleMateHelper(WHITE) && isStaleMateHelper(BLACK);
    }

    private boolean isStaleMateHelper(Player player) {
        return noMovesAvailable(player) && !kingIsInCheck(player);
    }

    public boolean isCheckMate(Player player) {
        return noMovesAvailable(player) && kingIsInCheck(player);
    }

    public BoardModel deepCopy() {
        BoardModel newModel = new BoardModel();
        newModel.clear();
        for (Piece[] row : board) {
            for (Piece piece : row) {
                newModel.addPiece(piece.deepCopy());
            }
        }

        return newModel;
    }

    public void clear() {
        clearRanks(0, 7);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Piece[] row : board) {
            for (Piece piece : row) {
                char letter = pieceToLetter(piece);
                result.append(String.valueOf(letter) + ' ');
            }

            result.append('\n');
        }

        return result.toString();
    }

    private char pieceToLetter(Piece piece) {
        char letter = piece.getClass().getSimpleName().charAt(0);
        if (piece instanceof Knight) {
            letter = 'N';
        }

        if (piece.getPlayer() == WHITE) {
            letter = Character.toLowerCase(letter);
        }

        if (piece.isEmpty()) {
            letter = '.';
        }

        return letter;
    }
}