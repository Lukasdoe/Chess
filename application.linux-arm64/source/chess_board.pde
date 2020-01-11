class chessBoard {
  chessPiece Wpieces[];
  chessPiece Bpieces[];

  //coordinates of the highlighted piece
  int highX = -1;
  int highY = -1;

  chessPiece board[][];
  player turn; //whose turn is it?

  public chessBoard() {
    board = new chessPiece[8][8]; //chess board
    Wpieces = new chessPiece[16]; //white pieces
    Bpieces = new chessPiece[16]; //black pieces
    turn = player.WHITE; //which player starts?
  }

  public void updatePiece(chessPiece p, int posX, int posY, int oldX, int oldY) {
    board[oldX][oldY] = null;  //delete old piece spot
    board[posX][posY] = p; //pot piece object into new spot
  }

  public void highlight(int x, int y) {
    if (highX != -1 || highY != -1) { //if there is already a piece selected
      if (turn == player.WHITE) {
        for (int i = 0; i < Wpieces.length; i++) {
          if (Wpieces[i] != null && Wpieces[i].equals(board[highX][highY])) { //search for the already selected piece
            if (move(i, x / (width / 10) - 1, y / (width / 10) - 1)) { //try to move to the desired cell
              println("Valid move!");
              turn = player.BLACK; //change turn back to black player
            } else {
              println("Sorry, but you are not allowed to do that..."); //throw an error message
            }
            //reset everything
            highX = -1;
            highY = -1;
            Wpieces[i].highlight();
            break;
          }
        }
      } else { //same as above, but for the black player
        for (int i = 0; i < Bpieces.length; i++) {
          if (Bpieces[i] != null && Bpieces[i].equals(board[highX][highY])) {
            if (move(i, x / (width / 10) - 1, y / (width / 10) - 1)) {
              println("Valid move!");
              turn = player.WHITE;
            } else {
              println("Sorry, but you are not allowed to do that...");
            }
            highX = -1;
            highY = -1;
            Bpieces[i].highlight();
            break;
          }
        }
      }
    } else { //new piece is going to be selected
      chessPiece p = board[x / (width / 10) - 1][y / (width / 10) - 1]; //find a chess piece where to user just mouse clicked
      if (p != null && p.side == turn) { //is there a piece and does it belong to the clicking player? -> highlight it!
        p.highlight();
        highX = p.posX;
        highY = p.posY;
      }
    }
  }

  public void drawBoard() { //draws all the graphical components of the board
    if (turn == player.WHITE) {
      background(230);
      fill(0);
    } else {
      background(60);
      fill(255);
    }

    for (int y = height - height / 20 * 3, l = 1; l <= 8; y -= height / 10, l++) {
      textAlign(CENTER);
      text(l, width / 10 / 2, y);
      text(l, width - width / 10 / 2, y);
    }

    text("a", (width / 20 * 3) + width / 10 * 0, height / 20);
    text("b", (width / 20 * 3) + width / 10 * 1, height / 20);
    text("c", (width / 20 * 3) + width / 10 * 2, height / 20);
    text("d", (width / 20 * 3) + width / 10 * 3, height / 20);
    text("e", (width / 20 * 3) + width / 10 * 4, height / 20);
    text("f", (width / 20 * 3) + width / 10 * 5, height / 20);
    text("g", (width / 20 * 3) + width / 10 * 6, height / 20);
    text("h", (width / 20 * 3) + width / 10 * 7, height / 20);

    text("a", (width / 20 * 3) + width / 10 * 0, height - height / 20);
    text("b", (width / 20 * 3) + width / 10 * 1, height - height / 20);
    text("c", (width / 20 * 3) + width / 10 * 2, height - height / 20);
    text("d", (width / 20 * 3) + width / 10 * 3, height - height / 20);
    text("e", (width / 20 * 3) + width / 10 * 4, height - height / 20);
    text("f", (width / 20 * 3) + width / 10 * 5, height - height / 20);
    text("g", (width / 20 * 3) + width / 10 * 6, height - height / 20);
    text("h", (width / 20 * 3) + width / 10 * 7, height - height / 20);

    boolean black = true;   
    strokeWeight(2);
    noFill();
    stroke(0);
    for (int x = width / 10, k = 0; k < 8; x += width / 10, k++) {
      if (black) {
        fill(100);
        black = !black;
      } else {
        fill(200);
        black = !black;
      }
      for (int y = height / 10, l = 0; l < 8; y += height / 10, l++) {
        if (black) {
          fill(100);
          black = !black;
        } else {
          fill(200);
          black = !black;
        }
        pushMatrix();
        translate(x, y);
        square(0, 0, width / 10);
        popMatrix();
      }
    }
  }

  public void init() { //spawn the starting chess pieces

    Wpieces[0] = new rook(player.WHITE, 0, 7); 
    Wpieces[1] = new knight(player.WHITE, 1, 7);
    Wpieces[2] = new bishop(player.WHITE, 2, 7); 
    Wpieces[3] = new queen(player.WHITE, 3, 7);
    Wpieces[4] = new king(player.WHITE, 4, 7); 
    Wpieces[5] = new bishop(player.WHITE, 5, 7); 
    Wpieces[6] = new knight(player.WHITE, 6, 7); 
    Wpieces[7] = new rook(player.WHITE, 7, 7); 

    for (int i = 8, k = 0; i < 16; i++, k++) { //spawn all white pawns
      Wpieces[i] = new pawn(player.WHITE, k, 6);
    }

    Bpieces[0] = new rook(player.BLACK, 0, 0); 
    Bpieces[1] = new knight(player.BLACK, 1, 0); 
    Bpieces[2] = new bishop(player.BLACK, 2, 0); 
    Bpieces[3] = new queen(player.BLACK, 3, 0); 
    Bpieces[4] = new king(player.BLACK, 4, 0); 
    Bpieces[5] = new bishop(player.BLACK, 5, 0); 
    Bpieces[6] = new knight(player.BLACK, 6, 0); 
    Bpieces[7] = new rook(player.BLACK, 7, 0);

    for (int i = 8, k = 0; i < 16; i++, k++) {   //spawn all black pawns
      Bpieces[i] = new pawn(player.BLACK, k, 1);
    }
  }

  public void showPieces() { //'draw' all pieces onto the pre-drawn board using their built in function
    for (int i = 0; i < Wpieces.length; i++) {
      if (Wpieces[i] != null) {
        Wpieces[i].show();
      }
    }
    for (int i = 0; i < Bpieces.length; i++) {
      if (Bpieces[i] != null) {
        Bpieces[i].show();
      }
    }
  }

  public void updatePieces() { //call all pieces' update function
    for (int i = 0; i < Wpieces.length; i++) {
      if (Wpieces[i] != null) {
        Wpieces[i].update(this);
      }
    }
    for (int i = 0; i < Bpieces.length; i++) {
      if (Bpieces[i] != null) {
        Bpieces[i].update(this);
      }
    }
  }

  public boolean move(int fig, int newX, int newY) {
    if (turn == player.WHITE) { //white is currently playing
      if (board[newX][newY] != null) { //and the occupied field is not yet empty
        for (int i = 0; i < Bpieces.length; i++) { //checks all black pieces
          if (Bpieces[i] != null && Bpieces[i].equals(board[newX][newY])) { //is there one of the black pieces on the occupied field?
            if (Wpieces[fig].move(board, newX, newY)) { //is the move valid for the given chess piece?
              println("A black piece has been captured"); //if so, that piece is captured
              Bpieces[i] = null; //...and removed from the available pieces
              return true;
            }
          }
        }
        return false; //there is not a black piece on the occupied field, aka player tries to capture his own piece -> error
      } //if program continues from here, the occupied field is free
      return Wpieces[fig].move(board, newX, newY); //is the move valid for the given chess piece?
      //false means: the method Wpieces[fig].move(newX, newY) did not return true -> invalid move
    } //player is not white, do the whole thing from above again for black player
    else { //player is black
      if (board[newX][newY] != null) { //and the occupied field is not yet empty
        println("Player tries to occupy filled cell");
        for (int i = 0; i < Wpieces.length; i++) { //checks all black pieces
          if (Wpieces[i] != null && Wpieces[i].equals(board[newX][newY])) { //is there one of the white pieces on the occupied field?
            if (Bpieces[fig].move(board, newX, newY)) {
              println("A white piece has been captured"); //if so, that piece is captured
              Wpieces[i] = null; //...and removed from the available pieces'
              return true; //is the move valid for the given chess piece?
            }
          }
        }
        return false; //there is not a white piece on the occupied field, aka player tries to capture his own piece -> error
      } //if program continues from here, the occupied field is free
      return (Bpieces[fig].move(board, newX, newY)); //is the move valid for the given chess piece?
      //false means: the method Bpieces[fig].move(newX, newY) did not return true -> invalid move
    }
  }
}
