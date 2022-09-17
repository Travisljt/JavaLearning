package edu.uob;

import java.util.ArrayList;
import java.util.HashSet;

public class GameText {

  public static ArrayList<String> getInvText(GameController game) {
    ArrayList<String> invText = new ArrayList<>();
    invText.add("List the artefacts you have:");
    boolean checkEmpty = true;
    //check if GameEntity -> player's inventory, then return
    for (GameEntity entity : game.getGameWorld().getAllEntities().keySet()) {
      if (game.getGameWorld()
          .getAllEntities()
          .get(entity)
          .equals(game.getCurrentPlayer().getName())) {
        invText.add(entity.getDescription());
        checkEmpty = false;
      }
    }
    if (checkEmpty) {
      invText.add("Your inventory is empty");
    }
    return invText;
  }

  public static ArrayList<String> getLookText(GameController game) {
    ArrayList<String> lookText = new ArrayList<>();
    lookText.add("You are in " + game.getCurrentLocation().getDescription() + ", you can see:");

    // All entities in the location
    if (game.getGameWorld().getAllEntities().containsValue(game.getCurrentLocation().getName())) {
      // Iterate over the allEntities' key array
      for (GameEntity entity : game.getGameWorld().getAllEntities().keySet()) {
        if (game.getGameWorld()
            .getAllEntities()
            .get(entity)
            .equals(game.getCurrentLocation().getName())) {
          lookText.add(entity.getDescription());
        }
      }
    } else {
      lookText.add("Nothing in the location");
    }

    // All players in the location
    for (Player player : game.getAllPlayers()) {
      if (player.getCurrentPosition().getName().equals(game.getCurrentLocation().getName())
          && !player.getName().equals(game.getCurrentPlayer().getName())) {
        lookText.add("Other player: " + player.getName());
      }
    }

    // All paths to other location
    lookText.add("You can access from here:");
    for (String location : game.getGameWorld().getAllPaths().keySet()) {
      if (location.equals(game.getCurrentLocation().getName())) {
        lookText.add(game.getGameWorld().getAllPaths().get(location));
      }
    }

    return lookText;
  }

  public static ArrayList<String> getHealthText(GameController game) {
    ArrayList<String> healthText = new ArrayList<>();
    healthText.add("Your health level is " + game.getCurrentPlayer().getHealthLevel());
    return healthText;
  }

  public static ArrayList<String> getGetText(GameController game, ArrayList<String> newCommand) {
    ArrayList<String> getText = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    if (game.getGameWorld().getAllEntities().containsValue(game.getCurrentLocation().getName())) {
      boolean checkGetNothing = true;
      // Iterate over the newCommand array
      for (String entityName : newCommand) {
        // Iterate over the allEntities' key array
        for (GameEntity entity : game.getGameWorld().getAllEntities().keySet()) {
          // Check newCommand if is the entity or not &&
          // check the entity if it is in the current location or not &&
          // Check the entity if it is artefact or not
          if (entityName.equals(entity.getName())
              && game.getGameWorld()
                  .getAllEntities()
                  .get(entity)
                  .equals(game.getCurrentLocation().getName())
              && !entity.getImmovable()) {
            getText.add("You pick up " + entity.getDescription());
            game.getGameWorld().getAllEntities().put(entity, game.getCurrentPlayer().getName());
            checkGetNothing = false;
          }
        }
        sb.append(entityName).append(" ");
      }
      if (checkGetNothing) {
        getText.add(
            "You can not get "
                + "["
                + sb
                + "]"
                + "\nBecause it is not in the location or it is immovable");
      }
    } else {
      getText.add("You can not get the stuff");
    }
    return getText;
  }

  public static ArrayList<String> getDropText(GameController game, ArrayList<String> newCommand) {
    ArrayList<String> dropText = new ArrayList<>();
    boolean checkDropNothing = true;
    // Same as above
    for (String entityName : newCommand) {
      for (GameEntity entity : game.getGameWorld().getAllEntities().keySet()) {
        if (entityName.equals(entity.getName())
            && game.getGameWorld()
                .getAllEntities()
                .get(entity)
                .equals(game.getCurrentPlayer().getName())) {
          dropText.add("You drop off " + entity.getDescription());
          game.getGameWorld().getAllEntities().put(entity, game.getCurrentLocation().getName());
          checkDropNothing = false;
        }
      }
    }
    if (checkDropNothing) {
      dropText.add("You cannot drop the stuff: There is nothing related in your inventory");
    }
    return dropText;
  }

  public static ArrayList<String> getGotoText(GameController game, ArrayList<String> newCommand) {
    ArrayList<String> gotoText = new ArrayList<>();
    boolean checkGotoNoPlace = true;
    for (String location : newCommand) {
      //Check current location if is equal to command
      for (String source : game.getGameWorld().getAllPaths().keySet()) {
        if (source.equals(game.getCurrentLocation().getName())
            && game.getGameWorld().getAllPaths().get(source).equals(location)) {
          for (Location newLocation : game.getGameWorld().getAllLocations()) {
            if (newLocation.getName().equals(location)) {
              game.setCurrentLocation(newLocation);
              gotoText = getLookText(game);
              checkGotoNoPlace = false;
            }
          }
        }
      }
    }

    if (checkGotoNoPlace) {
      gotoText.add("You cant not goto that location");
    }
    return gotoText;
  }

  // Assume that there is two open action, open trapdoor and open bluedoor
  // Assume the command type in is "open the door"
  //It should be allowed, and throw the options
  public static ArrayList<String> moreActionsText(
      HashSet<GameAction> gameActions, String trigger, ArrayList<String> commands) {
    ArrayList<String> actionsText = new ArrayList<>();
    actionsText.add("There is more than one thing you can do:");
    for (GameAction gameAction : gameActions) {
      for (String command : commands) {
        for (String subject : gameAction.getSubjects()) {
          if (subject.contains(command)) {
            actionsText.add(trigger + " " + gameAction.getSubjects().toString());
          }
        }
      }
    }
    actionsText.add("Which one do you want?");
    return actionsText;
  }
}
