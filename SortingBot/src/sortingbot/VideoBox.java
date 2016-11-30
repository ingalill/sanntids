/*
 * Works as a storage box
 */
package sortingbot;

/**
 *
 * @author Demy
 */
import java.awt.image.BufferedImage;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class VideoBox{
    
    private Mat frame;
    private boolean available;
    
    public VideoBox(){
        available = false; //make the consumer wait at the beginning
    }
    
    public synchronized void putFrame(Mat frame){
        while (available) {
            // value is beeing read, wait for consumer
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        // put the value in the VideoBox(as storagebox)
        this.frame = frame;
        available = true;
        notifyAll();
    }
    
    public synchronized Mat getFrame(){
        while (!available) {
            // value not available, wait for producer
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        // reading value and resetting flag, wake up other threads (producer)
        available = false;
        notifyAll();
        return this.frame;
    }
}