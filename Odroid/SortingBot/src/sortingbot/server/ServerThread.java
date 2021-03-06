/*
 * http://stackoverflow.com/questions/24182610/java-socket-server-client-to-client-file-transfer 
 * http://www.codeproject.com/Questions/898073/java-How-to-send-a-multiple-images-over-socket?arn=9 
 */
package sortingbot.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import sortingbot.CommandBox;
import sortingbot.VideoBox;

/**
 * @date 18.10.2016
 * @author inga lill bjolstad
 */
public class ServerThread implements Runnable {

    private DataInputStream dataInputStream = null;
    private Socket serverSocket = null;
    private BufferedReader infromClient;
    private DataOutputStream outputBuffer;

    // private PrintStream printStream; // write out to itself.
    private VideoBox videoBox;

    private HashMap<String, ServerCommand> commands;
    private VideoCommand videoCommand;
    private CommandBox commandBox;
    public boolean playPause;

    /**
     * Constructor
     *
     * @param serverSocket
     * @param videoBox
     */
    public ServerThread(Socket serverSocket, VideoBox videoBox, CommandBox commandBox) {
        this.serverSocket = serverSocket;
        this.videoBox = videoBox;
        this.commandBox = commandBox;
        commands = new HashMap<>();
        addCommands();
        playPause = false;
    }

    @Override
    public void run() {

        try {
            outputBuffer = new DataOutputStream(serverSocket.getOutputStream());
            dataInputStream = new DataInputStream(serverSocket.getInputStream()); // denne skal brukes
            infromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            if (serverSocket != null && dataInputStream != null) {
                while (true) {
                    try {

                        /* int type = 1;
                         System.out.println("Sending type " + type + " to client");
                         outputBuffer.writeInt(type); //  Write type of the message (1 = image)
                         outputBuffer.flush();
                         ImageIO.write(matToImg(videoBox.getFrame()), "png", outputBuffer);
                         //printStream.flush();
                         // SEND SIZE OF THE PACKET!
                         */
                        String line = infromClient.readLine();
                        CommandParser parser = new CommandParser(line);

                        String command = parser.getName(); // eks move
                        String[] arguments = parser.getArgArray();     // eks left   
                        
                        //System.out.println("Command: " + command + " " + parser.getAllArgs());
                        ServerCommand cmd = commands.get(command);
                        if (cmd != null) {
                            String reply = cmd.process(command, arguments);
                            //System.out.println("Got command, reply = " + reply + "\n");
                            // Handle special case - next image frame
                            
                            if (cmd == videoCommand && "nextframe".equals(reply)) {
                                // Send the next frame to the client
                                // TODO - send size of next frames
                                Mat imgMat = videoBox.getFrame();
                                byte[] imgBytes = matToByteArray(imgMat);
                                int sizeInBytes = imgBytes.length;
                                int imgWidth = imgMat.width();
                                int imgHeight = imgMat.height();
//                                BufferedImage img = matToImg(imgMat);
//                                ByteArrayOutputStream tmp = new ByteArrayOutputStream();                                
//                                tmp.close();
//                                Integer imageSize = tmp.size();
                                outputBuffer.writeBytes("nextframe " + sizeInBytes
                                        + " " + imgWidth + " " + imgHeight);
                                outputBuffer.writeBytes("\n");
                                outputBuffer.write(imgBytes);
                                outputBuffer.flush();
                            }
                            else {
                                outputBuffer.writeBytes(reply);
                                outputBuffer.writeBytes("\n");
                            }
                        }

                        //Thread.sleep(1000);
                    } catch (IOException e) {
                        System.err.println("IOException:  " + e);
                    } 
//                    catch (InterruptedException ex) {
//                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    } // end of run

    /**
     * Add commands to the hashmap
     */
    public void addCommands() {
        videoCommand = new VideoCommand();
        videoCommand.setCommandbox(commandBox);
        commands.put("control", new ControlCommand());
        commands.put("video", videoCommand);
        commands.put("frame", new FrameCommand());
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

    public static byte[] matToByteArray(Mat in) {
        byte[] data = new byte[in.height() * in.width() * (int) in.elemSize()];
        in.get(0, 0, data);
        return data;
    }

}
