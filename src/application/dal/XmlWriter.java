/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.dal;

import application.common.entities.AuctionRoom;
import application.common.entities.BidRoom;
import application.common.entities.SaleRoom;
import application.managers.log.LoggerManager;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Tsikon
 */
public class XmlWriter implements IDataWriter {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;

    // root elements
    private Document doc;
    private Element rootElement;
    private Element bean;
    private Element itemProperty;
    private Element lifeTimeProperty;
    
    private Attr idAttr;
    private Attr classAttr;
    private Attr nameAttr;
    private Attr valueAttr;
    
    private String fileName;
    private String filePath;
    
    public XmlWriter() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("beans");
            doc.appendChild(rootElement);
        }
        catch(ParserConfigurationException | DOMException e) {
            LoggerManager.getInstance().getProgramLogger().log(Level.WARNING, "Error while trying to write sale details.");
        }
        
        setRootElementAttr();
        setDefaultFileName();
        setDefaultFilePath();
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
    
    private void setDefaultFilePath() {
        filePath = "DataBase\\";
    }
    
    private void setDefaultFileName() {
        fileName = "lastDaySales.xml";
    }
    
    @Override
    public void appendSale(SaleRoom sale) {
        
        // Adding a new bean element
        bean = doc.createElement("bean");
	rootElement.appendChild(bean);
        
        
        idAttr = doc.createAttribute("id");
        idAttr.setValue(sale.getName());
        bean.setAttributeNode(idAttr);
        
        
        classAttr = doc.createAttribute("class");
        if(sale instanceof BidRoom) {
            classAttr.setValue("application.common.entities.BidRoom");
        }
        else if(sale instanceof AuctionRoom) {
            classAttr.setValue("application.common.entities.AuctionRoom");
        }
        else {
            // TODO - throw a custom exception
        }
        bean.setAttributeNode(classAttr);
        
        itemProperty = doc.createElement("property");
        nameAttr = doc.createAttribute("name");
        valueAttr = doc.createAttribute("value");
        nameAttr.setValue("itemId");
        valueAttr.setValue("" + sale.getItemId());
        itemProperty.setAttributeNode(nameAttr);
        itemProperty.setAttributeNode(valueAttr);
        bean.appendChild(itemProperty);
        
        lifeTimeProperty = doc.createElement("property");
        nameAttr = doc.createAttribute("name");
        valueAttr = doc.createAttribute("value");
        nameAttr.setValue("lifeTime");
        valueAttr.setValue("LifeTime here");
        lifeTimeProperty.setAttributeNode(nameAttr);
        lifeTimeProperty.setAttributeNode(valueAttr);
        bean.appendChild(lifeTimeProperty);
    }
    
    @Override
    public void saveDataToDestination() {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath + fileName));
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
            
        } catch (TransformerException ex) {
            Logger.getLogger(XmlWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void setRootElementAttr() {
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue("http://www.springframework.org/schema/beans");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:xsi");
        attr.setValue("http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:aop");
        attr.setValue("http://www.springframework.org/schema/aop/spring-aop-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:context");
        attr.setValue("http://www.springframework.org/schema/context/spring-context-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:flow");
        attr.setValue("http://www.springframework.org/schema/webflow-config/spring-webflow-config-1.0.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:jms");
        attr.setValue("http://www.springframework.org/schema/jms/spring-jms-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:jee");
        attr.setValue("http://www.springframework.org/schema/jee/spring-jee-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:lang");
        attr.setValue("http://www.springframework.org/schema/lang/spring-lang-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:osgi");
        attr.setValue("http://www.springframework.org/schema/osgi/spring-osgi.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:tx");
        attr.setValue("http://www.springframework.org/schema/tx/spring-tx-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:util");
        attr.setValue("http://www.springframework.org/schema/util/spring-util-2.5.xsd");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xmlns:p");
        attr.setValue("http://www.springframework.org/schema/p");
        rootElement.setAttributeNode(attr);
        
        attr = doc.createAttribute("xsi:schemaLocation");
        attr.setValue("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd " + 
          "http://www.springframework.org/schema/aop/spring-aop-2.5.xsd http://www.springframework.org/schema/aop/spring-aop-2.5.xsd/spring-spring-aop-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/context/spring-context-2.5.xsd http://www.springframework.org/schema/context/spring-context-2.5.xsd/spring-spring-context-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/webflow-config/spring-webflow-config-1.0.xsd http://www.springframework.org/schema/webflow-config/spring-webflow-config-1.0.xsd/spring-spring-webflow-config-1.0.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/jms/spring-jms-2.5.xsd http://www.springframework.org/schema/jms/spring-jms-2.5.xsd/spring-spring-jms-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/jee/spring-jee-2.5.xsd http://www.springframework.org/schema/jee/spring-jee-2.5.xsd/spring-spring-jee-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/lang/spring-lang-2.5.xsd http://www.springframework.org/schema/lang/spring-lang-2.5.xsd/spring-spring-lang-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/osgi/spring-osgi.xsd http://www.springframework.org/schema/osgi/spring-osgi.xsd/spring-spring-osgi.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/tx/spring-tx-2.5.xsd http://www.springframework.org/schema/tx/spring-tx-2.5.xsd/spring-spring-tx-2.5.xsd-2.5.xsd " + 
          "http://www.springframework.org/schema/util/spring-util-2.5.xsd http://www.springframework.org/schema/util/spring-util-2.5.xsd/spring-spring-util-2.5.xsd-2.5.xsd)");         
        rootElement.setAttributeNode(attr);
        
    }
}
