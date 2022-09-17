package edu.uob;

import java.util.ArrayList;
import java.util.Locale;

public class CommandAnalyzer {
  private final String command;
  private final String playerName;

  //Limit the command into two element and split with ':',
  //First is the player name and second is the real command
  public CommandAnalyzer(String command) {
    String[] commands = command.split(":",2);
    this.playerName = commands[0];
    if (commands.length > 1) {
      this.command = commands[1];
    } else {
      this.command = null;
    }
  }

  public String[] getCommand() {
    if (this.command == null) {
      return null;
    } else {
      //remove redundant symbols
      return command
          .replaceAll("([-!.:;,'\"])", " ")
          .replaceAll("\\s+", " ")
          .trim()
          .toLowerCase()
          .split(" ");
    }
  }

  public String getPlayerName() {
    return playerName;
  }
}
