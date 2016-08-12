import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class webServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("ERROR: Incorrect flags for server initialization.");
            System.err.println("Usage: java WebServer --port=<port number>");
            System.exit(1);
        }
        String portNumberRaw = args[0];
        if (checkArgs(portNumberRaw) == false) {
            System.out.println("ERROR: Bad flags.");
            System.err.println("Usage: java WebServer --port=<port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0].substring(7));
        System.out.println(new File(".").getCanonicalPath());
        //create serversocket outside of loop or else exceptions
        ServerSocket serverSocket = new ServerSocket(portNumber);


        while (true) {
            try {
                Socket connection = serverSocket.accept();
                new RequestHandler(connection);
            } catch (Exception e) {
                System.out.println("Server failed with exception: " + e);
            }
        }
    
    }//end of main 


    public static boolean checkArgs(String portNumber) {
        System.out.println(portNumber.substring(0,7));
        if (portNumber.substring(0,7).compareTo("--port=") == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
