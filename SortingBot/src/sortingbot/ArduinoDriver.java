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
    
    private boolean objectCaught;
    
    private final String cForward = "w50"; //Move forward at speed 50
    private final String cStop="w0"; //Stop
    private final String cRight = "d70"; //Turn right at speed 70
    private final String cLeft = "a70"; //Turn left at speed 70
    private final String cReadSS = "vss"; //Read value from the short range sensor
    private final String cReadLS = "vls"; //Read value from the long range sensor
    
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
           //System.out.println("hei");
           //communication.sendString(right,true);
           String in=communication.sendString(cReadSS,true);
           String in2=communication.sendString(cReadSS,true);
           String in3=communication.sendString(cReadSS,true);
            System.out.println("Input: "+in);
            System.out.println("Input: "+in2);
            System.out.println("Input: "+in3);
            while(commandBox.isAutoDrive()){
                seek();
                if(!commandBox.isAutoDrive()) break;
                getObject();
                if(!commandBox.isAutoDrive()) break;
                locateGoal();
                if(!commandBox.isAutoDrive()) break;
                placeObject();

             }
             //System.out.println("ute");
       }
    }
    public void seek(){
        System.out.println("1");
        //Tells the arduino to turn in a circle
        communication.sendString(cRight,true);
        /*System.out.println("In: "+nothing);
        String in=communication.sendString(readSS,false);
        System.out.println("Input: "+in);*/
        while((!commandBox.getObjectFound())&&(commandBox.isAutoDrive())){
            System.out.println("seeking...");
        }
        communication.sendString(cStop, true);
        System.out.println("object found...");
       
    }
    public void getObject(){
        // Use sensors and camera to go and get the object
        System.out.println("2");
        boolean done=false;
        while(!done){
            // while object not caught and objectFound is still true
             while((commandBox.getObjectFound())&&(!objectCaught)){
                communication.sendString(cForward, true);
                System.out.println("Drive forward");
                if(commandBox.getAdjustedDirection()>4){
                    communication.sendString("r70", true);
                    communication.sendString("l50", true);
                } else if(commandBox.getAdjustedDirection()<-4){
                    communication.sendString("l70", true);
                    communication.sendString("r50", true);
                } else if((commandBox.getAdjustedDirection()<4)&&(commandBox.getAdjustedDirection()>-4)){
                    communication.sendString(cForward, true);
                }
                checkIfCaught();
            }   
            communication.sendString(cStop, true);
            //if object not found and not not caught seek() again.
            if((!commandBox.getObjectFound())&&(!objectCaught)){
                seek();
            }
        }
        
        
    }
    public void locateGoal(){
        //Find the right area to place current object
        System.out.println("3");
    }
    public void placeObject() {
        //Drive the object to its target
        System.out.println("4");
        //Object caught=false;
    }
    public void checkIfCaught(){
        System.out.println("check if caught");
        String in=communication.sendString(cReadSS,false);
        System.out.println("get value");
        int val=Integer.parseInt(in);
        System.out.println(in);
        if(val<=6){
            objectCaught=true;
        } else {
            objectCaught=false;
        }
    }
}