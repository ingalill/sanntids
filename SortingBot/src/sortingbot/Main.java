/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
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
    private static Server server;
    private static Timer timer;
    private static ArduinoCommunication communication;
    private static ArduinoDriver driver;
    private static CommandBox commandBox;
    
    public static void main(String[] args) {
        
       long timedelay = 500; // repeat every 500ms
       long delay     =1000;    // starts after 1 seond. 
       timer = new Timer();
           
        //explaination of classes:
        // Camera() : class that starts a connection with a camera
        // FrameGrabber() : a thread that takes a single frame from the opened camera for each run (can be used for both camera feedback to the gui and image processing)
        // ImageHandler() : processes an image(in our case a single frame) and finds the specified physical object from the image
        // Thresholds() : an enum that holds the values for specified colors for use with OpenCV
        // Server() : creates sockets for each client and sends info
        // VideoBox() : Handles the total input stream from the camera by use of a FrameGrabber class and sends the video stream to the client
        // ArduinoHandler() : creates an Serial connection with arduino board
        // MORE TO COME
        
        //load the necessary libraries:
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.setProperty("java.library.path", "C:/OpenCv 3.1/build/java/x64/opencv_java310.dll");
        //System.setProperty("java.library.path", "/Users/ingalill/Documents/opencv/opencv_libs");
        //System.setProperty("java.library.path","C:/Users/Aleksander/Desktop/Skole/2016-2017/Sanntid programering/sanntids/Arduino/ArduinoJavaTest/rxtxSerial.dll");
        //start camera from Camera class
        camera = new Camera();
        //Starting communication with the arduino
        communication = new ArduinoCommunication();
        //Creating  a new command box for handling communication between ArduinoDriver
        // and the rest of the project
        commandBox= new CommandBox();
        //Creates the arduino driver
        driver = new ArduinoDriver(communication,commandBox);
        //start the arduino driver thread
        driver.start();
        //start videograbber as an storagebox for frames
        videoBox = new VideoBox();
        //start imageHandler to prosess images
        handler = new ImageHandler(commandBox);
        //start a framegrabber for the gui
        //vGrabber = new FrameGrabber(camera,handler,grabber); //for use for testing the processing
        vGrabber = new FrameGrabber(camera,videoBox);
        //odroidGrabber = new FrameGrabber(camera,handler,videoBox,true);
        //odroidGrabber = new FrameGrabber(camera,handler);
        
        // Start the server
        server = new Server();
        server.setVideoBox(videoBox);
        server.setCommandBox(commandBox);
        server.start();
        
        //Start framegrabber with timed intervals
        //timer.scheduleAtFixedRate(odroidGrabber, delay*0, timedelay);
        
        //start gui
//        java.awt.EventQueue.invokeLater(
//            new Runnable() {
//            @Override
//            public void run() {
//                new Gui(videoBox).setVisible(true);
//            }
//        }); 
        
        
        //REMEMBER: .currentThread().setPriority(Thread.MAX_PRIORITY); //ask demy why
        
        //TODO for the main() class
        //2. start a network server
        //3. start a connection with arduino
        //4. create a timer
        //5. create an ImageHandler() object
        //6. create a framegrabber object for image processing and set its time interval
        //7. create a framegrabber object for video stream to clients
    }
    
}
