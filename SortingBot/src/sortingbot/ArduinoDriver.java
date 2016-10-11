/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

import java.io.BufferedReader;
import java.io.OutputStream;

/**
 *
 * @author MalenB
 */
public class ArduinoDriver extends Thread{
     
    private static BufferedReader input;
    private static OutputStream output;
    private String forward50 = "w50";
    private ArduinoCommunication com;
    
//    public ArduinoDriver(BufferedReader in, OutputStream out) {
//        this.input=in;
//        this.output=out;
//  }
    public ArduinoDriver(ArduinoCommunication com){
        this.com = com;
    }
    
    public void run() {                
	//the following line will keep this app alive for 1000 seconds,
	//waiting for events to occur and responding to them (printing incoming messages to console).

    }
    public void seek(){
        
    }
    public void getObject(){
        
    }
    public void locateTarget(){
        
    }
    public void placeObject() {
        
    }
}