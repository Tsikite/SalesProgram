package application.common.entities;

import application.managers.data.SalesManager;
import application.managers.log.LoggerManager;
import application.managers.ui.UserInterfaceManager;
import java.util.logging.Logger;

public class User extends Thread {

    private final int MAX_NUM_OF_BIDS = 40;
    private final int MIN_NUM_OF_BIDS = 20;
    private static int idGenerator = 1000;
    private int id;
    private String userName;
    private String whileWaiting;
    // **************************
    private Logger logger;
    // **************************

    public User(String userName, String whileWaiting) {

        setUserName(userName);
        setWhileWaiting(whileWaiting);
        logger = LoggerManager.getInstance().getUserLogger(userName);
        id = idGenerator++;
        logger.info(id + " " + userName + "User created");

    }

    @Override
    public void run() {

        this.setName("Thread-user" + getId());
        int size;
        int price;
        int currentPrice;
        int numOfBids = (int) (Math.random() * (MAX_NUM_OF_BIDS - MIN_NUM_OF_BIDS) + MIN_NUM_OF_BIDS);
        SaleRoom sale;
        int saleIndex;
        for (int i = 0; (i < numOfBids) && SalesManager.isSalesDayOn(); i++) {

            // Choosing random sale for the current user
            if (SalesManager.isAllSalesFinished()) {
                i = MAX_NUM_OF_BIDS + 1; // ends the user bidding
            }

            size = SalesManager.getAllActiveSales().size();
            if (size > 0) {
                try {
                    saleIndex = (int) Math.floor(Math.random() * size);
                    System.out.println((saleIndex + 1) + " out of " + size);
                    sale = SalesManager.getAllActiveSales().get(saleIndex);


                    if (sale.type == SalesManager.TYPE_BID) {
                        price = ((int) (Math.random() * sale.getItem().getBidInitPrice()))
                                + sale.getItem().getBidInitPrice();
                        sale.makeAnOffer(this, price);
//                        System.out.println(getUserName()
//                                + " has made an offer to BidRoom for item "
//                                + sale.getItem().getName() + " - " + price);
                    } else {
                        currentPrice = ((AuctionRoom) sale).getHighestPrice();
                        price = ((int) (Math.random() * currentPrice * 0.15)) + currentPrice;
                        sale.makeAnOffer(this, price);
//                        System.out.println(getUserName()
//                                + " has made an offer to AuctionRoom "
//                                + sale.getItem().getName() + " - " + price);
                    }
                    UserInterfaceManager.getInstance().userAddOffer(getUserName(),
                            sale.getItem().getName(), price);



                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sorry, there are no more available sales at the moment (" + e.getMessage() + ")");
                }
            }

            delay(10000, 5000);
        }
    }

    public void delay(int max, int min) {
        try {
            sleep((int) (Math.random() * (max - min) + min));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getUserId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getWhileWaiting() {
        return whileWaiting;
    }

    public void setWhileWaiting(String whileWaiting) {
        this.whileWaiting = whileWaiting;
    }

    @Override
    public String toString() {
        return "\nName: name " + userName + " While waiting action: "
                + whileWaiting;
    }
}
