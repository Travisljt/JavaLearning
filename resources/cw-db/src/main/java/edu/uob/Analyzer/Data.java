package edu.uob.Analyzer;

import edu.uob.DB.Cell;
import edu.uob.DB.Row;
import edu.uob.DB.Table;
import edu.uob.DBServer;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Data {

  public static boolean createDatabase(String dataBaseName) {
    File file = new File(DBServer.directory + File.separator + dataBaseName);
    if (!file.exists()) {
      return file.mkdir();
    } else {
      return false;
    }
  }

  public static void writeAttributes(Table table, File file) {
    try {
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter writer = new BufferedWriter(fileWriter);
      ArrayList<String> attributes = table.getAttributes();
      for (String Attributes : attributes) {
        writer.write(Attributes);
        writer.write("\t");
      }
      writer.newLine();
      writer.flush();
      writer.close();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean createFileWithoutAttributes(Table table) throws IOException {
    File file =
        new File(
            DBServer.directory
                + File.separator
                + table.getDataBaseName()
                + File.separator
                + table.getTableName()
                + ".tab");
    return file.createNewFile();
  }

  public static boolean createFileWithAttributes(Table table) {
    File file =
        new File(
            DBServer.directory
                + File.separator
                + table.getDataBaseName()
                + File.separator
                + table.getTableName()
                + ".tab");
    if (!file.exists()) {
      writeAttributes(table, file);
      return true;
    } else {
      return false;
    }
  }

  public static Table readFile(String dataBaseName, String tableName) {
    Table newTable = new Table();
    File file =
        new File(
            DBServer.directory
                + File.separator
                + dataBaseName
                + File.separator
                + tableName
                + ".tab");
    if (!file.exists()) {
      return null;
    } else {
      newTable.setDataBaseName(dataBaseName);
      newTable.setTableName(tableName);
      try {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String firstLine, mainLine;
        firstLine = reader.readLine(); // First line is attribute
        if (firstLine != null && !firstLine.equals("")) {
          ArrayList<String> attributes = new ArrayList<>(Arrays.asList(firstLine.split("\t")));
          newTable.setAttributes(attributes);
        }
        while ((mainLine = reader.readLine()) != null && !mainLine.equals("")) {
          Row row = new Row();
          for (String cell : mainLine.split("\t")) {
            row.addCell(cell);
          }
          newTable.addRow(row);
        }
        reader.close();
        fileReader.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
      return newTable;
    }
  }

  public static void writeFile(Table table) {
    File file =
        new File(
            DBServer.directory
                + File.separator
                + table.getDataBaseName()
                + File.separator
                + table.getTableName()
                + ".tab");
    if (file.exists()) {
      try {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        ArrayList<String> attributes = table.getAttributes();
        for (String Attributes : attributes) {
          writer.write(Attributes);
          writer.write("\t");
        }
        writer.newLine();
        writer.flush();
        for (int i = 0; i < table.getRowNumber(); i++) {
          Row row = table.getRow(i);
          for (int j = 0; j < row.getCellNumber(); j++) {
            Cell cells = row.getCell(j);
            writer.write(cells.getData());
            writer.write("\t");
          }
          writer.newLine();
          writer.flush();
        }
        writer.close();
        fileWriter.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Can not write file (File not found)");
    }
  }

  public static boolean deleteTable(Table table) {
    File file =
        new File(
            DBServer.directory
                + File.separator
                + table.getDataBaseName()
                + File.separator
                + table.getTableName()
                + ".tab");
    if (file.isFile() && file.exists()) {
      return file.delete();
    } else {
      return false;
    }
  }

  public static boolean deleteDataBase(String dataBaseName) {
    File file = new File(DBServer.directory + File.separator + dataBaseName);
    if (file.isDirectory() && file.exists()) {
      try {
        File[] files = file.listFiles();
        if (files != null) {
          for (File f : files) {
            f.delete();
          }
        }
        return file.delete();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static Table deleteColumn(Table table, int index) {
    ArrayList<String> attributes = table.getAttributes();
    attributes.remove(index);
    table.setAttributes(attributes);
    for(int i=0;i<table.getRowNumber();i++){
      Row row = table.getRow(i);
      row.removeCell(index);
    }
    return table;
  }

  public static Table selectColumn(Table table, ArrayList<Integer> indexes){
    ArrayList<String> attributes = table.getAttributes();
      for (int i = table.getAttributes().size() - 1; i >= 0; i--) {
        if (!indexes.contains(i)) {
          attributes.remove(i);
        }
    }
    for(int i=0;i<table.getRowNumber();i++){
      Row row = table.getRow(i);
      for(int j=table.getRow(i).getCellNumber()-1;j>=0;j--){
        if(!indexes.contains(j)){
          row.removeCell(j);
        }
      }
    }
    return table;
  }

  public static String idIncrease(Table table){
    int idCounter = table.getRowNumber();
    String id = "1";
    if(idCounter>0){
      int number = Integer.parseInt(table.getRow(idCounter-1).getCell(0).getData())+1;
      id = String.valueOf(number);
    }
    return id;
  }

  public static Table tableRearrange(Table table) {
    int idIndex = table.getAttributesOfIndex("id");
    for (int i = 0; i < table.getRowNumber() - 1; i++) {
      Row row1 = table.getRow(i);
      int num1 = Integer.parseInt(row1.getCell(idIndex).getData());
      for (int j = i + 1; j < table.getRowNumber(); j++) {
        Row row2 = table.getRow(j);
        int num2 = Integer.parseInt(row2.getCell(idIndex).getData());
        if (num1 > num2) {
          Row temp = table.getRow(i);
          table.seRow(i, table.getRow(j));
          table.seRow(j, temp);
        }
      }
    }
    return table;
  }

  public static ArrayList<String> printTable(Table table) {
    if (table == null) {
      return null;
    }
    ArrayList<String> consequence = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();
    if (table.getAttributes() == null) {
      consequence.add("Can not found the attributes");
      return consequence;
    }
    // Write Attributes
    for (int i = 0; i < table.getAttributes().size(); i++) {
      stringBuilder.append(table.getAttributes().get(i));
      stringBuilder.append("\t");
    }
    consequence.add(stringBuilder.toString());
    // Write each cell
    for (int i = 0; i < table.getRowNumber(); i++) {
      Row row = table.getRow(i);
      StringBuilder sb = new StringBuilder();
      for (int j = 0; j < row.getCellNumber(); j++) {
        sb.append(row.getCell(j).getData());
        sb.append("\t");
      }
      consequence.add(sb.toString());
    }

    return consequence;
  }
}
