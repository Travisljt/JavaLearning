package edu.uob.DB;

import java.util.*;

public class Row {
  private final ArrayList<Cell> Row;

  public Row() {
    this.Row = new ArrayList<>();
  }

  public void addCell(String data) {
    Cell cell = new Cell(data);
    this.Row.add(cell);
  }

  public void removeCell(int index) {
    this.Row.remove(index);
  }

  public Cell getCell(int index) {
    return this.Row.get(index);
  }

  public int getCellNumber() {
    return this.Row.size();
  }
}
