package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.uob.OXOMoveException.*;

import static org.junit.jupiter.api.Assertions.*;


// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class ControllerTests {
  OXOModel model;
  OXOController controller;

  // create your standard 3*3 OXO board (where three of the same symbol in a line wins) with the X
  // and O player
  private static OXOModel createStandardModel() {
    OXOModel model = new OXOModel(3, 3, 3);
    model.addPlayer(new OXOPlayer('X'));
    model.addPlayer(new OXOPlayer('O'));
    return model;
  }

  // we make a new board for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup() {
    model = createStandardModel();
    controller = new OXOController(model);
  }

  // here's a basic test for the `controller.handleIncomingCommand` method
  @Test
  void testHandleIncomingCommand() throws OXOMoveException {
    // take note of whose gonna made the first move
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");

    // A move has been made for A1 (i.e. the [0,0] cell on the board), let's see if that cell is
    // indeed owned by the player
    assertEquals(firstMovingPlayer, controller.gameModel.getCellOwner(0, 0));
  }

//   here's a complete game where we find out if someone won
  @Test
  void testBasicWinWithA1A2A3() throws OXOMoveException {
    // take note of whose gonna made the first move
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a3");

    // OK, so A1, A2, A3 is a win and that last A3 move is made by the first player (players
    // alternative between moves) let's make an assertion to see whether the first moving player is
    // the winner here
    assertEquals(
        firstMovingPlayer,
        model.getWinner(),
        "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testBasicWinWithA1B2C3()throws OXOMoveException{
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("c3");
    //a1,b2,c3 is a won and the c3 move is made by the first player
    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }
  @Test
  void testBasicWinWithC1B2A3()throws OXOMoveException{
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    //c1,b2,a3 is a won and the c3 move is made by the first player
    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }
  @Test
  void testBasicWinWithA1B1C1()throws OXOMoveException{
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("c1");
    //a1,b1,c1 is a won and the c3 move is made by the first player
    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }
  @Test
  void testThrows()throws OXOMoveException{

    //Test each command
    assertThrows(InvalidIdentifierLengthException.class, ()-> controller.handleIncomingCommand("aa1"));
    assertThrows(InvalidIdentifierCharacterException.class, ()-> controller.handleIncomingCommand("$1"));
    assertThrows(InvalidIdentifierCharacterException.class, ()-> controller.handleIncomingCommand("a$"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d1"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("a4"));
    controller.handleIncomingCommand("A1");
    assertThrows(CellAlreadyTakenException.class, ()-> controller.handleIncomingCommand("a1"));
  }

  @Test
  void testGameDrawn()throws OXOMoveException{
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c3");
    controller.handleIncomingCommand("c2");
    //At the moment the board is full of sign, but it still has no winner set
    //So that it should be the game drawn.
    assertTrue(model.isGameDrawn(), "Game was expected to be drawn but wasn't");
  }

  @Test
  void testAddAndRemove()throws OXOMoveException{
    controller.addRow();
    //Try to fill in d1 box
    controller.handleIncomingCommand("d1");
    //It should be a new row of the board
    assertEquals(
            model.cell.get(3).get(0).getPlayingLetter(),
            'X',
            "Row doesnt add");
    assertEquals(
            model.cell.get(3).get(1).getPlayingLetter(),
            ' ',
            "Row doesnt add");
    assertEquals(
            model.cell.get(3).get(2).getPlayingLetter(),
            ' ',
            "Row doesnt add");
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d4"));
    controller.addColumn();
    //Try to fill in a4 box
    controller.handleIncomingCommand("a4");
    //It should be a new colum of the board
    assertEquals(
            model.cell.get(0).get(3).getPlayingLetter(),
            'O',
            "Colum doesnt add");
    assertEquals(
            model.cell.get(1).get(3).getPlayingLetter(),
            ' ',
            "Colum doesnt add");
    assertEquals(
            model.cell.get(2).get(3).getPlayingLetter(),
            ' ',
            "Colum doesnt add");
    assertEquals(
            model.cell.get(3).get(3).getPlayingLetter(),
            ' ',
            "Colum doesnt add");

    //After removing, the command below should be out of the side range
    controller.removeRow();
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d1"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d2"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d3"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d4"));
    controller.removeColumn();
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("a4"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("b4"));
    assertThrows(OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("c4"));
  }
  @Test
  void testAddMorePlayers()throws OXOMoveException{
    model.addPlayer(new OXOPlayer('S'));
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b2");
    OXOPlayer thirdMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("c3");
    //As logic, the first player should fill in a2 box, and the second player should fill in a3 box
    //And the third player should fill in c3 box
    assertEquals(
            firstMovingPlayer.getPlayingLetter(), model.cell.get(0).get(1).getPlayingLetter(),
            "firstPlayer was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
    assertEquals(
            secondMovingPlayer.getPlayingLetter(), model.cell.get(0).get(2).getPlayingLetter(),
            "secondPlayer was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
    assertEquals(
            thirdMovingPlayer.getPlayingLetter(), model.cell.get(2).get(2).getPlayingLetter(),
            "thirdPlayer was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }
  @Test
  void testWinThreshDecrease()throws OXOMoveException{
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.decreaseWinThreshold();
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b1");
    //Change winThresh to 2, the a1,b1 should set the winner
    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }
  @Test
  void testWinThreshIncrease()throws OXOMoveException{
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.increaseWinThreshold();
    controller.addRow();
    controller.addColumn();
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("c2");
    controller.handleIncomingCommand("d1");
    //Change winThresh to 4, the a1,b1,c1,d1 should set the winner
    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

}
