package edu.uob.command;

import edu.uob.DB.Row;
import edu.uob.DB.Table;
import edu.uob.DBServer;



import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Insert extends General {
  public Insert(String command) {
    this.command = command;
  }

  public void insertParser() {
    String regex = "(insert\\s+into)(.+)(values)";
    String tableName = getMatchTokenSpecial(regex, this.command, 2);
    if (tableName != null) {
      setTableName(tableName.toLowerCase());
      setCheckTypeTrue();
    } else {
      setCheckTypeFalse();
      setErrorMessage("Table null");
    }
    regex = "(values)(.+)(;$)";
    String valuesBrackets = getMatchTokenSpecial(regex, this.command, 2);
    if (valuesBrackets != null) {
      setValues(valuesBrackets);
      setCheckTypeTrue();
    } else {
      setCheckTypeFalse();
      setErrorMessage("Values null");
    }
  }

  public Table insertExecution() {
    Table table = readFile(DBServer.database, getTableName());
    if (table != null) {
      String id = idIncrease(table);
      String Value = removeSymbol(id + "," + this.values);
      String[] newValue = Value.split(",");
      Row row = new Row();
      for (String v : newValue) {
        row.addCell(v);
      }
      table.addRow(row);
      return table;
    } else {
      setErrorMessage("Table isn't exist");
    }
    return null;
  }
}
