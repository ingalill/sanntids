/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 * @author Demy Patrick Gielesen og Inga Lill Bjølstad
 */
import java.util.Timer;
import org.opencv.core.Core;
import sortingbot.server.Server;

public class Main {

    /**
     * @date test
     * @param args the command line arguments
     */
    private static Camera camera;
    private static VideoBox videoBox;
    private static ImageHandler handler;
    private static FrameGrabber vGrabber;//framegrabber for video
    private static FrameGrabber odroidGrabber;
    private static FrameGrabber debugGrabber;
    private static Server server;
    private static Timer timer;
    private static ArduinoCommunication communication;
    private static ArduinoDriver driver;
    private static CommandBox commandBox;

    public static void main(String[] args) {
        //load the necessary libraries:
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        long timedelay = 1; // repeat every 500ms
        long delay = 0;    // starts after 1 seond. 
        timer = new Timer();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        //Creating  a new command box for handling communication between ArduinoDriver
        // and the rest of the project
        commandBox = new CommandBox();

        //start videograbber as an storagebox for frames
        videoBox = new VideoBox();

        // Start the server
        server = new Server();
        server.setVideoBox(videoBox);
        server.setCommandBox(commandBox);
        server.start();
        
        //explaination of classes:
        // Camera() : class that starts a connection with a camera
        // FrameGrabber() : a thread that takes a single frame from the opened camera for each run (can be used for both camera feedback to the gui and image processing)
        // ImageHandler() : processes an image(in our case a single frame) and finds the specified physical object from the image
        // Thresholds() : an enum that holds the values for specified colors for use with OpenCV
        // Server() : creates sockets for each client and sends info
        // VideoBox() : Handles the total input stream from the camera by use of a FrameGrabber class and sends the video stream to the client
        // ArduinoHandler() : creates an Serial connection with arduino board

        //start camera from Camera class
        camera = new Camera();
        //Starting communication with the arduino
        communication = new ArduinoCommunication();

        //Creates the arduino driver
        driver = new ArduinoDriver(communication, commandBox);
        //start the arduino driver thread
        driver.start();
        //start imageHandler to prosess images
        handler = new ImageHandler(commandBox);
        //start a framegrabber for the gui
        vGrabber = new FrameGrabber(camera,videoBox);
        timer.schedule(vGrabber, 0); //Start the videograbber as timerTask, instead of casting it as thread
//        debugGrabber = new FrameGrabber(camera, handler, videoBox, true);     //debug
        odroidGrabber = new FrameGrabber(camera,handler);

        //Start framegrabber with timed intervals
        timer.scheduleAtFixedRate(odroidGrabber, delay, timedelay);
//        timer.scheduleAtFixedRate(debugGrabber, delay, timedelay);            //debug

        //Debug GUI
//        java.awt.EventQueue.invokeLater(
//                new Runnable() {
//            @Override
//            public void run() {
//                new Gui(videoBox).setVisible(true);
//            }
//        });

    }

}
