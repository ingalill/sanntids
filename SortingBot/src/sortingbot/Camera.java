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
import org.opencv.highgui.VideoCapture;

public class Camera {
    
    private VideoCapture camera;
    
    public void startCamera(){
        this.camera = new VideoCapture(-1);
        if (!camera.isOpened())
        {
            System.out.println("Camera hasnt been found or failed to start...");
        }
    }
    
    //synchronized because multiple threads are gonna ask for acces to the camera
    public synchronized VideoCapture getCam(){
        return this.camera;
    }
}
