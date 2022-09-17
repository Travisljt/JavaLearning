package edu.uob.command;

import edu.uob.DB.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

import edu.uob.DB.Table;


import static edu.uob.Analyzer.StringRegex.*;
import static edu.uob.Analyzer.Data.*;

public class Join extends General {
  public Join(String command) {
    this.command = command;
    this.token = new ArrayList<>();
  }

  public void joinParser() {
    String regex = "(join)(.+)(and)(.+)(on)(.+)(and)(.+)(;$)";
    Matcher matcher = getMatcher(regex, this.command);
    if (matcher != null) {
      this.attributes =
          (matcher.group(6).trim().toLowerCase() + "," + matcher.group(8).trim().toLowerCase());
      this.tableName =
          (matcher.group(2).trim().toLowerCase() + "," + matcher.group(4).trim().toLowerCase());
      setCheckTypeTrue();
    } else {
      setCheckTypeFalse();
      setErrorMessage("table or attribute is null, check the command");
    }
  }

  public Table joinExecution(String database) {
    String[] attributes = this.attributes.split(",");
    String[] tableName = this.tableName.split(",");
    Table table1 = readFile(database, tableName[0]);
    Table table2 = readFile(database, tableName[1]);
    if (table1 != null && table2 != null) {
      int index1 = table1.getAttributesOfIndex(attributes[0]);
      int index2 = table2.getAttributesOfIndex(attributes[1]);
      if(index1==-1 || index2==-1){
        setErrorMessage("One of attributes no match");
        return null;
      }
      deleteColumn(table1,index1);
      deleteColumn(table2,index2);
      if((table1.getAttributesOfIndex("id")!=-1) ||(table1.getAttributesOfIndex("id")!=-1)){
        if(table1.getAttributesOfIndex("id")!=-1){
          deleteColumn(table1,0);
        }
        if(table2.getAttributesOfIndex("id")!=-1){
          deleteColumn(table2,0);
        }
      }
      Table newTable = new Table();
      newTable.getAttributes().add("id");
      for(int i=0;i<table1.getAttributes().size();i++){
        newTable.getAttributes().add(table1.getAttributes().get(i));
      }
      for(int i=0;i<table2.getAttributes().size();i++){
        newTable.getAttributes().add(table2.getAttributes().get(i));
      }
      for(int i=0;i<table1.getRowNumber();i++){
        Row row = table1.getRow(i);
        Row row1 = new Row();
        row1.addCell(String.valueOf(i+1));
        for(int j=0;j<table1.getRow(i).getCellNumber();j++){
           row1.addCell(row.getCell(j).getData());
        }
        newTable.addRow(row1);
      }
      for(int i=0;i<table2.getRowNumber();i++){
        Row row = table2.getRow(i);
        Row row1 = newTable.getRow(i);
        for(int j=0;j<table2.getRow(i).getCellNumber();j++){
          row1.addCell(row.getCell(j).getData());
        }
      }
      return  newTable;
    } else {
      setErrorMessage("Table isn't exist");
      return null;
    }
  }
}
