package sortingbot.server;

import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import java.io.*;
import sortingbot.VideoBox;
import org.opencv.core.Mat;
/**
 * @date 11.10.2016
 * @author inga lill bjolstad
 */
public class Server {
    private VideoBox videograbber;
    private ServerSocket serverSocket;
    private Socket socket;
    //private BufferedReader inputStream;
    private PrintWriter output;
    
    private static final int maxServers = 10;
    private static final ServerThread[] threads = new ServerThread[maxServers];
    
    public static void main(String[] args) throws InterruptedException{
        new Server();
    }
    
    public Server() throws InterruptedException 
    {
        //We need a try-catch because lots of errors can be thrown
        try {
                 while(true) {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server started at: " + new Date());
            
            //Wait for a client to connect
            socket = serverSocket.accept();
             
            //Create the streams
            output = new PrintWriter(socket.getOutputStream(), true);
           // inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                      
            //Tell the client that he/she has connected
            output.println("You have connected at: " + new Date());
      
            //Loop that runs server functions. // bør få egen tråd
       
                //test
                for(int i =0; i<maxServers; i++){
                    if(threads[i] == null){
                        (threads[i] = new ServerThread(socket, threads)).run();
                        try{
                            Thread.sleep(10);
                        }
                        catch(InterruptedException e){
                             break;
                        }
                    }
                    if(i == maxServers){
                        PrintStream printSt = new PrintStream(socket.getOutputStream());
                        printSt.println("Server is busy, come back later");
                        printSt.close();
                        socket.close();
                    }                     
                }
                
               /* //This will wait until a line of text has been sent. skal inn i serverThread
                String input = inputStream.readLine();
                System.out.println("You got the input: " + input); */
            } 
            
        } catch(IOException exception) {
            System.out.println("Error med tilkobling: " + exception);
        }
    }
                    
      
    /*
    *Take an Mat and convert it to an BufferedImage
    *@Param Mat input.
    *@Return BufferedImage output.
    */
    public static BufferedImage matToImg(Mat in){
        BufferedImage out;
        byte[] data = new byte[in.height() *in.width() *(int)in.elemSize()];
        int type;
        in.get(0,0,data);
        
        if(in.channels() == 0){
         type = BufferedImage.TYPE_BYTE_GRAY;  
        }
        else{
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        out = new BufferedImage(in.height(),in.width(),type);
        out.getRaster().setDataElements(0,0,in.height(),in.width(),data);
        return out;
    }
    
} // end of class
   
