class rook extends chessPiece {
  public rook(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_r" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public rook copy() {
    return new rook(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if ((newX == posX && newY != posY)) {  //moving just in x-Direction
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
