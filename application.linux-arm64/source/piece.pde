enum player {
  WHITE, 
    BLACK
}

abstract class chessPiece {
  player side;
  public int posX;
  public int posY;

  public int oldX;
  public int oldY;

  boolean highlight = false;

  PImage img;

  public chessPiece(player s, int x, int y) {
    side = s;
    posX = x;
    oldX = x;
    posY = y;
    oldY = y;
  }

  abstract chessPiece copy();

  public void update(chessBoard board) {
    board.updatePiece(this, posX, posY, oldX, oldY);
  }

  public void show() {
    pushMatrix();
    translate((posX + 1) * width / 10, (posY + 1) * width / 10);
    image(img, 0, 0, width/10, height/10);
    if (highlight) {
      fill(255, 165, 0, 10);
      strokeWeight(2);
      stroke(255, 140, 0);
      square(0, 0, width / 10);
    }
    popMatrix();
  }

  public void highlight() {
    highlight = !highlight;
  }

  public boolean move(chessPiece board[][], int newX, int newY) {
    if (isMoveAllowed(board, newX, newY)) {
      oldX = posX;
      oldY = posY;
      posX = newX;
      posY = newY;
      return true;
    }
    return false;
  }

  protected boolean isMoveAllowed(chessPiece board[][], int newX, int newY) {
    return true;
  }
}
