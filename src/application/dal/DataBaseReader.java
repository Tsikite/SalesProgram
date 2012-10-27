package application.dal;

import application.common.exceptions.ReaderInitException;
import application.common.exceptions.SalesReadException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataBaseReader implements IDataReader {

    /**
     * This class execute the call for the database.
     */
    /**
     * user in database. user: whileWaiting="surfing" mail="gogo@gmail.com"
     * name="Faruk"
     */
    /**
     * user in database. user: whileWaiting="surfing" mail="gogo@gmail.com"
     * name="Faruk"
     */
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    private Document document;

    public DataBaseReader() {
    }

    @Override
    public void Init() throws ReaderInitException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://localhost/saleProject";
            connection = (Connection) DriverManager.getConnection(dbUrl, "root", "");
            System.out.println("Database connection established");
            statement = (Statement) connection.createStatement();


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DataBaseReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        }
        Element root = document.createElement("users");
        document.appendChild(root);
        NodeList list = null;
        try {
            rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
//                System.out.println(rs.getString("Name") + "- "
//                        + rs.getString("Email"));

               createUserNode(rs.getString("Name"), rs.getString("WhileWaiting"), rs.getString("Email"), document);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
         list = document.getFirstChild().getChildNodes();
        return list;
    }

    @Override
    public NodeList getSales() throws SalesReadException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node createUserNode(String name, String whileWaiting, String mail, Document document) {
        Element user = document.createElement("user");
        Attr attribute = document.createAttribute("name");
        attribute.setValue(name);
        user.setAttributeNode(attribute);
        attribute = document.createAttribute("whileWaiting");
        attribute.setValue(whileWaiting);
        user.setAttributeNode(attribute);
        attribute = document.createAttribute("mail");
        attribute.setValue(mail);
        user.setAttributeNode(attribute);
        document.getFirstChild().appendChild(user);
        return user;
    }
}
