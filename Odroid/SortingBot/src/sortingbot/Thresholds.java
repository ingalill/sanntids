/*
 * Enum class
 */
package sortingbot;

/**
 *
 * @author Demy
 */

public enum Thresholds {
    
    BLUEUPPER(110,110,30),
    BLUELOWER(130,255,255),
    ORANGEUPPER(0,220,0),
    ORANGELOWER(90,255,255);
//    REDUPPER,
//    REDLOWER,
//    GREENUPPER,
//    GREENLOWER;
    
    private final int hue;
    private final int sat;
    private final int val;
    
    Thresholds(int hue, int sat, int val){
        this.hue = hue;
        this.sat = sat;
        this.val = val;
    }
    
    public int hue(){return hue;};
    public int sat(){return sat;};
    public int val(){return val;};
    
}
