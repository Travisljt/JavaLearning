package edu.uob.command;

import edu.uob.DB.Table;


import static edu.uob.Analyzer.Data.*;
import static edu.uob.Analyzer.StringRegex.*;

public class Drop extends General {

  private String dropType;

  public Drop(String command) {
    this.command = command;
  }

  public void setDropType(String dropType) {
    this.dropType = dropType;
  }

  public String getDropType() {
    return this.dropType;
  }

  public void dropParser() {
    String regex = "(DROP\\s+)([A-Z]+)(.+;$)";
    String type = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);
    if (type != null) {
      setDropType(type.toUpperCase());
      if (type.equals("DATABASE")) {
        regex = "(DROP\\s+DATABASE)(.+)(;$)";
        String dataBaseName = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);
        if (dataBaseName != null) {
          this.dataBaseName = dataBaseName.toLowerCase();
          setCheckTypeTrue();
        } else {
          setCheckTypeFalse();
          setErrorMessage(" Match the null database");
        }
      } else if (type.equals("TABLE")) {

        regex = "(DROP\\s+TABLE)(.+)(;$)";
        String tableName = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);
        if (tableName != null) {
          this.tableName = tableName.toLowerCase();
          setCheckTypeTrue();
        } else {
          setCheckTypeFalse();
          setErrorMessage("Match the null table");
        }
      } else {
        setCheckTypeFalse();
        setErrorMessage("Unknown keyword");
      }
    } else {
      setCheckTypeFalse();
      setErrorMessage("No match database or table");
    }
  }

  public boolean databaseExecution() {
    if (deleteDataBase(this.dataBaseName)) {
      return true;
    }else {
      setErrorMessage("Can not delete the database");
      return false;
    }
  }

  public boolean tableExecution(String dataBaseName) {
    Table table = new Table();
    table.setDataBaseName(dataBaseName);
    table.setTableName(this.tableName);
    if(deleteTable(table)){
      return true;
    }else{
      setErrorMessage("Can not delete table: Invalid table name?");
      return false;
    }

  }
}
