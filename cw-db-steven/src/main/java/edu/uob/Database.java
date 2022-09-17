package edu.uob;

import java.util.ArrayList;

public class Database {
    private final String name;
    private final ArrayList<Table> tableList;

    public Database(String name){
        this.name = name;

        tableList = new ArrayList<Table>();
    }

    public String getName(){
        return name;
    }

    public void addTable(Table table){
        tableList.add(table);
    }

    public ArrayList<Table> getTableList(){
        return tableList;
    }

    public void printTable(){
        for(int i = 0; i < tableList.size(); i++){
            System.out.println(tableList.get(i).getTableName());
        }
    }

}
