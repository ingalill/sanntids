/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;

/**
 *
 * @author Aleksander og inga lill
 */
public class Main {
    
   private static Client client; 
    public static void main(String[] args) throws InterruptedException {  
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // Create an objeckt of each  class
    client = new Client();
    new Thread(client).start();

    
    
    /* Create and display the form */
        java.awt.EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new SortingBotGUI(client).setVisible(true);
                            //new Client().setVisible(true); // start gui
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                } 
            
        }); 
    }
}
