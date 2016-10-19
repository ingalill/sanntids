/*
 * -Djava.library.path="C:\Users\ingab\Desktop\inga\OpenCv 3.1\build\java\x64;C:\Users\ingab\Desktop\inga\sanntids\Arduino\ArduinoJavaTest"
* http://stackoverflow.com/questions/24182610/java-socket-server-client-to-client-file-transfer 
*/
package sortingbot.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *@date 18.10.2016
 * @author ingalillbjolstad
 */
public class ServerThread implements Runnable {
    // send a frame from the server to the client.
    // from videograbber/videobox.
    // get a mat and send it to the client
    // get Frame from server and send it to client

    private DataInputStream dataInputStream = null;
    private PrintStream printStream = null;
    private Socket serverSocket = null;
    private final ServerThread[] threads;
    private int maxServerCount;
    private BufferedReader inputStream;
    private static boolean closed = false;

    public ServerThread(Socket serverSocket, ServerThread[] threads) {
        this.serverSocket = serverSocket;
        this.threads = threads;
        maxServerCount = threads.length;
    }
    
    @Override
    public void run(){
        int maxServerCount = this.maxServerCount;
        ServerThread[] threads = this.threads;

        try{
            dataInputStream = new DataInputStream(serverSocket.getInputStream());
            printStream = new PrintStream(serverSocket.getOutputStream());
           // output = new PrintWriter(serverSocket.getOutputStream(), true);
          //  inputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            inputStream = new BufferedReader(new InputStreamReader(System.in)); 
            
            //This will wait until a line of text has been sent. skal inn i serverThread
           //     String input = inputStream.readLine(); // KLIKK
             //   System.out.println("You got the input: " + input);
                
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(serverSocket != null && dataInputStream != null && inputStream != null){
            try{
                String input = inputStream.readLine(); // KLIKK
                System.out.println("You got the input: " + input);
            } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
        }

    }
    
    
    
}
