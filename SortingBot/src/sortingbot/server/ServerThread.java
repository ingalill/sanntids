/*
 * -Djava.library.path="C:\Users\ingab\Desktop\inga\OpenCv 3.1\build\java\x64;C:\Users\ingab\Desktop\inga\sanntids\Arduino\ArduinoJavaTest"
* http://stackoverflow.com/questions/24182610/java-socket-server-client-to-client-file-transfer 
http://www.codeproject.com/Questions/898073/java-How-to-send-a-multiple-images-over-socket?arn=9 
 */
package sortingbot.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import sortingbot.VideoBox;
import javax.imageio.ImageIO;

/**
 * @date 18.10.2016
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
    private OutputStream outputBuffer;
    //  private static boolean closed = false;

    private VideoBox videograbber;

    public ServerThread(Socket serverSocket, ServerThread[] threads) {
        this.serverSocket = serverSocket;
        this.threads = threads;
        maxServerCount = threads.length;
    }

    @Override
    public void run() {
        int maxServerCount = this.maxServerCount;
        ServerThread[] threads = this.threads;

        try {

            outputBuffer = serverSocket.getOutputStream();
            dataInputStream = new DataInputStream(serverSocket.getInputStream());
            printStream = new PrintStream(serverSocket.getOutputStream());
            // output = new PrintWriter(serverSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            //   inputStream = new BufferedReader(new InputStreamReader(System.in)); 

            if (serverSocket != null && dataInputStream != null && inputStream != null) {
                try { // send frames skal inn her 

                    ImageIO.write(matToImg(videograbber.getFrame()), "png", printStream);
                    printStream.flush();
                    //This will wait until a line of text has been sent.
                    String input = inputStream.readLine();
                    System.out.println("You got the input: " + input);
                } catch (IOException e) {
                    System.err.println("IOException:  " + e);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if(serverSocket != null && dataInputStream != null && inputStream != null){
//            try{ // send frames skal inn her 
//                //This will wait until a line of text has been sent.
//                outputBuffer.write(videograbber.getFrame());
//                videograbber.getFrame();
//                String input = inputStream.readLine(); 
//                System.out.println("You got the input: " + input);
//            } catch (IOException e) {
//        System.err.println("IOException:  " + e);
//      }
//        }
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
