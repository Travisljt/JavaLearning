package edu.uob;

import java.io.File;
import java.util.*;

public class GameWorld {
    private ArrayList<Location> allLocations = new ArrayList<>();
    private HashMap<GameEntity, String> allEntities = new HashMap<>();
    private IdentityHashMap<String,String> allPaths = new IdentityHashMap<>();
    private TreeMap<String, HashSet<GameAction>> allActions = new TreeMap<>();
    private final ArrayList<String> buildInCommand = new ArrayList<>();

    public GameWorld(File entitiesFile,File actionsFile){
        ParserEntities parserEntities = new ParserEntities(entitiesFile);
        ParserAction parserAction = new ParserAction(actionsFile);
        this.allLocations = parserEntities.getAllLocations();
        this.allEntities = parserEntities.getAllEntities();
        this.allPaths = parserEntities.getAllPaths();
        this.allActions = parserAction.getAllActions();
        //insert build-in command
        this.buildInCommand.add("inv");
        this.buildInCommand.add("inventory");
        this.buildInCommand.add("get");
        this.buildInCommand.add("drop");
        this.buildInCommand.add("goto");
        this.buildInCommand.add("look");
        this.buildInCommand.add("health");
    }

    public Boolean entitiesIsEmptyFile(){
        return this.allLocations == null && this.allEntities == null;
    }

    public Boolean actionSIsEmptyFile(){
        return this.allActions == null;
    }

    public ArrayList<Location> getAllLocations() {
        return allLocations;
    }

    public HashMap<GameEntity, String> getAllEntities() {
        return allEntities;
    }

    public IdentityHashMap<String, String> getAllPaths() {
        return allPaths;
    }

    public TreeMap<String, HashSet<GameAction>> getAllActions() {
        return allActions;
    }

    public ArrayList<String> getBuildInCommand() {
        return buildInCommand;
    }
}
