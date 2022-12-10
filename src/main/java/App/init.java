/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package App;

import Login.Login;
import javax.swing.JOptionPane;

/**
 *
 * @author nrnbt
 */
public class init extends java.awt.Frame {

    /**
     * Creates new form init
     */
    public init() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new keeptoo.KGradientPanel();
        ipAddressInput = new javax.swing.JTextField();
        ipAddressLabel = new javax.swing.JLabel();
        connectButton = new com.k33ptoo.components.KButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ipAddressInput.setBackground(new java.awt.Color(0,0,0,0));
        ipAddressInput.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        ipAddressInput.setForeground(new java.awt.Color(255, 255, 255));
        ipAddressInput.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        ipAddressInput.setCaretColor(new java.awt.Color(255, 51, 255));
        ipAddressInput.setOpaque(false);
        background.add(ipAddressInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 240, 30));

        ipAddressLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        ipAddressLabel.setForeground(new java.awt.Color(255, 255, 255));
        ipAddressLabel.setText("Enter the IP address");
        background.add(ipAddressLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        connectButton.setText("Connect");
        connectButton.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        connectButton.setkBorderRadius(30);
        connectButton.setkEndColor(new java.awt.Color(0, 204, 204));
        connectButton.setkFillButton(false);
        connectButton.setkHoverEndColor(new java.awt.Color(204, 0, 204));
        connectButton.setkHoverForeGround(new java.awt.Color(255, 204, 255));
        connectButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        connectButton.setkIndicatorThickness(4);
        connectButton.setkStartColor(new java.awt.Color(255, 255, 255));
        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectButtonMouseClicked(evt);
            }
        });
        background.add(connectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 130, 30));

        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void connectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectButtonMouseClicked
        if(ipAddressInput.getText() == ""){
            JOptionPane.showMessageDialog(background, "Fill the input");
        } else {
            this.hide();
            new Login(ipAddressInput.getText()).setVisible(true);
        }
    }//GEN-LAST:event_connectButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new init().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KGradientPanel background;
    private com.k33ptoo.components.KButton connectButton;
    private javax.swing.JTextField ipAddressInput;
    private javax.swing.JLabel ipAddressLabel;
    // End of variables declaration//GEN-END:variables
}
