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
    boolean autoDrive=true;
    boolean autoDriveAvailable;
    boolean advance;
    boolean right;
    boolean left;
    boolean back;
    
    // TODOOOOOOOOOOOO
    // -Vi treng noko som kan gir kommando at roboten skal stoppe (men kan fortsette der den slapp, så det blir ei slags pause funksjon)
    //- Komando for auto/manuel mode + kommado for retning og fart
    //*****************************************************************
    //- kommando for å ta imot et tall som tilsvarer både rettning og hastighet til roboten for når han kjører og skal til å svinge
    //- Kommando for å ta imot distanse målt frå camera
    //- Kommando for å kjøre fremmover kalt advance
    //- Kommando for å kjøre til høre kalt right
    //- Kommando for å kjøre til venstre kalt left
    //- Kommando for å kjøre bakover kalt back
    

    public CommandBox(){
        objectFoundAvailable=false;
        autoDriveAvailable=false;
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
        return objectFound;
    }
}
