/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.common.timerTasks;

import application.common.entities.SaleRoom;
import application.managers.data.DataManager;
import application.managers.data.SalesManager;
import java.util.List;
import java.util.TimerTask;

/**
 *
 * @author Tsikon
 */
public class EndOfDay extends TimerTask {

    @Override
    public void run() {
        // Closing sales day
        SalesManager.closeSalesDay();
        
        // Waiting for all users to finish the current offers
        
        
        // Closing all sales
        List<SaleRoom> sales = SalesManager.getAllSalesList();
        
        // Deactivating all the sales and saving it to xml
        while (sales.size()>0) {
                SalesManager.endSale(sales.get(sales.size()-1));
        }
        DataManager.writer.saveDataToDestination();
    }
}
