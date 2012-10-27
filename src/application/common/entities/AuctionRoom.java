package application.common.entities;

import application.common.timerTasks.SaleExtend;
import application.common.timerTasks.SaleKiller;
import application.managers.data.DataManager;
import application.managers.data.SalesManager;
import application.managers.log.LoggerManager;
import application.managers.ui.UserInterfaceManager;
import java.util.Timer;

public class AuctionRoom extends SaleRoom {

    private static int AuctionSerial = 0;
    // **************************
    private boolean timeExtendMode = false;

    public AuctionRoom(int itemId) {
        super(itemId);
        type = SalesManager.TYPE_AUCTION;
    }

    public AuctionRoom(Item item) {
        super(item);
        type = SalesManager.TYPE_AUCTION;
    }

    public AuctionRoom() {
        type = SalesManager.TYPE_AUCTION;
    }

    public void init() {
        logger_tag = "Auction - " + AuctionSerial++;
        logger = LoggerManager.getInstance().getAuctionRoomLogger(logger_tag);
        logger.info(logger_tag + " Auction was created - time "
                + getLifeTime() + " seconds");

        timer = new Timer();
        // When the auction enters the time extend mode, every offer sets the
        // timer to the
        // time configured in the configuration as auction time extend
        // The auction will enter extend mode when the timer is lower than the
        // auction time extend
        if (getLifeTime() <= DataManager.configuration
                .getAuctionTimeExtend()) {
            timer.schedule(new SaleKiller(this),
                    DataManager.configuration.getAuctionTimeExtend() * 1000);
        } else {    // Setting a timer. When time is over - the TimeTask will set the
            // sale as inactive.
            timer.schedule(new SaleExtend(this),
                    getLifeTime() * 1000
                    - DataManager.configuration.getAuctionTimeExtend()
                    * 1000);
        }

        //initOffers();
    }

    @Override
    public void run() {
        init();

        UserInterfaceManager.getInstance().auctionRoomSaleActive(logger_tag, getItem().getName());
        super.run();

        // The auction will have minimum time variable. if an offer is made when
        // the time is below minimum,
        // end time will reset to minimum time. (i.e if the minimum time is 10
        // seconds and an offer was made
        // 5 seconds before the auction closes, the end time will reset to 10
        // seconds again.
        // The auction will end if no offer has been made in the last 10 seconds
        // of the sale).
        if (this.getAllOffers().size() > 1) {
//			System.out.println("The winner is "
//					+ getWinner().theUser.getUserName() + " "
//					+ getWinner().getPrice() + " item: " + item.getName());
            UserInterfaceManager.getInstance().auctionRoomDeclareWinner(
                    logger_tag, getWinner().theUser.getUserName(),
                    getWinner().getPrice(), getItem().getName());
        } else if (this.getAllOffers().size() == 1) {
            UserInterfaceManager.getInstance().auctionRoomDeclareNoWinner(logger_tag, getItem().getName());
        }
        UserInterfaceManager.getInstance().auctionRoomSaleDeactive(logger_tag, getItem().getName());
    }

    @Override
    public void initOffers() {
        getAllOffers().add(
                new Offer(new User("Dummy", null), getItem().getAuctionInitPrice()));
        setWinner(getAllOffers().peek());
    }

    public void turnOnTimeExtend() {
        timeExtendMode = true;
    }

    @Override
    public synchronized boolean makeAnOffer(User theUser, int price) {
        if (isActive()) {

            UserInterfaceManager.getInstance().auctionRoomGotAnOffer(logger_tag, theUser.getName(), price, getItem().getName());

            if (timeExtendMode) {
                reSchedualTimer(DataManager.configuration
                        .getAuctionTimeExtend() * 1000);
                System.out.println("Time extended");
            }

            getAllOffers().add(new Offer(theUser, price));

            // In case there where other offers before that, if the current
            // offer is higher than the winning offer, set it as the winning
            // one.
            if (price > getWinner().price) {

                // Adding the Offer to the offers container
                setWinner(getAllOffers().peek());
            }
            return true;
        } else {
            return false;
        }
    }

    public int getHighestPrice() {
        return getWinner().getPrice();
    }
}
