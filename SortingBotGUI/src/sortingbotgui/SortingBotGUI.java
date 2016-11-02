/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbotgui;

// Libaries and shit
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import java.net.MalformedURLException;
import javax.swing.JButton;
import javax.swing.JLabel;


/**
 *
 * @author Aleksander
 * 
 * Client Lager et objekt av GUI
 * GUI skal si ifra når den får en innput of skal trigge Client til 
 * å sende verdien.
 */

public class SortingBotGUI extends javax.swing.JFrame implements ActionListener{
    
    // definitions
    private DaemonThread myThread = null;
    private Client client;
    private boolean available;
    
    int count = 0;
    VideoBox cgrabber = null;

    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();

    /**
     * Creates new form SortingBotGUI
     */
    public SortingBotGUI() {
    //public SortingBotGUI(VideoBox abox)  {
        //cgrabber = abox;
        SetupGui();
        initComponents();
        
        //Setup of the buttons that are avaible when starting the program
        // Buttons need .setEnabled() while checkboxes need .setSelected()
        jManuel.setEnabled(false);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(false);
        jRight.setEnabled(false);
        jLeft.setEnabled(false);
        jBack.setEnabled(false);
        jPause.setEnabled(false);
        jStop.setEnabled(false);
        jReset.setEnabled(false);
        
        // Adding Actionlisteners to the necesary buttons/all the buttons
        jManuel.addActionListener(this);
        jAuto.addActionListener(this);
        jAdvance.addActionListener(this);
        jRight.addActionListener(this);
        jLeft.addActionListener(this);
        jBack.addActionListener(this);
        jStop.addActionListener(this);
        jPlay.addActionListener(this);
        jPause.addActionListener(this);
        jReset.addActionListener(this);
        jQuit.addActionListener(this);
        jGotBlue.addActionListener(this);
        jGotOrange.addActionListener(this);
        jGotRed.addActionListener(this);
        jPlacedBlue.addActionListener(this);
        jPlacedOrange.addActionListener(this);
        jPlacedRed.addActionListener(this);
       
    }

