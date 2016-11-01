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
public class GetFrame implements ServerCommand {
    
    // legg inn alt med videobox her 
    
    @Override
    public String getName(){
    return "get frame";
    }
    
    @Override
    public String getShortDesc(){
        return "";
    }
    
    @Override 
    public String process(String command, String[] arguments){
        String returnString = "";
        
        return returnString;
    }
    
    @Override 
    public String getLongDesc(){
        return "";
    }
    
    
}
