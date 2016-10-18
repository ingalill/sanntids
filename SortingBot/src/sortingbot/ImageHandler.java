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
    private CommandBox comBox;
    
    public ImageHandler(CommandBox comBox){
        this.comBox = comBox;
    }
    
    public Mat processFrame(Mat frame){ 
        Mat blurredImage = new Mat();
        Mat hsvImage = new Mat();
        Mat mask = new Mat();
        //Mat resized = new Mat(); 
        //Imgproc.resize(frame, resized, new Size(100,100));
        
        //remove some noise
        Imgproc.medianBlur(frame, blurredImage, 7);//averages the colors to be more constant (removes noise well)
        //Imgproc.blur(frame, blurredImage, new Size(7, 7));//blurs the image, the easiest
        //Imgproc.bilateralFilter(frame,blurredImage, 9, 75, 75)//heavy filtering (takes alot of time)
        
        //convert the frame to HSV
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        //setup the limits for the color blue
//        Scalar upperLimit = new Scalar(names.BLUEUPPER.hue(),names.BLUEUPPER.sat(),names.BLUEUPPER.val());
//        Scalar lowerLimit = new Scalar(names.BLUELOWER.hue(),names.BLUELOWER.sat(),names.BLUELOWER.val());
        Scalar upperLimit = new Scalar(names.ORANGEUPPER.hue(),names.ORANGEUPPER.sat(),names.ORANGEUPPER.val());
        Scalar lowerLimit = new Scalar(names.ORANGELOWER.hue(),names.ORANGELOWER.sat(),names.ORANGELOWER.val());
        //filter the image and remove everything that is NOT blue
        Core.inRange(hsvImage, upperLimit, lowerLimit, mask);
        
        // morphological operators
        // dilate with large element, erode with small ones
        Mat morphOutput = new Mat();
        Mat morphPart1 = new Mat();
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
        Imgproc.erode(mask, morphPart1, erodeElement);
        //Imgproc.erode(blurredImage2, morphOutput, erodeElement);
        
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
        Imgproc.dilate(morphPart1, morphOutput, dilateElement);
        //Imgproc.dilate(mask, morphOutput, dilateElement);
        
//        test for å finne formen på blokka, men er ikke bra nok
//        Mat gray = new Mat();
//        Mat edges = new Mat();
//        Mat lines = new Mat();
//        Imgproc.cvtColor(frame,gray,Imgproc.COLOR_BGR2GRAY);
//        Imgproc.Canny(gray,edges,50,150);
//        int threshold = 50;
//        int minLineSize = 20;
//        int lineGap = 20;
//
//        Imgproc.HoughLinesP(edges, lines, 1, Math.PI/180, threshold, minLineSize, lineGap);
//
//        for (int x = 0; x < lines.cols(); x++) 
//        {
//              double[] vec = lines.get(0, x);
//              double x1 = vec[0], 
//                     y1 = vec[1],
//                     x2 = vec[2],
//                     y2 = vec[3];
//              Point start = new Point(x1, y1);
//              Point end = new Point(x2, y2);
//
//              Imgproc.line(gray, start, end, new Scalar(255,0,0), 3);
//
//        }
        
        
        //find the contours, find the centers, and find a circle whose radius is as big as the object in screen
        Mat hierarchy = new Mat();
        Mat copyOfOutput = morphOutput.clone();
        List<MatOfPoint> contours = new ArrayList<>();
        List<Point> centers = new ArrayList<>();
        List<float[]> radiuss = new ArrayList<>();
        Imgproc.findContours(copyOfOutput, contours, hierarchy, Imgproc.RETR_LIST ,Imgproc.CHAIN_APPROX_SIMPLE);
        for(int i = 0; contours.size() > i; i++)
        {
            Point center = new Point();
            float[] radius = new float[1];
            MatOfPoint2f points =  new MatOfPoint2f(contours.get(i).toArray());
            Imgproc.minEnclosingCircle(points, center, radius);
            //-----------------------------------------------------------
            //System.out.println(points.size().height);
            //-----------------------------------------------------------
            if(radius[0] > 1){//40 for full hd
                centers.add(center);
                radiuss.add(radius);
            }
        }
        //finn største sirkelen og dens plassering i arrayen
        float previous = 0;
        int biggestRad = 0;
        for(int i=0; radiuss.size() > i; i++){
            if(radiuss.get(i)[0] > previous){
                previous = radiuss.get(i)[0];
                biggestRad = i;
            }
        }
        //send signal til CommandBox
        int horizontalSpeed = 0;
        if(centers.size() > 0){
            if(centers.get(biggestRad).x > morphOutput.size().width /2){//bruka fart endring på 20
                horizontalSpeed = (int) Math.round(((centers.get(biggestRad).x-(morphOutput.size().width /2))/(morphOutput.size().width /2))*20);
            }
            else{
                horizontalSpeed = (int) Math.round(-20+((centers.get(biggestRad).x)/(morphOutput.size().width /2))*20);
            }
            System.out.println(horizontalSpeed);
        }
        else{
            System.out.println("No object in sight!");
        }
        
        
        //draw the circles on the objects, (only for debugging)
//        Scalar Red = new Scalar(0,0,255);
//        if(!centers.isEmpty()){
//            for(int i = 0; i < centers.size(); i++){
//                Imgproc.circle(morphOutput, centers.get(i), Math.round(radiuss.get(i)[0]),Red);
//            }
//        }
        
       
        return morphOutput;
        //return mask;
    }
}
