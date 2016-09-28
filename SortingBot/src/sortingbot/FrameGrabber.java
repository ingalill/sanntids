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

public class FrameGrabber extends Thread {
    
    private VideoCapture camera;
    private VideoGrabber grabber;
    private ImageHandler handler;
    private Mat currentFrame;
    private int frameGrabberMode;//is this thread going to generate a video stream(1) or process an image(0)?
    
    public FrameGrabber(Camera cam, ImageHandler imgH){
        this.camera = cam.getCam();
        this.handler = imgH;
        frameGrabberMode = 0;
    }
    
    public FrameGrabber(Camera cam, VideoGrabber vidG){
        this.camera = cam.getCam();
        this.grabber = vidG;
        frameGrabberMode = 1;
    }
    
    @Override
    public void run(){
        camera.read(currentFrame);
        if(frameGrabberMode == 1){
            grabber.sendFrame(currentFrame);
        }
        else{
            handler.processFrame(currentFrame);
        }
    }
}
