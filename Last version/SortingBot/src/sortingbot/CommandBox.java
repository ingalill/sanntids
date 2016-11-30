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
    // sets wether the object is found or not
    public void setObjectFound(boolean objectFound){
        this.objectFound=objectFound;
    }
    // returns true if the object is found and false if not
    public boolean getObjectFound(){
        return objectFound;
    }
    // sets wether the mode is auto or not, if not auto it is manual
    public void setAutoDrive(boolean autoDrive){
        this.autoDrive=autoDrive;
    }
    // returns true if the mode is auto and false if manual
    public boolean isAutoDrive(){
        return autoDrive;
    }
    //sets a value for where an object is compared to the center of the camera
    // it is negative if the object is to the left and positive if it is to the right
    public synchronized void adjustDirection(int dir){
        adjustedDirection=dir;
    }
    // returns a value for which direction an object is (negative to the left, positive to the right)
    public synchronized int getAdjustedDirection(){
        return adjustedDirection;
    }
    // Sets wether the goal is found or not
    public void setGoalFound(boolean aGoalFound){
        goalFound=aGoalFound;
    }
    // returns true if the goal is in sight and false if not
    public boolean isGoalFound(){
        return goalFound;
    }
    // used by ArduinoDriver to tell which state in auto mode it is (1-4)
    public synchronized void setState(int aState){
        state=aState;
    }
    // returns which state is currently executing
    public synchronized int getState(){
        return state;
    }
   // Used in manual mode to set a direction the robot should drive in
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
   // Used by ArduinoDriver, tells which direction it should drive in
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
   // Sets a value for the distance to the goal
    public synchronized void setGoalDistance(int aDist){
        distanceGoal=aDist;
    }
    // returns the distance to the goal
    public synchronized int getGoalDistance(){
        return distanceGoal;
    }
    // Set to true when the robot should start
    public void setStart(boolean aStart){
        this.start = aStart;
    }
    // returns true if the robot should start
    public boolean getStart(){
        return this.start;
    }
   
}
