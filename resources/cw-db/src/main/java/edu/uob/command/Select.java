package edu.uob.command;

import edu.uob.DB.Operator;
import edu.uob.DB.Row;
import edu.uob.DB.Table;

import java.util.*;
import java.util.regex.Matcher;

import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Select extends General {

  public Select(String command) {
    this.command = command;
    this.token = new ArrayList<>();
  }

  public void selectParser() {
    if (matchAttribute()) {
      if (matchTable()) {
        matchCondition();
        setCheckTypeTrue();
      } else {
        setCheckTypeFalse();
        setErrorMessage("tableName is null");
      }
    } else {
      setCheckTypeFalse();
      setErrorMessage("attributes is null");
    }
  }

  public boolean matchAttribute() {
    String regex = "(select)(.+)(from)";
    String attribute = getMatchTokenSpecial(regex, this.command, 2);
    if (attribute != null) {
      if (attribute.equals("")) {
        return false;
      } else {
        this.attributes = attribute;
        return true;
      }
    } else {
      return false;
    }
  }

  public boolean matchTable() {
    if (optionalKeyword(this.command, "where")) {
      String regex = "(from)(.+)(where)";
      String tableName = getMatchTokenSpecial(regex, this.command, 2);
      if (tableName != null) {
        if (tableName.equals("")) {
          return false;
        } else {
          this.tableName = tableName.toLowerCase();
          return true;
        }

      } else {
        return false;
      }

    } else {
      String regex = "(from)(.+)(;$)";
      String tableName = getMatchTokenSpecial(regex, this.command, 2);
      if (tableName != null) {
        this.tableName = tableName.toLowerCase();
        return true;
      } else {
        return false;
      }
    }
  }

  public void matchCondition() {
    if (optionalKeyword(this.command, "where")) {
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
          String newToken =
              this.token.get(i).replace("(", "").replace(")", "").replaceAll("\\s", "");
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
  }

  public Table selectExecution(String dataBaseName) {
    Table table = readFile(dataBaseName, this.tableName);
    if (table != null) {
      if (this.condition != null) {
        table = selectSpecialTable(table);
      }
      if (!getAttributes().equals("*")) {
        table = selectAttributes(table);
      }
      return table;
    } else {
      setErrorMessage("Table isn't exist");
      return null;
    }
  }

  public Table selectAttributes(Table table) {
    ArrayList<Integer> indexes = new ArrayList<>();
    String[] attributes = removeSymbol(this.attributes).split(",");
    for (String attribute : attributes) {
      int index = table.getAttributesOfIndex(attribute);
      if (index == -1) {
        setErrorMessage("Attributes not exist");
        return null;
      }
      indexes.add(index);
    }
    return selectColumn(table, indexes);
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
          new Operator(matcher.group(1).trim(), matcher.group(2).trim(), matcher.group(3).replaceAll("'", "").trim());
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
