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

    /**
     * Set commandBox
     *
     * @param commandbox
     */
    public void setCommandbox(CommandBox commandbox) {
        this.commandbox = commandbox;
    }

    /**
     * Get name method
     *
     * @return string
     */
    @Override
    public String getName() {
        return "control command";
    }

    /**
     * Process what argument have been sent and decides what to do based on the
     * argument.
     *
     * @param command
     * @param arguments
     * @return
     */
    @Override
    public String process(String command, String[] arguments) {
        if (arguments.length < 1) {
            return "err No arguments";
        }

        String response = "ok";
        String arg = arguments[0];
        switch (arg) {
            case "manuel":
                commandbox.setAutoDrive(false);
                System.out.println("Hello from ControlCommand");
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
                commandbox.setStart(true);
                break;

            case "reset":
                // TODO: malén og demy                
                break;

            case "quit":
                System.exit(0);
                break;
        }
        return response;
    }

    /**
     *
     * @return string of a short description
     */
    @Override
    public String getShortDesc() {
        return "This controls the movement.";
    }

    /**
     * @return string of a Long description
     */
    @Override
    public String getLongDesc() {
        return "Movements the control are able to do";
    }

}
