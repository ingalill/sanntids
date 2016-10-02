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
import org.opencv.core.Mat;

public class VideoGrabber {
    
    private Mat frame;
    private boolean available;
    
    public VideoGrabber(){
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
        // put the value in the VideoGrabber(as storagebox)
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
