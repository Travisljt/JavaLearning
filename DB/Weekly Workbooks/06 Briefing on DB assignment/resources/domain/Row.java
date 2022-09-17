package domain;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private List<Column> columns;

    public Row() {
        this.columns = new ArrayList<>();
    }

    public Column createColumn() {
        Column col = new Column();
        this.columns.add(col);
        return col;
    }

    public Column createColumn(String data) {
        Column col = new Column(data);
        this.columns.add(col);
        return col;
    }

    public Column getColumn(int index) {
        return this.columns.get(index);
    }

    public int getColNum() {
        return this.columns.size();
    }

    public void removeColumn(int i) {
        this.columns.remove(i);
    }
}
