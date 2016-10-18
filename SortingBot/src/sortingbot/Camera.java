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
//import org.opencv.highgui.VideoCapture;
//import org.opencv.imgcodecs.Imgcodecs;


import org.opencv.videoio.VideoCapture;
//import org.opencv.core.Mat;


public class Camera {
    
    private VideoCapture camera;
    private boolean cameraReady;
    
    public Camera(){
        cameraReady = false;
        startCamera();
    }
    
    private void startCamera(){
        this.camera = new VideoCapture(0); //-1 for odroid.
        if (!camera.isOpened())
        {
            System.err.println("Camera hasnt been found or failed to start...");             
            System.exit(1);//stop the program 
        }
        cameraReady = true;
    }
    
    //synchronized because multiple threads are gonna ask for acces to the camera
    public synchronized VideoCapture getCam(){
        if(!cameraReady){//make threads wait until the camera has been started 
            try{
                wait();
            }
            catch(Exception e){
                System.out.print("Thread couldnt wait!");
            }
        }
        else{
            return this.camera;
        }
        return null;
    }
}
