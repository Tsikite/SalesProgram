package application.common.entities.factory;

import application.common.entities.AuctionRoom;
import application.common.entities.BidRoom;
import application.common.entities.Item;

public class SaleFactory {

    public static BidRoom getNewBid(Item item) {
        return new BidRoom(item);
    }

    public static AuctionRoom getNewAuction(Item item) {
        return new AuctionRoom(item);
    }
}
