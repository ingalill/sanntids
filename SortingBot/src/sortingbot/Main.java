/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author Demy Patrick Gielesen
 */
import org.opencv.core.Core;

public class Main {

    /**
     * @param args the command line arguments
     */
    private static Camera camera;
    private static VideoGrabber grabber;
    private static FrameGrabber vGrabber;//framegrabber for video
    
    public static void main(String[] args) {
        //explaination of classes:
        // Camera() : class that starts a connection with a camera
        // FrameGrabber() : a thread that takes a single frame from the opened camera for each run (can be used for both camera feedback to the gui and image processing)
        // ImageHandler() : processes an image(in our case a single frame) and finds the specified physical object from the image
        // Thresholds() : an enum that holds the values for specified colors for use with OpenCV
        // Server() : creates sockets for each client and sends info
        // VideoGrabber() : Handles the total input stream from the camera by use of a FrameGrabber class and sends the video stream to the client
        // ArduinoHandler() : creates an Serial connection with arduino board
        // MORE TO COME
        
        
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        //TODO for the main() class
        //1. start camera from Camera class
        camera = new Camera();
        camera.startCamera();
        //start videograbber as frame storagebox
        grabber = new VideoGrabber();
        //start a framegrabber for the gui
        vGrabber = new FrameGrabber(camera,grabber);
        //start gui
        java.awt.EventQueue.invokeLater(
            new Runnable() {
            public void run() {
                new Gui(grabber).setVisible(true);
            }
        });
        
        
        
        //REMEMBER: .currentThread().setPriority(Thread.MAX_PRIORITY); //ask demy why
        
        
        //2. start a network server
        //3. start a connection with arduino
        //4. create a timer
        //5. create an ImageHandler() object
        //6. create a framegrabber object for image processing and set its time interval
        //7. create a framegrabber object for video stream to clients
    }
    
}
