package edu.uob;

import java.util.ArrayList;
import java.util.Objects;

public class CheckSQL {
    String[] ArrayString;
    private final ArrayList<String> CommandList;
    Table CheckedTable;
    Database CheckedDatabase;
    int commandNo;
    boolean isCorrect;

    public CheckSQL(String command){
        if(IsContainOperator(command)){
            ArrayString = command.replaceAll("==", " == ").split("\\s");
        }else{
            ArrayString = command.split("\\s");
        }
//        replaceAll("==", " == ").
        CommandList = new ArrayList<String>();
        commandNo = 0;

        OptimizeArrayString();
//        CheckCommandType();
        PrintCommand();
    }

    public boolean IsContainOperator(String command){
        String[] operator = {"==", ">", "<", ">=", "<=", "!=", "LIKE"};

        for(int i = 0; i < operator.length; i++){
            if(command.contains(operator[i])){
                return true;
            }
        }
        return false;
    }

    public void SetTable(Table table){
        this.CheckedTable = table;
    }

    public void SetDatabase(Database database){
        this.CheckedDatabase = database;
    }

    public void PrintCommand(){
        for(int i = 0; i < CountNumberOfCommand(); i++){
            System.out.println(CommandList.get(i));
        }
    }

    public String GetCommand(){
        return CommandList.get(commandNo);
    }

    public int CountNumberOfCommand(){
        return CommandList.size();
    }

    public void OptimizeArrayString(){
        for (String s : ArrayString) {
            if (!Objects.equals(s, "")) {
                CommandList.add(s);
            }
        }
    }

    public void CheckCommandType(){
        if(Objects.equals(CommandList.get(commandNo), "USE")){
            commandNo++;
//            ExecuteUse();
        }else{
            System.out.println("Error command!");
        }
//        else if(Objects.equals(CommandList.get(0), "DROP")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "CREATE")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "ALTER")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "INSERT") && Objects.equals(CommandList.get(0), "INTO")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "SELECT")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "UPDATE")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "DELETE") && Objects.equals(CommandList.get(0), "FROM")){
//            return true;
//        }else if(Objects.equals(CommandList.get(0), "JOIN")){
//            return true;
//        }
    }

    public void ExecuteUse(){
        System.out.println(GetCommand());

    }

//
//    public boolean checkLetter(){
//
//    }
//
//    public boolean checkDigit(){
//
//    }
//
//    public boolean checkValidSymbol(){
//
//    }
//
//    public boolean checkOperator(){
//
//    }

}
