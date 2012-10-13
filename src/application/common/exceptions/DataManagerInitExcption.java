package application.common.exceptions;

import application.common.utils.TagNames;

public class DataManagerInitExcption extends Exception{

	public DataManagerInitExcption(String message){
		super(TagNames.T_EXCEPTION_DATA_MANAGER_INIT+message);
		
	}
}
