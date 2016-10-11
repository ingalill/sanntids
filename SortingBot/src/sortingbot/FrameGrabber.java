/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author Demy og inga
 */


import java.util.TimerTask;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Mat;

public class FrameGrabber extends TimerTask {
    
    private Thread frameGrabber;
    private VideoCapture camera;
    private VideoGrabber grabber;
    private ImageHandler handler;
    private Mat currentFrame;
    private int frameGrabberMode;//is this thread going to generate a video stream(1) or process an image(0)?
    
    public FrameGrabber(Camera cam, ImageHandler imgH, VideoGrabber vidG){
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.handler = imgH;
        this.grabber = vidG;
        frameGrabberMode = 0;
        //implement runnable
        frameGrabber = new Thread(this); // create a thread
        frameGrabber.start(); // start this thread
    }
    
    public FrameGrabber(Camera cam, ImageHandler imgH, VideoGrabber vidG, boolean Debug){
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.handler = imgH;
        this.grabber = vidG;
        frameGrabberMode = 1;
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        //implement runnable
        //frameGrabber = new Thread(this); // create a thread
        // frameGrabber.start(); // start this thread
    }
    
    public FrameGrabber(Camera cam, VideoGrabber vidG){
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.grabber = vidG;
        frameGrabberMode = 2;
        //implement runnable
       // frameGrabber = new Thread(this); // create a thread
      //  frameGrabber.start(); // start this thread
    }
    
    @Override
    public void run(){
        
        switch (frameGrabberMode) {
            case 0: //Gui
                while(true){
                    grabber.putFrame(getFrame()); // legger currentframe inn i grabber. 
                }
            case 1: //Debug
                grabber.putFrame(handler.processFrame(getFrame()));
                break;
            default: //odroid handling
                //TODO
                break;
        }
    }
    
    private Mat getFrame(){
        if (camera.isOpened())
        {
            try
            {
                camera.read(currentFrame); //read from the video stream and downloads to currentframe.
            }
            catch (Exception e)
            {
                // log the (full) error
                System.err.print("ERROR, could not retrieve an image from the camera\n");
                e.printStackTrace();
            }
        }
        return currentFrame;
    }
}
