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
    private int horizontalSpeed;
    private Mat morphOutput;
    private Mat resized ; 
    private Mat blurredImage ;
    private Mat hsvImage;
    private Mat mask;
    private Mat morphPart1;
        
    public ImageHandler(CommandBox comBox){
        this.comBox = comBox;
        horizontalSpeed = 20;
        morphOutput = new Mat();
        resized = new Mat(); 
        blurredImage = new Mat();
        hsvImage = new Mat();
        mask = new Mat();
        morphPart1 = new Mat();
    }
    
    public Mat processFrame(Mat frame){ 
        
        if(!frame.empty()){
            //Resize the frame but keep 16:9 ratio
            Imgproc.resize(frame, resized, new Size(320,90));
            //remove some noise
            Imgproc.medianBlur(resized, blurredImage, 7);//averages the colors to be more constant (removes noise well)
            
            /*
            *in case of the need for a different type of blur filter:
            *Imgproc.blur(frame, blurredImage, new Size( 7, 7));//blurs the image, the easiest
            *Imgproc.bilateralFilter(frame,blurredImage, 9, 75, 75)//heavy filtering (takes alot of time)
            */
            
            //convert the frame to HSV
            Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
            
            //setup the limits for the color blue
            Scalar upperLimit = null;
            Scalar lowerLimit = null;
            if(comBox.getState()<=2){
                upperLimit = new Scalar(names.ORANGEUPPER.hue(),names.ORANGEUPPER.sat(),names.ORANGEUPPER.val());
                lowerLimit = new Scalar(names.ORANGELOWER.hue(),names.ORANGELOWER.sat(),names.ORANGELOWER.val());
            }
            else{
                upperLimit = new Scalar(names.BLUEUPPER.hue(),names.BLUEUPPER.sat(),names.BLUEUPPER.val());
                lowerLimit = new Scalar(names.BLUELOWER.hue(),names.BLUELOWER.sat(),names.BLUELOWER.val()); 
            }

            //filter the image and remove everything that is NOT blue
            Core.inRange(hsvImage, upperLimit, lowerLimit, mask);

            /* ********************************** DISABLED**************************************
            *  found out later that it wasnt needed with the low resolution we use
            */
            // morphological operators
            // dilate with large element, erode with small ones
//            Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
//            Imgproc.erode(mask, morphPart1, erodeElement);
//            //Imgproc.erode(blurredImage2, morphOutput, erodeElement);
//
//            Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
//            Imgproc.dilate(morphPart1, morphOutput, dilateElement);
//            //Imgproc.dilate(mask, morphOutput, dilateElement);
            //**********************************************************************************
            
            //find the contours, find the centers, and find a circle whose radius is as big as the object in screen
            Mat hierarchy = new Mat();
            Mat copyOfOutput = mask.clone();
            List<MatOfPoint> contours = new ArrayList<>();
            List<Point> centers = new ArrayList<>();//store every center point of every visible object in the mask
            List<float[]> radiuss = new ArrayList<>();//store every radius of every visible object
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
                if(radius[0] > 4){//Only store the circle that have a radius bigger then 4 pixels wide
                    // use >40 when using full hd
                    centers.add(center);
                    radiuss.add(radius);
                }
            }
            
            //find the position of the biggest circle in the array
            float previous = 0;
            int biggestRad = 0;
            for(int i=0; radiuss.size() > i; i++){
                if(radiuss.get(i)[0] > previous){
                    previous = radiuss.get(i)[0];
                    biggestRad = i;
                }
            }

            //Decide the speed the robot has to turn with
            if(centers.size() > 0){ //if there are objects in sight
                comBox.setObjectFound(true);
                //calculate the ratio of the position of the object according to the screen width:
                if(centers.get(biggestRad).x > mask.size().width /2){//use +-20 as speed change
                    horizontalSpeed = (int) Math.round(((centers.get(biggestRad).x-(mask.size().width /2))/(mask.size().width /2))*20);
                }
                else{
                    horizontalSpeed = (int) Math.round(-20+((centers.get(biggestRad).x)/(mask.size().width /2))*20);
                }
//                System.out.println(horizontalSpeed);
                comBox.adjustDirection(horizontalSpeed);

                if(comBox.getState()>2){
                    //System.out.println("Rad: " + radiuss.get(biggestRad)[0]);
                    comBox.setGoalFound(true);
                    if(radiuss.get(biggestRad)[0]>10){
                        comBox.setGoalDistance(200-(int)radiuss.get(biggestRad)[0]);
                        //System.out.println("Rad: "+ (200-(int)radiuss.get(biggestRad)[0]));
                    }
                }
            }
            else{
                //if the object dissapeared when it wasnt in the "middle" of the screen, then remove the objectFound flag
                if(horizontalSpeed>6||horizontalSpeed<-6){ 
                    //System.out.println("No object in sight!");
                    comBox.adjustDirection(0);
                    comBox.setObjectFound(false);
                }
            }
        }
        return mask;
    }
}
