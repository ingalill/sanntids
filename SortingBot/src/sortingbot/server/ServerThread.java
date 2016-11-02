/*
* http://stackoverflow.com/questions/24182610/java-socket-server-client-to-client-file-transfer 
* http://www.codeproject.com/Questions/898073/java-How-to-send-a-multiple-images-over-socket?arn=9 
*/
package sortingbot.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import sortingbot.VideoBox;
import javax.imageio.ImageIO;

/**
 * @date 18.10.2016
 * @author inga lill bjolstad
 */
public class ServerThread implements Runnable {
    // send a frame from the server to the client.
    // from videograbber/videobox.
    // get a mat and send it to the client
    // get Frame from server and send it to client

    private DataInputStream dataInputStream = null;
    private Socket serverSocket = null;
    //private BufferedReader infromClient;
    private DataOutputStream outputBuffer;
   // private PrintStream printStream; // write out to itself.
    private CommandHandler command = new CommandHandler();

    private VideoBox videoBox;
    
    // test
    DatagramPacket packet;

    public ServerThread(Socket serverSocket, VideoBox videoBox) {
        this.serverSocket = serverSocket;
        this.videoBox = videoBox;
    }

    @Override
    public void run() {

        String line ="";
        
        try {

            outputBuffer = new DataOutputStream(serverSocket.getOutputStream());
            dataInputStream = new DataInputStream(serverSocket.getInputStream());
            //infromClient =  new BufferedReader(new InputStreamReader(System.in));
            
      
            if(command.check(line).equalsIgnoreCase("quit")){
                System.out.println("goodbye");
            } else{
                System.out.println(command.check(line));
            }
            
            
            if (serverSocket != null && dataInputStream != null) {
                try { // send frames skal inn her
                    
                    
                   //GetFrame klassen?
                    int type = 1;
                    System.out.println("Sending type " + type + " to client");
                    outputBuffer.writeInt(type); //  Write type of the message (1 = image)
                    outputBuffer.flush();
                    // !!! ImageIO.write(matToImg(videoBox.getFrame()), "png", outputBuffer);
                    //printStream.flush();
                    // SEND SIZE OF THE PACKET!
                    int size = 2; // fiks

                    Thread.sleep(1000);
                } catch (IOException e) {
                    System.err.println("IOException:  " + e);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
  

    /*
    *Take an Mat and convert it to an BufferedImage
    *@Param Mat input.
    *@Return BufferedImage output.
     */
    public static BufferedImage matToImg(Mat in) {
        BufferedImage out;
        byte[] data = new byte[in.height() * in.width() * (int) in.elemSize()];
        int type;
        in.get(0, 0, data);

        if (in.channels() == 0) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        out = new BufferedImage(in.height(), in.width(), type);
        out.getRaster().setDataElements(0, 0, in.height(), in.width(), data);
        return out;
    }

}
