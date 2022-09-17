package domain;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String tableName;

    private Database database;

    private List<Row> rows;

    private List<String> columnNames;

    public Table() {
        this.rows = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }

    public int getColIndex(String columnName) {
        return this.columnNames.indexOf(columnName);
    }

    public Row createRow() {
        Row row = new Row();
        this.rows.add(row);
        return row;
    }

	public Row getRow(int index) {
		return this.rows.get(index);
	}

    public int getRowNum() {
        return this.rows.size();
    }

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public void removeRow(int i) {
        this.rows.remove(i);
    }
}