    /*
    Each code is performed after one of the button in GUI is pressed
    Change the print statment to do desired code to be executed.
    
    Note To Do. Probebly would behove us to switch to switch case.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jPlay){
        System.out.println("Play With Me <3");
        }
        if(e.getSource() == jAdvance){
            System.out.println("Advance");
        }
        if(e.getSource() == jRight){
            System.out.println("All-Right-y");
        }
        if(e.getSource() == jLeft){
            System.out.println("Lefty loosy");
        }
        if(e.getSource() == jBack){
            System.out.println("Back up");
        }
        if(e.getSource() == jStop){
            System.out.println("HammerTime");
        }
        if(e.getSource() == jPause){
            System.out.println("Pause   :|  ");
        }
        if(e.getSource() == jReset){
            System.out.println("Reset");
        }
        if(e.getSource() == jQuit){
            System.out.println("Quiting is for loosers...Looser");
        }
        if(e.getSource() == jManuel){
            System.out.println("okay, fuck it, you do it then");
        }
        if(e.getSource() == jAuto){
            System.out.println("FINE, I Will do it myself");
        }
    }
        
    // class of thread
    class DaemonThread implements Runnable
    {
        protected volatile boolean runnable = false;

        @Override
        public  void run()
        {
            synchronized(this)
            {
                while(runnable)
                {
                    try
                    {
//                       Mat img = client.putFrame();
//                        //GetFrame Method
//                        //Use GetImage Method in the bottom to get Mat image
//                        frame = getImage(img);
//                        
//                        //frame = cgrabber.getFrame();
//                        Imgcodecs.imencode(".bmp", frame, mem);
//                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                        BufferedImage buff = client.putFrame();
                        Graphics g=jVideo.getGraphics();

                        if (g.drawImage(buff, 0, 0, getWidth(), getHeight() -150 , 0, 0, buff.getWidth(), buff.getHeight(), null))

                        if(runnable == false)
                        {
                            System.out.println("GUI is going to wait()");
                            this.wait();
                        }
                     }
                     catch(Exception ex)
                     {
                        System.out.println("Error, GUI image processing failed\n");
                     }
                }
            }
         }
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBack = new javax.swing.JButton();
        jCheckLabel = new javax.swing.JLabel();
        jLeft = new javax.swing.JButton();
        jControlLabel = new javax.swing.JLabel();
        jRight = new javax.swing.JButton();
        jStart = new javax.swing.JButton();
        jStop = new javax.swing.JButton();
        jGotBlue = new javax.swing.JCheckBox();
        jVideo = new javax.swing.JPanel();
        jGotOrange = new javax.swing.JCheckBox();
        jPlay = new javax.swing.JButton();
        jGotRed = new javax.swing.JCheckBox();
        jPause = new javax.swing.JButton();
        jPlacedBlue = new javax.swing.JCheckBox();
        jManuel = new javax.swing.JButton();
        jPlacedOrange = new javax.swing.JCheckBox();
        jAuto = new javax.swing.JButton();
        jPlacedRed = new javax.swing.JCheckBox();
        jAdvance = new javax.swing.JButton();
        jVideoLabel = new javax.swing.JLabel();
        jReset = new javax.swing.JButton();
        jQuit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBack.setText("Back");
        jBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBackActionPerformed(evt);
            }
        });

        jCheckLabel.setText("Checklist");

        jLeft.setText("Left");
        jLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftActionPerformed(evt);
            }
        });

        jControlLabel.setText("Controls");

        jRight.setText("Right");
        jRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightActionPerformed(evt);
            }
        });

        jStart.setText("Start");
        jStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStartActionPerformed(evt);
            }
        });

        jStop.setText("Stop");
        jStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopActionPerformed(evt);
            }
        });

        jGotBlue.setText("Got Blue");

        javax.swing.GroupLayout jVideoLayout = new javax.swing.GroupLayout(jVideo);
        jVideo.setLayout(jVideoLayout);
        jVideoLayout.setHorizontalGroup(
            jVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jVideoLayout.setVerticalGroup(
            jVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );

        jGotOrange.setText("Got Orange");

        jPlay.setText("Play");
        jPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlayActionPerformed(evt);
            }
        });

        jGotRed.setText("Got Red");

        jPause.setText("Pause");
        jPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPauseActionPerformed(evt);
            }
        });

        jPlacedBlue.setText("Placed Blue");

        jManuel.setText("Manuel Drive");
        jManuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jManuelActionPerformed(evt);
            }
        });

        jPlacedOrange.setText("Placed Orange");
        jPlacedOrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlacedOrangeActionPerformed(evt);
            }
        });

        jAuto.setText("Auto Drive");
        jAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAutoActionPerformed(evt);
            }
        });

        jPlacedRed.setText("Placed Red");

        jAdvance.setText("Advance");
        jAdvance.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAdvanceActionPerformed(evt);
            }
        });

        jVideoLabel.setText("Video");

        jReset.setText("Reset");
        jReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jResetActionPerformed(evt);
            }
        });

        jQuit.setText("Quit");
        jQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jQuitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jGotBlue)
                                .addGap(18, 18, 18)
                                .addComponent(jPlacedBlue))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jGotOrange)
                                .addGap(18, 18, 18)
                                .addComponent(jPlacedOrange))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jGotRed)
                                .addGap(18, 18, 18)
                                .addComponent(jPlacedRed))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jCheckLabel)))
                .addGap(132, 132, 132)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jReset, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(jQuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(135, 135, 135)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jAdvance)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRight))
                    .addComponent(jBack)
                    .addComponent(jControlLabel))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(582, 582, 582)
                                .addComponent(jManuel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jAuto))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jPlay)
                                .addGap(18, 18, 18)
                                .addComponent(jPause))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jVideoLabel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jVideoLabel)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPlay)
                            .addComponent(jPause))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jCheckLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jGotBlue)
                            .addComponent(jPlacedBlue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jGotOrange)
                            .addComponent(jPlacedOrange))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPlacedRed)
                            .addComponent(jGotRed)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jControlLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jAdvance, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLeft)
                                    .addComponent(jRight)
                                    .addComponent(jStop)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jBack)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jAuto)
                                    .addComponent(jManuel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jReset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jQuit)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBackActionPerformed
        // Drive Back
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(false);       // Activate Back button
        jStop.setEnabled(true);
    }//GEN-LAST:event_jBackActionPerformed

    private void jLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftActionPerformed
        // Drive Left
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(false);       // Activate Left button
        jBack.setEnabled(true);        // Deactivate Back button
        jStop.setEnabled(true);
    }//GEN-LAST:event_jLeftActionPerformed

    private void jRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightActionPerformed
        // Drive Right
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(false);      // Activate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(true);        // Deactivate Back button
        jStop.setEnabled(true);
    }//GEN-LAST:event_jRightActionPerformed

    private void jStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStartActionPerformed
        // Start Driving in auto mode.
        jStart.setEnabled(false);
        jManuel.setEnabled(true);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(false);     // Deactivate advance button
        jRight.setEnabled(false);       // Deactivate Right button
        jLeft.setEnabled(false);        // Deactivate Left button
        jBack.setEnabled(false);
        jReset.setEnabled(true);
    }//GEN-LAST:event_jStartActionPerformed

    private void jStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopActionPerformed
        // Stop Bot driving.
        jManuel.setEnabled(false);
        jAuto.setEnabled(true);
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(true);
        jStop.setEnabled(false);
    }//GEN-LAST:event_jStopActionPerformed

    private void jPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPlayActionPerformed
        /// start button
        //webSource =new VideoCapture(0); // Video capture from defult cam

        myThread = new DaemonThread(); // creat object of thread class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                      // Start Thread
        jPlay.setEnabled(false);  //Deactivate play button
        jPause.setEnabled(true);  // activate stop button
    }//GEN-LAST:event_jPlayActionPerformed

    private void jPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPauseActionPerformed
        /// stop button
        myThread.runnable = false;      //stop thread
        jPause.setEnabled(false);     // activate play button
        jPlay.setEnabled(true);      // deactivate stop button

        //webSource.release();            // stop capturing from cam

    }//GEN-LAST:event_jPauseActionPerformed

    private void jManuelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jManuelActionPerformed
        // Code to enable manuel drive..
        jManuel.setEnabled(false);      //Deactivate start button
        jAuto.setEnabled(true);         // activate stop button
        jAdvance.setEnabled(true);      // Activate advance button
        jRight.setEnabled(true);        // Deactivate Right button
        jLeft.setEnabled(true);         // Deactivate Left button
        jBack.setEnabled(true);         // Deactivate Back button
        
    }//GEN-LAST:event_jManuelActionPerformed

    private void jPlacedOrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPlacedOrangeActionPerformed
        // Set true when colored object is in place
        //        if(color == place){
            //            checkBox = jPlacedOrange.setSelected(true);
            //        }
        System.out.println("Placed Orange");
    }//GEN-LAST:event_jPlacedOrangeActionPerformed

    private void jAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAutoActionPerformed
        // Code to enable auto drive.
        jManuel.setEnabled(true);       //Deactivate start button
        jAuto.setEnabled(false);        // activate stop button
        jAdvance.setEnabled(false);     // Deactivate advance button
        jRight.setEnabled(false);       // Deactivate Right button
        jLeft.setEnabled(false);        // Deactivate Left button
        jBack.setEnabled(false);        // Deactivate Back button
    }//GEN-LAST:event_jAutoActionPerformed

    private void jAdvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAdvanceActionPerformed
        // Drive Forward
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(false);    // Activate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(true);        // Deactivate Back button
        jStop.setEnabled(true);
    }//GEN-LAST:event_jAdvanceActionPerformed

    private void jResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jResetActionPerformed
        // start over
        jReset.setEnabled(false);
        jStart.setEnabled(true);
        jManuel.setEnabled(false);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(false);
        jRight.setEnabled(false);
        jLeft.setEnabled(false);
        jBack.setEnabled(false);
        jGotBlue.setSelected(false);
        jGotOrange.setSelected(false);
        jGotRed.setSelected(false);
        jPlacedBlue.setSelected(false);
        jPlacedOrange.setSelected(false);
        jPlacedRed.setSelected(false);
    }//GEN-LAST:event_jResetActionPerformed

    private void jQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jQuitActionPerformed
        System.out.println("Quit");
    }//GEN-LAST:event_jQuitActionPerformed
    
    public void SetupGui(){
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // load native library of opencv
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */ 
    }
    
