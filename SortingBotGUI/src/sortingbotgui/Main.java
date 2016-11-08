/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;

/**
 *
 * @author Aleksander
 */
public class Main {
    
    public static void main(String[] args) {  
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // Create an objeckt of each  class
   
    
    /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    //new SortingBotGUI(videoBox).setVisible(true);
                    new SortingBotGUI().setVisible(true);
                } 
            
        });
    }
}
