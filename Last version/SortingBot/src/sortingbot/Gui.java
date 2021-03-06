/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingbot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


/**
 *
 * @author Demy
 */
public class Gui extends javax.swing.JFrame implements ActionListener{
    
    // definitions
    private DaemonThread myThread = null;
    int count = 0;
    VideoBox grabber = null;

    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Dummy test");
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
                        frame = grabber.getFrame();
                        Imgcodecs.imencode(".bmp", frame, mem);
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                        BufferedImage buff = (BufferedImage) im;
                        Graphics g=jPanel1.getGraphics();

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
     * Creates new form Gui
     */
    public Gui(VideoBox vg){
        grabber = vg;
        SetupGui();
        initComponents();
        jManuel.setEnabled(false);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(false);
        jRight.setEnabled(false);
        jLeft.setEnabled(false);
        jBack.setEnabled(false);
        jPause.setEnabled(false);
        jStop.setEnabled(false);
        
        jRight.addActionListener(this);
        jLeft.addActionListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPlay = new javax.swing.JButton();
        jPause = new javax.swing.JButton();
        jManuel = new javax.swing.JButton();
        jAuto = new javax.swing.JButton();
        jAdvance = new javax.swing.JButton();
        jBack = new javax.swing.JButton();
        jLeft = new javax.swing.JButton();
        jRight = new javax.swing.JButton();
        jStart = new javax.swing.JButton();
        jGotBlue = new javax.swing.JCheckBox();
        jGotOrange = new javax.swing.JCheckBox();
        jGotRed = new javax.swing.JCheckBox();
        jPlacedBlue = new javax.swing.JCheckBox();
        jPlacedOrange = new javax.swing.JCheckBox();
        jPlacedRed = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jStop = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );

        jPlay.setText("Play");
        jPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlayActionPerformed(evt);
            }
        });

        jPause.setText("Pause");
        jPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPauseActionPerformed(evt);
            }
        });

        jManuel.setText("Manuel Drive");
        jManuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jManuelActionPerformed(evt);
            }
        });

        jAuto.setText("Auto Drive");
        jAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAutoActionPerformed(evt);
            }
        });

        jAdvance.setText("Advance");
        jAdvance.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAdvanceActionPerformed(evt);
            }
        });

        jBack.setText("Back");
        jBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBackActionPerformed(evt);
            }
        });

        jLeft.setText("Left");
        jLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftActionPerformed(evt);
            }
        });

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

        jGotBlue.setText("Got Blue");

        jGotOrange.setText("Got Orange");

        jGotRed.setText("Got Red");

        jPlacedBlue.setText("Placed Blue");

        jPlacedOrange.setText("Placed Orange");
        jPlacedOrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlacedOrangeActionPerformed(evt);
            }
        });

        jPlacedRed.setText("Placed Red");

        jLabel1.setText("Video");

        jLabel2.setText("Checklist");

        jLabel3.setText("Controls");

        jToggleButton1.setText("Reset");

        jStop.setText("Stop");
        jStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopActionPerformed(evt);
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
                                .addComponent(jPlacedRed)))
                        .addGap(132, 132, 132)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(jStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel2)))
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
                    .addComponent(jLabel3))
                .addGap(0, 28, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jPlay)
                                .addGap(18, 18, 18)
                                .addComponent(jPause))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(582, 582, 582)
                                .addComponent(jManuel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jAuto)))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jGotBlue, jGotOrange, jGotRed, jPlacedBlue, jPlacedOrange, jPlacedRed});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jAdvance, jBack, jLeft, jRight});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jAdvance, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLeft)
                                    .addComponent(jRight)
                                    .addComponent(jStop))))
                        .addGap(10, 10, 10)
                        .addComponent(jBack)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jAuto)
                            .addComponent(jManuel)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPlay)
                            .addComponent(jPause))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jLabel2)
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
                            .addComponent(jGotRed))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    }//GEN-LAST:event_jAdvanceActionPerformed

    private void jRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightActionPerformed
        // Drive Right
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(false);      // Activate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(true);        // Deactivate Back button
    }//GEN-LAST:event_jRightActionPerformed

    private void jLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftActionPerformed
        // Drive Left
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(false);       // Activate Left button
        jBack.setEnabled(true);        // Deactivate Back button
    }//GEN-LAST:event_jLeftActionPerformed

    private void jBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBackActionPerformed
        // Drive Back
        jManuel.setEnabled(false);      //Deactivate start button
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(false);       // Activate Back button
    }//GEN-LAST:event_jBackActionPerformed

    private void jStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStartActionPerformed
        // Start Driving in auto mode.
        jManuel.setEnabled(true);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(false);     // Deactivate advance button
        jRight.setEnabled(false);       // Deactivate Right button
        jLeft.setEnabled(false);        // Deactivate Left button
        jBack.setEnabled(false);    
    }//GEN-LAST:event_jStartActionPerformed

    private void jPlacedOrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPlacedOrangeActionPerformed
        // Set true when colored object is in place
//        if(color == place){
//            checkBox = jPlacedOrange.setEnabled(true);
//        }
    }//GEN-LAST:event_jPlacedOrangeActionPerformed

    private void jStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopActionPerformed
        // Stop Bot driving.
        jManuel.setEnabled(true);
        jAuto.setEnabled(false);
        jAdvance.setEnabled(true);     // Deactivate advance button
        jRight.setEnabled(true);       // Deactivate Right button
        jLeft.setEnabled(true);        // Deactivate Left button
        jBack.setEnabled(true);
        jStop.setEnabled(false);
    }//GEN-LAST:event_jStopActionPerformed

    public void clientToGui(){
     // Get image from Client and use in GUI
    }
//    public static void main(String args[]) {      
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
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jAdvance;
    private javax.swing.JButton jAuto;
    private javax.swing.JButton jBack;
    private javax.swing.JCheckBox jGotBlue;
    private javax.swing.JCheckBox jGotOrange;
    private javax.swing.JCheckBox jGotRed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jLeft;
    private javax.swing.JButton jManuel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jPause;
    private javax.swing.JCheckBox jPlacedBlue;
    private javax.swing.JCheckBox jPlacedOrange;
    private javax.swing.JCheckBox jPlacedRed;
    private javax.swing.JButton jPlay;
    private javax.swing.JButton jRight;
    private javax.swing.JButton jStart;
    private javax.swing.JButton jStop;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
