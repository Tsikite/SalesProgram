package application.dal;

import application.common.exceptions.ReaderInitException;
import application.common.exceptions.SalesReadException;
import org.w3c.dom.NodeList;

public class DataBaseReader implements IDataReader {

	/**
	 * This class execute the call for the database.
	 */
	public DataBaseReader()  {

	}

	@Override
	public void Init() throws ReaderInitException {
	
		
	}

	@Override
	public NodeList getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public NodeList getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public NodeList getSales() throws SalesReadException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
