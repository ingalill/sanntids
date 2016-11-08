package sortingbot.server;

import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import java.io.*;
import org.opencv.core.Mat;
import sortingbot.VideoBox;

/**
 * @date 11.10.2016
 * @author inga lill bjolstad
 */
public class Server extends Thread {
    private VideoBox videoBox;

    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter output;

    private static final int maxClients = 10;
    private static final ServerThread[] threads = new ServerThread[maxClients];

    public VideoBox getVideograbber() {
        return videoBox;
    }

    public void setVideoBox(VideoBox videoBox) {
        this.videoBox = videoBox;
    }

    @Override
    public void run() {
        //We need a try-catch because lots of errors can be thrown
        try {
            serverSocket = new ServerSocket(5000);
                System.out.println("Server started at: " + new Date());
            while (true) {

                //Wait for a client to connect
                socket = serverSocket.accept();

                //Create the streams
                output = new PrintWriter(socket.getOutputStream(), true);
            
                //Tell the client that he/she has connected
                output.println("You have connected at: " + new Date());

                //test
                for (int i = 0; i < maxClients; i++) {
                    if (threads[i] == null) {
                        threads[i] = new ServerThread(socket, videoBox);
                        new Thread(threads[i]).start();
                        break;
                    }
                    if (i == maxClients) {
                        try (PrintStream printSt = new PrintStream(socket.getOutputStream())) {
                            printSt.println("Server is busy, come back later");
                        }
                        socket.close();
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("Error med tilkobling: " + exception);
        }
    }

} // end of class

