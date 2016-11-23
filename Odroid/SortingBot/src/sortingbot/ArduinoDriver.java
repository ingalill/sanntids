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
     
    private final ArduinoCommunication communication;
    private final CommandBox commandBox;
    
    private boolean objectCaught;
    private String direction;
    private boolean restart;
    
    private final String cForward = "w100 "; //Move forward 
    private final String cStop="w0 "; //Stop
    private final String cBackward = "s100 "; //Move backward 
    private final String cRight = "d120 "; //Turn right 
    private final String cLeft = "a120 "; //Turn left 
    private final String cReadSS = "vss "; //Read value from the short range sensor
    private final String cReadLS = "vls "; //Read value from the long range sensor
    private final String cRightFast="r120 "; //Set right wheels to full speed
    private final String cRightSlow="r100 "; //Set right wheels to standard speed
    private final String cLeftFast="l120 "; //Set left wheels to full speed
    private final String cLeftSlow="l100 "; //Set left wheels to standard speed
    
    //Todo:
    // Manuel mode
    public ArduinoDriver(ArduinoCommunication com, CommandBox box){
        this.communication = com;
        this.commandBox=box;
        objectCaught=false;
        direction="";
        restart=false;
        //this.start(); // start this thread
    }
    
    public void run() {   
       while(true){
            while(commandBox.isAutoDrive()){
                restart=false;
                seek();
                if(!commandBox.isAutoDrive()) break;
                getObject();
                if(!commandBox.isAutoDrive()) break;
                locateGoal();
                if(!commandBox.isAutoDrive()) break;
                placeObject();

             }
             //System.out.println("Manual mode");
            direction=commandBox.getDirection();
            System.out.print("Direction: ");
            System.out.println(direction);
            manuelDrive(direction);
       }
    }
    public void seek(){
        System.out.println("stage: 1");
        commandBox.setState(1);
        //Tells the arduino to turn in a circle while the object is not found
        communication.sendCommand(cLeft);
        while((!commandBox.getObjectFound())&&(commandBox.isAutoDrive()));
        communication.sendCommand(cStop);
        System.out.println("object found...");
       
    }
    public void getObject(){
        // Use sensors and camera to drive to the object
        System.out.println("stage: 2");
        boolean done=false;
        commandBox.setState(2);
        while((!done)&&(commandBox.isAutoDrive())&&(!restart)){
            // while object is not caught and objectFound is still true, object found is set false when
            // the object no longer is in the camera view
             while((commandBox.getObjectFound())&&(!objectCaught)&&(commandBox.isAutoDrive())){
                if(commandBox.getAdjustedDirection()>4){
                    communication.sendCommand(cLeftFast);
                    communication.sendCommand(cRightSlow);
                    //System.out.println("Turn left");
                } else if(commandBox.getAdjustedDirection()<-4){
                    communication.sendCommand(cRightFast);
                    communication.sendCommand(cLeftSlow);
                    //System.out.println("Turn right");
                } else if((commandBox.getAdjustedDirection()<4)&&(commandBox.getAdjustedDirection()>-4)){
                    communication.sendCommand(cForward);
                    //System.out.println("Drive forward");
                }
                checkIfCaught();
            }  
//             System.out.println("object caught");
            communication.sendCommand(cStop);
            //if object not found and not not caught, seek() again.
            if((!commandBox.getObjectFound())&&(!objectCaught)){
                restart=true;
            } else {
                done=true;
            }
        }
        
        
    }
    public void locateGoal(){
        //Find the right area to place current object
        System.out.println("stage: 3");
        boolean located=false;
        while((!located)&&(!restart)){
            commandBox.setState(3);
            communication.sendCommand(cLeftFast);
            communication.sendCommand(cRightSlow);
            while((!commandBox.isGoalFound()&&(objectCaught)&&(commandBox.isAutoDrive()))){
                checkIfCaught();
            }
            communication.sendCommand(cStop);
            if(!objectCaught){
                restart=true;
            } else if((objectCaught)&&(commandBox.isGoalFound())){
                located=true;
            }
        }
    }
    public void placeObject() {
        //Drive the object to its target
        System.out.println("stage: 4");
        
        boolean goalReached=false;
        while((!goalReached)&&(commandBox.isAutoDrive())&&(!restart)){
            commandBox.setState(4);
            int distanceFromGoal;
            // while object i still caught and goal is still in view
            boolean nearGoal = false;
           while((objectCaught)&&(commandBox.isGoalFound())&&(commandBox.isAutoDrive())&&(!nearGoal)){
               
                if(commandBox.getAdjustedDirection()>6){
                    communication.sendCommand(cRightFast);
                    communication.sendCommand(cLeftSlow);
                    //System.out.println("Turn left");
                } else if(commandBox.getAdjustedDirection()<-6){
                    communication.sendCommand(cLeftFast);
                    communication.sendCommand(cRightSlow);
                    //System.out.println("Turn right");
                } else if((commandBox.getAdjustedDirection()<6)&&(commandBox.getAdjustedDirection()>-6)){
                    communication.sendCommand(cForward);
                    
                }
                distanceFromGoal=commandBox.getGoalDistance();
                System.out.println(distanceFromGoal);
                //System.out.println("Drive forward");
                System.out.print("Distance from goal: ");
                System.out.println(distanceFromGoal);
                if(distanceFromGoal<20) nearGoal = true;
                
                checkIfCaught();
            }
            System.out.println("1234");
           if(!objectCaught){
               restart=true;
           } else if((commandBox.isGoalFound())&&(objectCaught)&&(commandBox.isAutoDrive())){
               goalReached=true;
               System.out.println("goal reached");
               communication.sendCommand(cBackward);
               while((true)&&(commandBox.isAutoDrive())){
                    distanceFromGoal=commandBox.getGoalDistance();
                    if(distanceFromGoal>40) break;
               }
               
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
    public void manuelDrive(String aDriection){
        switch(direction){
               case "w":
                   communication.sendCommand(cForward);
                break;
                case "s":
                    communication.sendCommand(cBackward);
                break;
                case "d":
                    communication.sendCommand(cRight);
                break;
                case "a":
                   communication.sendCommand(cLeft);
                break;
                case "x":
                    communication.sendCommand(cStop);
                break;
            }
    }
}