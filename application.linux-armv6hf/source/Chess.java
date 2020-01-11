import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.ArrayList; 
import java.util.Collections; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Chess extends PApplet {




chessBoard board;

public void setup() {
  board = new chessBoard();
  
  board.init();
}

public void draw() {
  board.drawBoard();
  board.updatePieces();
  board.showPieces();
}

public void mousePressed() {
  if (mouseX < (width - width / 10) && mouseX > (width / 10) && mouseY < (height - height / 10) && mouseY > (height / 10)) {
    board.highlight(mouseX, mouseY);
  }
}
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

  public abstract chessPiece copy();

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
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Chess" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
