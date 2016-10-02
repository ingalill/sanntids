/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author Demy
 */

//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

//import org.opencv.highgui.VideoCapture;
import org.opencv.core.Mat;

public class FrameGrabber implements Runnable {
    
    private Thread frameGrabber;
    private VideoCapture camera;
    private VideoGrabber grabber;
    private ImageHandler handler;
    private Mat currentFrame;
    private int frameGrabberMode;//is this thread going to generate a video stream(1) or process an image(0)?
    private Camera cam;
    
    public FrameGrabber(Camera cam, ImageHandler imgH){
        this.camera = cam.getCam();
        this.handler = imgH;
        frameGrabberMode = 0;
        //implement runnable
        frameGrabber = new Thread(this); // create a thread
        frameGrabber.start(); // start this thread
    }
    
    public FrameGrabber(Camera cam, VideoGrabber vidG){
        this.camera = cam.getCam();
        this.grabber = vidG;
        frameGrabberMode = 1;
        //implement runnable
        frameGrabber = new Thread(this); // create a thread
        frameGrabber.start(); // start this thread
    }
    
    @Override
    public void run(){
        
        if (camera.isOpened())
        {
            try
            {
//                if(camera.grab())
//                {
//                    camera.retrieve(currentFrame);
//                }
//                else
//                {
//                    System.out.print("Y U NO WORK?!");
//                }
                //this.camera.read(currentFrame);
                currentFrame = cam.readFrame();
            }
            catch (Exception e)
            {
                // log the (full) error
                System.err.print("ERROR, could not retrieve an image from the camera\n");
                e.printStackTrace();
            }
        }
        if(frameGrabberMode == 1){
            grabber.putFrame(currentFrame);
        }
        else{
            handler.processFrame(currentFrame);
        }
    }
}
