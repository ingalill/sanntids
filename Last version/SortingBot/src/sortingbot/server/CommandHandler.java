/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * class process a string and executes commands.
 * @author Demy Gielesen
 */
public class CommandHandler {

    private final ArrayList<ServerCommand> commandList;

    //Get a reference to a list of comments.
    public CommandHandler() {
        CommandList theList = new CommandList();
        commandList = theList.getArray();
    }

    /*
     *checks if the given string is a valid command and executes it.
     */
    public String check(String command) {
        String returnString = null;
        if (!checkList(getEachWord(command)[0])) {
            returnString = "That is not a valid command";
        } else {
            returnString = execute(command);
        }
        return returnString;
    }

    /*
     *checks the commandlist if there is a command of given name available
     */
    private boolean checkList(String command) {
        boolean returnValue = false;
        for (ServerCommand possibleCommand : commandList) {
            if (possibleCommand.getName().equalsIgnoreCase(command)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    /*
     * Splits a string into separate words and returns a string array
     */
    private String[] getEachWord(String command) {
        //split for each whitespace found.
        String[] words = command.split("\\s+");
        return words;
    }

    /*
     *testing if there are any arguments and execution 
     */
    private String execute(String inputCommand) {
        ServerCommand process = null;
        String[] words = getEachWord(inputCommand);

        for (ServerCommand possibleCommand : commandList) {
            if (possibleCommand.getName().equalsIgnoreCase(words[0])) {
                process = possibleCommand;
            }
        }

        String[] arguments = new String[0];
        //create a string[] without the first word
        if (words.length > 1) {
            arguments = Arrays.copyOfRange(words, 1, words.length);
        }

        if (!(process == null)) {
            return process.process(words[0], arguments);
        } else {
            return "An error occured";
        }
    }
}
