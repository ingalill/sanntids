/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import sortingbot.Camera;
import sortingbot.CommandBox;

/**
 * @author inga lill og aleksander
 */
public class VideoCommand implements ServerCommand {

    Camera camera;
    CommandBox commandbox;

    /**
     * Get name of the argument eks. play
     * @return string of video command
     */
    @Override
    public String getName() {
        return "Video command";
    }

    /**
     * set commandBox
     * @param commandbox 
     */
    public void setCommandbox(CommandBox commandbox) {
        this.commandbox = commandbox;
    }
    
    @Override
    public String process(String command, String[] arguments) {
        if (arguments.length < 1) return "err No arguments";
        
        String response = "ok";
        String arg = arguments[0];
        
        switch (arg) {
            case "play":
                 // TODO: malén og demy                
                response = "nextframe";
                break;

            case "pause":
                 // TODO: malén og demy
                break;

            case "gotblue":
                commandbox.setObjectFound(true);
                break;

            case "gotorange":
                commandbox.setObjectFound(true);
                break;

            case "gotred":
                commandbox.setObjectFound(true);
                break;

            case "placedblue":
                // TODO: malén og demy
                break;

            case "placedorange":
                // TODO: malén og demy
                break;

            case "placedred":
                // TODO: malén og demy
                break;
        }
        return response;
    }

    /**
     * 
     * @return string of short description
     */
    @Override
    public String getShortDesc() {
        return "Class for video commands";
    }

    /**
     * @return string of long description
     */
    @Override
    public String getLongDesc() {
        return "This class is used for telling what each of the command arguments do";
    }
}
