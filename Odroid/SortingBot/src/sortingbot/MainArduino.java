/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author MalenB
 */
public class MainArduino {
    private static ArduinoCommunication communication;
    private static ArduinoDriver driver;
    private static CommandBox commandBox;
    
    public static void main(String[] args){
        communication = new ArduinoCommunication();
        //Creating  a new command box for handling communication between ArduinoDriver
        // and the rest of the world
        commandBox= new CommandBox();
        //Starting the arduino driver
        driver = new ArduinoDriver(communication,commandBox);
        driver.start();
       try {
            Thread.sleep(1000);
 
        } catch(Exception e) {}
        commandBox.setObjectFound(true);
        try {
            Thread.sleep(6000);
 
        } catch(Exception e) {}
        commandBox.setGoalFound(true);
         System.out.println("set goal found");
         try {
            Thread.sleep(1000);
 
        } catch(Exception e) {}
         commandBox.setAutoDrive(false);
         System.out.println("set auto drive false");
         try {
            Thread.sleep(1000);
 
        } catch(Exception e) {}
        commandBox.setDirection("d");
        try {
            Thread.sleep(1000);
 
        } catch(Exception e) {}
        commandBox.setDirection("w");
        try {
            Thread.sleep(1000);
 
        } catch(Exception e) {}
        commandBox.setDirection("a");
        
    }
    
}
