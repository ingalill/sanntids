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
public interface ServerCommand {

    /**
     * Command name, should be lowercase
     *
     * @return
     */
    String getName();

    /**
     * Process a command with specified arguments, return response
     *
     * @param command command received from the user/client
     * @param arguments optional arguments supplied with the command
     * @return Output of the command
     */
    String process(String command, String[] arguments);

    /**
     * Short description of the command (max one line)
     *
     * @return
     */
    String getShortDesc();

    /**
     * Long description of the command
     *
     * @return
     */
    String getLongDesc();

}
