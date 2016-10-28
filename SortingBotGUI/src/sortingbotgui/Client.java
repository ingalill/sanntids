/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

/**
 *
 * @author ingalillbjolstad
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Date;
//We need a Scanner to receive input from the user
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import sortingbotgui.SortingBotGUI;

public class Client{

    private Mat frames;
    private Socket socket;
    private PrintWriter output;
    private DataInputStream input;
    private BufferedImage bufferedImage;
    private SortingBotGUI Gui;
    
    Client(SortingBotGUI Gui){
        this.Gui = Gui;
    }
    
    public void actionPeformed(ActionEvent e) {
        
    }
    

    public static void main(String[] args) {
        Client c = new Client(null);
        c.run();
    }

    // HA EN EGEN THREAD SOM TAR SEG AV MOTTAKELSEN AV FRAMES.
    // is going to ask for frames from the server
    public void run() {
        //We set up the scanner to receive user input
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new DataInputStream(socket.getInputStream());
            System.out.println("Client started at: " + new Date());

            //This will wait for the server to send the string to the client saying a connection
            //has been made.
            //String inputString = input.readLine();
            //System.out.println(inputString);
            //Again, here is the code that will run the client, this will continue looking for 
            //input from the user then it will send that info to the server.
            while (true) {
                //Here we look for input from the user
                //String userInput = scanner.nextLine();
                //Now we write it to the server
                //output.println(userInput);
                
                // Get the first integer, it should be the type of the message
                // Type 1 means image
                int msgType = input.readInt();
          
                
                System.out.println("Got message type: " + msgType);
                //BufferedImage image = ImageIO.read(socket.getInputStream());
                
                // BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
            /* EKSEMPEL HVOR IMG SKAL bLI SENDT VIDERE
                BufferedImage image = ImageIO.read(socket.getInputStream());
                JLabel label = new JLabel(new ImageIcon(image));
                f.getContentPane().add(label);
                */
            }
        } catch (IOException exception) {
            System.out.println("Error client: " + exception);
        }
        
        /// test
        //BufferedImage image2 = ImageIO.read(socket.getInputStream());

       /* try {
            BufferedImage image = ImageIO.read(socket.getInputStream());
            if (image != null) {
             //   new ImageWindow(image); //creates a viewing frame for the image
            } else {
                System.out.println("Screenshot failed"); //2nd and 3rd.. etc. screenshot fails (only 1st works)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /*Convert an image to a mat
     @Param BufferedImage input
     @Return Mat output  
     */
    public static Mat imgToMat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;
        int height = in.getHeight();
        int width = in.getWidth();

        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
            out = new Mat(width, height, CvType.CV_8UC3);
            data = new byte[height * width * (int) out.elemSize()];
            int[] databuffer = in.getRGB(0, 0, height, width, null, 0, height);
            for (int i = 0; i < databuffer.length; i++) {
                data[i * 3] = (byte) ((databuffer[i] >> 16) & 0xFF);
                data[i * 3 + 1] = (byte) ((databuffer[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((databuffer[i] >> 0) & 0xFF);
            }
        } else {
            out = new Mat(width, height, CvType.CV_8UC3);
            data = new byte[height * width * (int) out.elemSize()];
            int[] databuffer = in.getRGB(0, 0, height, width, null, 0, height);
            for (int i = 0; i < databuffer.length; i++) {
                r = (byte) ((databuffer[i] >> 16) & 0xFF);
                g = (byte) ((databuffer[i] >> 8) & 0xFF);
                b = (byte) ((databuffer[i] >> 0) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
            }
        }
        out.put(0, 0, data);
        return out;
    }

} // end of class
