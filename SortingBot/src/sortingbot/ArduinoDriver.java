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
    
    private final String cForward = "w50 "; //Move forward at speed 50
    private final String cStop="w0 "; //Stop
    private final String cBackward = "s50 "; //Move backward at speed 50
    private final String cRight = "d70 "; //Turn right at speed 70
    private final String cLeft = "a70 "; //Turn left at speed 70
    private final String cReadSS = "vss "; //Read value from the short range sensor
    private final String cReadLS = "vls "; //Read value from the long range sensor
    
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
            while(commandBox.isAutoDrive()){
                seek();
                if(!commandBox.isAutoDrive()) break;
                getObject();
                if(!commandBox.isAutoDrive()) break;
                locateGoal();
                if(!commandBox.isAutoDrive()) break;
                placeObject();

             }
             //System.out.println("Manual mode");
       }
    }
    public void seek(){
        System.out.println("1");
        commandBox.setState(1);
        //Tells the arduino to turn in a circle
        communication.sendCommand(cRight);
        while((!commandBox.getObjectFound())&&(commandBox.isAutoDrive()));
            //System.out.println("seeking...");
        
        communication.sendCommand(cStop);
        System.out.println("object found...");
       
    }
    public void getObject(){
        // Use sensors and camera to go and get the object
        System.out.println("2");
        boolean done=false;
        while((!done)&&(commandBox.isAutoDrive())){
            commandBox.setState(2);
            // while object not caught and objectFound is still true, object found is set false when
            // the object no longer is in the camera view
             while((commandBox.getObjectFound())&&(!objectCaught)&&(commandBox.isAutoDrive())){
                if(commandBox.getAdjustedDirection()>4){
                    communication.sendCommand("r70 ");
                    communication.sendCommand("l50 ");
                    //System.out.println("Turn left");
                } else if(commandBox.getAdjustedDirection()<-4){
                    communication.sendCommand("l70 ");
                    communication.sendCommand("r50 ");
                    //System.out.println("Turn right");
                } else if((commandBox.getAdjustedDirection()<4)&&(commandBox.getAdjustedDirection()>-4)){
                    communication.sendCommand(cForward);
                    //System.out.println("Drive forward");
                }
                checkIfCaught();
            }  
             System.out.println("object caught");
            communication.sendCommand(cStop);
            //if object not found and not not caught, seek() again.
            if((!commandBox.getObjectFound())&&(!objectCaught)){
                seek();
            } else {
                done=true;
            }
        }
        
        
    }
    public void locateGoal(){
        //Find the right area to place current object
        System.out.println("3");
        boolean located=false;
        while(!located){
            commandBox.setState(3);
            communication.sendCommand(cForward);
            communication.sendCommand("r70 ");
            while((!commandBox.isGoalFound()&&(objectCaught)&&(commandBox.isAutoDrive()))){
                checkIfCaught();
            }
            communication.sendCommand(cStop);
            if(!objectCaught){
                seek();
                getObject();
            } else if((objectCaught)&&(commandBox.isGoalFound())){
                located=true;
            }
        }
        System.out.println("goal located");
    }
    public void placeObject() {
        //Drive the object to its target
        System.out.println("4");
        
        boolean goalReached=false;
        while((!goalReached)&&(commandBox.isAutoDrive())){
            commandBox.setState(4);
            int distanceFromGoal;
            // while object i still caught and goal is still in view
           while((objectCaught)&&(commandBox.isGoalFound())&&(commandBox.isAutoDrive())){
               
                if(commandBox.getAdjustedDirection()>4){
                    communication.sendCommand("r70 ");
                    communication.sendCommand("l50 ");
                    //System.out.println("Turn left");
                } else if(commandBox.getAdjustedDirection()<-4){
                    communication.sendCommand("l70 ");
                    communication.sendCommand("r50 ");
                    //System.out.println("Turn right");
                } else if((commandBox.getAdjustedDirection()<4)&&(commandBox.getAdjustedDirection()>-4)){
                    communication.sendCommand(cForward);
                    distanceFromGoal=Integer.parseInt(communication.getInput(cReadLS));
                    //System.out.println("Drive forward");
                    System.out.print("Distance from goal: ");
                    System.out.println(distanceFromGoal);
                    if(distanceFromGoal<20) break;
                }
                checkIfCaught();
            }
           
           if(!objectCaught){
               seek();
               getObject();
               locateGoal();
           } else if((commandBox.isGoalFound())&&(objectCaught)){
               System.out.println("goal reached");
               communication.sendCommand(cBackward);
               while((true)&&(commandBox.isAutoDrive())){
                    distanceFromGoal=Integer.parseInt(communication.getInput(cReadLS));
                    if(distanceFromGoal>40) break;
               }
               goalReached=true;
           }
        }
        objectCaught=false;
    }
    public void checkIfCaught(){
        System.out.println("check if caught");
        String in=communication.getInput(cReadSS);
        int val=Integer.parseInt(in);
        if(val<=6){
            objectCaught=true;
        } else {
            objectCaught=false;
        }
    }
}