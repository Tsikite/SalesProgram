package application.common.entities;

import application.common.timerTasks.SaleKiller;
import application.managers.data.DataManager;
import application.managers.data.SalesManager;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SaleRoom extends Thread {

    protected String logger_tag = "";
    protected Logger logger;
    //-----------------------------
    protected int type;
    private static int idGenerator = 1000;
    private int id;
    //protected Item item;
    protected int itemId;
    private Stack<Offer> allOffers = new Stack<>();
    private Offer winner = null;
    // Should become true as the sale thread starts.
    private boolean isActive = false;
    protected Timer timer;
    protected CountDownLatch latch;
    protected int lifeTime;

    public SaleRoom(int itemId) {
        this.setItemId(itemId);
        id = idGenerator++;
    }

    public SaleRoom(Item item) {
        this.setItemId(item.getId());
    }

    public SaleRoom() {
    }

    @Override
    public void run() {
        this.setName(showType() + itemId);
        activateRoom();

        //System.out.println("Sale is active");
        latch = new CountDownLatch(1);
        try {
            if (SalesManager.isSalesDayOn()) {
                latch.await();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SaleRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public Stack<Offer> getOffersStack() {
        return allOffers;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getType() {
        return type;
    }

    public int getSaleId() {
        return id;
    }

    public void stopTimer() {
        timer.cancel();
        //	System.out.println("Timer stopped");
    }

    public List<User> getUsersOnTheSale() {
        List<User> users = new LinkedList<User>();

        for (int i = 1; i < allOffers.size(); i++) {
            if (!users.contains(allOffers.get(i).getTheUser())) {
                users.add(allOffers.get(i).getTheUser());
            }
        }

        return users;
    }

    public void deactivateRoom() {
        //System.out.println("Room Deactivated");

        isActive = false;
        SalesManager.moveSaleToFinishedList(this);
        timer.cancel();
        freeLatch();

    }

    public void activateRoom() {
        initOffers();
        isActive = true;
    }

    abstract public boolean makeAnOffer(User theUser, int price);

    abstract public void initOffers();

    @Override
    public String toString() {
        return "For item " + DataManager.getItemById(itemId).toString() + ", the winner is " + getWinner().toString();
    }

    public void reSchedualTimer(int timeLength) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new SaleKiller(this), timeLength);
    }

    public String showType() {
        if (type == SalesManager.TYPE_BID) {
            return "Bid";
        } else {
            return "Auction";
        }
    }

    public void freeLatch() {
        latch.countDown();
    }

    public Offer getWinningOffer() {
        return getWinner();
    }

    public Item getItem() {
        return DataManager.getItemById(itemId);
    }

    protected synchronized void setWinner(Offer winner) {
        this.winner = winner;
    }

    protected synchronized Offer getWinner() {
        return winner;
    }

    protected synchronized void setAllOffers(Stack<Offer> allOffers) {
        this.allOffers = allOffers;
    }

    protected synchronized Stack<Offer> getAllOffers() {
        return allOffers;
    }

    public boolean isActive() {
        return isActive;
    }
}
