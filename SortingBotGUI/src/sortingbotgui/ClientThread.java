/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 08.11.2016
 * @author inga lill bjølstad
 */
public class ClientThread implements Runnable{
    
    private Socket threadSocket;
    private PrintWriter output;
    private BufferedReader input;
    
    public ClientThread(Socket socket){
        this.threadSocket = socket;
    }
              
    
    @Override
    public void run(){
        try {
            output = new PrintWriter(threadSocket.getOutputStream(),true);
            input = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));
            
            output.println("You have connected at: " + new Date());
            
            while(true){
                String inputs = input.readLine();
                System.out.println("You got the input: " + inputs);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
}
