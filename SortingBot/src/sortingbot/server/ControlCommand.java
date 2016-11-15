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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        // legg inn manuel, aut, advance osv. 
        // skriver true false inn på commandbox
        //commandbox.setState(true);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShortDesc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLongDesc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
