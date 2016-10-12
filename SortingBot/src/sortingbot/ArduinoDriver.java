/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

import java.io.BufferedReader;
import java.io.OutputStream;

/**
 *
 * @author MalenB
 */
public class ArduinoDriver extends Thread{
     
    private static BufferedReader input;
    private static OutputStream output;
    private final ArduinoCommunication communication;
    private final CommandBox commandBox;
    private boolean autoDrive=true;
    
    private String forward50 = "w50"; //Move forward at speed 50
    private String right70 = "d70"; //Move right at speed 70

    public ArduinoDriver(ArduinoCommunication com, CommandBox box){
        this.communication = com;
        this.commandBox=box;
    }
    
    public void run() {   
        while(autoDrive){
            seek();
            getObject();
            locateTarget();
        }
    }
    public void seek(){
        //Checks if an object has not been found
        while(!commandBox.getObjectFound()){
            //Tells the arduino to turn in a circle
            communication.SendString(right70);
            
        }
    }
    public void getObject(){
        // Use sensors and camera to go and getthe object
        
    }
    public void locateTarget(){
        //Find the right area to place current object
    }
    public void placeObject() {
        //Drive the object to its target
    }
}