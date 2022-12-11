package App;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Login.Login;
import client.CreateAuctionRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.GetAllAuctionRequest;
import client.GetAllAuctionResponse;
import client.GetMyAuctionsRequest;
import client.GetMyAuctionsResponse;
import client.GetMyBidsRequest;
import client.GetMyBidsResponse;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author asus
 */
public class TabbedPanels extends javax.swing.JFrame {
    File f = null;
    String path = null;
    private ImageIcon format = null;
    String fname=null;
    int s = 0;
    byte[] pimage=null;

    /**
     * Creates new form Layout
     */
    public int auctionId;
    public int userId;
    public String ipAddress;
    
    public TabbedPanels(int userId_, String ipAddress) throws ParseException {
        initComponents();
        userId = userId_;
        this.ipAddress = ipAddress;
        try {
            showData("accepted");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(AuctionList != null && !AuctionList.auctionList.isEmpty()){
            emptyLabel.setVisible(false);
            int left = 15;
            int top = 10;
            int panelCount = 0;
     
            for(int i=0; i < AuctionList.auctionList.size(); i++){
       
                javax.swing.JPanel auctionPanel = new javax.swing.JPanel();
                javax.swing.JPanel timerPanel = new javax.swing.JPanel();
                javax.swing.JLabel timerLabel = new javax.swing.JLabel();
                javax.swing.JPanel pricePanel = new javax.swing.JPanel();
                javax.swing.JLabel priceLabel = new javax.swing.JLabel();
                javax.swing.JPanel titlePanel = new javax.swing.JPanel();
                javax.swing.JLabel createdByLabel = new javax.swing.JLabel();
                javax.swing.JLabel titleLabel = new javax.swing.JLabel();
                javax.swing.JLabel imgLabel = new javax.swing.JLabel();

                auctionPanel.setMinimumSize(new java.awt.Dimension(200, 260));
                auctionPanel.setPreferredSize(new java.awt.Dimension(200, 260));
                auctionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                timerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                timerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
                timerPanel.add(timerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 30));
                timerPanel.setBackground(new Color(255,255,255));
                timerPanel.setBorder(new javax.swing.border.MatteBorder(0, 2, 2, 2, new java.awt.Color(0, 0, 0)));
                auctionPanel.add(timerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 190, 30));

                pricePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                priceLabel.setText("Start Price: " + AuctionList.auctionList.get(i).startPrice);
                priceLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
                pricePanel.add(priceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 30));

                pricePanel.setBackground(new Color(255,255,255));
                pricePanel.setBorder(new javax.swing.border.MatteBorder(0, 2, 0, 2, new java.awt.Color(0, 0, 0)));
                auctionPanel.add(pricePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 190, 30));

                titlePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                createdByLabel.setText("Created by: " + AuctionList.auctionList.get(i).user);
                createdByLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
                titlePanel.add(createdByLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, 180, 25));

                titleLabel.setText("Title: " + AuctionList.auctionList.get(i).title);
                titleLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
                titlePanel.add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 25));
                titlePanel.setBackground(new Color(255,255,255));
                titlePanel.setBorder(new javax.swing.border.MatteBorder(0, 2, 0, 2, new java.awt.Color(0, 0, 0)));

                ImageIcon imageIcon = new ImageIcon(AuctionList.auctionList.get(i).img); 
                Image image = imageIcon.getImage(); 
                Image newimg = image.getScaledInstance(190, 190,  java.awt.Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(newimg));
                imgLabel.setBorder(new javax.swing.border.MatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

                auctionPanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 190, 50));
                auctionPanel.add(imgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 150));
                int id = AuctionList.auctionList.get(i).id;
                auctionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        auctionId = id;
                        try {
                            auctionPanelMouseClicked(evt);
                        } catch (IOException ex) {
                            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                auctionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        auctionPanelMouseEntered(evt);
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        auctionPanelMouseExited(evt);
                    }
                });
            

                scrollablePanel.add(auctionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
                Timer startCountTimer;
                Timer endCountTimer;
                if(AuctionList != null && AuctionList.auctionList != null && AuctionList.auctionList.get(i).startTime != null && AuctionList.auctionList.get(i).endTime != null){
                    DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startDate = (Date)formatter1.parse(AuctionList.auctionList.get(i).startTime);
                    Date endDate = (Date)formatter1.parse(AuctionList.auctionList.get(i).endTime);
                    
                    endCountTimer = new Timer(1000, null);
                      endCountTimer.addActionListener((ActionEvent evt) -> {
                          long start = (endDate.getTime()  - new Date().getTime()) / 1000;
                          long second = start % 60;
                          start = start / 60;
                          long minut = start % 60;
                          start = start / 60;
                          long hour = start % 24;
                          start = start / 24;
                          long day = start % 7;
                          start = start / 7;
                          long week = start;
                          if((endDate.getTime() - new Date().getTime()) / 1000 % 60 >= 0){
                            timerLabel.setText("End Time: " +week+":"+ day+":"+ hour+":"+minut+":"+second);
                          } else {
                            auctionPanel.hide();
                          }
                    });

                    startCountTimer = new Timer(1000, null);
                      startCountTimer.addActionListener((ActionEvent evt) -> {
                          long start = (startDate.getTime() - new Date().getTime()) / 1000;
                          long second = start % 60;
                          start = start / 60;
                          long minut = start % 60;
                          start = start / 60;
                          long hour = start % 24;
                          start = start / 24;
                          long day = start % 7;
                          start = start / 7;
                          long week = start;
                          if((startDate.getTime() - new Date().getTime()) / 1000 % 60 >= 0){
                            timerLabel.setText("Start Time: " +week+":"+ day+":"+ hour+":"+minut+":"+second);
                          } else {
                              endCountTimer.start();
                          }
                    });
                      
                    if((startDate.getTime() - new Date().getTime()) / 1000 % 60 >= 0){
                        startCountTimer.start();
                    } else if ((endDate.getTime()  - new Date().getTime()) / 1000 % 60 >= 0){
                        endCountTimer.start();
                    }
                }
                
                scrollablePanel.add(auctionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(left, top, 190, 260));
                left = left + 200;
                panelCount = panelCount + 1;
                if((panelCount % 5) == 0){
                    top = top + 280;
                    left = 15;
                }
            }
        } else {
           emptyLabel.setVisible(true); 
        }
    }
    public static GetAllAuctionResponse AuctionList;
    
    public void showData(String stausFilter) throws ClassNotFoundException {
        try (Socket socket = new Socket(ipAddress, 1234)) {

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            GetAllAuctionRequest request = new GetAllAuctionRequest("auctions", stausFilter);
            oos.writeObject(request);
            
            Object obj = ois.readObject();
            GetAllAuctionResponse dataList;
            if (obj.getClass().getName().equals("client.GetAllAuctionResponse")
		&& (dataList = (GetAllAuctionResponse) obj) != null) {
                AuctionList = dataList;
            }
            
            oos.close();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
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

        navbar = new com.k33ptoo.components.KGradientPanel();
        AppTitle = new javax.swing.JLabel();
        logOutButton = new com.k33ptoo.components.KButton();
        auctionsButton = new com.k33ptoo.components.KButton();
        createAuctionButton = new com.k33ptoo.components.KButton();
        myAuctionsButton = new com.k33ptoo.components.KButton();
        myBidsButton = new com.k33ptoo.components.KButton();
        tabs = new javax.swing.JTabbedPane();
        auctionsPanel = new keeptoo.KGradientPanel();
        header = new keeptoo.KGradientPanel();
        headerLabel = new javax.swing.JLabel();
        auctionsScroll = new javax.swing.JScrollPane();
        scrollablePanel = new keeptoo.KGradientPanel();
        emptyLabel = new javax.swing.JLabel();
        createAuctionPanel = new com.k33ptoo.components.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        labelImage = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        titleTxt = new javax.swing.JTextField();
        startPriceInput = new javax.swing.JTextField();
        startPriceInput2 = new javax.swing.JTextField();
        insertImgBtn = new com.k33ptoo.components.KButton();
        submitBtn = new com.k33ptoo.components.KButton();
        descriptionScroll = new javax.swing.JScrollPane();
        descriptionTxt = new javax.swing.JTextArea();
        myAuctionPanel = new keeptoo.KGradientPanel();
        myAuctionsTableScroll = new javax.swing.JScrollPane();
        myAuctionsTable = new javax.swing.JTable();
        myBidsPanel = new keeptoo.KGradientPanel();
        myBidsScroll = new javax.swing.JScrollPane();
        myBidsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navbar.setkBorderRadius(0);
        navbar.setkEndColor(new java.awt.Color(0, 255, 204));
        navbar.setkStartColor(new java.awt.Color(153, 0, 153));
        navbar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AppTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        AppTitle.setForeground(new java.awt.Color(255, 255, 255));
        AppTitle.setText("ABEY AUCTION");
        navbar.add(AppTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 61));

        logOutButton.setText("Log Out");
        logOutButton.setkBorderRadius(30);
        logOutButton.setkEndColor(new java.awt.Color(51, 51, 255));
        logOutButton.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        logOutButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        logOutButton.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });
        navbar.add(logOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 130, 30));

        auctionsButton.setText("Auctions");
        auctionsButton.setkBorderRadius(30);
        auctionsButton.setkEndColor(new java.awt.Color(0, 204, 204));
        auctionsButton.setkHoverEndColor(new java.awt.Color(0, 255, 204));
        auctionsButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        auctionsButton.setkHoverStartColor(new java.awt.Color(153, 0, 153));
        auctionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auctionsButtonActionPerformed(evt);
            }
        });
        navbar.add(auctionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 30));

        createAuctionButton.setText("Create Auction");
        createAuctionButton.setkBorderRadius(30);
        createAuctionButton.setkEndColor(new java.awt.Color(0, 204, 204));
        createAuctionButton.setkHoverEndColor(new java.awt.Color(0, 255, 204));
        createAuctionButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        createAuctionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAuctionButtonActionPerformed(evt);
            }
        });
        navbar.add(createAuctionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        myAuctionsButton.setText("My Auctions");
        myAuctionsButton.setkBorderRadius(30);
        myAuctionsButton.setkEndColor(new java.awt.Color(0, 204, 204));
        myAuctionsButton.setkHoverEndColor(new java.awt.Color(0, 255, 204));
        myAuctionsButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        myAuctionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myAuctionsButtonActionPerformed(evt);
            }
        });
        navbar.add(myAuctionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 30));

        myBidsButton.setText("My Bids");
        myBidsButton.setkBorderRadius(30);
        myBidsButton.setkEndColor(new java.awt.Color(0, 204, 204));
        myBidsButton.setkHoverEndColor(new java.awt.Color(0, 255, 204));
        myBidsButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        myBidsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myBidsButtonActionPerformed(evt);
            }
        });
        navbar.add(myBidsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, 30));

        getContentPane().add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 500));

        tabs.setPreferredSize(new java.awt.Dimension(1000, 500));

        auctionsPanel.setkEndColor(new java.awt.Color(255, 255, 255));
        auctionsPanel.setkStartColor(new java.awt.Color(255, 255, 255));
        auctionsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("Auctions");
        header.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 120, -1));

        auctionsPanel.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 70));

        scrollablePanel.setkEndColor(new java.awt.Color(255, 255, 255));
        scrollablePanel.setkStartColor(new java.awt.Color(255, 255, 255));
        scrollablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emptyLabel.setFont(new java.awt.Font("SansSerif", 2, 24)); // NOI18N
        emptyLabel.setIcon(new javax.swing.ImageIcon("/home/nrnbt/NetBeansProjects/master/src/main/java/images/empty.png")); // NOI18N
        emptyLabel.setText("Sorry, No Active Auctions");
        scrollablePanel.add(emptyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 530, 190));

        auctionsScroll.setViewportView(scrollablePanel);

        auctionsPanel.add(auctionsScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1040, 440));

        tabs.addTab("Auctions", auctionsPanel);

        createAuctionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Create Auction");
        createAuctionPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 310, 40));

        labelImage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        createAuctionPanel.add(labelImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 210, 200));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Description:");
        createAuctionPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 190, 40));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Title:");
        createAuctionPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 140, 30));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Start Price:");
        createAuctionPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 180, 30));

        titleTxt.setBackground(new Color(0,0,0,0));
        titleTxt.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        titleTxt.setCaretColor(new java.awt.Color(255, 255, 255));
        titleTxt.setOpaque(false);
        createAuctionPanel.add(titleTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 250, 30));

        startPriceInput.setBackground(new Color(0,0,0,0));
        startPriceInput.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        startPriceInput.setCaretColor(new java.awt.Color(255, 255, 255));
        startPriceInput.setOpaque(false);
        createAuctionPanel.add(startPriceInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 250, 30));

        startPriceInput2.setBackground(new Color(0,0,0,0));
        startPriceInput2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        startPriceInput2.setOpaque(false);
        createAuctionPanel.add(startPriceInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 250, 30));

        insertImgBtn.setText("Insert Image");
        insertImgBtn.setkBorderRadius(30);
        insertImgBtn.setkEndColor(new java.awt.Color(51, 51, 255));
        insertImgBtn.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        insertImgBtn.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        insertImgBtn.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        insertImgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertImgBtnActionPerformed(evt);
            }
        });
        createAuctionPanel.add(insertImgBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 110, 30));

        submitBtn.setText("Submit");
        submitBtn.setkBorderRadius(30);
        submitBtn.setkEndColor(new java.awt.Color(51, 51, 255));
        submitBtn.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        submitBtn.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        submitBtn.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });
        createAuctionPanel.add(submitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, 110, 30));

        descriptionTxt.setColumns(20);
        descriptionTxt.setRows(5);
        descriptionTxt.setLineWrap(true);
        descriptionTxt.setWrapStyleWord(true);
        descriptionScroll.setViewportView(descriptionTxt);

        createAuctionPanel.add(descriptionScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, 360, 180));

        tabs.addTab("createAcution", createAuctionPanel);

        myAuctionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        myAuctionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Title", "Start Price", "Status", "Description", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, javax.swing.ImageIcon.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myAuctionsTableScroll.setViewportView(myAuctionsTable);
        if (myAuctionsTable.getColumnModel().getColumnCount() > 0) {
            myAuctionsTable.getColumnModel().getColumn(0).setMinWidth(30);
            myAuctionsTable.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        myAuctionPanel.add(myAuctionsTableScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 37, 960, 440));

        tabs.addTab("myAuction", myAuctionPanel);

        myBidsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        myBidsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Bid Amount", "Auction Title", "Auction Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, javax.swing.ImageIcon.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myBidsScroll.setViewportView(myBidsTable);
        if (myBidsTable.getColumnModel().getColumnCount() > 0) {
            myBidsTable.getColumnModel().getColumn(0).setMinWidth(30);
            myBidsTable.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        myBidsPanel.add(myBidsScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 980, 450));

        tabs.addTab("myBids", myBidsPanel);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, -38, 1040, 540));
        tabs.getAccessibleContext().setAccessibleName("Home");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
       this.hide();
       new Login(ipAddress).setVisible(true);
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void auctionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auctionsButtonActionPerformed
        tabs.setSelectedIndex(0);
        try {
            showData("accepted");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
//        auctionsButton.setkStartColor(new Color(153,0,153));
//        auctionsButton.setkEndColor(new Color(0,255,204));
//        createAuctionButton.setkStartColor(new Color(0,153,153));
//        createAuctionButton.setkEndColor(new Color(0,204,204));
//        myAuctionsButton.setkStartColor(new Color(0,153,153));
//        myAuctionsButton.setkEndColor(new Color(0,204,204));
//        myBidsButton.setkStartColor(new Color(0,153,153));
//        myBidsButton.setkEndColor(new Color(0,204,204));
//        aboutUSButton.setkStartColor(new Color(0,153,153));
//        aboutUSButton.setkEndColor(new Color(0,204,204));
    }//GEN-LAST:event_auctionsButtonActionPerformed

    private void createAuctionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAuctionButtonActionPerformed
        tabs.setSelectedIndex(1);
