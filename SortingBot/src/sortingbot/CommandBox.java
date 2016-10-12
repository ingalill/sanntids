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
    
    // TODOOOOOOOOOOOO
    // -Vi treng noko som kan gir kommando at roboten skal stoppe (men kan fortsette der den slapp, så det blir ei slags pause funksjon)
    
    
    
    
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
