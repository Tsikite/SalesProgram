package application.common.entities.factory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import application.common.entities.User;

public class UserFactory {

    public static User getUser(Node userTagNode) {
        User theUser = null;

        if (userTagNode.getNodeType() == Node.ELEMENT_NODE) {
            // theUser = new User();
            Element eElement = (Element) userTagNode;
            String name = eElement.getAttribute("name");
            String whileWaiting = eElement.getAttribute("whileWaiting");
            theUser = new User(name, whileWaiting);
        }

        return theUser;
    }

    public static User getUser(String userName, String whileWaiting) {
        return new User(userName, whileWaiting);
    }
}
