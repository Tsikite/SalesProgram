/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.dal;

import application.common.entities.SaleRoom;

/**
 *
 * @author Tsikon
 */
public interface IDataWriter {
    public void appendSale(SaleRoom sale);
    public void saveDataToDestination();
}
