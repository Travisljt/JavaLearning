package edu.uob;

import java.util.ArrayList;

public class Table {
    private String name;
    private final ArrayList<String> attributeList;
    private final ArrayList<ArrayList<String>> dataList;

    public Table(String name){
        this.name = name;
        attributeList = new ArrayList<String>();

        dataList = new ArrayList<ArrayList<String>>(100);
        for(int i = 0; i < 100; i++)  {
            dataList.add(new ArrayList<String>());
        }
    }

    public String getTableName(){
        return name;
    }

    public ArrayList getAttributeList(){
//        String[] attributeArray = new String[0];
        System.out.println(attributeList.get(0));

//        for(int i = 0; i < attributeList.size(); i++){
//            attributeArray[i] = attributeList.get(i);
//            System.out.println(attributeArray[i]);
//        }
        return attributeList;
    }

    public void getAllData(){
        System.out.println(dataList.get(0));
    }

    public void addAttributeList(String attribute){
        attributeList.add(attribute);
    }

    public void addData(int index, String data){
        dataList.get(index).add(data);
    }
}
