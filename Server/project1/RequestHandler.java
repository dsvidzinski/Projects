import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestHandler extends Thread {
    private Socket activeSocket;

    public RequestHandler(Socket someSocket){
        this.activeSocket = someSocket;
        //launch thread;
        start();
    }

    private String redirectResolver(String uri_element){

	//specifing the files which holds redirectories 
	String redirect_file = "redirect.defs";
    
        File redirectDirectory = new File("../project1/www/" + redirect_file);
        String header = "HTTP/1.1 301 Moved Permanently\r\nLocation:";
        String redirectURI = "";
        if (redirectDirectory.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(redirectDirectory))){
                String line;
                while ((line = br.readLine()) != null) {
                 		
                    String [] split = line.split("\\s+");
                    if (split.length >= 2) {
                        String query = split[0];

                        if (query.charAt(0) == '/'){
                            query = query.substring(1);
                        }

                        if (query.compareTo(uri_element) == 0) {
                            redirectURI = header + split[1] + "\r\n\r\n";
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return redirectURI;
    }

    private String createHeader(File theFile){
        String responseLine = "";
        String mimeType = "";
        try {
            String filePath = theFile.getCanonicalPath();
            responseLine = "HTTP/1.1 200 OK\r\n";
	    
	    //defualt mimeType 
            mimeType = "text/plain\r\n";

            if (filePath.endsWith(".html")){ mimeType = "text/html\r\n";}
            if (filePath.endsWith(".jpg")||filePath.endsWith(".jpeg")){ mimeType = "image/jpeg\r\n";}
            if (filePath.endsWith(".png")){ mimeType = "image/png\r\n";}
            if (filePath.endsWith(".pdf")){ mimeType = "application/pdf\r\n";}
            if (filePath.endsWith(".txt")){ mimeType = "text/plain\r\n";}
        } catch (IOException e) {
            e.printStackTrace();
        }

	Date now = new Date();
        return responseLine + "Date: " + now + "\nContent-Type:" + mimeType;
    }
    //handleURI corrects the user's URI to some comprehensible filename; avoids accessing directories, should
    // probably remove ".." and such, too...
    private String handleURI(String filename){
        if (filename.endsWith("/")) {
            filename += "index.html";
	    System.out.println("The name of the file is " +filename);
	}
        if (filename.charAt(0) == '/'){
            filename = filename.substring(1);
        }

	 return filename;
    }
    //must override run method to circumscribe thread behaviour
    public void run(){
        try{
	    
            BufferedReader in = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
            //we use a printstream instead of an printwriter because transmitting the file requires writing raw bytes
            // to the socket.
            PrintStream out = new PrintStream(new BufferedOutputStream(activeSocket.getOutputStream()), true);
            //just read the first line, the rest is irrelevant to us anyway
            String requestLine = in.readLine();

	    StringTokenizer tokenizeRequest = new StringTokenizer(requestLine);
	    String first_token = tokenizeRequest.nextToken();
	    
            try{

                if (tokenizeRequest.hasMoreElements() && first_token.equalsIgnoreCase("GET")){
		    String filename = tokenizeRequest.nextToken();
                    filename = handleURI(filename);

		    File someFile = new File("../project1/www/" + filename);
		    
		    System.out.println("The filename is "+ filename); 

                    if (someFile.exists() && !filename.equals("redirect.defs")) {
			
			
                        
                        //output the header to the socket, add a second linefeed to indicate the start of the message
                        // body
                        String header = createHeader(someFile) + "\r\n";
                        System.out.println(header);
                        out.print(header);
                  
			//http://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
                        //let's assume no object for our server will be more than 8MB
                        //(this seems to be the canonical pattern for writing a file to a TCP stream.
                        
			byte[] data = new byte[8192];
                        InputStream filebytes = new FileInputStream(someFile);
                        int counter = 0;
                        while ((counter = filebytes.read(data)) > 0){
                            out.write(data, 0, counter);
                        }
                        activeSocket.close();

                    }else{
			
			String redirect = redirectResolver(filename);
		
		        if (redirect.compareTo("") != 0) {
			    System.out.println(redirect); 
                            out.println(redirect + "\r\n");
			    activeSocket.close();
			}
			else {
			    throw (new FileNotFoundException());
			}
		    }

                }else if (tokenizeRequest.hasMoreElements() && first_token.equalsIgnoreCase("HEAD")){
		    
		    String filename = tokenizeRequest.nextToken();
		    filename = handleURI(filename);

		    System.out.println("The file we are trying to get is " + filename);
                    File someFile = new File("../project1/www/" + filename);
                    System.out.println(someFile.getCanonicalFile());
                    
		    if (someFile.exists() && !filename.equals("redirect.defs")){
                        String header = createHeader(someFile) + "\r\n";
                        System.out.println(header);
                        out.print(header);
                        activeSocket.close();

                 }else{
                    
                        String redirect = redirectResolver(filename);
                        if (redirect.compareTo("") != 0){
			    out.println(redirect + "\r\n");
			    activeSocket.close();}
                        else throw new FileNotFoundException();
		    }
		
		}else { //IF NOT GET OR HEAD 
		    //System.out.println("403"); 
		    Date now = new Date();

		    String response1 = "HTTP/1.1 403 Forbidden \r\n\r\n403 request is Forbidden\n";
		    String response2 = "HTTP/1.1 403 Forbidden \nDate: " +now + "\n403 " + first_token + "  request is Forbidden\n";

                    System.out.println(response2); 
		    out.println(response1);
                    
		    activeSocket.close();
                }

            }catch (FileNotFoundException except){
		// System.out.println("404");
		Date now = new Date();
                
		String response1 = "HTTP/1.1 404 File Not Found\r\n\r\n404:File Not Found\n";
		String response2 = "HTTP/1.1 404 File Not Found\nDate: " + now + "\n404:File Not Found\n";

                System.out.println(response2); 		
		out.println(response1);

                activeSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


} 
