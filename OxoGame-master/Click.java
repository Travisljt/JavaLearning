import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.event.*;


public class Click extends Application {
  private Board board;
  private int turn;

 public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);

    board = new Board();

    // ------------ TOP BAR --------------
    VBox topBar = new VBox();
    topBar.setMinSize(200, 100);
    topBar.setSpacing(10);
    topBar.setAlignment(Pos.CENTER);
    Button BtnPlayer = new Button("Player");
    BtnPlayer.setMinSize(100, 20);
    Button BtnWon = new Button("  ");
    BtnWon.setMinSize(100, 20);
    topBar.getChildren().addAll(BtnPlayer, BtnWon);


    // ----------- BOTTOM BAR -------------
    HBox bottomBar = new HBox();
    bottomBar.setMinSize(200, 100);
    bottomBar.setSpacing(10);
    bottomBar.setAlignment(Pos.CENTER);
    Button btnPlayAgain = new Button("Play again");
    Button btnEnd = new Button("End Game");
    bottomBar.getChildren().addAll(btnPlayAgain, btnEnd);



    // ------- Creates the button and ------
    //          central playing grid
    Button btn1;
    btn1 = new Button("  ");
    createButton(btn1);

    Button btn2;
    btn2 = new Button("  ");
    createButton(btn2);

    Button btn3;
    btn3 = new Button("  ");
    createButton(btn3);

    Button btn4;
    btn4 = new Button("  ");
    createButton(btn4);

    Button btn5;
    btn5 = new Button("  ");
    createButton(btn5);

    Button btn6;
    btn6 = new Button("  ");
    createButton(btn6);

    Button btn7;
    btn7 = new Button("  ");
    createButton(btn7);

    Button btn8;
    btn8 = new Button("  ");
    createButton(btn8);

    Button btn9;
    btn9 = new Button("  ");
    createButton(btn9);

    GridPane gridPane = new GridPane();
    gridPane.setMinSize(250, 250);
    gridPane.setAlignment(Pos.CENTER);
    gridPane.add(btn1, 0, 0);
    gridPane.add(btn2, 1, 0);
    gridPane.add(btn3, 2, 0);
    gridPane.add(btn4, 2, 1);
    gridPane.add(btn5, 1, 1);
    gridPane.add(btn6, 0, 1);
    gridPane.add(btn7, 2, 2);
    gridPane.add(btn8, 1, 2);
    gridPane.add(btn9, 0, 2);


    // ------- Creating BorderPane ----------
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(topBar);
    borderPane.setBottom(bottomBar);
    borderPane.setCenter(gridPane);


    // -------- Creating the scene ----------
    Scene scene = new Scene(borderPane);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Tic Tac Toe");
    primaryStage.show();


    // -- Sets the variables for the game --
    play(btn1, btn2, btn3, btn4, btn5,
         btn6, btn7, btn8, btn9,
         BtnPlayer, BtnWon);


    // BUTTON EVENTS
    btn1.setOnAction(e -> buttonClick("a1", btn1, BtnPlayer, BtnWon));
    btn2.setOnAction(e -> buttonClick("a2", btn2, BtnPlayer, BtnWon));
    btn3.setOnAction(e -> buttonClick("a3", btn3, BtnPlayer, BtnWon));
    btn4.setOnAction(e -> buttonClick("b3", btn4, BtnPlayer, BtnWon));
    btn5.setOnAction(e -> buttonClick("b2", btn5, BtnPlayer, BtnWon));
    btn6.setOnAction(e -> buttonClick("b1", btn6, BtnPlayer, BtnWon));
    btn7.setOnAction(e -> buttonClick("c3", btn7, BtnPlayer, BtnWon));
    btn8.setOnAction(e -> buttonClick("c2", btn8, BtnPlayer, BtnWon));
    btn9.setOnAction(e -> buttonClick("c1", btn9, BtnPlayer, BtnWon));
    btnPlayAgain.setOnAction(e -> play(btn1, btn2, btn3,
                                       btn4, btn5, btn6,
                                       btn7, btn8, btn9,
                                       BtnPlayer, BtnWon));
   btnEnd.setOnAction(e -> endGame(primaryStage));


  }

  /* ==============================
     Function to create gridButtons
     ============================== */
  private void createButton(Button btn) {
    btn.setMinSize(60, 60);
  }

  /* ===========================================================
     Saves and executes moves. Stops players from playing twice
     on the same spot on the grid.
     =========================================================== */
  private void buttonClick(String s, Button btn, Button btn1, Button btn2) {
       if (btn.getText() == "  ") {
         board.save_move(s);
       }
       move(btn, btn1, btn2);
  }



  /* ======================================================
     Changes the content on the buttons
     ====================================================== */
  private void move(Button btn, Button btn1, Button btn2) {

    int player = board.player_num(turn);

    if (btn.getText() == "  " && player == 1)
    {
        btn.setText("X");
        turn ++;
    }

    if (btn.getText() == "  " && player == 2)
    {
      btn.setText("O");
      turn ++;
    }
    if (btn.getText() == "O" || btn.getText() == "X")
    {
      ;
    }

    if (board.game_won() == true)
    {
      btn2.setText("Game Won by player " + board.player_num(turn));
    }
    else if (board.draw() == true)
    {
      btn2.setText("Draw");
    }
    else
    {
      btn1.setText("Player " + board.player_num(turn));
    }

  }

  /* ===========================================
     Resets the board if they want to play again
     =========================================== */
  private void play(Button btn1, Button btn2, Button btn3,
                        Button btn4, Button btn5, Button btn6,
                        Button btn7, Button btn8, Button btn9,
                        Button Player, Button Won) {
     btn1.setText("  ");
     btn2.setText("  ");
     btn3.setText("  ");
     btn4.setText("  ");
     btn5.setText("  ");
     btn6.setText("  ");
     btn7.setText("  ");
     btn8.setText("  ");
     btn9.setText("  ");
     Player.setText("Player");
     Won.setText("  ");
     board.set_board();
     turn = 1;
  }

  private void endGame(Stage primaryStage) {
    primaryStage.close();
  }
}
