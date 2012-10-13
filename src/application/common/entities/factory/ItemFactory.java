package application.common.entities.factory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import application.common.entities.Item;


public class ItemFactory {
	
	public static Item getItem(Node itemTagNode) {
		
		Item theItem = null;
		
		if (itemTagNode.getNodeType() == Node.ELEMENT_NODE) {
		
			Element eElement = (Element) itemTagNode;
			String name = eElement.getAttribute("name");
			int auctionInitPrice = Integer.parseInt(eElement.getAttribute("auctionInitPrice").toString());
			int bidInitPrice = Integer.parseInt(eElement.getAttribute("bidInitPrice").toString());
			int id = Integer.parseInt(eElement.getAttribute("id").toString());
			theItem = new Item(name, auctionInitPrice, bidInitPrice, id);
		}
		
		return theItem;
	}
	
	public static Item getItem(String name, int auctionInitPrice, int bidInitPrice, int id) {
		return new Item(name, auctionInitPrice, bidInitPrice, id);
	}

}
