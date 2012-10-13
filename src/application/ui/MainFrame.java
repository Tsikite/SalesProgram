/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.ui;

import application.common.entities.AuctionRoom;
import application.common.entities.Item;
import application.common.entities.Offer;
import application.common.entities.SaleRoom;
import application.common.entities.User;
import application.common.exceptions.SaleTypeException;
import application.managers.data.DataManager;
import application.managers.data.SalesManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tsikon
 */
public class MainFrame extends JFrame {

    private int width;
    private int height;
    // Active sales table
    private UnEditableTableModel saleTableModel;
    private JTable tableSales;
    // Users on the selected sale table
    private DefaultTableModel usersTableModel;
    private JTable tableUsers;
    // Sale info
    private JTextPane textArea;
    // Register new user - need to implement listener.
    private JTextField usernameField;
    private JButton registerButton;
    private JLabel newUserMessage;
    // Add new sale
    //private JTextField salenameField;
    private JTextField itemnameField;
    private JRadioButton auctionRButton, bidRButton;
    private JButton addButton;
    private JTextField initPriceField;
    private JLabel newSaleMessaage;
    // Make an offer
    private JButton placeOfferButton;
    private JTextField priceTextField;
    private JComboBox chosenUser;
    private DefaultComboBoxModel allusersCBoxModel;
    private JLabel makeOfferMassage;
    // End sale
    private JButton saleStopButton;

