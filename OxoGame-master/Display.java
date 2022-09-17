/*
This class is only necessary if you wish to play the game
from the terminal in plain text
*/

import java.util.Scanner;

class Display {

  void rules() {
    System.out.println("Enter desired position. e.g. a2 or b1");
  }

  String select_move(int player) {

    String coordinates;
    String error = "error";

    Scanner move = new Scanner(System.in);
    System.out.println("Player " + player);
    System.out.println("Enter row and column: ");

    try {
      coordinates = move.nextLine();
    }
    catch (Exception e)
    {
      return error;
    }
    return coordinates;
  }

  String play() {

    String play_again = "yes";

    System.out.println("GAME WON!");

    Scanner play = new Scanner(System.in);
    System.out.println("Would you like to play again? (yes/no)");
    play_again = play.nextLine();
    System.out.println("play again: " + play_again);
    return play_again;
  }

  void print_board(char[][] position) {

    int i, j;

    for (i = 0; i <= 3; i++)
    {
      for (j = 0; j <= 3; j++)
      {
        System.out.print(" " + position[i][j]);
      }
      System.out.println();
    }
  }

  void error_message() {
    System.out.println("ERROR!");
    System.out.println("Enter a row letter followed by a column number.");
    System.out.println("Examples: a1 or b2.");
  }

}
