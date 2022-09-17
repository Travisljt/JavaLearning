/*
* This Class is used to determine whether:
1. a player has won the game or whether it is a draw.
2. Initialises the game board
3. whose turn it is to play
*/

class Board {

  private char[][] position = new char[4][4];
  private int player;


  private void main(String[] args) {
      Board program = new Board();
      program.run(args);
  }

  void run(String[] args) {
     boolean testing = false;
     assert(testing = true);
     if (args.length == 0 && testing) test();
  }


  char[][] getPosition() {
    return position;
  }

  /* =====================================
  Resets board to be empty after each game
  ======================================== */
  void set_board() {

    int i, j;

    position[1][0] = 'a';
    position[2][0] = 'b';
    position[3][0] = 'c';

    position[0][1] = '1';
    position[0][2] = '2';
    position[0][3] = '3';

    player = 1;

    for (i = 1; i <= 3; i++)
    {
      for (j = 1; j <= 3; j++)
      {
        position[i][j] = '.';
      }
    }
    if (test_setboard() == false)
    {
      System.out.println("Board failed to initialise");
    }
  }

  /* =============================
     Saves position to coordinates
     ============================= */
  int save_move(String s) {

    int row, col;
    int error = -1;
    int O = 1;
    int X = 2;
    char x;
    char y;
    //char x = s.charAt(0);
    //char y = s.charAt(1);

    try {
      x = s.charAt(0);
      y = s.charAt(1);
    }
    catch (Exception e)
    {
      return error;
    }

    if (x != 'a' && x != 'b' && x != 'c')
    {
      return error;
    }
    if (y != '1' && y != '2' && y != '3')
    {
      return error;
    }

    row = x - 'a' + 1;
    col = y - '0';

    if (player == 1)
    {
      position[row][col] = 'O';
      return O;
    }
    if (player == 2)
    {
      position[row][col] = 'X';
      return X;
    }
    return error;
  }

  /* =================================
  Determines which player's turn it is
  ==================================== */
  int player_num(int i) {
    if (i % 2 == 0)
    {
      player = 2;
      return player;
    }
    player = 1;
    return player;
  }


  /* =========================
     Determines if game is won
     ========================= */
  boolean game_won() {

    int i;

    for (i = 1; i <= 3; i++) {
      if (check_rows(i) == true ||
          check_cols(i) == true ||
          check_diagonals(i) == true) {
        return true;
      }
    }
    return false;
  }


  // ========== checking if won by row =============
  boolean check_rows(int i) {
    if ((position[i][i] == 'X' ||
        position[i][i] == 'O') &&
        (position[i][1] == position[i][2] &&
        position[i][1] == position[i][3]))
    {
      return true;
    }
    return false;
  }

  // ======= checking if won by column =============
  boolean check_cols(int i) {
    if ((position[i][i] == 'X' ||
         position[i][i] == 'O') &&
        (position[1][i] == position[2][i] &&
         position[1][i] == position[3][i]))
    {
      return true;
    }
    return false;
  }

  // ========= checking if won by diagonals ===========
  boolean check_diagonals(int i) {

    int j = 0;

    if (i == 1 || i == 3) {
      if (i == 1) {
        j = 3;
      }
      else if (i == 3) {
        j = 1;
      }

      if ((position[1][i] == 'X' ||
          position[1][i] == 'O') &&
          (position[1][i] == position[2][2] &&
          position[1][i] == position[3][j]))
      {
        return true;
      }
    }
    return false;
  }

  /* ================================
     Game is a draw if no empty cells
     remain on the board game
     ================================ */
  boolean draw() {

    int i, j, empty_cells = 0;

    for(i = 1; i <= 3; i++)
    {
      for (j = 1; j <= 3; j++)
      {
        if (position[i][j] == '.')
        {
          return false;
        }
      }
    }
    return true;
  }



  // ============== Testing ===============

  void test() {
    testwon();
    testdraw();
    test_playernum();
    test_savemove();
  }

