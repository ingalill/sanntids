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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Client implements ActionListener, Runnable {

    private Socket socket;
    private PrintWriter output;
    private DataOutputStream outputs;
    //private BufferedReader infromServer;
    private DataInputStream input;
    private ArrayList<String> buffer;
    private ArrayList<BufferedImage> bufferImg;

    public Client() throws InterruptedException {
        buffer = new ArrayList<>();
        bufferImg = new ArrayList<>();
    }

    // This asks for frames from the server
    @Override
    public void run() {
        try {
            
            createMessage("video play");
            socket = new Socket("localhost", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            outputs = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            //infromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client started at: " + new Date());

            while (true) {
                
                if (buffer.isEmpty()) {
                    createMessage("video play");
                }

                for (int i = 0; i < buffer.size(); i++) {
                    String sendByte = buffer.get(i);
                    //outputs.writeBytes("%"); // is the start of a new commando
                    outputs.writeBytes(sendByte);
                    System.out.println("Sending byte from client: " + sendByte);
                    outputs.writeBytes("\n");
                    String reply = input.readLine();
                    System.out.println("Reply from the server: " + reply + "\n");
                    CommandParser parser = new CommandParser(reply);

                    // Send/get frames
                    if (parser.getName().equals("nextframe")) {
                        fetchNextFrame(parser);
                    }
                }
                // clears the buffer so the lates frame is shown next.
                buffer.clear();
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
    public BufferedImage getFrame() throws IOException {
        return ImageIO.read(socket.getInputStream());
    }

    /**
     * 
     * @param args 
     */
    public synchronized void createMessage(String args) {
        buffer.add(args);
        notifyAll();
    }

    /**
     * Gets the next frame and adds it to a buffer
     * @param parser 
     */
    private void fetchNextFrame(CommandParser parser) {
        String[] args = parser.getArgArray();
        if (args == null || args.length < 3) {
            System.out.println("NextFrame command lacking arguments");
            return;
        }
        int imgSizeBytes = Integer.valueOf(args[0]);
        int imgWidth = Integer.valueOf(args[1]);
        int imgHeight = Integer.valueOf(args[2]);

        if (imgSizeBytes <= 0 || imgWidth <= 0 || imgHeight <= 0) {
            return;
        }

        // read the frame to byte array
        byte[] imgBytes = new byte[imgSizeBytes];
        try {
            input.readFully(imgBytes);
            // Convert it to Mat
            Mat mat;
            mat = new Mat(imgWidth, imgHeight, CvType.CV_8UC3);
            mat.put(0, 0, imgBytes);
            addBuffFrame(matToImg(mat));

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     *  Gives the buffered image and removes the old image from the  buffer
     * @return tempImg 
     */
    public BufferedImage getBuffImg(){
        BufferedImage tempImg = null;
        if(!bufferImg.isEmpty())
        {
            tempImg = bufferImg.get(0);
            bufferImg.remove(0);
        }
        return tempImg;
}
    
    /**
     * Adds BuffImg to bufferImg from BuferedImage
     * @param BuffImg 
     */
    public void addBuffFrame(BufferedImage BuffImg) {
        bufferImg.add(BuffImg);
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
            
            // Arange the colors from BGR to RGB
            byte b;
            for(int i = 0; i < data.length; i = i + 3){
                b = data[i];
                data[i] = data[i + 2];
                data[i + 2] = b;
            }
        }
        out = new BufferedImage(in.height(), in.width(), type);
        out.getRaster().setDataElements(0, 0, in.height(), in.width(), data);
        return out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} // end of class