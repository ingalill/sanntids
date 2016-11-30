package sortingbot;

/**
 *
 * @author Demy
 */


import java.util.TimerTask;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Mat;

public class FrameGrabber extends TimerTask {
    
    private Thread frameGrabber;
    private VideoCapture camera;
    private Camera cam;
    private VideoBox box;
    private ImageHandler handler;
    private Mat currentFrame;
    private int frameGrabberMode;//is this thread going to generate a video stream(1) or process an image(0)?
    
    //a framegrabber to be used for sending storing images in a videobox only
    public FrameGrabber(Camera cam, VideoBox vidG){
        this.cam = cam;
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.box = vidG;
        frameGrabberMode = 1;
        //implement runnable
        frameGrabber = new Thread(this); // create a thread
        frameGrabber.start(); // start this thread
    }
    
    //a framegrabber for debugging, it stores processed images in the videobox
    public FrameGrabber(Camera cam, ImageHandler imgH, VideoBox vidG, boolean Debug){
        this.cam = cam;
        this.camera = cam.getCam();
        currentFrame = new Mat();
        this.handler = imgH;
        this.box = vidG;
        frameGrabberMode = 2;
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
    
    //a framegrabber that processes the images and calculates coordinates
    public FrameGrabber(Camera cam, ImageHandler imgH){
        this.cam = cam;
        this.camera = cam.getCam();
        this.handler = imgH;
        currentFrame = new Mat();
        frameGrabberMode = 0;
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
    
    @Override
    public void run(){
        
        switch (frameGrabberMode) {
            case 1: //Gui
                while(true){
                    box.putFrame(getFrame()); // legger currentframe inn i grabber. 
                }
            case 2: //Debug
                box.putFrame(handler.processFrame(getFrame()));
                break;
            default: //odroid handling
                handler.processFrame(getFrame());
                break;
        }
    }
    
    //Get the next frame from the camera
    private Mat getFrame(){
        if (camera.isOpened())
        {
            try
            {
                currentFrame = cam.getFrame();
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
