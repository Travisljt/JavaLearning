package edu.uob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

/** This class implements the STAG server. */
public final class GameServer {

  private static final char END_OF_TRANSMISSION = 4;
  private final GameController controller;
  private final ArrayList<Player> allPlayers;

  public static void main(String[] args) throws IOException {
    //    File entitiesFile =
    //        Paths.get("config" + File.separator + "basic-entities.dot").toAbsolutePath().toFile();
    //    File actionsFile =
    //        Paths.get("config" + File.separator + "basic-actions.xml").toAbsolutePath().toFile();
    //    File entitiesFile =
    //            Paths.get("config" + File.separator +
    // "extended-entities.dot").toAbsolutePath().toFile();
    //    File actionsFile =
    //            Paths.get("config" + File.separator +
    // "extended-actions.xml").toAbsolutePath().toFile();
    File entitiesFile =
        Paths.get("config" + File.separator + "self-test-entities.dot").toAbsolutePath().toFile();
    File actionsFile =
        Paths.get("config" + File.separator + "self-test-actions.xml").toAbsolutePath().toFile();
    GameServer server = new GameServer(entitiesFile, actionsFile);
    server.blockingListenOn(8888);
  }

  /**
   * KEEP this signature (i.e. {@code edu.uob.GameServer(File, File)}) otherwise we won't be able to
   * mark your submission correctly.
   *
   * <p>You MUST use the supplied {@code entitiesFile} and {@code actionsFile}
   *
   * @param entitiesFile The game configuration file containing all game entities to use in your
   *     game
   * @param actionsFile The game configuration file containing all game actions to use in your game
   */
  public GameServer(File entitiesFile, File actionsFile) {
    GameWorld gameWorld = new GameWorld(entitiesFile, actionsFile);
    this.controller = new GameController(gameWorld);
    this.allPlayers = this.controller.getAllPlayers();
  }

  /**
   * KEEP this signature (i.e. {@code edu.uob.GameServer.handleCommand(String)}) otherwise we won't
   * be able to mark your submission correctly.
   *
   * <p>This method handles all incoming game commands and carries out the corresponding actions.
   */
  public String handleCommand(String command) {
    StringBuilder stringBuilder = new StringBuilder();
    Response response = new Response();

    // If file is empty, send error message
    if (this.controller.getGameWorld().entitiesIsEmptyFile()
        || this.controller.getGameWorld().actionSIsEmptyFile()) {
      response.addResult("There is no world to play\n");
      return "Error: " + response.getResult().get(0);
    }
    CommandAnalyzer commandAnalyzer = new CommandAnalyzer(command);

    // If type nothing, send error message
    if (commandAnalyzer.getCommand() == null) {
      response.addResult("You should type some texts\n");
      return "Error: " + response.getResult().get(0);
    }

    Player currentPlayer = getCurrentPlayer(commandAnalyzer.getPlayerName());
    response = this.controller.getResponse(commandAnalyzer.getCommand());
    currentPlayer.setCurrentPosition(this.controller.getCurrentLocation());

    for (String s : response.getResult()) {
      stringBuilder.append(s);
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  public Player getCurrentPlayer(String playerName) {
    // Check current player's name, if not in the list, just add it
    for (Player allPlayer : this.allPlayers) {
      if (allPlayer.getName().equals(playerName)) {
        this.controller.setCurrentPlayer(allPlayer);
        this.controller.setCurrentLocation(allPlayer.getCurrentPosition());
        return allPlayer;
      }
    }
    Player newPlayer = new Player(playerName, "");
    this.controller.setCurrentPlayer(newPlayer);
    this.controller.setCurrentLocation(newPlayer.getCurrentPosition());
    newPlayer.setCurrentPosition(this.controller.getCurrentLocation());
    this.controller.addNewPlayer(newPlayer);
    return newPlayer;
  }

  //  === Methods below are there to facilitate server related operations. ===

  /**
   * Starts a *blocking* socket server listening for new connections. This method blocks until the
   * current thread is interrupted.
   *
   * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
   * you want to.
   *
   * @param portNumber The port to listen on.
   * @throws IOException If any IO related operation fails.
   */
  public void blockingListenOn(int portNumber) throws IOException {
    try (ServerSocket s = new ServerSocket(portNumber)) {
      System.out.println("Server listening on port " + portNumber);
      while (!Thread.interrupted()) {
        try {
          blockingHandleConnection(s);
        } catch (IOException e) {
          System.out.println("Connection closed");
        }
      }
    }
  }

  /**
   * Handles an incoming connection from the socket server.
   *
   * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
   * * you want to.
   *
   * @param serverSocket The client socket to read/write from.
   * @throws IOException If any IO related operation fails.
   */
  private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
    try (Socket s = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
      System.out.println("Connection established");
      String incomingCommand = reader.readLine();
      if (incomingCommand != null) {
        System.out.println("Received message from " + incomingCommand);
        String result = handleCommand(incomingCommand);
        writer.write(result);
        writer.write("\n" + END_OF_TRANSMISSION + "\n");
        writer.flush();
      }
    }
  }
}
