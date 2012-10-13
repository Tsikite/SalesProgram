package application.managers.ui;

public interface IUserInterfaceManager {

	void auctionRoomSaleActive(String SaleName,String item);
	void auctionRoomSaleDeactive(String SaleName,String item);
	void auctionRoomGotAnOffer(String SaleName,String userName,int price,String item);
	void auctionRoomDeclareWinner(String SaleName,String userName,int price,String item);
        void auctionRoomDeclareNoWinner(String SaleName, String item);
	//*****************************************************************************************
	void bidRoomSaleActive(String SaleName,String item);
	void bidRoomSaleDeactive(String SaleName,String item);
	void bidRoomGotAnOffer(String SaleName,String userName,int price,String item);
	void bidRoomDeclareWinner(String SaleName,String userName,int price,String item);
        void bidRoomDeclareNoWinner(String SaleName, String item);
	//*****************************************************************************************
	void userAddOffer(String userName,String itemName,int price);
	//*****************************************************************************************
}