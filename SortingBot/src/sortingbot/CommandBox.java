/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author Demy
 */
public class CommandBox {
    boolean objectFound = false;
    boolean objectFoundAvailable;
    
    public CommandBox(){
        objectFoundAvailable=false;
    }
    public synchronized void setObjectFound(boolean objectFound){
        while(objectFoundAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        this.objectFound=objectFound;
    }
    public synchronized boolean getObjectFound(){
        return objectFound;
    }
}
