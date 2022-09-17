package edu.uob.command;

import edu.uob.Analyzer.Data;
import edu.uob.DB.Operator;

import java.util.ArrayList;

public abstract class General {
  protected String command;
  protected String dataBaseName;
  protected String tableName;
  protected String attributes;
  protected String values;
  protected boolean checkType;
  protected String errorMessage;
  protected String condition;
  protected ArrayList<String> token;
  protected String[] inversePolish;
  protected Operator uniqueCondition;
  protected String carryValue;
  protected static final String operator ="==|>=|<=|<|>|LIKE|!=";


  public General() {}

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String getDataBaseName() {
    return dataBaseName;
  }

  public void setDataBaseName(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setAttributes(String attributes) {
    this.attributes = attributes;
  }

  public String getAttributes() {
    return attributes;
  }

  public String getValues() {
    return values;
  }

  public void setValues(String values) {
    this.values = values;
  }

  public void setCheckTypeFalse() {
    this.checkType = false;
  }

  public void setCheckTypeTrue(){
    this.checkType = true;
  }
  public boolean checkTypeCondition() {
    return checkType;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getCondition(){return condition;}

  public void setCondition(String condition) {
    this.condition = condition;
  }
  public void setInversePolish(String[] inversePolish){
    this.inversePolish = inversePolish;
  }
  public String[] getInversePolish(){
    return this.inversePolish;
  }
}
