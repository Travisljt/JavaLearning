package edu.uob;


public abstract class GameEntity
{
    String name;
    String description;
    //Check the entity if is immovable or not
    Boolean isImmovable;

    public GameEntity(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.isImmovable = true;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Boolean getImmovable() { return isImmovable;}
}
