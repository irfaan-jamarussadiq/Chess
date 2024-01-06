package src.pieces;

import src.board.BoardModel;

public enum PieceColor {
	WHITE {
		public int getPieceStartingRank() {
			return 1;
		}

		public int getEnPassantStartingRank() {
			return 5;
		} 

		public int getPawnDirection() {
			return 1;
		}
	}, 
	BLACK {
		public int getPieceStartingRank() {
			return BoardModel.SIZE;
		}

		public int getEnPassantStartingRank() {
			return 4;
		} 

		public int getPawnDirection() {
			return -1;
		}
	};

	public abstract int getPieceStartingRank();

	public abstract int getEnPassantStartingRank();
	public abstract int getPawnDirection(); 
}
