🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿🙿
# Chess ♞

This project implements a game of chess using object oriented design principles.

## Program Design
### Pieces
A piece is defined by a PieceType (pawn, rook, knight, bishop, queen, or king) and a PieceColor (white or black). It does not know about its location with respect to the chessboard.
### Board
The Board class is responsible for moving pieces on the chess board, but it is not concerned with move validation or how the piece moves. There are a few complexities with regards to moving pieces, including castling, where more than one piece is moving at once and en passant, where the captured piece is not on the destination square. For these reasons, a MoveStrategy interface defines the contract for moving a piece, and various subclasses implement this interface to define how the move is made.
### Game
### Move Validation

## GUI Examples
| Normal Position | Castling Moves | Check | Checkmate |
| ------------- | ------------- | ------------- | ------------- |
| <img src="./assets/gui_screenshot.PNG" alt="Normal Chess Position" width="250"/> | <img src="./assets/gui_castling.PNG" alt="Castling" width="250"/> | <img src="./assets/gui_check.PNG" alt="Check" width="250"/> | <img src="./assets/gui_checkmate.PNG" alt="Checkmate" width="250"/> |
