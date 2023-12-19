package board;

import pieces.KingMoveGenerator;

class Game {
	private BoardModel board;

	public Game() {	
		board = new BoardModel();
	}

	public boolean kingIsInCheck(Player player) {
		return board.squareIsAttacked(player.getKingRank(), player.getKingFile());
	}	

	public boolean kingIsInCheckmate(Player player) {
		return kingIsInCheck(player) && kingHasNoMoves(player);
	}

	public boolean kingIsInStalemate(Player player) {
		return !kingIsInCheck(player) && kingHasNoMoves(player);
	}

	private boolean kingHasNoMoves(Player player) {
		KingMoveGenerator generator = new KingMoveGenerator(board); 
		List<Move> kingMoves = generator.getMoves(player.getKingRank(), player.getKingFile());
		if (!player.getKingHasMoved()) {
			kingMoves.addAll(generator.getCastlingMoves(player.getKingRank(), player.getKingFile()));	
		}

		for (Move move : kingMoves) {
			if (!squareAttacked(move.getEndRank(), move.getEndFile())) {
				return false;
			}
		}

		return true;
	}
}
