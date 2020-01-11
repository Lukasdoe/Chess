class pawn extends chessPiece {

  public pawn(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_p" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public pawn copy() {
    return new pawn(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if (side == player.WHITE) { //(white player)
      if (newX == posX) { //normal move in only y direction
        if (posY == 6 && (newY == posY - 2)) { //first move max 2 steps
          if (board[posX][posY - 1] != null || board[posX][posY - 2] != null) return false; //there is a piece blocking the path
          return true;
        }
        if (newY == posY - 1) { //other moves max 1 step
          if (board[posX][posY - 1] != null) return false; //there is a piece blocking the path
          return true;
        }
        return false;
      } else if ((newY == posY - 1) && (abs(newX - posX) == 1)) {
        if (board[newX][newY] != null && board[newX][newY].side == player.BLACK) return true; //there is a piece to be captured
        return false;
      }
    }
    if (side == player.BLACK) { //(black player)
      if (newX == posX) { //normal move in only y direction
        if (posY == 1 && (newY == posY + 2)) { //first move max 2 steps
          if (board[posX][posY + 1] != null || board[posX][posY + 2] != null) return false; //there is a piece blocking the path
          return true;
        }
        if (newY == posY + 1) { //other moves max 1 step
          if (board[posX][posY + 1] != null) return false; //there is a piece blocking the path
          return true;
        }
        return false;
      } else if ((newY == posY + 1) && (abs(newX - posX) == 1)) {
        if (board[newX][newY] != null && board[newX][newY].side == player.WHITE) return true; //there is a piece to be captured
        return false;
      }
    }
    return false; //tries to move on a forbidden cell
  }
}
