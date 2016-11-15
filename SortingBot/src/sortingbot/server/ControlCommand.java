/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import sortingbot.CommandBox;

/**
 * @version 09.11.2016
 * @author inga lill og aleksander
 */
public class ControlCommand implements ServerCommand {

    private CommandBox commandbox;

    @Override
    public String getName() {
      return "control command";
    }

    @Override
    public String process(String command, String[] arguments) {
        
        switch (command) {
            case "manuel":
                commandbox.setAutoDrive(false);
                break;
                
            case "auto":
                commandbox.setAutoDrive(true);
                break;
                
            case "advance":
               commandbox.setDirection("w");
                break;
                
            case "left":
                commandbox.setDirection("a");
                break;
                
            case "right":
                commandbox.setDirection("d");
                break;
                
            case "back":
                commandbox.setDirection("s");
                break;

            case "stop":
                commandbox.setDirection("x");
                break;

            case "start":
                // TODO: malén og demy
                break;

            case "reset":
                // TODO: malén og demy                
                break;

            case "quit":
                System.exit(0);
                break;
                
        }
        return command;
    }

    @Override
    public String getShortDesc() {
       return "This controls the movement.";
    }

    @Override
    public String getLongDesc() {
      return "";
    }

}
