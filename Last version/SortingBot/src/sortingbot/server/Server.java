package sortingbot.server;

import java.net.*;
import java.util.*;
import java.io.*;
import sortingbot.CommandBox;
import sortingbot.VideoBox;

/**
 * @date 11.10.2016
 * @author inga lill bjolstad
 */
public class Server extends Thread {

    private VideoBox videoBox;
    private CommandBox commandBox;
    private ServerSocket serverSocket;
    private Socket socket;
    private static final int maxClients = 10;
    private static final ServerThread[] threads = new ServerThread[maxClients];

    /**
     * Get videoBox
     * @return videoBox
     */
    public VideoBox getVideograbber() {
        return videoBox;
    }

    /**
     * Set videoBox
     * @param videoBox
     */
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

                // makes a new thread as long as there are not max numbers of clients for clients.
                for (int i = 0; i < maxClients; i++) {
                    if (threads[i] == null) {
                        threads[i] = new ServerThread(socket, videoBox, commandBox); // new thread to handle the connection 
                        new Thread(threads[i]).start();
                        break;
                    }
                    // to many users/clients prints statement
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

    /*
    * Set command for commandBox
     */
    public void setCommandBox(CommandBox commandBox) {
        this.commandBox = commandBox;
    }

} // end of class

