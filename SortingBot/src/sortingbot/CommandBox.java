/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

import static java.lang.Math.abs;
/**
 *
 * @author Demy
 */
public class CommandBox {
    boolean objectFound = false;
    boolean objectFoundAvailable;
    boolean autoDrive=true;
    boolean autoDriveAvailable;
    //when object is located use these values to adjust direction (float or int??)
    float adjustedDirection; //value for how much to turn to the right or left
    boolean adjustedDirAvailable;
    
    // TODOOOOOOOOOOOO
    // -Vi treng noko som kan gir kommando at roboten skal stoppe (men kan fortsette der den slapp, så det blir ei slags pause funksjon)
    //- kommado for retning og fart (manuel mode)
    //- kommando for å ta imot et tall som tilsvarer både rettning og hastighet til roboten for når han kjører og skal til å svinge
    //- Kommando for å ta imot distanse målt frå camera
    
    
    public CommandBox(){
        objectFoundAvailable=false;
        autoDriveAvailable=false;
        adjustedDirection=0;
        adjustedDirAvailable=false;
    }
    public synchronized void setObjectFound(boolean objectFound){
        while(objectFoundAvailable){
            try{
                //wait for consumer to read value
                wait();
            }catch (InterruptedException e){
            }
        }
        this.objectFound=objectFound;
        objectFoundAvailable=true;
        notifyAll();
    }
    public synchronized boolean getObjectFound(){
        while(!objectFoundAvailable){
            //Wait for producer to set value
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        objectFoundAvailable=false;
        notifyAll();
        return objectFound;
    }
    public synchronized void setAutoDrive(boolean autoDrive){
        while(autoDriveAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        this.autoDrive=autoDrive;
        autoDriveAvailable=true;
        notifyAll();
    }
    public synchronized boolean isAutoDrive(){
        while(!autoDriveAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        autoDriveAvailable=false;
        notifyAll();
        return autoDrive;
    }
    //use method to set values for how much to turn left or right
    public synchronized void adjustDirection(float val){
        while(adjustedDirAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        adjustedDirection=val;
        adjustedDirAvailable=true;
        notifyAll();
    }
    public synchronized float getAdjustedDirection(){
        while(!adjustedDirAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        adjustedDirAvailable=false;
        notifyAll();
        return adjustedDirection;
    }
}
