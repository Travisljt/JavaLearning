package edu.uob.command;

import java.io.File;

import static edu.uob.Analyzer.StringRegex.*;

public class Use extends General {
  public Use(String command) {
    this.command = command;
  }

  public void useParser(){
    String regex = "(use)(.+)(;$)";
    String dataBaseName = getMatchTokenSpecial(regex,this.command,2);
    if(dataBaseName != null){
      this.dataBaseName = dataBaseName.toLowerCase();
      setCheckTypeTrue();
    }else {
      setCheckTypeFalse();
      setErrorMessage("Usage command: USE <database>;");
    }
  }

  public boolean useExecution(File[] files){
    for(File file : files){
        if(file.isDirectory()){
          if(this.dataBaseName.equals(file.getName())){
              return true;
          }
        }
    }
    setErrorMessage("Unknown database");
    return false;
  }
}
