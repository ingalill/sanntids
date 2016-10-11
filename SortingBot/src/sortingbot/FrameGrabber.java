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
      //  frameGrabber = new Thread(this); // create a thread
       // frameGrabber.start(); // start this thread
    }
    
    public FrameGrabber(Camera cam, VideoGrabber vidG){
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.grabber = vidG;
        frameGrabberMode = 1;
        //implement runnable
       // frameGrabber = new Thread(this); // create a thread
      //  frameGrabber.start(); // start this thread
    }
    
    @Override
    public void run(){
        //while(true){
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
            if(frameGrabberMode == 1){
                grabber.putFrame(currentFrame); // legger currentframe inn i grabber. 
            }
            else{
                grabber.putFrame(handler.processFrame(currentFrame)); 
            }
        //}
    }
}
