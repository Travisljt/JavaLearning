package edu.uob.command;


import edu.uob.DB.Table;
import edu.uob.DBServer;


import java.util.ArrayList;

import java.util.Objects;


import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Alter extends General {
  private String alterType;

  public Alter(String command) {
    this.command = command;
  }

  public String getAlterType() {
    return alterType;
  }

  public void setAlterType(String alterType) {
    this.alterType = alterType;
  }

  public void alterParser() {
    String regex = "(alter\\s+table)(.+)(add|drop)(.+)(;$)";
    String type = getMatchTokenSpecial(regex, this.command, 3);
    if (type != null) {
      String tableName =
          Objects.requireNonNull(getMatchTokenSpecial(regex, this.command, 2)).toLowerCase();
      setTableName(tableName);
      String attribute = Objects.requireNonNull(getMatchTokenSpecial(regex, this.command, 4));
      setAttributes(attribute);
      type = type.toUpperCase();
      if (type.equals("ADD")) {
        setAlterType("ADD");
        setCheckTypeTrue();
      } else if (type.equals("DROP")) {
        setAlterType("DROP");
        setCheckTypeTrue();
      } else {
        setCheckTypeFalse();
        setErrorMessage("No match keyword add|drop");
      }
    } else {
      setCheckTypeFalse();
      setErrorMessage("Something wrong with add|drop type(it is null)");
    }
  }

  public Table alterExecution() {
    Table table = readFile(DBServer.database, getTableName());
    if (table != null) {
      if (getAlterType().equals("ADD")) {
        ArrayList<String> attributes = table.getAttributes();
        attributes.add(this.attributes);
        for (int i = 0; i < table.getRowNumber(); i++) {
          table.getRow(i).addCell(" ");
        }
        return table;
      } else if (getAlterType().equals("DROP")) {
        int dropColumNum = table.getAttributesOfIndex(this.attributes);
        if (dropColumNum == -1) {
          setErrorMessage("Can not find the attribute");
          return null;
        } else {
          return deleteColumn(table, dropColumNum);
        }
      }
    } else {
      setErrorMessage("Table isn't exist");
    }
    return null;
  }
}
