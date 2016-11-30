package sortingbot;

/**
 *
 * @author Demy og Malén
 */
public class CommandBox {
    boolean start;
    boolean objectFound;
    boolean autoDrive;
    int adjustedDirection;
    boolean advance;
    boolean right;
    boolean left;
    boolean back;
    boolean stop;
    boolean goalFound;
    int state;
    int distanceGoal;
    boolean setDirAvailable;
    
    public CommandBox(){
        start=false;
        autoDrive=true; //set the mode to auto 
        objectFound = false; // the object is not jet found
        adjustedDirection=0;
        goalFound=false; // the goal is not jet found
        state=0; //autodrive mode is not jet started. States: 1-seek,2-get object,3-locate target, 4-place object
        advance=false;
        right=false;
        left=false;
        back=false;
        stop=false;
        distanceGoal=100;
        setDirAvailable = true;
    }
    
    /*
    *
    */
    public void setObjectFound(boolean objectFound){
        this.objectFound=objectFound;
    }
    
    public boolean getObjectFound(){
        return objectFound;
    }
    
    public void setAutoDrive(boolean autoDrive){
        this.autoDrive=autoDrive;
    }
    
    public boolean isAutoDrive(){
        return autoDrive;
    }
    
    public synchronized void adjustDirection(int dir){
        adjustedDirection=dir;
    }
   
    public synchronized int getAdjustedDirection(){
        return adjustedDirection;
    }

    public void setGoalFound(boolean aGoalFound){
        goalFound=aGoalFound;
    }

    public boolean isGoalFound(){
        return goalFound;
    }

    public synchronized void setState(int aState){
        state=aState;
    }

    public synchronized int getState(){
        return state;
    }
   
   public synchronized void setDirection(String aDirection){
        while(!setDirAvailable){
             try{
                 wait();
             }catch (InterruptedException e){}
         }
        setDirAvailable = false;
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
        notifyAll();
   }
   
   public synchronized String getDirection(){
        while(setDirAvailable){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        setDirAvailable = true;
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
        notifyAll();
        return dir;
    }
   
    public synchronized void setGoalDistance(int aDist){
        distanceGoal=aDist;
    }
    
    public synchronized int getGoalDistance(){
        return distanceGoal;
    }
    
    public void setStart(boolean aStart){
        this.start = aStart;
    }
    
    public boolean getStart(){
        return this.start;
    }
   
}
