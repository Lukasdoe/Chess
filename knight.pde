class knight extends chessPiece {

  public knight(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_n" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public knight copy() {
    return new knight(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if ((abs(newX - posX) != 3) && (abs(newY - posY) != 3) && ((abs(newY - posY) + abs(newX - posX)) == 3)) {  //moving in the "L"-Shape
      return true;
    }
    return false; //tries to move on a forbidden cell
  }
}
