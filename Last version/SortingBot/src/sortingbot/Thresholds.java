/*
 * Enum class
 */
package sortingbot;

/**
 *
 * @author Demy
 */

public enum Thresholds {
    //these values have been found after testing what range our blocks are within
    BLUEUPPER(110,110,30),//Blue gets distorted alot due to background noise.
    BLUELOWER(130,255,255),
    ORANGEUPPER(0,220,0),//orange seems to react to red aswell, but there isnt much red as distortion
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