    public MainFrame() throws HeadlessException {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        width = 890;
        height = 600;

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Defining look size and location of main frame
        setLookAndFeel();
        this.setTitle("Sale Program");

        this.setSize(width, height);
        setLocationRelativeTo(null);

        // Defining main panel
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        mainPanel.add(getActiveSalesPanel());
        mainPanel.add(getSaleUsersPanel());
        mainPanel.add(getRegisterNewUserPanel());
        mainPanel.add(getAddNewSalePanel());
        mainPanel.add(getSaleInfoPanel());
        mainPanel.add(getMakeAnOfferPanel());
        mainPanel.add(getSaleStopButton());
        
        setJMenuBar(getMyMenuBar());

        initGUIComponenets();

        this.add(mainPanel);
        tableUsers.requestFocus();

        setVisible(true);
        
        updateSaleTable();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JPanel getSaleUsersPanel() {

        JPanel registeredUsersPanel;
        JScrollPane scrollerSales;

        usersTableModel = new DefaultTableModel();
        tableUsers = new JTable(usersTableModel);
        tableUsers.setEnabled(false);
        tableUsers.setRowHeight(30);
        scrollerSales = new JScrollPane(tableUsers);
        registeredUsersPanel = new JPanel();

        tableUsers.setPreferredScrollableViewportSize(new Dimension((int) (width * 0.18), (int) (height * 0.60)));
        scrollerSales.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        registeredUsersPanel.setBorder(BorderFactory.createTitledBorder("Users currently on the sale"));
        registeredUsersPanel.add(scrollerSales);


        //registeredUsersPanel.setPreferredSize(new Dimension((int) (width * 0.20), (int) (height * 0.70)));

        // init table model with data
        usersTableModel.setColumnIdentifiers(new Object[]{"Users"});


        return registeredUsersPanel;
    }

    private JPanel getActiveSalesPanel() {

        JScrollPane scrollerSales;
        JPanel itemsPanel;
        
        saleTableModel = new UnEditableTableModel();
        tableSales = new JTable(saleTableModel);
        tableSales.setRowHeight(30);
        scrollerSales = new JScrollPane(tableSales);
        itemsPanel = new JPanel();
        
        tableSales.setPreferredScrollableViewportSize(new Dimension((int) (width * 0.18), (int) (height * 0.60)));
        scrollerSales.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Current Active Sales"));
        itemsPanel.add(scrollerSales);

        saleTableModel.setColumnIdentifiers(new Object[]{"Sale Name"});
        return itemsPanel;
    }

    private JPanel getRegisterNewUserPanel() {

        JPanel regNewUserPanel;

        regNewUserPanel = new JPanel();

        regNewUserPanel.setBorder(BorderFactory.createTitledBorder("Register New User"));

        regNewUserPanel.setPreferredSize(new Dimension((int) (width * 0.25), (int) (height * 0.70)));

        JLabel username = new JLabel("User name: ");
        usernameField = new JTextField();
        registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.setPreferredSize(new Dimension((int) (width * 0.20), 22));
        newUserMessage = new JLabel();

        usernameField.setPreferredSize(new Dimension((int) (width * 0.16), 22));

        regNewUserPanel.add(username);
        regNewUserPanel.add(usernameField);
        regNewUserPanel.add(registerButton);
        regNewUserPanel.add(newUserMessage);

        return regNewUserPanel;
    }

    private JPanel getAddNewSalePanel() {

        JPanel addNewSalePanel;

        addNewSalePanel = new JPanel();

        addNewSalePanel.setBorder(BorderFactory.createTitledBorder("Add New Sale"));

        addNewSalePanel.setPreferredSize(new Dimension((int) (width * 0.25), (int) (height * 0.70)));

        //JLabel salename = new JLabel("Sale name: ");
        //salenameField = new JTextField();
        JLabel itemname = new JLabel("Item name: ");
        itemnameField = new JTextField();

        addButton = new JButton();
        addButton.setText("Add");

        JLabel saleTypeLabel = new JLabel("Sale type: ");
        ButtonGroup buttonGroup = new ButtonGroup();
        auctionRButton = new JRadioButton("Auction");
        buttonGroup.add(auctionRButton);
        bidRButton = new JRadioButton("Bid");
        buttonGroup.add(bidRButton);
        auctionRButton.setSelected(false);
        bidRButton.setSelected(false);

//        JLabel saleTimeLabel = new JLabel("Sale time: ");
//        String[] timesList = {"Choose time", "5", "10", "15", "20", "25", "30"};
//        JComboBox times;
//        times = new JComboBox(timesList);
//        times.setPreferredSize(new Dimension((int)(width*0.11), 22));
//        times.setSelectedIndex(0);

        JLabel initPriceLabel = new JLabel("Initial price: ");
        initPriceField = new JTextField();


        addButton.setPreferredSize(new Dimension((int) (width * 0.20), 22));
        //salenameField.setPreferredSize(new Dimension((int) (width * 0.16), 22));
        itemnameField.setPreferredSize(new Dimension((int) (width * 0.16), 22));
        initPriceField.setPreferredSize(new Dimension((int) (width * 0.1), 22));

        newSaleMessaage = new JLabel();

        //addNewSalePanel.add(salename);
        //addNewSalePanel.add(salenameField);
        addNewSalePanel.add(itemname);
        addNewSalePanel.add(itemnameField);
        addNewSalePanel.add(saleTypeLabel);
        addNewSalePanel.add(auctionRButton);
        addNewSalePanel.add(bidRButton);
//        addNewSalePanel.add(saleTimeLabel);
//        addNewSalePanel.add(times);
        addNewSalePanel.add(initPriceLabel);
        addNewSalePanel.add(initPriceField);
        addNewSalePanel.add(addButton);
        addNewSalePanel.add(newSaleMessaage);

        return addNewSalePanel;
    }

    private JPanel getMakeAnOfferPanel() {
        JPanel activeSalesPanel = new JPanel();
        placeOfferButton = new JButton();
        priceTextField = new JTextField();
        makeOfferMassage = new JLabel();

        priceTextField.setPreferredSize(new Dimension((int) (width * 0.15), 22));

        activeSalesPanel.setBorder(BorderFactory.createTitledBorder("Make An Offer Here"));
        activeSalesPanel.setPreferredSize(new Dimension((int) (width * 0.30), (int) (height * 0.23)));

        placeOfferButton.setText("Place An Offer");

        String[] usersList = {"Choose user"};

        allusersCBoxModel = new DefaultComboBoxModel(usersList);
        chosenUser = new JComboBox(allusersCBoxModel);
        chosenUser.setPreferredSize(new Dimension((int) (width * 0.11), 22));
        chosenUser.setSelectedIndex(0);
        makeOfferMassage.setPreferredSize(new Dimension((int) (width * 0.23), 22));


        activeSalesPanel.add(chosenUser);
        activeSalesPanel.add(priceTextField);
        activeSalesPanel.add(placeOfferButton);
        activeSalesPanel.add(makeOfferMassage);


        return activeSalesPanel;
    }

    private JPanel getSaleInfoPanel() {
        JPanel infoPanel = new JPanel();

        infoPanel.setBorder(BorderFactory.createTitledBorder("Sale Info"));
        //infoPanel.setPreferredSize(new Dimension((int) (width * 0.30), (int) (height * 0.23)));

        textArea = new JTextPane();
        textArea.setPreferredSize(new Dimension((int) (width * 0.39), (int) (height * 0.17)));

        textArea.setAutoscrolls(true);
        textArea.setEditable(false);

        infoPanel.add(textArea);

        return infoPanel;
    }

    private JPanel getSaleStopButton() {
        JPanel buttonPanel = new JPanel();
        saleStopButton = new JButton();

        saleStopButton.setPreferredSize(new Dimension((int) (width * 0.25), (int) (height * 0.17)));
        saleStopButton.setText("End Sale");
        buttonPanel.add(saleStopButton);

        return buttonPanel;
    }

    private void addSaleToTable(SaleRoom sale) {
        String itemName = sale.getItem().getName();
        saleTableModel.addRow(new Object[]{itemName});
    }

    private void registerNewUser(User user) {
        String userName = user.getUserName();

        chosenUser.addItem(userName);
    }

    private void initRegUserComponents() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (usernameField.getText().length() == 0) {
                    newUserMessage.setText("Please fill in your name.");
                } else {
                    User newUser = new User(usernameField.getText(), "nothing");
                    newUser.setUserName(usernameField.getText());
                    DataManager.registerNewUser(newUser);
                    allusersCBoxModel.addElement(newUser.getUserName());
                    newUserMessage.setText(newUser.getUserName() + " succesfully added");
                }
            }
        });
    }
    
    private void initNewSaleComponents() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //String saleName = salenameField.getText();
                String itemName = itemnameField.getText();
                String initPrice = initPriceField.getText();


