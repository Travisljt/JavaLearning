package edu.uob;

import java.util.HashSet;

public class GameAction {

  private HashSet<String> triggers;
  private HashSet<String> subjects;
  private HashSet<String> consumed;
  private HashSet<String> produced;
  private String narration;

  public GameAction() {
    this.triggers = null;
    this.subjects = null;
    this.consumed = null;
    this.produced = null;
    this.narration = null;
  }

  public void setTriggers(HashSet<String> triggers) {
    this.triggers = triggers;
  }

  public void setSubjects(HashSet<String> subjects) {
    this.subjects = subjects;
  }

  public void setConsumed(HashSet<String> consumed) {
    this.consumed = consumed;
  }

  public void setProduced(HashSet<String> produced) {
    this.produced = produced;
  }

  public void setNarration(String narration) {
    this.narration = narration;
  }

  public HashSet<String> getTriggers() {
    return triggers;
  }

  public HashSet<String> getSubjects() {
    return subjects;
  }

  public HashSet<String> getConsumed() {
    return consumed;
  }

  public HashSet<String> getProduced() {
    return produced;
  }

  public String getNarration() {
    return narration;
  }
}
