class queen extends chessPiece {

  public queen(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_q" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public queen copy() {
    return new queen(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if (abs(newX - posX) == abs(newY - posY)) {  //moving one X-Cell for each Y-Cell -> allowed movement!
      int dirX = Integer.signum(newX - posX);
      int dirY = Integer.signum(newY - posY);

      //for loop is the same as for the bishop
      for (int k = posX + dirX, l = posY + dirY; k != newX && l != newY; k += dirX, l += dirY) { //check all cells in the path
        if (board[k][l] != null) return false; //there is a piece blocking the path
      }
      return true; //path is clear
    }
    //the following movement code is the same for the rook
    else if ((newX == posX && newY != posY)) {  //moving just in x-Direction
      for (int i = min(newY, posY) + 1; i < max(newY, posY); i++) { //check all cells in the path
        if (board[posX][i] != null) return false; //there is a piece blocking the path
      }
      return true; //path is clear
    } else if ((newX != posX && newY == posY)) {  //second possibility: moving just in y direction
      for (int i = min(newX, posX) + 1; i < max(newX, posX); i++) { //check all cells in the path
        if (board[i][posY] != null) return false; //there is a piece blocking the path
      }
      return true; //path is clear
    }
    return false; //tries to move on a forbidden cell
  }
}
