package edu.uob.Analyzer;


import edu.uob.DB.Table;
import edu.uob.DBServer;
import edu.uob.Response.Response;
import edu.uob.command.*;

import java.io.File;
import java.io.IOException;

import static edu.uob.Analyzer.Data.printTable;
import static edu.uob.Analyzer.Data.writeFile;
import static edu.uob.Analyzer.StringRegex.keyCommand;
import static edu.uob.Analyzer.StringRegex.removeSpaces;

public class Query {
  private String command;
  private final String directory;


  public Query(String command) {
    this.command = command;
    this.directory = DBServer.directory;
  }

  public Response getResponse() throws IOException {
    Response response = new Response();
    if (this.command.isEmpty()) {
      response.getConsequence().add("There is no command received");
      response.setStatus("ERROR");
      return response;
    }
    this.command = removeSpaces(this.command);
    if (!this.command.endsWith(";")) {
      response.getConsequence().add("Semi colon missing");
      response.setStatus("ERROR");
      return response;
    }
    String keyWord = keyCommand(this.command);
    if (keyWord != null) {
      switch (keyWord) {
        case "USE" -> response = useFunction(this.command);
        case "CREATE" -> response = createFunction(this.command);
        case "DROP" -> response = dropFunction(this.command);
        case "ALTER" -> response = alterFunction(this.command);
        case "INSERT" -> response = insertFunction(this.command);
        case "SELECT" -> response = selectFunction(this.command);
        case "UPDATE" -> response = updateFunction(this.command);
        case "DELETE" -> response = deleteFunction(this.command);
        case "JOIN" -> response = joinFunction(this.command);
        default -> {
          response.setStatus("ERROR");
          response.getConsequence().add("Unknown command");
        }
      }
    }
    return response;
  }

  public Response useFunction(String command) {
    Response response = new Response();
    Use use = new Use(command);
    use.useParser();
    File[] files = new File(this.directory).listFiles();
    if (files != null) {
      if (use.useExecution(files)) {
        DBServer.database = use.getDataBaseName();
        response.setStatus("OK");
      } else {
        response.setStatus("ERROR");
        response.getConsequence().add(use.getErrorMessage());
      }
    } else {
      response.setStatus("ERROR");
      response.getConsequence().add("Interesting error in main directory");
    }
    return response;
  }

  public Response createFunction(String command) throws IOException {
    Response response = new Response();
    Create create = new Create(command);
    create.createParser();
    if (!create.checkTypeCondition()) {
      response.setStatus("ERROR");
      response.getConsequence().add(create.getErrorMessage());
    } else {
      if (create.getCreateType().equals("DATABASE")) {
        if (create.databaseExecution()) {
          response.setStatus("OK");
          DBServer.database = null;
        } else {
          response.setStatus("ERROR");
          response.getConsequence().add(create.getErrorMessage());
        }
      } else if (create.getCreateType().equals("TABLE")) {
        if (DBServer.database == null) {
          response.setStatus("ERROR");
          response.getConsequence().add("Please use database first");
        } else {
          if (create.tableExecution(DBServer.database)) {
            response.setStatus("OK");
          } else {
            response.setStatus("ERROR");
            response.getConsequence().add(create.getErrorMessage());
          }
        }
      }
    }
    return response;
  }

 public Response dropFunction(String command) {
   Response response = new Response();
   Drop drop= new Drop(command);
   drop.dropParser();
   if (!drop.checkTypeCondition()) {
     response.setStatus("ERROR");
     response.getConsequence().add(drop.getErrorMessage());
   } else{
     if (drop.getDropType().equals("DATABASE")) {
       if (drop.databaseExecution()) {
         response.setStatus("OK");
         DBServer.database = null;
       } else {
         response.setStatus("ERROR");
         response.getConsequence().add(drop.getErrorMessage());
       }
     } else if (drop.getDropType().equals("TABLE")) {
       if (DBServer.database == null) {
         response.setStatus("ERROR");
         response.getConsequence().add("Please use database first");
       } else {
         if (drop.tableExecution(DBServer.database)) {
           response.setStatus("OK");
         } else {
           response.setStatus("ERROR");
           response.getConsequence().add(drop.getErrorMessage());
         }
       }
     }
   }
   return response;
  }