//        createAuctionButton.setkStartColor(new Color(153,0,153));
//        createAuctionButton.setkEndColor(new Color(0,255,204));
//        auctionsButton.setkStartColor(new Color(0,153,153));
//        auctionsButton.setkEndColor(new Color(0,204,204));
//        myAuctionsButton.setkStartColor(new Color(0,153,153));
//        myAuctionsButton.setkEndColor(new Color(0,204,204));
//        myBidsButton.setkStartColor(new Color(0,153,153));
//        myBidsButton.setkEndColor(new Color(0,204,204));
//        aboutUSButton.setkStartColor(new Color(0,153,153));
//        aboutUSButton.setkEndColor(new Color(0,204,204));
    }//GEN-LAST:event_createAuctionButtonActionPerformed

    private void myAuctionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myAuctionsButtonActionPerformed
        tabs.setSelectedIndex(2);
        DefaultTableModel dm = (DefaultTableModel) myAuctionsTable.getModel();
        int rowCount = dm.getRowCount();
        if(rowCount > 0){
           for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
            } 
        }
        try {
            GetMyAuctions();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
//        myAuctionsButton.setkStartColor(new Color(153,0,153));
//        myAuctionsButton.setkEndColor(new Color(0,255,204));
//        auctionsButton.setkStartColor(new Color(0,153,153));
//        auctionsButton.setkEndColor(new Color(0,204,204));
//        createAuctionButton.setkStartColor(new Color(0,153,153));
//        createAuctionButton.setkEndColor(new Color(0,204,204));
//        myBidsButton.setkStartColor(new Color(0,153,153));
//        myBidsButton.setkEndColor(new Color(0,204,204));
//        aboutUSButton.setkStartColor(new Color(0,153,153));
//        aboutUSButton.setkEndColor(new Color(0,204,204));
    }//GEN-LAST:event_myAuctionsButtonActionPerformed

    private void myBidsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myBidsButtonActionPerformed
        tabs.setSelectedIndex(3);
        DefaultTableModel dm = (DefaultTableModel) myBidsTable.getModel();
        int rowCount = dm.getRowCount();
        if(rowCount > 0){
           for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
            } 
        }
        try {
            GetMyBids();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
//        myBidsButton.setkStartColor(new Color(153,0,153));
//        myBidsButton.setkEndColor(new Color(0,255,204));
//        auctionsButton.setkStartColor(new Color(0,153,153));
//        auctionsButton.setkEndColor(new Color(0,204,204));
//        createAuctionButton.setkStartColor(new Color(0,153,153));
//        createAuctionButton.setkEndColor(new Color(0,204,204));
//        myAuctionsButton.setkStartColor(new Color(0,153,153));
//        myAuctionsButton.setkEndColor(new Color(0,204,204));
//        aboutUSButton.setkStartColor(new Color(0,153,153));
//        aboutUSButton.setkEndColor(new Color(0,204,204));
    }//GEN-LAST:event_myBidsButtonActionPerformed

    private void insertImgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertImgBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG", "png","jpeg","jpg");
        fileChooser.addChoosableFileFilter(fnwf);
        int load = fileChooser.showOpenDialog(null);
        
        if(load==fileChooser.APPROVE_OPTION){
            f = fileChooser.getSelectedFile();
            
            path = f.getAbsolutePath();
            ImageIcon ii = new ImageIcon(path);
            Image img = ii.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            labelImage.setIcon(new ImageIcon(img));
        }
    }//GEN-LAST:event_insertImgBtnActionPerformed

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
       try {                                          
            String title = titleTxt.getText().toString();
            String description = descriptionTxt.getText().toString();
            String startPrice = startPriceInput.getText().toString();
            
            if (title.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter your Item Name.");
            } else if (f == null){
                JOptionPane.showMessageDialog(null, "Please insert an Image.");
            } else if (description.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter your Item Description.");
            } else if (startPrice.equals("")){
                JOptionPane.showMessageDialog(null, "Please enter your Item Start Price.");
            } else {
                BufferedImage bImage;
                bImage = ImageIO.read(f);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                byte[] imgData = bos.toByteArray();
                try (Socket socket = new Socket(ipAddress, 1234)) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    CreateAuctionRequest req = new CreateAuctionRequest(
                        userId,
                        title,
                        startPrice,
                        description,
                        imgData);
                    oos.writeObject(req);
                    oos.flush();
                    
                    String res = in.readLine();
                    JOptionPane.showMessageDialog(null, res);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TabbedPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitBtnActionPerformed

    public void GetMyAuctions() throws ClassNotFoundException {
        try (Socket socket = new Socket(ipAddress, 1234)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            GetMyAuctionsRequest req = new GetMyAuctionsRequest(userId);
            oos.writeObject(req);
            oos.flush();

            Object obj = ois.readObject();
            GetMyAuctionsResponse response;

            if (obj.getClass().getName().equals("client.GetMyAuctionsResponse")
                && (response = (GetMyAuctionsResponse) obj) != null) {

                if(response.auctionList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Nothing to show");
                } else {
                    DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
                    myAuctionsTable.setRowHeight(170);
                    Object[] row = new Object[6];
                    for (int i = 0; i < response.auctionList.size(); i++) {
                        row[0] = i + 1;
                        row[1] = response.auctionList.get(i).title;
                        row[2] = response.auctionList.get(i).startPrice;
                        row[3] = response.auctionList.get(i).status;
                        row[4] = "<html>" + response.auctionList.get(i).description + "</html>";
                        row[5] = new ImageIcon(response.auctionList.get(i).img);
                        model.addRow(row);
                    }
                }
            }
            
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void GetMyBids() throws ClassNotFoundException{
        try (Socket socket = new Socket(ipAddress, 1234)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            GetMyBidsRequest req = new GetMyBidsRequest(userId);
            oos.writeObject(req);
            oos.flush();

            Object obj = ois.readObject();
            GetMyBidsResponse response;

            if (obj.getClass().getName().equals("client.GetMyBidsResponse")
                && (response = (GetMyBidsResponse) obj) != null) {
                if(response.myBidsList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Nothing to show");
                } else {
                    DefaultTableModel model = (DefaultTableModel) myBidsTable.getModel();
                    myBidsTable.setRowHeight(170);
                    Object[] row = new Object[4];
                    for (int i = 0; i < response.myBidsList.size(); i++) {
                        row[0] = i + 1;
                        row[1] = response.myBidsList.get(i).bidAmount;
                        row[2] = response.myBidsList.get(i).AuctionTitle;
                        row[3] = new ImageIcon(response.myBidsList.get(i).AuctionImg);
                        model.addRow(row);
                    }
                }
            }
            
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void auctionPanelMouseEntered(java.awt.event.MouseEvent evt) {                                    
        evt.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }  
    
    private void auctionPanelMouseExited(java.awt.event.MouseEvent evt) {                                    
        evt.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }  
    
    private void auctionPanelMouseClicked(java.awt.event.MouseEvent evt) throws IOException, InterruptedException {                                               
        this.hide();
        new AuctionById(auctionId, userId, ipAddress).setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AppTitle;
    private com.k33ptoo.components.KButton auctionsButton;
    private keeptoo.KGradientPanel auctionsPanel;
    private javax.swing.JScrollPane auctionsScroll;
    private com.k33ptoo.components.KButton createAuctionButton;
    private com.k33ptoo.components.KGradientPanel createAuctionPanel;
    private javax.swing.JScrollPane descriptionScroll;
    private javax.swing.JTextArea descriptionTxt;
    private javax.swing.JLabel emptyLabel;
    private keeptoo.KGradientPanel header;
    private javax.swing.JLabel headerLabel;
    private com.k33ptoo.components.KButton insertImgBtn;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel labelImage;
    private com.k33ptoo.components.KButton logOutButton;
    private keeptoo.KGradientPanel myAuctionPanel;
    private com.k33ptoo.components.KButton myAuctionsButton;
    private javax.swing.JTable myAuctionsTable;
    private javax.swing.JScrollPane myAuctionsTableScroll;
    private com.k33ptoo.components.KButton myBidsButton;
    private keeptoo.KGradientPanel myBidsPanel;
    private javax.swing.JScrollPane myBidsScroll;
    private javax.swing.JTable myBidsTable;
    private com.k33ptoo.components.KGradientPanel navbar;
    private keeptoo.KGradientPanel scrollablePanel;
    private javax.swing.JTextField startPriceInput;
    private javax.swing.JTextField startPriceInput2;
    private com.k33ptoo.components.KButton submitBtn;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTextField titleTxt;
    // End of variables declaration//GEN-END:variables
}
