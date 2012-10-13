package application.common.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Offer {


	Calendar cal;
	SimpleDateFormat sdf;
	
	User theUser;
	int price;
	
	public Offer(User theUser, int price) {
		setTheUser(theUser);
		setPrice(price);
		cal = Calendar.getInstance();
		sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
	}


	public String getOfferTime() {
		return sdf.format(cal.getTime());
	}

	public User getTheUser() {
		return theUser;
	}

	public void setTheUser(User theUser) {
		this.theUser = theUser;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return theUser + " with the price " + price + " set on " + getOfferTime();
	}
}
