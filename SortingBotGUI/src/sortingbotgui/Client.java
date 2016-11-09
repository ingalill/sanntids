/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

/**
 *
 * @author inga lill bjolstad og aleksander
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
//We need a Scanner to receive input from the user
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import sortingbotgui.SortingBotGUI;

public class Client implements ActionListener, Runnable {

    // private Mat frames;
    private Socket socket;
    // private PrintWriter output;
    private DataInputStream input;
   // private HashMap<Object, String> controls;

  
 

    public Client() throws InterruptedException {
        run(); // run the client
    }

    // is going to ask for frames from the server
    @Override
    public void run() {
        //We set up the scanner to receive user input
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 5000);
            //output = new PrintWriter(socket.getOutputStream(), true);
            input = new DataInputStream(socket.getInputStream());
            System.out.println("Client started at: " + new Date());

            /* Create and display the form */
            
           java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {

                    try {
                       new SortingBotGUI().setVisible(true);
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
                       
            
            while (true) {

                try {
                    Thread.sleep(1000); //wait instead of sleep. consumer
                } catch (Exception e) {
                }
                // Get the first integer, it should be the type of the message
                // Type 1 means image
                int msgType = input.readInt();

                System.out.println("Got message type: " + msgType);

//                old way to get imgto mat to GUI side
//                BufferedImage image = ImageIO.read(socket.getInputStream());
//                Mat img = imgToMat(image);
//                Gui.getImage(img);
            }
        } catch (IOException exception) {
            System.out.println("Error client: " + exception);
        }

    }

    /**
     * Get the image from the server as a BufferedImage
     *
     * @return the image we get from the server
     * @throws IOException
     */
    public BufferedImage putFrame() throws IOException {
        return ImageIO.read(socket.getInputStream());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

} // end of class
