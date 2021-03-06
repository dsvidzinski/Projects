/*This code is based on Oracle                                                                                                                                                        Java tutorials for Servers */

import java.net.*;
import java.io.*;

public class P0_Server{
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Need to enter a correct PORT NUMBER!");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);

        System.out.println("The entered port number is " + portNumber);
        try (
	     ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
	     Socket clientSocket = serverSocket.accept();
	     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                }
            } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                               + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }//end of try-catch block                                                                                                                                                    
    }//end of main                                                                                                                                                                   
}//end of P0_Server    