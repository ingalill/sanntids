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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Client implements ActionListener, Runnable {

    private Socket socket;
    private PrintWriter output;
    private DataOutputStream outputs;

    private DataInputStream input;
    private ArrayList<String> buffer;

    public Client() throws InterruptedException {
        buffer = new ArrayList<>();
        //run(); // run the client
    }

    // is going to ask for frames from the server
    @Override
    public void run() {
        //We set up the scanner to receive user input
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            outputs = new DataOutputStream(socket.getOutputStream());
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

                // send the commands 
                if (buffer.size() != 0) {
                    for (int i = 0; i < buffer.size(); i++) {
                        outputs.writeBytes("%"); // is the start of a new commando
                        outputs.writeBytes(buffer.get(i));
                        //outputs.writeBytes("$");
                    }
                    buffer.clear();
                } else {
                    //send string med get
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

    public void createMessage(String args) {
        buffer.add(args);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

} // end of class
