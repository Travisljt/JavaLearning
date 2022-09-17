package edu.uob;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.objects.Edge;

public class ParserEntities {

  private final ArrayList<Location> allLocations = new ArrayList<>();
  // It should have comparator interface for GameEntity by using Treemap, so use hashmap to link entities to their location
  private final HashMap<GameEntity, String> allEntities = new HashMap<>();
  // Cause the paths are not unique, so we need to use IdentityHashMap to make it be like one key can match more than one values
  private final IdentityHashMap<String,String> allPaths = new IdentityHashMap<>();

  public ParserEntities(File entitiesFile) {
    try {
      Parser parser = new Parser();
      FileReader reader = new FileReader(entitiesFile);
      parser.parse(reader);
      Graph wholeDocument = parser.getGraphs().get(0);
      ArrayList<Graph> sections = wholeDocument.getSubgraphs();

      // The locations will always be in the first subgraph
      ArrayList<Graph> locations = sections.get(0).getSubgraphs();
      for (Graph location : locations) {
        ArrayList<Node> allLocationDetails = location.getNodes(false);
        Node locationDetails = allLocationDetails.get(0);
        this.allLocations.add(
            new Location(
                locationDetails.getId().getId(), locationDetails.getAttribute("description")));
        ArrayList<Graph> allEntities = location.getSubgraphs();
        for (Graph eachEntities : allEntities) {
          ArrayList<Node> entities = eachEntities.getNodes(false);
          for (Node entity : entities) {
            String keyword = eachEntities.getId().getId();
            switch (keyword) {
              case "artefacts" -> this.allEntities.put(
                      new Artefacts(entity.getId().getId(), entity.getAttribute("description")),
                      locationDetails.getId().getId());
              case "characters" -> this.allEntities.put(
                      new Characters(entity.getId().getId(), entity.getAttribute("description")),
                      locationDetails.getId().getId());
              case "furniture" -> this.allEntities.put(
                      new Furniture(entity.getId().getId(), entity.getAttribute("description")),
                      locationDetails.getId().getId());
            }
          }
        }
      }
      // The paths will always be in the second subgraph
      ArrayList<Edge> paths = sections.get(1).getEdges();
      for (Edge path : paths) {
        //Because use identityHashMap, it allows one key to more than one values
        //But it needs to put new String("location") instead of use "location" itself
          this.allPaths.put(new String(path.getSource().getNode().getId().getId()),path.getTarget().getNode().getId().getId());
      }
    } catch (FileNotFoundException | ParseException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Location> getAllLocations() {
    return allLocations;
  }

  public IdentityHashMap<String, String> getAllPaths() {
    return allPaths;
  }

  public HashMap<GameEntity, String> getAllEntities() {
    return allEntities;
  }
}
