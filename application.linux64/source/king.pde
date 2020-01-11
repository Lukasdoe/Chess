class king extends chessPiece {

  public king(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_k" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public king copy() {
    return new king(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if ((abs(posX - newX) == 1) || (abs(posY - newY) == 1)) {  //moving just one cell in any direction
      return true;
    }
    return false; //tries to move on a forbidden cell
  }
}
