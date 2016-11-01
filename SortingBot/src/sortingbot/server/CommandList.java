/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import java.util.ArrayList;

/**
 * @version 01.11.2016
 * @author inga lill bjølstad
 */
public class CommandList {
    
    private final ArrayList<ServerCommand> commandlist;
    
    
    public CommandList(){
        commandlist = new ArrayList<ServerCommand>();
        addCommands();
    }
    
    /**
     * Get a reference of the list. 
     * @return commandlist  
     */
    public ArrayList<ServerCommand> getArray(){
        return commandlist;
    }
    
    /**
     * Adding new element in the list.
     */
    private void addCommands(){
        commandlist.add(new GetFrame());
        commandlist.add(new GUIButton());
    }
}
