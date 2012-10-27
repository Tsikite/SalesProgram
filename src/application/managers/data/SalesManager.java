package application.managers.data;

import application.common.entities.Item;
import application.common.entities.SaleRoom;
import application.common.entities.factory.SaleFactory;
import application.common.exceptions.SaleTypeException;
import application.common.timerTasks.EndOfDay;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SalesManager {

    public static final int TYPE_AUCTION = 0;
    public static final int TYPE_BID = 1;
    private static TreeMap<Integer, SaleRoom> allSalesTree = new TreeMap<>();
    private static List<SaleRoom> activeSalesList = new LinkedList<>();
    private static List<SaleRoom> finishedSalesList = new LinkedList<>();
    private static boolean salesAreOn = true;
    private static ExecutorService salesLimiter;

    public static void createNewSaleRoom(int saleType, Item item) throws SaleTypeException {
        if (saleType != TYPE_AUCTION && saleType != TYPE_BID) {
            throw new SaleTypeException("Unknown sale type");
        } else {
            SaleRoom theSale = saleType == TYPE_BID ? SaleFactory.getNewBid(item) : SaleFactory.getNewAuction(item);
            theSale.setItemId(item.getId());
            allSalesTree.put(theSale.getSaleId(), theSale);
            activeSalesList.add(theSale);
            theSale.activateRoom();
        }
    }

    public static void closeSalesDay() {
        salesAreOn = false;
    }

    public void shutDownWaitingAuctions() {
        List<Runnable> waitingAuctions = salesLimiter.shutdownNow();

        for (int i = 0; i < waitingAuctions.size(); i++) {
            DataManager.writer.appendSale(((SaleRoom) waitingAuctions.get(i)));

        }
    }

    public static boolean isSalesDayOn() {
        return salesAreOn;
    }

    public static SaleRoom getSale(int saleId) {
        return allSalesTree.get(saleId);
    }

    public static synchronized List<SaleRoom> getAllActiveSales() {
        // This is mainly for a user who wants to make an offer to know all the current available sales
        List<SaleRoom> activeSales = new LinkedList<>();

        for (SaleRoom sale : getAllSalesList()) {
            if (sale.isActive()) {
                activeSales.add(sale);
            }
        }

        return activeSales;
    }

    public static void startAllSales() {
        for (SaleRoom sale : getAllSalesList()) {
            if (!sale.isAlive()) // The condition allows to add sales in runtime and start them using this method.
            {
                sale.start();
            }
        }
    }

    public static synchronized void setAllSalesList(List<SaleRoom> allSalesList) {
        SalesManager.activeSalesList = allSalesList;
    }

    public static synchronized List<SaleRoom> getAllSalesList() {
        return activeSalesList;
    }

    public static void start(boolean startThread) {

        salesLimiter = Executors.newFixedThreadPool(DataManager.configuration.getMaxAuctionAtATime());

        ApplicationContext context = new FileSystemXmlApplicationContext("DataBase/sales.xml");
        SaleRoom saleRoom;

        for (String saleName : DataManager.salesNames) {
            saleRoom = (SaleRoom) context.getBean(saleName);
            activeSalesList.add(saleRoom);
            allSalesTree.put(saleRoom.getItemId(), saleRoom);

            if (startThread) {
                if (saleRoom.getType() == TYPE_AUCTION) {
                    salesLimiter.execute(saleRoom);
                } else {
                    saleRoom.start();
                }
            } else {
                saleRoom.activateRoom();
            }
        }

        Timer endOfDay = new Timer();
        endOfDay.schedule(new EndOfDay(), 10000);
    }

    private static void shutDownExecutor() {
        salesLimiter.shutdownNow();
    }

    public static synchronized void moveSaleToFinishedList(SaleRoom theSale) {

        activeSalesList.remove(theSale);
        System.err.println("The sale " + theSale.getName() + " has been moved to finished sales. (" + activeSalesList.size() + ")");
        finishedSalesList.add(theSale);
    }

    public static void printSalesList() {
        System.out.println("***********************************");
        for (int i = 0; i < activeSalesList.size(); i++) {
            System.out.println("(" + i + ")" + activeSalesList.get(i).showType() + " --- " + activeSalesList.get(i).getItem().getName() + "(" + (activeSalesList.get(i).isAlive() ? "Alive" : "Dead") + ", " + activeSalesList.get(i).getState().toString() + ", " + (activeSalesList.get(i).isActive() ? "Active" : "Inactive") + ")");
        }
        System.out.println("***********************************");
    }

    public static boolean isAllSalesFinished() {

        if (activeSalesList.isEmpty()) {
            return true;
        }

        return false;
    }

    public static void endSale(SaleRoom sale) {
        System.out.println("-----------***  endSale()-" + sale.getName() + "  ***-------------");
        sale.deactivateRoom();
        DataManager.writer.appendSale(sale);
    }
}
