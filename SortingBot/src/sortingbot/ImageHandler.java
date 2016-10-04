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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ImageHandler {
    
    private Thresholds names;
    
    public Mat processFrame(Mat frame){
        Mat blurredImage = new Mat();
        Mat hsvImage = new Mat();
        Mat mask = new Mat();
        
        //remove some noise
        Imgproc.blur(frame, blurredImage, new Size(7, 7));
        //convert the frame to HSV
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        //setup the limits for the color blue
        Scalar upperLimit = new Scalar(names.BLUEUPPER.hue(),names.BLUEUPPER.sat(),names.BLUEUPPER.val());
        Scalar lowerLimit = new Scalar(names.BLUELOWER.hue(),names.BLUELOWER.sat(),names.BLUELOWER.val());
        //filter the image and remove everything that is NOT blue
        Core.inRange(hsvImage, upperLimit, lowerLimit, mask);
        
        
        Mat morphOutput = new Mat();
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
        Imgproc.erode(mask, morphOutput, erodeElement);
        Imgproc.erode(mask, morphOutput, erodeElement);
        return morphOutput;
    }
}
