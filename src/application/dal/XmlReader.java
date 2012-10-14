package application.dal;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.common.exceptions.ReaderInitException;

public class XmlReader implements IDataReader {

	/**
	 * This class execute the call for xml files.
	 */
	
	private Document itemsDoc;
	private Document configDoc;
	private Document usersDoc;
        private Document salesDoc;
	
	
	public XmlReader() {

	}

	@Override
	public void Init() throws ReaderInitException {

		/*
		 * Reading all xml data files and creating the document to start manipulate the data
		 */
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			
			File auctionsFile = new File("DataBase/items.xml");
			itemsDoc = dBuilder.parse(auctionsFile);
			itemsDoc.getDocumentElement().normalize();
			
			File configFile = new File("DataBase/config.xml");
			configDoc = dBuilder.parse(configFile);
			configDoc.getDocumentElement().normalize();
			
			File usersFile = new File("DataBase/users.xml");
			usersDoc = dBuilder.parse(usersFile);
			usersDoc.getDocumentElement().normalize();
                        
                        File salesFile = new File("DataBase/sales.xml");
                        salesDoc = dBuilder.parse(salesFile);
                        salesDoc.getDocumentElement().normalize();
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public NodeList getConfiguration() {
		NodeList nList = configDoc.getElementsByTagName("system");
		return nList;
	}

	@Override
	public NodeList getItems() {
		NodeList nList = itemsDoc.getElementsByTagName("item");
		return nList;
	}

	@Override
	public NodeList getUsers() {
		NodeList nList = usersDoc.getElementsByTagName("user");
		return nList;
	}
	
	public NodeList getNull() {
		NodeList nList = usersDoc.getElementsByTagName("noname");
		return nList;
	}
	
        @Override
	public NodeList getSales() {
            NodeList nList = salesDoc.getElementsByTagName("bean");
            return nList;
        }
}
