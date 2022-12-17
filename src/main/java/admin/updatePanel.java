/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package admin;

import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
/**
 *
 * @author nrnbt_
 */
public class updatePanel extends javax.swing.JPanel {

    /**
     * Creates new form updatePanel
     */
    public int auctionId;
    public String userName;
    public String userEmail;
    public String userPhone;
    public String ipAddress;

    public updatePanel(String name, String email, String phone, int id, String ipAddress) {
        this.ipAddress = ipAddress;
        auctionId = id;
        userName = name;
        userEmail = email;
        userPhone = phone;
        initComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        endDayLabel = new javax.swing.JLabel();
        startDayLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        userEmalLabel = new javax.swing.JLabel();
        userPhoneLabel = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        startTimeLabel = new javax.swing.JLabel();
        startTime = new javax.swing.JTextField();
        endTimeLabel = new javax.swing.JLabel();
        endTime = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        loadingIcon = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        endDayLabel.setText("End Day");
        add(endDayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

        startDayLabel.setText("Start Day");
        add(startDayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        userNameLabel.setText("user name: " + userName);
        add(userNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 240, -1));

        userEmalLabel.setText("user email: " + userEmail);
        add(userEmalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 250, 20));

        userPhoneLabel.setText("user phone number: " + userPhone);
        add(userPhoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 240, 20));
        add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 250, 30));
        add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 250, 30));

        startTimeLabel.setText("Start Time(24 hour format)");
        add(startTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        startTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimeActionPerformed(evt);
            }
        });
        add(startTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 250, 30));

        endTimeLabel.setText("End Time(24 hour format)");
        add(endTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

        endTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeActionPerformed(evt);
            }
        });
        add(endTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 250, 30));

        okButton.setText("Ok");
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        add(okButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, -1, -1));

        cancelButton.setText("Cancel");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });
        add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 410, -1, -1));
        add(loadingIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 40, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void startTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startTimeActionPerformed

    private void endTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeActionPerformed

    private void okButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okButtonMouseClicked
        if(jDateChooser1.getDate() == null || jDateChooser2.getDate() == null || startTime.getText().equals("") || endTime.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fill the inputs", "Empty input error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                LocalTime.parse(startTime.getText());
                LocalTime.parse(endTime.getText());
                loadingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading-icon.gif")));
                try (Socket socket = new Socket(ipAddress, 1234)) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");

                    Date startDay = jDateChooser1.getDate();
                    String startDayFormatted = DateFor.format(startDay);

                    Date endDay = jDateChooser2.getDate();
                    String endDayFormatted = DateFor.format(endDay);

                    UpdateAuctionDateRequest request = new UpdateAuctionDateRequest(
                            auctionId, 
                            startTime.getText(),
                            endTime.getText(),
                            startDayFormatted,
                            endDayFormatted
                        );
                    oos.writeObject(request);
                    oos.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = in.readLine();
                    loadingIcon.setIcon(null);
                    if(response.contains("Updated")){
                        int okClicked = JOptionPane.showOptionDialog(
                            null, 
                            "Successfully updated",
                            "Update Result",
                            JOptionPane.OK_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new Object[]{"Ok"},
                            null
                        );
                        if(okClicked == 0){
                            Window[] windows = Window.getWindows();
                            for (Window window : windows) {
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    if (dialog.getContentPane().getComponentCount() == 1
                                        && dialog.getContentPane().getComponent(0) instanceof JOptionPane){
                                        dialog.dispose();
                                    }
                                }
                            }  
                        }
                    } else {
                        JOptionPane.showMessageDialog(endTime, response, "update auction error", JOptionPane.ERROR_MESSAGE);
                    }

                    oos.close();
                    in.close();
                    socket.close();

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(endTime, "Server occured in server. Please contact to admin", "Error", JOptionPane.ERROR_MESSAGE);
                }
                loadingIcon.setIcon(null);
            } catch (DateTimeParseException | NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Enter correct time format", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_okButtonMouseClicked


    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getContentPane().getComponentCount() == 1
                    && dialog.getContentPane().getComponent(0) instanceof JOptionPane){
                    dialog.dispose();
                }
            }
        }
    }//GEN-LAST:event_cancelButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel endDayLabel;
    private javax.swing.JTextField endTime;
    private javax.swing.JLabel endTimeLabel;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel loadingIcon;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel startDayLabel;
    private javax.swing.JTextField startTime;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JLabel userEmalLabel;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel userPhoneLabel;
    // End of variables declaration//GEN-END:variables
}