  void testwon() {
    if (position[1][1] == 'X' &&
        position[1][2] == 'X' &&
        position[1][3] == 'X')
    {
      assert(game_won()==true);
    }

    if (position[1][1] == 'O' &&
        position[1][2] == 'O' &&
        position[1][3] == 'O')
    {
      assert(game_won()==true);
    }

    if (position[2][1] == 'X' &&
        position[2][2] == 'X' &&
        position[2][3] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[2][1] == 'O' &&
        position[2][2] == 'O' &&
        position[2][3] == '0')
    {
          assert(game_won()==true);
    }

    if (position[3][1] == 'X' &&
        position[3][2] == 'X' &&
        position[3][3] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[3][1] == 'O' &&
        position[3][2] == 'O' &&
        position[3][3] == 'O')
    {
          assert(game_won()==true);
    }

    if (position[1][1] == 'X' &&
        position[2][1] == 'X' &&
        position[3][1] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[1][1] == 'O' &&
        position[2][1] == 'O' &&
        position[3][1] == 'O')
    {
          assert(game_won()==true);
    }

    if (position[1][2] == 'X' &&
        position[2][2] == 'X' &&
        position[3][2] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[1][2] == 'O' &&
        position[2][2] == 'O' &&
        position[3][2] == 'O')
    {
          assert(game_won()==true);
    }

    if (position[1][3] == 'X' &&
        position[2][3] == 'X' &&
        position[3][3] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[1][3] == 'O' &&
        position[2][3] == 'O' &&
        position[3][3] == 'O')
    {
          assert(game_won()==true);
    }

    if (position[1][1] == 'X' &&
        position[2][2] == 'X' &&
        position[3][3] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[1][1] == 'O' &&
        position[2][2] == 'O' &&
        position[3][3] == 'O')
    {
          assert(game_won()==true);
    }

    if (position[1][3] == 'X' &&
        position[2][2] == 'X' &&
        position[3][1] == 'X')
    {
          assert(game_won()==true);
    }

    if (position[1][3] == 'O' &&
        position[2][2] == 'O' &&
        position[3][1] == 'O')
    {
          assert(game_won()==true);
    }

    else
    {
      assert(game_won()==false);
      System.out.println("All won tests passed");
    }
  }

  void testdraw() {
    if (position[1][1] == '.' ||
        position[1][2] == '.' ||
        position[1][3] == '.' ||
        position[2][1] == '.' ||
        position[2][2] == '.' ||
        position[2][3] == '.' ||
        position[3][1] == '.' ||
        position[3][2] == '.' ||
        position[3][3] == '.')
    {
      assert(draw()== false);
    }
    else
    {
      assert(draw()== true);
      System.out.println("All draw tests passed");
    }
  }

  void test_savemove() {

      player = 1;
      assert(save_move("a1") == 1);
      assert(save_move("a2") == 1);
      assert(save_move("a3") == 1);
      assert(save_move("b1") == 1);
      assert(save_move("b2") == 1);
      assert(save_move("b3") == 1);
      assert(save_move("c1") == 1);
      assert(save_move("c2") == 1);
      assert(save_move("c3") == 1);

      player = 2;
      assert(save_move("a1") == 2);
      assert(save_move("a2") == 2);
      assert(save_move("a3") == 2);
      assert(save_move("b1") == 2);
      assert(save_move("b2") == 2);
      assert(save_move("b3") == 2);
      assert(save_move("c1") == 2);
      assert(save_move("c2") == 2);
      assert(save_move("c3") == 2);


      assert(save_move("1a") == -1);
      assert(save_move("11") == -1);
      assert(save_move("2b") == -1);
      assert(save_move("ab") == -1);
  }

  void test_playernum() {
    assert(player_num(0) == 2);
    assert(player_num(2) == 2);
    assert(player_num(4) == 2);
    assert(player_num(6) == 2);
    assert(player_num(8) == 2);
    assert(player_num(1) == 1);
    assert(player_num(3) == 1);
    assert(player_num(5) == 1);
    assert(player_num(7) == 1);
    assert(player_num(9) == 1);
  }

  boolean test_setboard() {
    if (position[1][0] != 'a')
    {
      return false;
    }
    if (position[2][0] != 'b')
    {
      return false;
    }
    if (position[3][0] != 'c')
    {
      return false;
    }
    if (position[0][1] != '1')
    {
      return false;
    }
    if (position[0][2] != '2')
    {
      return false;
    }
    if (position[0][3] != '3')
    {
      return false;
    }
    if (position[1][1] != '.')
    {
      return false;
    }
    if (position[1][2] != '.')
    {
      return false;
    }
    if (position[1][3] != '.')
    {
      return false;
    }
    if (position[2][1] != '.')
    {
      return false;
    }
    if (position[2][2] != '.')
    {
      return false;
    }
    if (position[2][3] != '.')
    {
      return false;
    }
    if (position[3][1] != '.')
    {
      return false;
    }
    if (position[3][2] != '.')
    {
      return false;
    }
    if (position[3][3] != '.')
    {
      return false;
    }
    else {
      return true;
    }
  }

}
