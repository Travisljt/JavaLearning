package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class SelfTests {

  private GameServer server;

  // Make a new server for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup() {
    File entitiesFile =
        Paths.get("config" + File.separator + "self-test-entities.dot").toAbsolutePath().toFile();
    File actionsFile =
        Paths.get("config" + File.separator + "self-test-actions.xml").toAbsolutePath().toFile();
    server = new GameServer(entitiesFile, actionsFile);
  }

  // Test to spawn a new server and send a simple "look" command
  @Test
  void testLookingAroundStartLocation() {
    String response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
        response.contains("cabin"), "Did not see description of room in response to look");
    assertTrue(
        response.contains("magic potion"),
        "Did not see description of artifacts in response to look");
    assertTrue(
            response.contains("axe"),
            "Did not see description of artifacts in response to look");
    assertTrue(
            response.contains("coin"),
            "Did not see description of artifacts in response to look");
    assertTrue(
        response.contains("wooden trapdoor"),
        "Did not see description of furniture in response to look");
    assertTrue(
            response.contains("forest"),
            "Did not see access paths in response to look");
  }
  @Test
  void testBasicCommand() {
    String response = server.handleCommand("Simon: get axe").toLowerCase();
    assertTrue(
            response.contains("pick up"), "Did not see description of artifacts in response to get");
    assertTrue(
            response.contains("axe"), "Did not see description of artifacts in response to get");
    response = server.handleCommand(("Simon: azxafaq asdva")).toLowerCase();
    assertTrue(
            response.contains("can not analyze"), "Did not see description of artifacts in response to get");
    response = server.handleCommand(("Simon: inv")).toLowerCase();
    assertTrue(
            response.contains("axe"), "Did not see description of artifacts in response to get");

    response = server.handleCommand("Simon: get potion").toLowerCase();
    assertTrue(
            response.contains("pick up"), "Did not see description of artifacts in response to get");
    assertTrue(
            response.contains("potion"), "Did not see description of artifacts in response to get");

    response = server.handleCommand(("Simon: inventory")).toLowerCase();
    assertTrue(
            response.contains("axe"), "Did not see description of artifacts in response to get");
    assertTrue(
            response.contains("potion"), "Did not see description of artifacts in response to get");

    response = server.handleCommand("Simon: drop potion").toLowerCase();
    assertTrue(
            response.contains("drop off"), "Did not see description of artifacts in response to get");
    assertTrue(
            response.contains("potion"), "Did not see description of artifacts in response to get");

    response = server.handleCommand(("Simon: inventory")).toLowerCase();
    assertTrue(
            response.contains("axe"), "Did not see description of artifacts in response to get");

    response = server.handleCommand(("Simon: health")).toLowerCase();
    assertTrue(
            response.contains("health level is 3"), "Did not see description of health in response to get");

    response = server.handleCommand(("Simon: goto forest")).toLowerCase();
    assertTrue(
            response.contains("dark forest"), "Did not see description of room in response to get");

    response = server.handleCommand(("Simon: inv goto")).toLowerCase();
    assertTrue(
            response.contains("only one build-in command"), "Did not see description of failure in response to get");
  }


  @Test
  void testActionCommand() {
    String response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
            response.contains("magic potion"),
            "Did not see description of artifacts in response to look");
    response = server.handleCommand("Simon: get potion").toLowerCase();
    assertTrue(
            response.contains("pick up"), "Did not see description of artifacts in response to get");
    assertTrue(
            response.contains("potion"), "Did not see description of artifacts in response to get");
    response = server.handleCommand("travis: look").toLowerCase();
    assertFalse(
             response.contains("magic potion"),
            "Did not see description of artifacts in response to look");
    assertTrue(
            response.contains("simon"), "Did not see description of player in response to get");
     server.handleCommand("Simon: goto !=\tforest");
    server.handleCommand("Simon: get key");
    server.handleCommand("Simon: goto cabin");
    response = server.handleCommand("Simon: open door").toLowerCase();
    assertTrue(
            response.contains("which one "), "Did not see description of choices in response to get");
    assertTrue(
            response.contains("trapdoor"), "Did not see description of furniture in response to get");
    assertTrue(
            response.contains("bluedoor"), "Did not see description of furniture in response to get");
    response = server.handleCommand("Simon: open unlock trapdoor").toLowerCase();
    assertTrue(
            response.contains("steps leading down into a cellar"), "Did not see description of path in response to get");
    response = server.handleCommand("Simon: inv").toLowerCase();
    assertFalse(
            response.contains("key"), "Did not see description of artefact in response to get");
    response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
            response.contains("cellar"), "Did not see description of path in response to get");
    server.handleCommand("Simon: open bluedoor");
    response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
            response.contains("gameworld"), "Did not see description of path in response to get");
    server.handleCommand("Simon: close bluedoor");
    response = server.handleCommand("Simon: look").toLowerCase();
    assertFalse(
            response.contains("gameworld"), "Did not see description of path in response to get");
    server.handleCommand("Simon: get axe");
    server.handleCommand("Simon: get coin");
    server.handleCommand("Simon: get potion");
    response = server.handleCommand("Simon: axe drop").toLowerCase();
    assertTrue(
            response.contains("drop off"), "Did not see description of room in response to drop");
    response = server.handleCommand("Simon: axe drop").toLowerCase();
    assertTrue(
            response.contains("cannot drop the stuff"), "Did not see description of room in response to drop");
    server.handleCommand("Simon: get axe");
    response = server.handleCommand("Simon: goto cellar").toLowerCase();
    assertTrue(
            response.contains("dusty cellar"), "Did not see description of room in response to get");
    assertTrue(
            response.contains("angry looking elf"), "Did not see description of character in response to get");
    response = server.handleCommand("Simon: pay the elf").toLowerCase();
    assertTrue(
            response.contains("he produces a shovel"), "Did not see description of narration in response to get");
    response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
            response.contains("sturdy shovel"), "Did not see description of room in response to get");
    response = server.handleCommand("Simon: hit the elf").toLowerCase();
    assertTrue(
            response.contains("lose some health"), "Did not see description of narration in response to get");
    response = server.handleCommand("Simon: health").toLowerCase();
    assertTrue(
            response.contains("2"), "Did not see description of narration in response to get");
    response = server.handleCommand("Simon: drink potion").toLowerCase();
    assertTrue(
            response.contains("your health improves"), "Did not see description of narration in response to get");
    server.handleCommand("Simon: hit the elf");
    server.handleCommand("Simon: hit the elf");
    response = server.handleCommand("Simon: hit the elf").toLowerCase();
    assertTrue(
            response.contains("you are dead"), "Did not see description of narration in response to get");
    response = server.handleCommand("Simon: look").toLowerCase();
    assertTrue(
            response.contains("cabin"), "Did not see description of room in response to look");
    assertFalse(
            response.contains("magic potion"),
            "Did not see description of artifacts in response to look");
    assertFalse(
            response.contains("axe"),
            "Did not see description of artifacts in response to look");
    assertFalse(
            response.contains("coin"),
            "Did not see description of artifacts in response to look");
    assertTrue(
            response.contains("wooden trapdoor"),
            "Did not see description of furniture in response to look");
    assertTrue(
            response.contains("forest"),
            "Did not see access paths in response to look");
    assertTrue(
            response.contains("cellar"),
            "Did not see access paths in response to look");
    response = server.handleCommand("Simon: inv").toLowerCase();
    assertTrue(
            response.contains("empty"), "Did not see description of room in response to look");
    response = server.handleCommand("Simon: goto cellar").toLowerCase();
    assertTrue(
            response.contains("axe"), "Did not see description of room in response to look");
    assertTrue(
            response.contains("elf"), "Did not see description of room in response to look");
  }
}
