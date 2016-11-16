/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import sortingbot.Camera;
import sortingbot.CommandBox;

/**
 *
 * @author inga lill og aleksander
 */
public class VideoCommand implements ServerCommand {

    Camera camera;
    CommandBox commandbox;

    @Override
    public String getName() {
        return "Video command";
    }

    @Override
    public String process(String command, String[] arguments) {
        switch (command) {
            case "play":
                 // TODO: malén og demy
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

        return command;
    }

    @Override
    public String getShortDesc() {
        return "";
    }

    @Override
    public String getLongDesc() {
        return "";
    }

}
