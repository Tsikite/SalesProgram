package application.managers.data;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import application.common.entities.Configurations;
import application.common.entities.Item;
import application.common.entities.User;
import application.common.entities.factory.ItemFactory;
import application.common.entities.factory.UserFactory;
import application.common.exceptions.ItemsReadException;
import application.common.exceptions.ConfigurationReadException;
import application.common.exceptions.DataManagerInitExcption;
import application.common.exceptions.ReaderInitException;
import application.common.exceptions.SalesReadException;
import application.common.exceptions.UsersReadException;
import application.dal.DataBaseReader;
import application.dal.IDataReader;
import application.dal.XmlReader;
import application.dal.XmlWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager implements IDataManager {

    enum DATA_RESOURCE_TYPE {

        XML, DATA_BASE, SERVER
    };
    private static DATA_RESOURCE_TYPE resourceType = DATA_RESOURCE_TYPE.XML;
    private static IDataReader reader;
    // *********************************************************************
    public static NodeList configNodeList;
    public static NodeList usersNodeList;
    public static NodeList itemsNodeList;
    public static NodeList salesNodeList;
    public static List<Item> allItems = new LinkedList<>();
    public static List<User> allUsers = new LinkedList<>();
    public static List<String> salesNames = new LinkedList<>();
    public static Configurations configuration = Configurations.getInstance();

    public static XmlWriter writer;
    
    public static void Init() throws DataManagerInitExcption {

        switch (resourceType) {

            case XML:
                writer = new XmlWriter();
                reader = new XmlReader();
                break;

            case DATA_BASE:
                reader = new DataBaseReader();
                break;

            case SERVER:
                break;

            default:
                break;
        }

        try {
            reader.Init();
            configNodeList = reader.getConfiguration();
            itemsNodeList = reader.getItems();
            usersNodeList = reader.getUsers();
            salesNodeList = reader.getSales();

            for (int i = 0; i < itemsNodeList.getLength(); i++) {

                Node nNode = itemsNodeList.item(i);
                allItems.add(ItemFactory.getItem(nNode));
            }

            for (int i = 0; i < usersNodeList.getLength(); i++) {

                Node nNode = usersNodeList.item(i);
                allUsers.add(UserFactory.getUser(nNode));
            }

            for (int i = 0; i < salesNodeList.getLength(); i++) {
                Node nNode = salesNodeList.item(i);
                salesNames.add(((Element) nNode).getAttribute("id").toString());
            }


            if (configNodeList.getLength() == 1) { // Only one System tag exists
                Node nNode = configNodeList.item(0);
                configuration.setAuctionTimeExtend(Integer.parseInt(((Element) nNode).getAttribute("auctionTimeExtend")));
                configuration.setMaxAuctionAtATime(Integer.parseInt(((Element) nNode).getAttribute("maxAuctionAtATime")));
                configuration.setSaleTime(Integer.parseInt(((Element) nNode).getAttribute("saleTime")));

            }

        } catch (SalesReadException e) {
            throw new DataManagerInitExcption(e.getMessage());
            //Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReaderInitException e) {
            // TODO - WRITE TO LOG
            throw new DataManagerInitExcption(e.getMessage());
            // e.printStackTrace();
        } catch (ConfigurationReadException e) {
            throw new DataManagerInitExcption(e.getMessage());
            //e.printStackTrace();
        } catch (ItemsReadException e) {
            throw new DataManagerInitExcption(e.getMessage());
            //e.printStackTrace();
        } catch (UsersReadException e) {
            e.printStackTrace();
        }

    }

    public static void Finish() {
    }

    public static void registerNewUser(User theUser) {
        allUsers.add(theUser);
    }

    public static boolean userIsRegistered(User user) {
        if (allUsers.contains(user)) {
            return true;
        }
        return false;
    }

    public static boolean userIsRegistered(int userId) {
        for (User user : allUsers) {
            if (user.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    public static Item getItemById(int id) {
        for (Item item : allItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