  public Response alterFunction(String command) {
    Response response = new Response();
    Alter alter = new Alter(command);
    alter.alterParser();
    if(alter.checkTypeCondition()){
    if(DBServer.database==null){
      response.setStatus("ERROR");
      response.getConsequence().add("Use database first");
    }else {
      Table table = alter.alterExecution();
      if(table==null){
        response.setStatus("ERROR");
        response.getConsequence().add(alter.getErrorMessage());
      }else{
      writeFile(table);
      response.setStatus("OK");
      }
    }
    } else {
      response.setStatus("ERROR");
      response.getConsequence().add(alter.getErrorMessage());
      }
    return response;
  }

  public Response insertFunction(String command) {
    Response response = new Response();
    Insert insert = new Insert(command);
    insert.insertParser();
    if(insert.checkTypeCondition()){
      if(DBServer.database==null){
        response.setStatus("ERROR");
        response.getConsequence().add("Use database first");
      }else {
        Table table = insert.insertExecution();
        if(table==null){
          response.setStatus("ERROR");
          response.getConsequence().add(insert.getErrorMessage());
        }else{
          writeFile(table);
          response.setStatus("OK");
        }
      }
    }else{
      response.setStatus("ERROR");
      response.getConsequence().add(insert.getErrorMessage());
    }
    return response;
  }

  public Response selectFunction(String command) {
    Response response = new Response();
    Select select = new Select(command);
    select.selectParser();
    if(select.checkTypeCondition()){
      if(DBServer.database==null){
        response.setStatus("ERROR");
        response.getConsequence().add("Use database first");
      }else{
        Table table = select.selectExecution(DBServer.database);
        if(table!=null){
          response.setStatus("OK");
          response.setConsequence(printTable(table));
        }else {
          response.setStatus("ERROR");
          response.getConsequence().add(select.getErrorMessage());
        }
      }
    }else{
      response.setStatus("ERROR");
      response.getConsequence().add(select.getErrorMessage());
    }
    return response;
  }

  public Response updateFunction(String command) {
    Response response = new Response();
    Update update = new Update(command);
    update.updateParser();
    if(update.checkTypeCondition()){
      if(DBServer.database==null){
        response.setStatus("ERROR");
        response.getConsequence().add("Use database first");
      }else{
        Table table = update.updateExecution(DBServer.database);
        if(table!=null){
          response.setStatus("OK");
          writeFile(table);
        }else {
          response.setStatus("ERROR");
          response.getConsequence().add(update.getErrorMessage());
        }
      }
    }else{
      response.setStatus("ERROR");
      response.getConsequence().add(update.getErrorMessage());
    }
    return response;
  }

  public Response deleteFunction(String command) {
    Response response = new Response();
    Delete delete = new Delete(command);
    delete.deleteParser();
    if(delete.checkTypeCondition()){
      if(DBServer.database==null){
        response.setStatus("ERROR");
        response.getConsequence().add("Use database first");
      }else{
        Table table = delete.deleteExecution(DBServer.database);
        if(table!=null){
          response.setStatus("OK");
          writeFile(table);
        }else {
          response.setStatus("ERROR");
          response.getConsequence().add(delete.getErrorMessage());
        }
      }
    }else{
      response.setStatus("ERROR");
      response.getConsequence().add(delete.getErrorMessage());
    }
    return response;
  }

  public Response joinFunction(String command) {
    Response response = new Response();
    Join join = new Join(command);
    join.joinParser();
    if(join.checkTypeCondition()){
      if(DBServer.database==null){
        response.setStatus("ERROR");
        response.getConsequence().add("Use database first");
      }else{
        Table table = join.joinExecution(DBServer.database);
        if(table!=null){
          response.setStatus("OK");
          response.setConsequence(printTable(table));
        }else {
          response.setStatus("ERROR");
          response.getConsequence().add(join.getErrorMessage());
        }
      }
    }else{
      response.setStatus("ERROR");
      response.getConsequence().add(join.getErrorMessage());
    }
        return response;
  }

}
