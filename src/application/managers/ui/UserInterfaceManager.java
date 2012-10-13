package application.managers.ui;

public class UserInterfaceManager implements IUserInterfaceManager {

    private UserInterfaceManager() {
    }

    @Override
    public void auctionRoomDeclareNoWinner(String SaleName, String item) {
        System.out.println("auctionRoomDeclareNoWinner: " + "SaleName: "
                + SaleName + ", item: " + item);
    }

    @Override
    public void bidRoomDeclareNoWinner(String SaleName, String item) {
        System.out.println("bidRoomDeclareNoWinner: " + "SaleName: "
                + SaleName + ", item: " + item);
    }

    private static class UserInterfaceManagerHolder {

        private final static UserInterfaceManager INSTANCE = new UserInterfaceManager();
    }

    public static IUserInterfaceManager getInstance() {
        return UserInterfaceManagerHolder.INSTANCE;
    }

    @Override
    public void auctionRoomSaleActive(String SaleName, String item) {
        System.out.println("auctionRoomSaleActive: " + "SaleName: "
                + SaleName + ", item: " + item);

    }

    @Override
    public void auctionRoomSaleDeactive(String SaleName, String item) {
        System.out.println("auctionRoomSaleDeactive: " + "SaleName: "
                + SaleName + ", item: " + item);

    }

    @Override
    public void auctionRoomGotAnOffer(String SaleName, String userName,
            int price, String item) {
        System.out.println("auctionRoomGotAnOffer: " + "SaleName: "
                + SaleName + ", item: " + item + ", user: " + userName
                + ", price: " + String.valueOf(price));

    }

    @Override
    public void auctionRoomDeclareWinner(String SaleName, String userName,
            int price, String item) {
        System.out.println("auctionRoomDeclareWinner: --- WINNER!! ---" + "SaleName: "
                + SaleName + ", item: " + item + ", user: " + userName
                + ", price: " + String.valueOf(price));
    }

    @Override
    public void bidRoomSaleActive(String SaleName, String item) {
        System.out.println("bidRoomSaleActive: " + "SaleName: "
                + SaleName + ", item: " + item);

    }

    @Override
    public void bidRoomSaleDeactive(String SaleName, String item) {
        System.out.println("bidRoomSaleDeactive: " + "SaleName: "
                + SaleName + ", item: " + item);

    }

    @Override
    public void bidRoomGotAnOffer(String SaleName, String userName,
            int price, String item) {
        System.out.println("bidRoomGotAnOffer: " + "SaleName: "
                + SaleName + ", item: " + item + ", user: " + userName
                + ", price: " + String.valueOf(price));

    }

    @Override
    public void bidRoomDeclareWinner(String SaleName, String userName,
            int price, String item) {
        System.out.println("bidRoomDeclareWinner: --- WINNER!! ---" + "SaleName: "
                + SaleName + ", item: " + item + ", user: " + userName
                + ", price: " + String.valueOf(price));

    }

    @Override
    public void userAddOffer(String userName, String itemName, int price) {
        System.out.println("userAddOffer: " + "user: " + userName
                + ", add price - " + String.valueOf(price) + " on item: " + itemName);

    }
}