//                if(saleName.length()==0) {
//                    newSaleMessaage.setText("Please fill sale name.");
//                }
                if (itemName.length() == 0) {
                    newSaleMessaage.setText("Please fill item name.");
                } else if (!auctionRButton.isSelected() && !bidRButton.isSelected()) {
                    newSaleMessaage.setText("Please choose sale type.");
                } else if (initPrice.length() == 0) {
                    newSaleMessaage.setText("Please fill initial price.");
                } else {
                    try {
                        Integer.parseInt(initPrice);
                    } catch (Exception e) {
                        newSaleMessaage.setText("Please fill valid price (Integer).");
                        return;
                    }
                    try {
                        newSaleMessaage.setText("Sale created.");

                        // Generatring uniqe id for the item
                        int id = 0;
                        boolean idOK = false;
                        while (!idOK) {
                            idOK = true;
                            id = (int) (Math.random() * 10000);
                            for (Item item : DataManager.allItems) {
                                if (id == item.getId()) {
                                    idOK = false;
                                }
                            }
                        }

                        Item item = new Item(itemName, Integer.parseInt(initPrice), Integer.parseInt(initPrice), id);
                        DataManager.allItems.add(item);
                        SalesManager.createNewSaleRoom(auctionRButton.isSelected() ? SalesManager.TYPE_AUCTION : SalesManager.TYPE_BID, item);
                        updateSaleTable();

                    } catch (SaleTypeException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private void initSaleStopComponents() {
        saleStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int saleIndex = tableSales.getSelectedRow();
                SaleRoom sale = SalesManager.getAllSalesList().get(saleIndex);
                SalesManager.getAllSalesList().remove(saleIndex);
                updateSaleTable();
                String winningMsg;
                if (sale.getOffersStack().size() > 1) {
                    winningMsg = "Congratulations " + sale.getWinningOffer().getTheUser().getUserName()
                            + ", you have won the item " + sale.getItem().getName() + " for " + sale.getWinningOffer().getPrice();
                } else {
                    winningMsg = "No one has won the item " + sale.getItem().getName();
                }
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        winningMsg,
                        "Sale Ended",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
    
    private void initMakeOfferComponents() {
        for (User user : DataManager.allUsers) {
            registerNewUser(user);
        }

        placeOfferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int saleIndex = tableSales.getSelectedRow();
                if (saleIndex == -1) {
                    makeOfferMassage.setText("Please choose sale");
                } else if (chosenUser.getSelectedIndex() == 0) {
                    makeOfferMassage.setText("Please choose user");
                } else {

                    SaleRoom sale = SalesManager.getAllSalesList().get(saleIndex);
                    int userIndex = chosenUser.getSelectedIndex();
                    User user = DataManager.allUsers.get(userIndex - 1);
                    int price;
                    try {
                        price = Integer.parseInt(priceTextField.getText());
                    } catch (Exception e) {
                        makeOfferMassage.setText("Please fill valid price.");
                        return;
                    }
                    if (sale instanceof AuctionRoom) {
                        if (price <= sale.getItem().getAuctionInitPrice()) {
                            //makeOfferMassage.setText("Please enter price higher than the initial price.");
                            makeOfferMassage.setText("The initial price is " + sale.getItem().getAuctionInitPrice());
                            return;
                        }
                    } else {
                        if (price <= sale.getItem().getBidInitPrice()) {
                            makeOfferMassage.setText("Your price is lower than the initial price.");
                            return;
                        }
                    }

                    sale.makeAnOffer(user, price);
                    updateSaleUsersTable();
                    makeOfferMassage.setText("");
                    setInfoText(sale);

                }
            }
        });
    }
    
    private void initSalesTableComponents() {
        tableSales.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                updateSaleUsersTable();
                SaleRoom selectedSale = getSelectedSale();
                if(selectedSale!=null) {
                    // If sale is selected and the winner is not the dummy offer
                    setInfoText(selectedSale);

                    tableUsers.requestFocus(); // to allow calling this function again.
                }
            }
            

            @Override
            public void focusLost(FocusEvent fe) {
            }
        });
    }
    
    private void initGUIComponenets() {
        
        initRegUserComponents();
        initNewSaleComponents();
        initSaleStopComponents();
        initMakeOfferComponents();
        initSalesTableComponents();
    }

    private void updateSaleTable() {
        while (saleTableModel.getRowCount() > 0) {
            saleTableModel.removeRow(0);
        }
        String name;
        for (int i = 0; i < SalesManager.getAllSalesList().size(); i++) {
            List<SaleRoom> list = SalesManager.getAllSalesList();
            SaleRoom sale = list.get(i);
            int itemId = sale.getItemId();
            Item item = DataManager.getItemById(itemId);
            name = item.getName();
            //name = SalesManager.getAllActiveSales().get(i).getItem().getName();
            saleTableModel.addRow(new Object[]{" " + sale.showType() + " - " + name});
        }
    }

    private void updateSaleUsersTable() {
        while (usersTableModel.getRowCount() > 0) {
            usersTableModel.removeRow(0);
        }
        SaleRoom sale = getSelectedSale();
//        if (sale != null) {
//            Stack<Offer> offers = sale.getOffersStack();
//            for (int i = 1; i < offers.size(); i++) {
//                usersTableModel.addRow(new Object[]{offers.get(i).getTheUser().getUserName()});
//            }
//        }
        if (sale != null) {
            List<User> usersOnTheList = sale.getUsersOnTheSale();
            if(usersOnTheList.size()>0) {
                for (User user : usersOnTheList) {
                    usersTableModel.addRow(new Object[]{user.getUserName()});
                }
            }
        }
    }

    private SaleRoom getSelectedSale() {
        int saleIndex = tableSales.getSelectedRow();
        if (saleIndex > -1) {
            SaleRoom sale = SalesManager.getAllSalesList().get(saleIndex);
            return sale;
        } else {
            makeOfferMassage.setText("Please choose sale");
        }

        return null;
    }

    private void setInfoText(SaleRoom selectedSale) {
        String toWrite;
        if (selectedSale != null && !selectedSale.getOffersStack().get(0).equals(selectedSale.getWinningOffer())) {
            if (selectedSale instanceof AuctionRoom) {
                toWrite = selectedSale.getWinningOffer().getTheUser().getUserName() + " has the highest offer - " + selectedSale.getWinningOffer().getPrice();
            }
            else {
                toWrite = "Initial price is " + selectedSale.getItem().getBidInitPrice();
            }
        }
        else {
            if (selectedSale instanceof AuctionRoom) {
                toWrite = "Initial price is " + selectedSale.getItem().getAuctionInitPrice();
            } else {
                toWrite = "Initial price is " + selectedSale.getItem().getBidInitPrice();
            }
        }
        textArea.setText(toWrite);
        
    }
    
    private JMenuBar getMyMenuBar() {
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        menu.add(menuItem);

        return menuBar;
    }
}
