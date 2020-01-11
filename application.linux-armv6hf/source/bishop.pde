class bishop extends chessPiece {

  public bishop(player s, int x, int y) {
    super(s, x, y);
    img = loadImage("figures/Chess_tile_b" + (s == player.WHITE ? "l" : "d") + ".png");
  }

  public bishop copy() {
    return new bishop(side, posX, posY);
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    if (abs(newX - posX) == abs(newY - posY)) {  //moving one X-Cell for each Y-Cell -> allowed movement!
      int dirX = Integer.signum(newX - posX);
      int dirY = Integer.signum(newY - posY);

      /** Following is a way too complicated for loop: k is the variable that counts the "steps" in x-Direction.
       l does the same for the Y-Direction. They should both start on the initial cell where the bishop did stand before his walk.
       Here we have to add "dirX" and "dirY" to jump over the first cell, because otherwise the bishop would be blocked by himself.
       The "dirX" is just +1 for every final field with a higher x-Coordinate than the starting field. Same for the "dirY". 
       This gives us a factor that now just has to be added to k and l in every for loop step. We also can't check the last cell, 
       because here it doesn't matter if there is a chess piece in there. Otherwise it wouldn't be possible to capture pieces. */

      for (int k = posX + dirX, l = posY + dirY; k != newX && l != newY; k += dirX, l += dirY) { //check all cells in the path
        if (board[k][l] != null) return false; //there is a piece blocking the path
      }
      return true; //path is clear
    }
    return false; //tries to move on a forbidden cell
  }
}
