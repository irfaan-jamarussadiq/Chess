package board;

import java.util.ArrayList;
import pieces.*;
import static board.GameState.*;
import static pieces.Player.*;

public class Game {
    private GameState state;
    private Player currentPlayer;
    private BoardModel model;
    private ArrayList<Move> moves;

    public Game() {
        state = ONGOING;
        currentPlayer = WHITE;
        model = new BoardModel();
        moves = new ArrayList<>();
    }

    public Game(BoardModel model) {
        state = ONGOING;
        currentPlayer = WHITE;
        this.model = model;
        moves = new ArrayList<>();
    }

    public BoardModel getModel() {
        return model;
    }

    public ArrayList<Move> getGameMoveList() {
        return moves;
    }

    public GameState getGameState() {
        return state;
    }

    public void updateGameState() {
        if (model.isCheckMate(BLACK)) {
            state = WHITE_WON;
        } else if (model.isCheckMate(WHITE)) {
            state = BLACK_WON;
        } else if (model.isStaleMate()) {
            state = DRAW;
        } else {
            state = ONGOING;
        }
    }

    public void addMove(int rank, int file, Piece piece) {
        if (piece.getPlayer() != currentPlayer) {
            throw new IllegalPlayerException();
        }

        moves.add(new Move(rank, file, piece));
        model.movePieceIfValid(piece, rank, file);
        updateGameState();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void playMove(Piece piece, int rank, int file) {
        addMove(rank, file, piece);
        currentPlayer = nextPlayer();
    }

    public Player nextPlayer() {
        return (currentPlayer == WHITE) ? BLACK : WHITE;
    }

    public void reset() {
        state = ONGOING;
        currentPlayer = WHITE;
        model.setUp();
        moves.clear();
    }
}