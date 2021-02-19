package board;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import pieces.*;

public class BoardView extends GridPane {

    public static final int SQUARE_SIZE = 80;
    public static final int SIZE = 8;

    private static Color dark = Color.DARKSLATEBLUE.brighter();
    private static Color light = Color.WHITE;

    public static final Background black = new Background(
        new BackgroundFill(dark, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background white = new Background(
        new BackgroundFill(light, CornerRadii.EMPTY, Insets.EMPTY));

    public BoardView() {
        setUpBoard();
        setUpPieces();
    }

    private void setUpBoard() {
        ColumnConstraints columnConstraints = new ColumnConstraints(SQUARE_SIZE);
        RowConstraints rowConstraints = new RowConstraints(SQUARE_SIZE);
        for (int i = 0; i < SIZE; i++) {
            getColumnConstraints().add(columnConstraints);
            getRowConstraints().add(rowConstraints);

            for (int j = 0; j < SIZE; j++) {
                StackPane field = new StackPane();
                field.setBackground(((i + j) & 1) == 0 ? white : black);
                add(field, i, j);
            }
        }
    }

    public void highlightSquare(int rank, int file) {
        Color color = Color.GREEN.brighter().brighter();
        highlightSquareHelper(rank, file, color);
    }

    public void highlightAttackSquare(int rank, int file, Color color) {
        highlightSquareHelper(rank, file, color);
    }

    public void highlightMoves(BoardModel model, Piece piece) {
        for (Move move : piece.getMoveList(model)) {
            if (!model.moveWouldCauseCheck(move, piece.getPlayer())) {
                highlightSquare(move.getRank(), move.getFile());
            }
        }
    }

    public void highlightCastlingMoves(BoardModel model, Piece king, int newFile) {
        if (!(king instanceof King)) {
            return;
        }

        if (model.isCastlingOOMove((King) king, king.getRank(), newFile + 2)) {
            highlightSquare(king.getRank(), king.getFile() + 1);
            highlightSquare(king.getRank(), king.getFile() + 2);
        } else if (model.isCastlingOOOMove((King) king, king.getRank(), newFile - 2)) {
            highlightSquare(king.getRank(), king.getFile() - 1);
            highlightSquare(king.getRank(), king.getFile() - 2);
            highlightSquare(king.getRank(), king.getFile() - 3);
        }
    }

    public void highlightKingIfAttacked(BoardModel model, Player player) {
        King king = model.findKing(player);
        if (model.kingIsInCheck(player)) {
            Color color = Color.RED.brighter().brighter();
            highlightAttackSquare(king.getRank(), king.getFile(), color);
        }

        if (model.isCheckMate(player)) {
            Color color = Color.PALEVIOLETRED.brighter().brighter();
            highlightAttackSquare(king.getRank(), king.getFile(), color);
        }
    }

    public void highlightSquareHelper(int rank, int file, Color color) {
        StackPane pane = (StackPane) getChildren().get(SIZE * file + rank);
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(fill));
    }

    public void unhighlightSquare(int rank, int file) {
        StackPane pane = (StackPane) getChildren().get(SIZE * file + rank);
        pane.setBackground(((rank + file) & 1) == 0 ? white : black);
    }

    public void unhighlightAll() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                unhighlightSquare(i, j);
            }
        }
    }

    public void addPiece(Piece piece) {
        ImageView pieceImage = getPieceImage(piece);
        pieceImage.setFitWidth(SQUARE_SIZE);
        pieceImage.setFitHeight(SQUARE_SIZE);

        int index = SIZE * piece.getFile() + piece.getRank();
        ((Pane) getChildren().get(index)).getChildren().add(pieceImage);
    }

    public void removePiece(int rank, int file) {
        for (Node node : getChildren()) {
            if (node instanceof StackPane
            && getColumnIndex(node) == file
            && getRowIndex(node) == rank) {
                int index = SIZE * file + rank;
                StackPane pane = ((StackPane) getChildren().get(index));
                if (pane.getChildren().size() == 1) {
                    pane.getChildren().remove(0);
                }
            }
        }
    }

    public void movePiece(Piece piece, int newRank, int newFile) {
        removePiece(piece.getRank(), piece.getFile());
        Piece pieceCopy = piece.deepCopy();
        pieceCopy.setRank(newRank);
        pieceCopy.setFile(newFile);
        removePiece(newRank, newFile);
        addPiece(pieceCopy);

        if (piece instanceof Pawn && (piece.getPlayer() == Player.WHITE && newRank == 0)
            || (piece.getPlayer() == Player.BLACK && newRank == 7)) {
            removePiece(newRank, newFile);
            addPiece(new Queen(newRank, newFile, piece.getPlayer()));
        }
    }

    public void setUpPieces() {
        setUpPieceRow(0);
        setUpPawns(1);
        setUpPawns(6);
        setUpPieceRow(7);
    }

    private void setUpPieceRow(int rank) {
        Player player = (rank == 0) ? Player.BLACK : Player.WHITE;
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
        Player player = (rank == 1) ? Player.BLACK : Player.WHITE;
        for (int file = 0; file < SIZE; file++) {
            addPiece(new Pawn(rank, file, player));
        }
    }
    
    private static ImageView getPieceImage(Piece piece) {
        if (piece.isEmpty()) {
            return null;
        }
        
        String pieceName = piece.getClass().getSimpleName().toLowerCase();
        String colorName = piece.getPlayer() == Player.WHITE ? "" : "2";
        Image image = new Image("images/" + pieceName + colorName + ".png");
        return new ImageView(image);
    }
}