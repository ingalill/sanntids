package sortingbot;

import java.io.BufferedReader;
import java.io.OutputStream;
/**
 *
 * @author Malén
 */
public class ArduinoDriver extends Thread{
     
    private final ArduinoCommunication communication;
    private final CommandBox commandBox;
    
    private boolean objectCaught;
    private String direction;
    private boolean restart;
    
    private final String cForward = "w50 "; //Move forward 
    private final String cStop="w0 "; //Stop
    private final String cBackward = "s50 "; //Move backward 
    private final String cRight = "d75 "; //Turn right 
    private final String cLeft = "a75 "; //Turn left 
    private final String cReadSS = "vss "; //Read value from the short range sensor
    private final String cReadLS = "vls "; //Read value from the long range sensor
    private final String cRightFast="r120 "; //Set right wheels to full speed
    private final String cRightSlow="r30 "; //Set right wheels to slow speed
    private final String cLeftFast="l120 "; //Set left wheels to full speed
    private final String cLeftSlow="l30 "; //Set left wheels to slow speed
    private final String cTurnFast="r90 "; //Set right wheels to full speed
    private final String cTurnSlow="l0 "; //Set speed on the left side wheels to 0

    public ArduinoDriver(ArduinoCommunication com, CommandBox box){
        this.communication = com;
        this.commandBox=box;
        objectCaught=false;
        direction="";
        restart=false;
    }
    
    public void run() {   
       while(true){
       // when the start command is true the program starts to execute either
       // auto mode methods or the instructions in manual mode
       if(commandBox.getStart()){
           // if the mode is auto it will run through the foru stages of auto mode
            while(commandBox.isAutoDrive()){
                restart=false; // sets the restart to false always before starting in state 1
                seek();
                if(!commandBox.isAutoDrive()) break; //checks if still in auto 
//              mode and breaks out of while loop if not
                getObject();
                if(!commandBox.isAutoDrive()) break;
                locateGoal();
                if(!commandBox.isAutoDrive()) break;
                placeObject();

             }
            // manual mode
            direction=commandBox.getDirection(); //gets the desired direction
//            System.out.print("Direction: ");
//            System.out.println(direction);
            manuelDrive(direction);
       }}
    }
    // Makes the robot turn in a circle until the object is in sight
    public void seek(){
        System.out.println("stage: 1");
        commandBox.setState(1); //sets state to 1
        communication.sendCommand(cLeft); // turn left 
        communication.sendCommand(cTurnFast); //sets right wheel faste to get bigger circle
        while((!commandBox.getObjectFound())&&(commandBox.isAutoDrive())&&(!objectCaught));
        communication.sendCommand(cStop); //stop before continuing
//        System.out.println("object found...");
       
    }
    // Drives toward the object until it is catched in the plow
    public void getObject(){
        System.out.println("stage: 2"); 
        boolean done=false; //flag for when to stop and move on to next stage
        commandBox.setState(2); //sets state to 2
        while((!done)&&(commandBox.isAutoDrive())){
            // while object is not caught and objectFound is still true, object found is set false when
            // the object no longer is in the camera view
             while((commandBox.getObjectFound())&&(!objectCaught)&&(commandBox.isAutoDrive())){
//                System.out.println(commandBox.getAdjustedDirection());
                if(commandBox.getAdjustedDirection()>6){
                    communication.sendCommand(cRightFast);
                    communication.sendCommand(cLeftSlow);
                } else if(commandBox.getAdjustedDirection()<-6){
                    communication.sendCommand(cLeftFast);
                    communication.sendCommand(cRightSlow);
                } else if((commandBox.getAdjustedDirection()<6)&&(commandBox.getAdjustedDirection()>-6)){
                    communication.sendCommand(cForward);
                } 
                checkIfCaught();
            }  
            communication.sendCommand(cStop);
            done=true; //when the object is not in view or is caught the while loop is done
        }
    }
    // Turns in a circle untill the goal is in sight
    public void locateGoal(){
        //Find the right area to place current object
        System.out.println("stage: 3");
        boolean located=false; //flag for when the goal is located and to move to the next stage
        if((!commandBox.getObjectFound())&&(!objectCaught)){
            restart=true;
        }
        while((!located)&&(!restart)){
            commandBox.setState(3); //sets state to 3
            communication.sendCommand(cLeft); //turn left
            communication.sendCommand(cTurnFast); //sets right wheel faste to get bigger circle
            while((!commandBox.isGoalFound()&&(commandBox.isAutoDrive())));
            communication.sendCommand(cStop);
            located=true;
        }
    }
    // Drive the object to the goal
    public void placeObject() {
        System.out.println("stage: 4");
        boolean goalReached=false;
        while((!goalReached)&&(commandBox.isAutoDrive())&&(!restart)){
            commandBox.setState(4); //sets state to 4
            int distanceFromGoal=200;
            // while object i still caught and goal is still in view
            boolean nearGoal = false; // flag for when goal is reached and the stages are complete
            while((commandBox.isGoalFound())&&(commandBox.isAutoDrive())&&(!nearGoal)){     
                if(commandBox.getAdjustedDirection()>6){
                    communication.sendCommand(cLeftFast);
                    communication.sendCommand(cRightSlow);
                } else if(commandBox.getAdjustedDirection()<-6){
                    communication.sendCommand(cRightFast);
                    communication.sendCommand(cLeftSlow);
                } else if((commandBox.getAdjustedDirection()<6)&&(commandBox.getAdjustedDirection()>-6)){
                    communication.sendCommand(cForward);  
                } 
                distanceFromGoal=commandBox.getGoalDistance();
//                System.out.print("Distance from goal: ");
//                System.out.println(distanceFromGoal);
                if(distanceFromGoal<75) nearGoal = true;
            }
            if((commandBox.isGoalFound())&&(commandBox.isAutoDrive())&&(distanceFromGoal<75)){
               goalReached=true;
               System.out.println("goal reached");
               communication.sendCommand(cBackward);
               while((true)&&(commandBox.isAutoDrive())){
                    distanceFromGoal=commandBox.getGoalDistance();
                    if(distanceFromGoal>200) break;
               }
               
            } else if(!commandBox.isGoalFound()){
                    locateGoal(); // if goal is no longe in sight, locate it again
            }
        }
        objectCaught=false;
        
    }
    //update the status of wether the object is caught or not
    public void checkIfCaught(){
//        System.out.println("check if caught");
        String in=communication.getInput(cReadSS);
        int val=Integer.parseInt(in);
        if(val<=6){
            objectCaught=true;
        } else {
            objectCaught=false;
        }
    }
    // Takes command for which direction to drive
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