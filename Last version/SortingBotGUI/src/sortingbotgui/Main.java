package sortingbotgui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;

/**
 *
 * @author Aleksander og inga lill
 */
public class Main {
    private static SortingBotGUI gui;
    private static Client client; 
    public static void main(String[] args) throws InterruptedException {  
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // Create an objeckt of each  class
    client = new Client();
    new Thread(client).start();

    
    
    /* Create and display the form of GUI */
        java.awt.EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            
                            gui = new SortingBotGUI(client);
                            gui.setVisible(true);
                            new Timer().scheduleAtFixedRate(new VideoStreamTimer(gui), 0, 30);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                } 
            
        }); 
    }
}

/**
 * A Timer used fot cideo stream
 */
class VideoStreamTimer extends TimerTask{
    private SortingBotGUI gui;
    public VideoStreamTimer(SortingBotGUI gui){
       this.gui = gui;
        
    }
    
    @Override
    public void run() {
         gui.setVideoFrame();
    }
}
