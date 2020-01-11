import java.util.ArrayList;
import java.util.Collections;

chessBoard board;

void setup() {
  board = new chessBoard();
  size(600, 600);
  board.init();
}

void draw() {
  board.drawBoard();
  board.updatePieces();
  board.showPieces();
}

void mousePressed() {
  if (mouseX < (width - width / 10) && mouseX > (width / 10) && mouseY < (height - height / 10) && mouseY > (height / 10)) {
    board.highlight(mouseX, mouseY);
  }
}
