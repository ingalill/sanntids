/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 01.11.2016
 * @author inga lill bjølstad
 */
public class GUIButton implements ServerCommand{
    
     private BufferedReader infromClient;
    
    @Override
    public String getName(){
    return "Gui buttons";
    }
    
    @Override
    public String getShortDesc(){
        return "";
    }
    
    @Override 
    public String process(String command, String[] arguments){
        String returnString = "";
        
       infromClient =  new BufferedReader(new InputStreamReader(System.in));
       String commando = null;
         try {
             commando = infromClient.readLine();
         } catch (IOException ex) {
             Logger.getLogger(GUIButton.class.getName()).log(Level.SEVERE, null, ex);
         }
       
        switch(commando){
            
            case "play":
                System.out.println("PLAAAAY");
                break;
            case "pause":
                break;
            case "quit":
                break;
            case "start":
                break;
            case "reset":
                break;
            case "advanced":
                break;
            case "left":
                break;
            case "right":
                break;
            case "back":
                break;
            case "manuel drive":
                break;
            case "autodrive":
                break;
            case "got blue":
                break;
            case "got orange":
                break;
            case "got red":
                break;
            case "placed blue":
                break;
            case "placed orange":
                break;
            case "placed red":
                break;
                
                
        }
        
        
        
        // switch case med knapper 
        return returnString;
    }
    
    @Override 
    public String getLongDesc(){
        return "";
    }
    
}

/*
Knapper:
- play
- pause
- start
- reset
- Adcance
- Left
- Stop
- Right
- Back
- Manuel Drive
- AutoDrive

Checklist:
- Got Blue
- Got Orange
- Got Red
- Placed Blue
- Placed Orange
- Placed Red

*/
