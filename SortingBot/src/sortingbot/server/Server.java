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
    
    public static void main(String[] args){
        new Server();
    }
    
    public Server() 
    {
        //We need a try-catch because lots of errors can be thrown
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started at: " + new Date());
            
            //Wait for a client to connect
            Socket socket = serverSocket.accept();
             
            //Create the streams
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            //Tell the client that he/she has connected
            output.println("You have connected at: " + new Date());
           //matToImg();
            /*
            Sende frames skal inn her, skal sendes hele tiden når connection er åpen
            Sende størrelsen på bilde over socket
            */
            
            //Loop that runs server functions
            while(true) {
                //This will wait until a line of text has been sent
                String chatInput = input.readLine();
                System.out.println("You got the input: " + chatInput);
            }
        } catch(IOException exception) {
            System.out.println("Error: " + exception);
        }
    }
    
    /*
    Skal i server
    Take an Mat and convert it to an BufferedImage
    @Param Mat input.
    @Return BufferedImage output.
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
   
