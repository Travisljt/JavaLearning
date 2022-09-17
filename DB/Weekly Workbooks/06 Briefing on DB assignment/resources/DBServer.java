import java.io.*;
import java.net.*;
import java.util.*;

import javax.management.Query;

import components.QueryManager;
import constant.Status;
import vo.Response;

class DBServer
{
    public DBServer(int portNumber)
    {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) processNextConnection(serverSocket);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextConnection(ServerSocket serverSocket)
    {
        try {
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connection Established");
            while(true) processNextCommand(socketReader, socketWriter);
        } catch(IOException ioe) {
            System.err.println(ioe);
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter) throws IOException, NullPointerException
    {
        String incomingCommand = socketReader.readLine();
        System.out.println("Received message: " + incomingCommand);
        // 接受指令
        QueryManager qm = QueryManager.getInstance(incomingCommand);
        // 解析指令并返回结果
        Response response = qm.queryParser();
        List<String> results = response.getResults();
        // 校验返回状态，若OK则输出相应信息，若ERROR则输出错误信息
        if (Status.OK.equals(response.getStatus())) {
            socketWriter.write("[OK]\n");
            for (String row : results) {
                socketWriter.write(row);
                socketWriter.newLine();
            }
        } else {
            socketWriter.write("[ERROR]: " + results.get(0));
        }
        socketWriter.write("\n" + ((char)4) + "\n");
        socketWriter.flush();
    }

    public static void main(String args[])
    {
        DBServer server = new DBServer(8888);
    }

}
