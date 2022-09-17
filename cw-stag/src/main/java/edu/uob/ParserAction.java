package edu.uob;

import java.io.IOException;
import java.io.File;
import java.util.HashSet;

import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ParserAction {

  private final TreeMap<String, HashSet<GameAction>> allActions = new TreeMap<>();

  public ParserAction(File actionsFile) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder.parse(actionsFile);
      Element root = document.getDocumentElement();
      NodeList actions = root.getChildNodes();

      // Get each action (only the odd items are actually actions - 1, 3, 5 etc.)
      for (int i = 1; i < actions.getLength(); i = i + 2) {
        Element eachAction = (Element) actions.item(i);
        Element triggers = (Element) eachAction.getElementsByTagName("triggers").item(0);
        Element subjects = (Element) eachAction.getElementsByTagName("subjects").item(0);
        Element consumed = (Element) eachAction.getElementsByTagName("consumed").item(0);
        Element produced = (Element) eachAction.getElementsByTagName("produced").item(0);
        Element narration = (Element) eachAction.getElementsByTagName("narration").item(0);
        GameAction gameAction = new GameAction();

        // Set each action with trigger, subject, consumed, produced and narration
        gameAction.setTriggers(getActionWord(triggers,"keyword"));
        gameAction.setSubjects(getActionWord(subjects,"entity"));
        gameAction.setConsumed(getActionWord(consumed,"entity"));
        gameAction.setProduced(getActionWord(produced,"entity"));
        gameAction.setNarration(narration.getTextContent());

        //Because use TreeMap, use each trigger as a key (i.e. 'open' with GameAction 1 and 'unlock' too)
        for (int j = 0; j < triggers.getElementsByTagName("keyword").getLength(); j++) {
          HashSet<GameAction> entities = new HashSet<>();
          String keyword = triggers.getElementsByTagName("keyword").item(j).getTextContent();

          //If there already have a key in hashSet<GameAction>, load it then add it.
          if (this.allActions.containsKey(keyword)) {
            entities = this.allActions.get(keyword);
          }
          entities.add(gameAction);
          this.allActions.put(keyword, entities);
        }
      }

    } catch (ParserConfigurationException pce) {
      System.err.println(
          "ParserConfigurationException was thrown when attempting to read actions file");
    } catch (SAXException saxe) {
      System.err.println("SAXException was thrown when attempting to read  actions file");
    } catch (IOException ioe) {
      System.err.println("IOException was thrown when attempting to read  actions file");
    }
  }

  public HashSet<String> getActionWord(Element e, String str) {
    HashSet<String> actionWord = new HashSet<>();
    for (int i = 0; i < e.getElementsByTagName(str).getLength(); i++) {
      String s = e.getElementsByTagName(str).item(i).getTextContent();
      actionWord.add(s);
    }
    return actionWord;
  }

  public TreeMap<String, HashSet<GameAction>> getAllActions() {
    return allActions;
  }
}
