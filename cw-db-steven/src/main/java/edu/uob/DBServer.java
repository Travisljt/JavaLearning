package edu.uob;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;

/** This class implements the DB server. */
public final class DBServer {
  private final ArrayList<Database> databaseList;
  private static final char END_OF_TRANSMISSION = 4;
  private static Database currentDatabase = null;

  public static void main(String[] args) throws IOException {
    new DBServer(Paths.get(".").toAbsolutePath().toFile()).blockingListenOn(8888);
  }

  /**
   * KEEP this signature (i.e. {@code edu.uob.DBServer(File)}) otherwise we won't be able to mark
   * your submission correctly.
   *
   * <p>You MUST use the supplied {@code databaseDirectory} and only create/modify files in that
   * directory; it is an error to access files outside that directory.
   *
   * @param databaseDirectory The directory to use for storing any persistent database files such
   *     that starting a new instance of the server with the same directory will restore all
   *     databases. You may assume *exclusive* ownership of this directory for the lifetime of this
   *     server instance.
   */
  public DBServer(File databaseDirectory) throws IOException {
    // TODO implement your server logic here
    databaseList = new ArrayList<Database>();

    String filePath = databaseDirectory.getPath();
    if(filePath.endsWith(".")){
      String newFilePath =  filePath.replaceAll("\\.", "file");

      File db = new File(newFilePath);
      File[] dbList = db.listFiles();

//      String databasePath = Arrays.toString(dbList).replaceAll("\\[", "").replaceAll("]", "");

      for(int i = 0; i < Objects.requireNonNull(dbList).length; i++){
        storeFolder(databaseList, String.valueOf(dbList[i]), dbList[i].getName());
      }
    }
  }

  public void storeFolder(ArrayList<Database> databaseList, String dbpath, String dbname) throws IOException {
    Database CheckedDB = new Database(dbname);

    File table = new File(dbpath);
    File[] tableList = table.listFiles();

    for(int i = 0; i < Objects.requireNonNull(tableList).length; i++){
        storeFile(CheckedDB, String.valueOf(tableList[i]), tableList[i].getName());
    }

    databaseList.add(CheckedDB);
  }

  public void storeFile(Database database,String filepath, String filename) throws IOException {
    Table CheckedTable = new Table(filename.replaceAll(".tab", ""));

    String[] ArrayString;
    int index;
    boolean isFistRow = true;

    File fileToOpen = new File(filepath);
    FileReader reader = new FileReader(fileToOpen);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line = bufferedReader.readLine();

    do{
      ArrayString = line.split("\\s");

      if(!isFistRow){
        index = Integer.parseInt(ArrayString[0]);
        index--;

        for(String s : ArrayString){
          CheckedTable.addData(index, s);
        }
      }else{
        for (String s : ArrayString) {
          CheckedTable.addAttributeList(s);
        }
      }
      line = bufferedReader.readLine();
      isFistRow = false;
    }while(line != null);

    database.addTable(CheckedTable);

    bufferedReader.close();
  }

  /**
   * KEEP this signature (i.e. {@code edu.uob.DBServer.handleCommand(String)}) otherwise we won't be
   * able to mark your submission correctly.
   *
   * <p>This method handles all incoming DB commands and carry out the corresponding actions.
   */
  public String handleCommand(String command) {
    // TODO implement your server logic here
    CheckSQL classCommand =  new CheckSQL(command);

    classCommand.CheckCommandType();

    if(Objects.equals(classCommand.GetCommand(), "USE")){
      if(CheckDatabaseIsExist(classCommand.GetCommand())){
//        System.out.println(currentDatabase.getName());
        classCommand.SetDatabase(currentDatabase);
      }
    }

    return "[OK] Thanks for your message: " + command;
  }

  public boolean CheckDatabaseIsExist(String database){
    for (Database value : this.databaseList) {
      if (Objects.equals(value.getName(), database)) {
        currentDatabase = value;
        return true;
      }
    }
    return false;
  }

  public boolean CheckTableIsExist(String table){
    ArrayList<Table> a = currentDatabase.getTableList();

    return false;
  }

  //  === Methods below are there to facilitate server related operations. ===

  /**
   * Starts a *blocking* socket server listening for new connections. This method blocks until the
   * current thread is interrupted.
   *
   * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
   * you want to.
   *
   * @param portNumber The port to listen on.
   * @throws IOException If any IO related operation fails.
   */
  public void blockingListenOn(int portNumber) throws IOException {
    try (ServerSocket s = new ServerSocket(portNumber)) {
      System.out.println("Server listening on port " + portNumber);
      while (!Thread.interrupted()) {
        try {
          blockingHandleConnection(s);
        } catch (IOException e) {
          System.err.println("Server encountered a non-fatal IO error:");
          e.printStackTrace();
          System.err.println("Continuing...");
        }
      }
    }
  }

  /**
   * Handles an incoming connection from the socket server.
   *
   * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
   * * you want to.
   *
   * @param serverSocket The client socket to read/write from.
   * @throws IOException If any IO related operation fails.
   */
  private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
    try (Socket s = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {

      System.out.println("Connection established: " + serverSocket.getInetAddress());
      while (!Thread.interrupted()) {
        String incomingCommand = reader.readLine();
        System.out.println("Received message: " + incomingCommand);
        String result = handleCommand(incomingCommand);
        writer.write(result);
        writer.write("\n" + END_OF_TRANSMISSION + "\n");
        writer.flush();
      }
    }
  }
}
