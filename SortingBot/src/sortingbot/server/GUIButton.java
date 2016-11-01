/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

/**
 * @version 01.11.2016
 * @author inga lill bjølstad
 */
public class GUIButton implements ServerCommand{
    
    
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
- gogogo!
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
