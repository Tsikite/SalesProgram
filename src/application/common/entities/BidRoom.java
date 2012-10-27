package application.common.entities;

import application.common.entities.factory.UserFactory;
import application.common.timerTasks.SaleKiller;
import application.managers.data.SalesManager;
import application.managers.log.LoggerManager;
import application.managers.ui.UserInterfaceManager;
import java.util.Timer;

public class BidRoom extends SaleRoom {

    private static int BidSerial = 0;

    // **************************
    public BidRoom(int itemId) {
        super(itemId);
        type = SalesManager.TYPE_BID;

    }

    public BidRoom(Item item) {
        super(item);
        type = SalesManager.TYPE_BID;

    }

    public BidRoom() {
        type = SalesManager.TYPE_BID;

    }

    @Override
    public void run() {
        init(); // It's here because the timer should start counting as the thread starts
        UserInterfaceManager.getInstance().bidRoomSaleActive(logger_tag, getItem().getName());
        super.run();
        if (this.getAllOffers().size() > 1) {
//			System.out.println("The winner is "
//					+ getWinner().theUser.getUserName() + " "
//					+ getWinner().getPrice() + " item: " + item.getName());
            UserInterfaceManager.getInstance().bidRoomDeclareWinner(
                    logger_tag, getWinner().theUser.getUserName(),
                    getWinner().getPrice(), getItem().getName());
        }
        UserInterfaceManager.getInstance().bidRoomSaleDeactive(logger_tag, getItem().getName());
    }

    public void init() {

        logger_tag = "Bid-" + BidSerial++;
        logger = LoggerManager.getInstance().getBidRoomLogger(logger_tag);
        logger.info(logger_tag + " Bid was created - time "
                + getLifeTime() + " seconds");

        // Setting a timer. When time is over - the TimeTask will set the sale
        // as inactive.
        timer = new Timer();
        timer.schedule(new SaleKiller(this),
                getLifeTime() * 1000);

        // Setting a dummy offer with the starting price.
        //initOffers();
    }

    @Override
    public void initOffers() {
        getAllOffers().add(new Offer(UserFactory.getUser("Dummy", null), getItem().getBidInitPrice()));
        setWinner(getAllOffers().peek());
    }

    @Override
    public synchronized boolean makeAnOffer(User theUser, int price) {
        if (isActive()) {

            UserInterfaceManager.getInstance().bidRoomGotAnOffer(logger_tag, theUser.getName(), price, getItem().getName());

            // Adding the Offer to the offers container
            getAllOffers().add(new Offer(theUser, price));

            // If the current offer is higher than the current winning offer,
            // set it as the winning one.
            if (getAllOffers().peek().price > getWinner().price) {
                setWinner(getAllOffers().peek());
            }
            return true;
        }
        return false;
    }
}
