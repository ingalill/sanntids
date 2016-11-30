/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses command received from the user, in the form <commandName> [<arg1>, ...
 * <argN>]
 *
 * @author Girts Strazdins, girts.strazdins@gmail.com, 2015-06-16
 */
public class CommandParser {

    private String name;
    private final List<String> args;
    /** Arguments as a single string */
    private String argsAsString;

    /**
     * Parse user input line, separate command and arguments
     *
     * @param inputLine
     */
    public CommandParser(String inputLine) {
        name = "";
        argsAsString = "";
        args = new ArrayList<>();
        if (inputLine != null && !inputLine.equals("")) {
            // Parse name and arguments - allow also in double quotes
            String regex = "\"([^\"]*)\"|(\\S+)";
            Matcher m = Pattern.compile(regex).matcher(inputLine);            
            String token;
            while (m.find()) {
                if (m.group(1) != null) {
                    token = m.group(1);
                } else {
                    token = m.group(0);
                }
                if (name.equals("")) {
                    // Fill in name first
                    name = token.toLowerCase();
                } else {
                    args.add(token);                    
                }
            }
            
            if (inputLine.length() > name.length()) {
                // Take all the arguments as a single string
                argsAsString = inputLine.substring(name.length() + 1);
            }
        }
    }

    /**
     * Get the name of the command
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Get all the arguments as a Collection
     * @return 
     */
    public Collection<String> getArgs() {
        return args;
    }
    
    /**
     * Get all the arguments as an array
     * @return 
     */
    public String[] getArgArray() {
        return args.toArray(new String[args.size()]);
    }
    
    /**
     * Get all the arguments as a single string
     * @return 
     */
    public String getAllArgs() {
        return argsAsString;
    }
    
    /**
     * Get argument by index
     * @param argIndex argument index, starting from 0
     * @return argument value or null if it does not exist (invalid index)
     */
    public String getArg(int argIndex) {
        if (argIndex < 0 || argIndex >= args.size()) return null;
        return args.get(argIndex);
    }
}
