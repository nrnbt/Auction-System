/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import App.Layout;
import client.LoginRequest;
import client.LoginResponse;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author nrnbt
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public String ipAddress;
    
    public Login(String ip) {
        initComponents();
        this.ipAddress = ip;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new keeptoo.KGradientPanel();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JPasswordField();
        btnExit = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        loginButton = new com.k33ptoo.components.KButton();
        usernameLabel1 = new javax.swing.JLabel();
        abeyText = new javax.swing.JLabel();
        adminIcon = new javax.swing.JLabel();
        hammerIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Background.setkEndColor(new java.awt.Color(44, 91, 233));
        Background.setMaximumSize(new java.awt.Dimension(1800, 900));
        Background.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                BackgroundMouseDragged(evt);
            }
        });
        Background.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BackgroundKeyPressed(evt);
            }
        });
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameTextField.setBackground(new Color(0,0,0,0));
        usernameTextField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        usernameTextField.setForeground(new java.awt.Color(255, 255, 255));
        usernameTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        usernameTextField.setCaretColor(new java.awt.Color(255, 51, 255));
        usernameTextField.setOpaque(false);
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });
        Background.add(usernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 288, 40));

        passwordTextField.setBackground(new Color(0,0,0,0));
        passwordTextField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        passwordTextField.setForeground(new java.awt.Color(255, 255, 255));
        passwordTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        passwordTextField.setCaretColor(new java.awt.Color(255, 102, 255));
        passwordTextField.setOpaque(false);
        passwordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextFieldActionPerformed(evt);
            }
        });
        Background.add(passwordTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, 290, 40));

        btnExit.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("X");
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });
        Background.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 30, 40));

        passwordLabel.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(255, 255, 255));
        passwordLabel.setText("Password");
        Background.add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 90, 20));

        loginButton.setText("Login");
        loginButton.setkBorderRadius(30);
        loginButton.setkEndColor(new java.awt.Color(0, 204, 204));
        loginButton.setkFillButton(false);
        loginButton.setkHoverEndColor(new java.awt.Color(204, 0, 204));
        loginButton.setkHoverForeGround(new java.awt.Color(255, 204, 255));
        loginButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        loginButton.setkStartColor(new java.awt.Color(255, 255, 255));
        loginButton.setPreferredSize(new java.awt.Dimension(63, 23));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        Background.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, 180, 40));

        usernameLabel1.setFont(new java.awt.Font("URW Bookman", 1, 24)); // NOI18N
        usernameLabel1.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel1.setText("ABEY Auction");
        Background.add(usernameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 200, 40));

        abeyText.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        abeyText.setForeground(new java.awt.Color(255, 255, 255));
        abeyText.setText("Username");
        Background.add(abeyText, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 90, 20));
        Background.add(adminIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));
        Background.add(hammerIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void passwordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextFieldActionPerformed

    private void BackgroundKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BackgroundKeyPressed
        
    }//GEN-LAST:event_BackgroundKeyPressed

    private void BackgroundMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundMouseDragged
//        new Drag(Background).moveWindow(evt);
    }//GEN-LAST:event_BackgroundMouseDragged

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String username=usernameTextField.getText();
        String pass=passwordTextField.getText();
        
        if(username.equals("")){
            JOptionPane.showMessageDialog(null,"Please enter your username.");
        }else if(pass.equals("")){
            JOptionPane.showMessageDialog(null,"Please enter your password.");
        }else{
            try (Socket socket = new Socket(ipAddress, 1234)) {

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                LoginRequest request = new LoginRequest(username, pass);

                oos.writeObject(request);
                oos.flush();
                
                Object obj = ois.readObject();
                LoginResponse response;
                
                if (obj.getClass().getName().equals("client.LoginResponse")
                    && (response = (LoginResponse) obj) != null) {
                    if(response.userId < 0){
                        JOptionPane.showMessageDialog(Background, "Username or password didn't match");
                    } else {
                        this.hide();
                        try {
                            new Layout(response.userId, ipAddress).setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KGradientPanel Background;
    private javax.swing.JLabel abeyText;
    private javax.swing.JLabel adminIcon;
    private javax.swing.JLabel btnExit;
    private javax.swing.JLabel hammerIcon;
    private com.k33ptoo.components.KButton loginButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JLabel usernameLabel1;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
