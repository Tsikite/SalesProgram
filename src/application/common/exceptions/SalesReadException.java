/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.common.exceptions;

import application.common.utils.TagNames;

/**
 *
 * @author Tsikon
 */
public class SalesReadException extends Exception {
    public SalesReadException(String message) {
		super(TagNames.T_EXCEPTION_SALES_READ + message);
	}
}
