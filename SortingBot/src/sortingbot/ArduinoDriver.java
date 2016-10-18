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
    
    private final String stop = "w0"; //stop
    private final String forward50 = "w50"; //Move forward at speed 50
    private final String fastForward = "w100"; //Move forward at speed 100
    private final String right70 = "d70"; //Move right at speed 70
    private final String readShortSensor = "vss"; //Read value from the short range sensor
    private final String readLongSensor = "vls"; //Read value from the long range sensor
    
    private boolean objectCaught;
    
    //Todo:
    // Manuel mode
    public ArduinoDriver(ArduinoCommunication com, CommandBox box){
        this.communication = com;
        this.commandBox=box;
        objectCaught=false;
        this.start(); // start this thread
    }
    
    public void run() {   
       while(true){
           communication.SendString(readShortSensor);
           System.out.println(communication.getInput());
           communication.SendString(readShortSensor);
           System.out.println(communication.getInput());
           //getObject();
            while(commandBox.isAutoDrive()){
                seek();
                getObject();
                locateGoal();
                placeObject();
             }
            System.out.println("done");
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
        // Use sensors and camera to go and get the object
        while(!objectCaught){
            System.out.println("in loop");
            communication.SendString(readLongSensor);
            int sensorVal=Integer.parseInt(communication.getInput());
            System.out.println(sensorVal);
            if(sensorVal>120){
                //communication.SendString(fastForward);
                System.out.println(">120");
            }else if((sensorVal>30)&&(sensorVal<120)){
                //communication.SendString(forward50);
                System.out.println("30>v>120");
            }else if(sensorVal<=30){
                System.out.println("<30");
                /*communication.SendString(readShortSensor);
                int shortVal=Integer.parseInt(communication.getInput());
                if(shortVal>6){
                    communication.SendString(forward50);
                }else if(shortVal<6){
                    communication.SendString(stop);
                    objectCaught=true;
                }*/
            }
        }
        
    }
    public void locateGoal(){
        //Find the right area to place current object
    }
    public void placeObject() {
        //Drive the object to its target
        
        //remember to set objectCaught to false again
    }
}