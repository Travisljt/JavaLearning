package edu.uob.command;

import edu.uob.DB.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Create extends General {

  private String createType;

  public Create(String command) {
    this.command = command;
  }

  public void setCreateType(String createType) {
    this.createType = createType;
  }

  public String getCreateType() {
    return this.createType;
  }

  public void createParser() {
    String regex = "(CREATE\\s+)([A-Z]+)(.+;$)";
    String type = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);

    if (type != null) {
      setCreateType(type.toUpperCase());
      if (type.equals("DATABASE")) {

        regex = "(CREATE\\s+DATABASE)(.+)(;$)";
        String dataBaseName = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);
        if (dataBaseName != null) {
          this.dataBaseName = dataBaseName.toLowerCase();
          setCheckTypeTrue();
        } else {
          setCheckTypeFalse();
          setErrorMessage(" Match the null database");
        }
      } else if (type.equals("TABLE")) {
        regex = "(CREATE\\s+TABLE)(.+)(\\(.+\\))";
        Matcher matcher = getMatcher(regex, this.command.toUpperCase());
        if (matcher == null) {
          regex = "(CREATE\\s+TABLE)(.+)(;$)";
          String tableName = getMatchTokenSpecial(regex, this.command.toUpperCase(), 2);
          if (tableName != null) {
            this.tableName = tableName.toLowerCase();
            setCheckTypeTrue();
          } else {
            setCheckTypeFalse();
            setErrorMessage("Match the null table");
          }
        } else {
          String tableName = getMatchTokenSpecial(regex, this.command, 2);
          String attributesBrackets = getMatchTokenSpecial(regex, this.command, 3);
          if (attributesBrackets != null && tableName != null) {
            this.tableName = tableName.toLowerCase();
            this.attributes = removeSymbol(attributesBrackets);
            setCheckTypeTrue();
          } else {
            setCheckTypeFalse();
            setErrorMessage("Match null table and attributes");
          }
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
    if (createDatabase(this.dataBaseName)) {
      return true;
    } else {
      setErrorMessage("Create database failed: Same name of database?");
      return false;
    }
  }

  public boolean tableExecution(String dataBaseName) throws IOException {
    Table table = new Table();
    table.setDataBaseName(dataBaseName);
    table.setTableName(this.tableName);
    if (this.attributes == null) {
      if(createFileWithoutAttributes(table)){
        return true;
      }else {
        setErrorMessage("Create table failed: Same name of table?");
        return false;
      }
    } else {
      String newAttribute = "id," + this.attributes;
      ArrayList<String> newAttributes = new ArrayList<>(Arrays.asList(newAttribute.split(",")));
      table.setAttributes(newAttributes);
      if(createFileWithAttributes(table)){
        return true;
      }else {
        setErrorMessage("Create table failed: Same name of table?");
        return false;
      }
    }
  }
}
