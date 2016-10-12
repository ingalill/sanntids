/*
 * http://hubpages.com/technology/How-to-create-a-server-in-Java 
mulitthread: http://hubpages.com/technology/How-to-build-a-server-in-Java-Part-3-Allowing-multiple-users-to-connect 
 */
package sortingbot.server;

import java.net.*;
import java.util.*;
import java.io.*;
/**
 * @date 11.10.2016
 * @author inga lill bjolstad
 */
public class Server {
    
    public static void main(String[] args){
       // Server server =
                new Server();
    }
    
     
    public Server() 
    {
        //We need a try-catch because lots of errors can be thrown
        try {
            ServerSocket sSocket = new ServerSocket(5000);
            System.out.println("Server started at: " + new Date());
             
             System.out.println("Socket address:" + sSocket.getLocalSocketAddress());
            
            //Wait for a client to connect
            Socket socket = sSocket.accept();
             
            //Create the streams
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             System.out.println("hei");
            //Tell the client that he/she has connected
            output.println("You have connected at: " + new Date());
           
             
            //Loop that runs server functions
            while(true) {
                //This will wait until a line of text has been sent
                String chatInput = input.readLine();
                System.out.println(chatInput);
            }
        } catch(IOException exception) {
            System.out.println("Error: " + exception);
        }
    }
}
   
