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
        List<SaleRoom> sales = SalesManager.getAllSalesList();
        for (int i = 0; i < sales.size(); i++) {
            SalesManager.endSale(sales.get(i));
        }
        
        DataManager.writer.saveDataToDestination();
    }
    
}
