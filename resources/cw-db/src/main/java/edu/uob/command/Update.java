package edu.uob.command;

import edu.uob.DB.Table;
import edu.uob.DB.Operator;
import edu.uob.DB.Row;

import java.util.*;
import java.util.regex.Matcher;

import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Update extends General {
  public Update(String command) {
    this.command = command;
    this.token = new ArrayList<>();
  }

  public void updateParser() {
    if (matchTable()) {
      if (matchAttribute()) {
        matchCondition();
        setCheckTypeTrue();
      } else {
        setCheckTypeFalse();
        setErrorMessage("attributes is null");
      }
    } else {
      setCheckTypeFalse();
      setErrorMessage("tableName is null");
    }
  }

  public boolean matchTable() {
    String regex = "(update)(.+)(set)";
    String tableName = getMatchTokenSpecial(regex, this.command, 2);
    if (tableName != null) {
      this.tableName = tableName.toLowerCase();
      return true;
    }
    return false;
  }

  public boolean matchAttribute() {
    String regex = "(set)(.+)(where)";
    String nameList = getMatchTokenSpecial(regex, this.command, 2);
    if (nameList != null) {
      ArrayList<String> nameListToken = new ArrayList<>();
      while (optionalKeyword(nameList, ",")) {
        regex = "(.+)(,)(.+$)";
        String words = getMatchTokenSpecial(regex, nameList, 3);
        nameListToken.add(words);
        nameList = getMatchTokenSpecial(regex, nameList, 1);
      }
      nameListToken.add(nameList);
      StringBuilder sb1 = new StringBuilder();
      StringBuilder sb2 = new StringBuilder();
      for (String listToken : nameListToken) {
        regex = "(.+)(=)(.+)";
        String s1 = getMatchTokenSpecial(regex, listToken, 1);
        String s2 = getMatchTokenSpecial(regex, listToken, 3);
        if (s1 == null || s2 == null) {
          return false;
        } else {
          sb1.append(s1).append(",");
          sb2.append(s2).append(",");
        }
      }
      this.attributes = sb1.toString();
      this.carryValue = sb2.toString();
      return true;
    }
    return false;
  }

  public void matchCondition() {
    String regex = "(where)(.+)(;$)";
    String newCommand = getMatchTokenSpecial(regex, this.command, 2);
    this.condition = newCommand;
    while (optionalKeywordFromRegex(newCommand, "and|or")) {
      regex = "(.+)(and|or)(.+$)";
      String words = getMatchTokenSpecial(regex, newCommand, 3);
      this.token.add(words);
      newCommand = getMatchTokenSpecial(regex, newCommand, 1);
    }
    this.token.add(newCommand);
    if (token.size() == 1) {
      removeSymbol(this.condition);
    } else {
      for (int i = 0; i < token.size(); i++) {
        String newToken = this.token.get(i).replace("(", "").replace(")", "").replaceAll("\\s", "");
        this.condition = this.condition.replace("(" + newToken + ")", String.valueOf(i));
      }
      this.condition = this.condition.replaceAll("(?i)and", "*").replaceAll("(?i)or", "+");
      String s = toSufferString(this.condition);
      this.inversePolish = s.split(",");
      for (int i = 0; i < this.inversePolish.length; i++) {
        if (isNumeric(this.inversePolish[i])) {
          int num = Integer.parseInt(inversePolish[i]);
          this.inversePolish[i] = removeSymbol(this.token.get(num));
        }
      }
    }
  }

  public Table updateExecution(String dataBase) {
    Table table = readFile(dataBase, this.tableName);
    if (table != null) {
      table = selectSpecialTable(table);
      table = selectAttributesUpdate(table);
      return table;
    } else {
      setErrorMessage("Table isn't exist");
      return null;
    }
  }

  public Table selectAttributesUpdate(Table table) {
    String[] attributes = this.attributes.split(",");
    String[] carryValue = this.carryValue.split(",");
    Table newTable = readFile(table.getDataBaseName(), table.getTableName());
    if (newTable != null) {
      for (int i = 0; i < attributes.length; i++) {
        int index = table.getAttributesOfIndex(attributes[i]);
        for(int j=0;j<table.getRowNumber();j++){
          String id = table.getRow(j).getCell(0).getData();
          for(int k=0;k<newTable.getRowNumber();k++){
            String newId = newTable.getRow(k).getCell(0).getData();
            if(newId.equals(id)){
              Row row = newTable.getRow(k);
              row.getCell(index).setData(carryValue[i]);
            }
          }
        }
       }
    }
    return newTable;
  }

  public Table selectSpecialTable(Table table) {
    if (this.inversePolish == null) {
      return selectRow(table, this.condition);
    } else {
      Stack<Table> st = new Stack<>();
      for (String polish : this.inversePolish) {
        if (Objects.equals(polish, "+") || Objects.equals(polish, "*")) {
          if (Objects.equals(polish, "+")) {
            Table newTable1 = st.pop();
            Table newTable2 = st.pop();
            Table table1 = new Table();
            table1.setTableName(newTable1.getTableName());
            table1.setAttributes(newTable1.getAttributes());
            table1.setDataBaseName(newTable1.getDataBaseName());
            for (int i = 0; i < newTable2.getRowNumber(); i++) {
              table1.addRow(newTable2.getRow(i));
            }
            for (int i = 0; i < newTable1.getRowNumber(); i++) {
              int cnt = 0;
              for (int j = 0; j < newTable2.getRowNumber(); j++) {
                if (!newTable1.getRow(i).equals(newTable2.getRow(j))) {
                  cnt++;
                }
                if (cnt == newTable2.getRowNumber()) {
                  table1.addRow(newTable1.getRow(i));
                }
              }
            }
            st.push(tableRearrange(table1));
          }
          if (Objects.equals(polish, "*")) {
            Table newTable1 = st.pop();
            Table newTable2 = st.pop();
            Table table1 = new Table();
            table1.setTableName(newTable1.getTableName());
            table1.setAttributes(newTable1.getAttributes());
            table1.setDataBaseName(newTable1.getDataBaseName());
            for (int i = 0; i < newTable1.getRowNumber(); i++) {
              for (int j = 0; j < newTable2.getRowNumber(); j++) {
                if (newTable1.getRow(i).equals(newTable2.getRow(j))) {
                  table1.addRow(newTable1.getRow(i));
                }
              }
            }
            if (table1.getRow(0) == null) {
              setErrorMessage("Condition match(AND) nothing");
              return null;
            }
            st.push(tableRearrange(table1));
          }

        } else {
          Table newTable = selectRow(table, polish);
          if (newTable == null) {
            return null;
          }
          st.push(newTable);
        }
      }
      return tableRearrange(st.pop());
    }
  }

  public Table selectRow(Table table, String token) {
    String regex = "(.+)(" + General.operator + ")(.+)";
    Matcher matcher = getMatcher(regex, token);
    if (matcher != null) {
      this.uniqueCondition =
          new Operator(
              matcher.group(1).trim(),
              matcher.group(2).trim(),
              matcher.group(3).replaceAll("'", "").trim());
      String attribute = this.uniqueCondition.getDomain();
      int attributeIndex = table.getAttributesOfIndex(attribute);
      if (attributeIndex == -1) {
        setErrorMessage("Attributes in condition no match");
        return null;
      }
      Table newTable = new Table();
      newTable.setTableName(table.getTableName());
      newTable.setDataBaseName(table.getDataBaseName());
      newTable.setAttributes(table.getAttributes());
      for (int i = 0; i < table.getRowNumber(); i++) {
        newTable.addRow(table.getRow(i));
      }
      for (int i = 0; i < table.getRowNumber(); i++) {
        Row row = table.getRow(i);
        String cellValue = row.getCell(attributeIndex).getData();
        if (this.uniqueCondition.getAnswer(cellValue)) {
          newTable.removeRow(row);
        }
      }
      return tableRearrange(newTable);
    } else {
      setErrorMessage("Condition wrong type");
      return null;
    }
  }
}
