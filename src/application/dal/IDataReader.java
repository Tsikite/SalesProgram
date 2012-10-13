package application.dal;

import org.w3c.dom.NodeList;

import application.common.exceptions.ItemsReadException;
import application.common.exceptions.ConfigurationReadException;
import application.common.exceptions.ReaderInitException;
import application.common.exceptions.SalesReadException;
import application.common.exceptions.UsersReadException;

public interface IDataReader {

	// Give the methods to deal with reading data from various places.
	
	void Init() throws ReaderInitException;
	
	NodeList getConfiguration() throws ConfigurationReadException;
	
	NodeList getItems() throws ItemsReadException;
	
	NodeList getUsers() throws UsersReadException;
        
        NodeList getSales() throws SalesReadException;
	
}
