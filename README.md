🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿
# Chess ♞

This project implements a game of chess using object oriented design principles.

## Program Design
### Pieces
A piece is defined by a PieceType (pawn, rook, knight, bishop, queen, or king) and a PieceColor (white or black). It does not know about its location with respect to the chessboard.
### Board
The Board class is responsible for moving pieces on the chessboard, but it is not concerned with move validation. Moving a piece in chess is not as simple as taking a piece from the starting location to the destination because of special cases like castling and en passant. Castling involves moving both the king and the rook at the same time and can be done in one of two directions. En passant is a special kind of pawn capture where a pawn can capture a pawn next to it, which is not on its destination square. Because of these special cases, I used the strategy design pattern and created a MoveStrategy interface that defines a contract for moving a piece. Other classes can then implement specific move strategies related to en passant, short castling, long castling, normal moves, and capture moves.

### Game
### Move Validation

## GUI Examples
| Normal Position | Castling Moves | Check | Checkmate |
| ------------- | ------------- | ------------- | ------------- |
| <img src="./assets/gui_screenshot.PNG" alt="Normal Chess Position" width="250"/> | <img src="./assets/gui_castling.PNG" alt="Castling" width="250"/> | <img src="./assets/gui_check.PNG" alt="Check" width="250"/> | <img src="./assets/gui_checkmate.PNG" alt="Checkmate" width="250"/> |
