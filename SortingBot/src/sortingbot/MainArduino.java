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
        
    }
    
}
