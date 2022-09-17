class Oxo {


  // ================= Main =====================
  public static void main(String[] args) {
      Oxo program = new Oxo();
      program.run(args);
  }

  void run(String[] args) {
    Board board = new Board();
    Display display = new Display();
    new_move(board, display);
  }

  /* ===========================================
  Gets user input and loops until user enters no
  ============================================== */
  void new_move(Board board, Display display) {

    String coordinates;
    String play_again = "yes";
    int turn = 1;
    int player = 1;
    char[][] position;


    board.set_board();
    display.rules();
    position = board.getPosition();
    display.print_board(position);

    while (play_again.equals("yes") == true)
    {
      board.set_board();


      while (board.game_won() != true)
      {
        player = board.player_num(turn);
        coordinates = display.select_move(player);

        // Handling user input errors
        if (coordinates == "error")
        {
          display.error_message();
        }

        // play
        else
        {
          board.save_move(coordinates);
          if (board.save_move(coordinates) == -1)
          {
            display.error_message();
          }
          else
          {
            position = board.getPosition();
            display.print_board(position);
            turn ++;
          }
        }
      }
      play_again = display.play();
    }
  }
}
