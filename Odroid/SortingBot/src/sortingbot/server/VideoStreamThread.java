/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @version 08.11.2016
 * @author inga lill bjølstad
 */
public class VideoStreamThread implements Runnable{
    
    private Socket serverSocket = null;
    private DataOutputStream outputBuffer;
    
    public VideoStreamThread(Socket serverSocket){
        this.serverSocket = serverSocket;
    
    }
    // has an output stream with image to the client
    
    @Override
    public void run(){
        
        try{
        outputBuffer = new DataOutputStream(serverSocket.getOutputStream());
        }catch(IOException ex){
            System.out.println("Do not work");
        }
        
    }
}
