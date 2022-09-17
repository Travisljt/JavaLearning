package edu.uob.DB;

import java.util.*;

public class Table {
  ArrayList<Row> table;
  ArrayList<String> attributes;
  private String tableName;
  private String dataBaseName;

  public Table() {
    this.table = new ArrayList<>();
    this.attributes = new ArrayList<>();
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  public void addRow(Row row){
    this.table.add(row);
  }
  public void removeRow(Row row){
    this.table.remove(row);
  }
  public int getRowNumber(){return this.table.size();}

  public Row getRow(int index){return this.table.get(index);}

  public void seRow(int index,Row row){ this.table.set(index,row);}

  public int getAttributesOfIndex(String attributes){return this.attributes.indexOf(attributes);}

  public String getDataBaseName() {
    return dataBaseName;
  }

  public void setDataBaseName(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }

  public ArrayList<String> getAttributes() {
    return attributes;
  }

  public void setAttributes(ArrayList<String> attributes) {
    this.attributes = attributes;
  }

}
