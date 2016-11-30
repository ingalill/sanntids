
package sortingbot;

/**
 * A class for testing and debugging the arduino part
 * @author Malen
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
