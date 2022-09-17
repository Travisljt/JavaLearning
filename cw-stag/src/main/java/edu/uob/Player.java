package edu.uob;

// This is the player entity which include health level and current location
public class Player extends GameEntity {

  private int healthLevel;
  private Location currentPosition;

  public Player(String name, String description) {
    super(name, description);
    this.isImmovable = false;
    //Default is 3
    this.healthLevel = 3;
    this.currentPosition = null;
  }

  public int getHealthLevel() {
    return healthLevel;
  }

  public void increaseHealthLevel() {
    //Maximum is 3
    if (this.healthLevel < 3) {
      this.healthLevel++;
    }
  }

  public void decreaseHealthLevel() {
    this.healthLevel--;
  }

  public void setHealthLevel(int healthLevel) {
    this.healthLevel = healthLevel;
  }

  public Location getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(Location currentPosition) {
    this.currentPosition = currentPosition;
  }
}
