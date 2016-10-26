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
    boolean objectFound;
    boolean objectFoundAvailable;
    boolean autoDrive;
    boolean autoDriveAvailable;
    int adjustedDirection;
    boolean advance;
    boolean right;
    boolean left;
    boolean back;
    boolean goalFound;
    int state;
    
    // TODOOOOOOOOOOOO
    // -Vi treng noko som kan gir kommando at roboten skal stoppe (men kan fortsette der den slapp, så det blir ei slags pause funksjon)
    //- kommado for retning og fart
    //****************************************************************
    //- kommando for å ta imot et tall som tilsvarer både rettning og hastighet til roboten for når han kjører og skal til å svinge
    //- Kommando for å kjøre fremmover kalt advance
    //- Kommando for å kjøre til høre kalt right
    //- Kommando for å kjøre til venstre kalt left
    //- Kommando for å kjøre bakover kalt back
    
    
    
    public CommandBox(){
        autoDrive=true;
        objectFound = false;
        objectFoundAvailable=true;
        autoDriveAvailable=true;
        adjustedDirection=0;
        goalFound=false;
        state=0;
    }
    public synchronized void setObjectFound(boolean objectFound){
        while(!objectFoundAvailable){
            try{
                //wait for consumer to read value
                wait();
            }catch (InterruptedException e){
            }
        }
        this.objectFound=objectFound;
        //objectFoundAvailable=true;
        notifyAll();
    }
    public synchronized boolean getObjectFound(){
        return objectFound;
    }
    public synchronized void setAutoDrive(boolean autoDrive){
        while(!autoDriveAvailable){
            //
            try{
                wait();
            }catch (InterruptedException e){
            }
        }
        this.autoDrive=autoDrive;
        //autoDriveAvailable=false;
        notifyAll();
    }
    public boolean isAutoDrive(){
        return autoDrive;
    }
   public void adjustDirection(int dir){
        adjustedDirection=dir;
    }
   public int getAdjustedDirection(){
       return adjustedDirection;
   }
   public void setGoalFound(boolean aGoalFound){
       goalFound=aGoalFound;
   }
   public boolean isGoalFound(){
       return goalFound;
   }
   public void setState(int aState){
       state=aState;
   }
   public int getState(){
       return state;
   }
}
