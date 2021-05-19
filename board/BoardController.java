package board;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pieces.King;
import pieces.Piece;
import pieces.Player;

public class BoardController implements EventHandler<MouseEvent> {
    
    private BoardModel model;
    private Game game;
    private BoardView view;

    private Piece prev = null;

    public BoardController() {
        model = new BoardModel();
        game = new Game(model); 
        view = new BoardView();
    }

    public BoardView getView() {
        return view;
    }

    public void movePiece(Piece piece, int newRank, int newFile) {
        if (model.isValidMove(piece, newRank, newFile) 
            && game.getCurrentPlayer() == piece.getPlayer()) {
        
            if (model.isCastlingMove(piece, newRank, newFile)) {
                King king = (King) piece;
                int rookFile = model.isCastlingOOMove(king, newRank, newFile) ? 7 : 0;
                Piece rook = model.pieceAt(king.getRank(), rookFile);
                castle(king, rook, newRank, newFile);
            } else {
                view.movePiece(piece, newRank, newFile);
                game.playMove(piece, newRank, newFile);
                model.movePiece(piece, newRank, newFile);
            }
        }
    }

    public void castle(Piece king, Piece rook, int newRank, int newFile) {
        view.movePiece(king, newRank, newFile);
        model.movePiece(king, newRank, newFile);
        game.playMove(king, newRank, newFile);

        int rookCastlingFile = rook.getFile() == 7 ? newFile - 1 : newFile + 1;
        view.movePiece(rook, newRank, rookCastlingFile);
        model.movePiece(rook, newRank, rookCastlingFile);
    }

    @Override
    public void handle(MouseEvent event) {
        int rank = (int) event.getY()/BoardView.SQUARE_SIZE;
        int file = (int) event.getX()/BoardView.SQUARE_SIZE;

        Piece piece = model.pieceAt(rank, file);
        if (prev != null) {
            view.unhighlightAll();
            movePiece(prev, rank, file);
            view.highlightKingIfAttacked(model, Player.WHITE);
            view.highlightKingIfAttacked(model, Player.BLACK); 
        } 
        
        if (prev == null || (game.getCurrentPlayer() == piece.getPlayer()
            && model.pieceAt(rank, file) == piece)) {
            view.highlightMoves(model, piece);
            view.highlightCastlingMoves(model, piece, file);
        }

        prev = piece;
    }
}
