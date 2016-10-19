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
    
    /*
    Skal i server
    Take an Mat and convert it to an BufferedImage
    @Param Mat input.
    @Return BufferedImage output.
    *
    public static BufferedImage matToImg(Mat in){
        BufferedImage out;
        byte[] data = new byte[320 *240 *(int)in.elemSize()];
        int type;
        in.get(0,0,data);
        
        if(in.channels() == 0){
         type = BufferedImage.TYPE_BYTE_GRAY;  
        }
        else{
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        out = new BufferedImage(320,240,type);
        out.getRaster().setDataElements(0,0,320,240,data);
        return out;
    }*/
    /*
    Clienten
    *
    public static Mat imgToMat(BufferedImage in){
        Mat out;
        byte[] data;
        int r,g,b;
        
        if(in.getType() == BufferedImage.TYPE_INT_RGB){
            out = new Mat(240,320, CvType.CV_8UC3);
            data = new byte[320*240 * (int)out.elemSize()];
            int[] databuffer = in.getRGB(0,0,320,240,null,0,320);
            for(int i=0; i<databuffer.length; i++){
                data[i*3] = (byte) ((databuffer[i] >> 16) & 0xFF);
                data[i*3+1] = (byte) ((databuffer[i]>> 8) & 0xFF);
                data[i*3+2] = (byte) ((databuffer[i] >> 0) & 0xFF);
            }         
        }
        else {
            out = new Mat(240,320,CvType.CV_8UC3);
            data = new byte[320*240 * (int)out.elemSize()];
            int[] databuffer = in.getRGB(0,0,320,240,null,0,320);
            for(int i=0; i<databuffer.length; i++){
                r = (byte) ((databuffer[i] >> 16) & 0xFF);
                g = (byte) ((databuffer[i] >> 8) & 0xFF);
                b = (byte) ((databuffer[i] >> 0) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 *g) + (0.07 *b));
            }
        }
        out.put(0,0,data);
        return out;
    }*/
    
} // end of class