//    Main Method for GUI now used in client part
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SortingBotGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        
////        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SortingBotGUI().setVisible(true);
//            }
//        });
//    }
    
    
        //Get img(image) From Client and use this method to use video in jVideo(Screen in GUI)
    public Mat getImage(Mat img){
        return img;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAdvance;
    private javax.swing.JButton jAuto;
    private javax.swing.JButton jBack;
    private javax.swing.JLabel jCheckLabel;
    private javax.swing.JLabel jControlLabel;
    private javax.swing.JCheckBox jGotBlue;
    private javax.swing.JCheckBox jGotOrange;
    private javax.swing.JCheckBox jGotRed;
    private javax.swing.JButton jLeft;
    private javax.swing.JButton jManuel;
    private javax.swing.JButton jPause;
    private javax.swing.JCheckBox jPlacedBlue;
    private javax.swing.JCheckBox jPlacedOrange;
    private javax.swing.JCheckBox jPlacedRed;
    private javax.swing.JButton jPlay;
    private javax.swing.JButton jQuit;
    private javax.swing.JButton jReset;
    private javax.swing.JButton jRight;
    private javax.swing.JButton jStart;
    private javax.swing.JButton jStop;
    private javax.swing.JPanel jVideo;
    private javax.swing.JLabel jVideoLabel;
    // End of variables declaration//GEN-END:variables
}
