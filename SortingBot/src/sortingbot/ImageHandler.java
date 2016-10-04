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
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import java.util.*;

public class ImageHandler {
    
    private Thresholds names; // enum class 
    
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
        
        // morphological operators
        // dilate with large element, erode with small ones
        Mat morphOutput = new Mat();
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
        Imgproc.erode(mask, morphOutput, erodeElement);
        Imgproc.erode(mask, morphOutput, erodeElement);
        
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
        Imgproc.dilate(mask, morphOutput, dilateElement);
        Imgproc.dilate(mask, morphOutput, dilateElement);
        
        //find the contours, find the centers, and find a circle whose radius is as big as the object in screen
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        List<Point> centers = new ArrayList<>();
        List<float[]> radiuss = new ArrayList<>();
        Imgproc.findContours(morphOutput, contours, hierarchy, Imgproc.RETR_LIST ,Imgproc.CHAIN_APPROX_SIMPLE);
        for(int i = 0; contours.size() > i; i++)
        {
            Point center = new Point();
            float[] radius = new float[1];
            Imgproc.minEnclosingCircle(new MatOfPoint2f(contours.get(i).toArray()), center, radius);
            if(radius[0] > 75){
                centers.add(center);
                radiuss.add(radius);
            }
        }
        
        //draw the circles on the objects, (only for debugging)
        Scalar Red = new Scalar(255,0,0);
        if(!centers.isEmpty()){
            for(int i = 0; i < centers.size(); i++){
                Imgproc.circle(morphOutput, centers.get(i), Math.round(radiuss.get(i)[0]),Red);
            }
        }
        
       
        return morphOutput;
        //return mask;
    }
}
