package edu.uob;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static edu.uob.GameText.*;

public class GameController {

  private final GameWorld gameWorld;
  private Player currentPlayer;
  private Location currentLocation;
  private final ArrayList<Player> allPlayers;

  public GameController(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
    this.currentPlayer = null;
    this.currentLocation = gameWorld.getAllLocations().get(0);
    this.allPlayers = new ArrayList<>();
  }

  public GameWorld getGameWorld() {
    return gameWorld;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentLocation(Location currentLocation) {
    if (currentLocation == null) {
      this.currentLocation = gameWorld.getAllLocations().get(0);
    } else {
      this.currentLocation = currentLocation;
    }
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void addNewPlayer(Player newPlayer) {
    allPlayers.add(newPlayer);
  }

  public ArrayList<Player> getAllPlayers() {
    return allPlayers;
  }

  public Response getResponse(String[] command) {
    Response response = new Response();
    int buildInCommandCounter = 0;
    int triggerCounter = 0;
    String buildInCommand = null;
    StringBuilder trigger = new StringBuilder();
    for (String s : command) {
      //Mark the build-in command, it should be only one
      if (isBuildInCommand(s)) {
        buildInCommandCounter++;
        buildInCommand = s;
      }
      //Append triggers into string builder
      if (isTrigger(s)) {
        triggerCounter++;
        trigger.append(s).append(" ");
      }
    }
    if (buildInCommandCounter >= 1) {
      if (buildInCommandCounter > 1) {
        response.addResult("You should type only one build-in command");
      } else {
        response.setResult(getBuildInText(buildInCommand, command));
      }
    }
    if (triggerCounter >= 1) {
      //Ambitious command check
      if (triggerCounter > 1) {
        if (isSameAction(trigger.toString().trim())) {
          String firstCommand = trigger.toString().trim().split(" ")[0];
          response.setResult(getTriggerText(firstCommand, command));
        } else {
          response.addResult("You should type only one trigger command");
        }
      } else {
        response.setResult(getTriggerText(trigger.toString().trim(), command));
      }
    }

    //If player's health level is 0, it can change state right now
    if(this.currentPlayer.getHealthLevel()==0){
      response.addResult(doDeathAction());
    }

    //No word for neither trigger nor build-in command
    if (buildInCommandCounter == 0 && triggerCounter == 0) {
      response.addResult("Error: can not analyze. Please check\n");
    }

    return response;
  }

  public Boolean isBuildInCommand(String s) {
    return this.gameWorld.getBuildInCommand().contains(s);
  }

  public Boolean isTrigger(String s) {
    return this.gameWorld.getAllActions().containsKey(s);
  }

  //Check the build-in command
  public ArrayList<String> getBuildInText(String buildInCommand, String[] command) {
    ArrayList<String> response = new ArrayList<>();
    ArrayList<String> newCommand;
    switch (buildInCommand) {
      case "inv", "inventory" -> response = getInvText(this);
      case "look" -> response = getLookText(this);
      case "health" -> response = getHealthText(this);
      case "get" -> {
        newCommand = removeWord(command, "get");
        response = getGetText(this, newCommand);
      }
      case "drop" -> {
        newCommand = removeWord(command, "drop");
        response = getDropText(this, newCommand);
      }
      case "goto" -> {
        newCommand = removeWord(command, "goto");
        response = getGotoText(this, newCommand);
      }
      default -> response.add("Not in the build-in command list");
    }
    return response;
  }

  //Remove keyword
  public ArrayList<String> removeWord(String[] command, String str) {
    ArrayList<String> newString = new ArrayList<>(Arrays.asList(command));
    newString.remove(str);
    return newString;
  }

  public ArrayList<String> getTriggerText(String trigger, String[] commands) {
    StringBuilder sb = new StringBuilder();
    ArrayList<String> response = new ArrayList<>();
    //Remove trigger in command
    ArrayList<String> command = removeWord(commands, trigger);
    HashSet<GameAction> doGameActions = new HashSet<>();
    HashSet<GameAction> gameActions = this.gameWorld.getAllActions().get(trigger);
    for (String str : command) {
      sb.append(str).append(" ");
    }
    for (GameAction gameAction : gameActions) {
      if (isMatchEntity(gameAction.getSubjects(), command)) {
        //Used for subsequent comparisons to see if it is the same action
        doGameActions.add(gameAction);
      }
    }

    if (doGameActions.size() >= 1) {
      if (doGameActions.size() > 1) {
        response = moreActionsText(doGameActions, trigger, command);
      } else {
        response = doAction(doGameActions);
      }
    } else {

      if (sb.toString().equals("")) {
        response.add("You need to type something after " + trigger);
      } else {
        response.add("You can not " + trigger + " [" + sb + "] in this location");
      }
    }
    return response;
  }

  public boolean isSameAction(String str) {
    String[] commands = str.split(" ");
    int sameActionCounter = 0;
    //Select the first one trigger, check other triggers if they are in the action -> triggers list or not
    for (int i = 1; i < commands.length; i++) {
      for (GameAction gameAction : this.gameWorld.getAllActions().get(commands[0])) {
        if (gameAction.getTriggers().contains(commands[i])) {
          sameActionCounter++;
        }
        if (sameActionCounter == commands.length - 1) {
          return true;
        }
      }
    }
    return false;
  }

  // Assume that there is two open action, open box and open door
  // Assume the command type in is "open the trapdoor"
  // ----------------------------------------
  // Assume that there is two open action, open gameBox and open trapdoor
  // Assume the command type in is "open the door", it should print "Which one do you want"
  // ----------------------------------------
  public boolean isMatchEntity(HashSet<String> subjects, ArrayList<String> commands) {
    if (subjects == null) {
      return true;
    }
    boolean subjectCheck = false;
    HashSet<String> entitiesInLocation = getEntities(this.currentLocation.getName());
    HashSet<String> entitiesInInv = getEntities((this.currentPlayer.getName()));
    HashSet<String> allEntities = new HashSet<>(entitiesInLocation);
    allEntities.addAll(entitiesInInv);
    for (String subject : subjects) {
      for (String command : commands) {
        if (subject.contains(command) || commands.contains(subject)) {
          subjectCheck = true;
          break;
        }
      }
    }
    return subjectCheck && allEntities.containsAll(subjects);
  }

  // Select entities from same location or same player inventory
  public HashSet<String> getEntities(String str) {
    HashSet<String> entities = new HashSet<>();
    for (GameEntity entity : this.gameWorld.getAllEntities().keySet()) {
      if (str.equals(this.gameWorld.getAllEntities().get(entity))) {
        entities.add(entity.getName());
      }
    }
    return entities;
  }

  public ArrayList<String> doAction(HashSet<GameAction> gameActions) {
    ArrayList<String> narration = new ArrayList<>();
    for (GameAction gameAction : gameActions) {
      doConsumeAction(gameAction.getConsumed());
      doProduceAction(gameAction.getProduced());
      narration.add(gameAction.getNarration());
    }
    return narration;
  }

  //Check the health as a special entity then remove if it is existing.
  public void doConsumeAction(HashSet<String> consumedActions) {
    HashSet<String> consumedEntities = new HashSet<>(consumedActions);
    if (consumedEntities.contains("health")) {
      this.currentPlayer.decreaseHealthLevel();
      consumedEntities.remove("health");
    }
    //Check if it is location or not ,then do the method.
    for (String consumedEntity : consumedEntities) {
      doActionForLocation(consumedEntity, false);
      doActionForEntity(consumedEntity,"storeroom");
    }
  }

  //Same as above
  public void doProduceAction(HashSet<String> producedActions) {
     HashSet<String> producedEntities = new HashSet<>(producedActions);
    if (producedEntities.contains("health")) {
      this.currentPlayer.increaseHealthLevel();
      producedEntities.remove("health");
    }
    for (String producedEntity : producedEntities) {
      doActionForLocation(producedEntity, true);
      doActionForEntity(producedEntity,this.currentLocation.getName());

    }
  }

  public void doActionForEntity(String entity, String location){
    for (GameEntity entities : this.gameWorld.getAllEntities().keySet()) {
      if (entities.getName().equals(entity)) {
        this.gameWorld.getAllEntities().put(entities, location);
      }
    }
  }


  public void doActionForLocation(String entity, boolean flag) {
    for (Location location : this.gameWorld.getAllLocations()) {
      if (entity.equals(location.getName())) {
        if (flag) {
          this.gameWorld.getAllPaths().put(new String(this.currentLocation.getName()), entity);
        } else {
          for (String key : this.gameWorld.getAllPaths().keySet()) {
            if (entity.equals(this.gameWorld.getAllPaths().get(key))) {
              this.gameWorld.getAllPaths().remove(key);
              break;//Avoid throw an exception
            }
          }
        }
      }
    }
  }

  public String doDeathAction(){
    for(GameEntity entity : this.gameWorld.getAllEntities().keySet()){
      if(this.gameWorld.getAllEntities().get(entity).equals(this.currentPlayer.getName())){
        this.gameWorld.getAllEntities().put(entity,this.currentLocation.getName());
      }
    }
    this.currentPlayer.setHealthLevel(3);
    this.currentLocation = this.gameWorld.getAllLocations().get(0);
    return "\nYou are dead because your health level is 0.\nYou lost all your belongings and back to the start area";
  }
}
