/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot.server;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import sortingbot.VideoBox;

/**
 * @version 01.11.2016
 * @author inga lill bjølstad
 */
public class GetFrame implements ServerCommand {
    
    // legg inn alt med videobox her 
    private VideoBox videoBox;
    
    @Override
    public String getName(){
    return "get frame";
    }
    
    @Override
    public String getShortDesc(){
        return "Get the frames";
    }
    
    @Override 
    public String process(String command, String[] arguments){
        String returnString = "";
        
        return returnString;
    }
    
    @Override 
    public String getLongDesc(){
        return getShortDesc() + " from the server";
    }
    
    
     /*
    *Take an Mat and convert it to an BufferedImage
    *@Param Mat input.
    *@Return BufferedImage output.
     */
    public static BufferedImage matToImg(Mat in) {
        BufferedImage out;
        byte[] data = new byte[in.height() * in.width() * (int) in.elemSize()];
        int type;
        in.get(0, 0, data);

        if (in.channels() == 0) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        out = new BufferedImage(in.height(), in.width(), type);
        out.getRaster().setDataElements(0, 0, in.height(), in.width(), data);
        return out;
    }
    
}
