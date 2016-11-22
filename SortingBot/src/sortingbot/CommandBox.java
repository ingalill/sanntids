/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

/**
 *
 * @author Demy og Malen
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
    boolean stop;
    boolean goalFound;
    int state;
    int distanceGoal;
    
    // TODOOOOOOOOOOOO
    // -Vi treng noko som kan gir kommando at roboten skal stoppe (men kan fortsette der den slapp, så det blir ei slags pause funksjon)
    //- kommado for retning og fart
    
    
    
    public CommandBox(){
        autoDrive=true; //set the mode to auto 
        objectFound = false; // the object is not jet found
        objectFoundAvailable=true; 
        autoDriveAvailable=true;
        adjustedDirection=0;
        goalFound=false; // the goal is not jet found
        state=0; //autodrive mode is not jet started. States: 1-seek,2-get object,3-locate target, 4-place object
        advance=false;
        right=false;
        left=false;
        back=false;
        stop=false;
        distanceGoal=100;
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
   public void setDirection(String aDirection){
       advance=false;
       right=false;
       left=false;
       back=false;
       stop=false;
       switch(aDirection){
           case "w":
               advance=true;
            break;
           case "s":
               back=true;
            break;
           case "d":
               right=true;
            break;
           case "a":
               left=true;
            break;
           case "x":
               stop=true;
            break;
       }
   }
   public String getDirection(){
       String dir="";
       if(advance){
           dir="w";
       } else if(back){
           dir="s";
       } else if(right){
           dir="d";
       } else if(left){
           dir="a";
       } else if(stop){
           dir="x";
       }
       return dir;
    }
   public void setGoalDistance(int aDist){
       distanceGoal=aDist;
   }
   public int getGoalDistance(){
       return distanceGoal;
   }
}
