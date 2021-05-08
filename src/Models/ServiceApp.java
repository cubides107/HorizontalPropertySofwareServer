package Models;

import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.persistence.Persistence;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceApp {

    private HorizontalProperty horizontalProperty;
    private Persistence persistence;

    public ServiceApp() {
        horizontalProperty = new HorizontalProperty();
        persistence = new Persistence();
    }

    public void setNameHorizontalProperty(String name) {
        horizontalProperty.setNameHorizontalProperty(name);
    }

    public String getHorizontalProperty() {
        return horizontalProperty.getName();
    }

    public NodeProperties getNodeRootProperties() {
        return horizontalProperty.getNodeRootProperties();
    }

    public void addPropertyToUser(int idFather, PropertyNodeUser propertyNodeUser) {
        horizontalProperty.addPropertyToUser(idFather, propertyNodeUser);
    }

    public boolean addUser(Client user) {
        return horizontalProperty.addUser(user);
    }

    public int getCountProperties() {
        return horizontalProperty.getCountProperties();
    }

    public void addHouse() {
        horizontalProperty.addHouse();
    }

    public void addBuilding() {
        horizontalProperty.addBuilding();
    }

    public void addPool() {
        horizontalProperty.addPool();
    }

    public void addApartment(int idFather) {
        horizontalProperty.addApartment(idFather);
    }

    public void printTreeProperties() {
        horizontalProperty.printTreeProperties();
    }

    public void printTreeUsers() {
        horizontalProperty.printTreeUsers();
    }

    public int getCountUsers() {
        return horizontalProperty.getCountUsers();
    }

    public void addNewField() {
        horizontalProperty.addField();
    }

    public void addNewCommonRoom() {
        horizontalProperty.addCommonRoom();
    }

    public void deleteProperty(int idProperty) {
        horizontalProperty.deleteProperty(idProperty);
    }

    public void deleteUser(int idUserToDelete) {
        horizontalProperty.deleteUser(idUserToDelete);
    }

    public void deletePropertyToUser(int idProperty) {
        horizontalProperty.deletePropertyToUser(idProperty);
    }

    public boolean checkIsExitsUser(String nameUser) {
        return horizontalProperty.checkIsExitsUser(nameUser);
    }

    public ByteArrayOutputStream createXmlToUser(String nameUser) throws ParserConfigurationException, TransformerException {
        NodeUser nodeUser = horizontalProperty.searchByNameUser(nameUser);
        ArrayList<NodeUser> childList = nodeUser.getChildList();
        ArrayList<NodeProperties> listPropertiesToUser = new ArrayList<>();
        for (NodeUser node : childList) {
            listPropertiesToUser.add(horizontalProperty.searchPropertyToUser(node.getData().getId()));
        }
        return persistence.writeXmlToUser(nodeUser, listPropertiesToUser);
    }

    public ByteArrayOutputStream createUsersBYXml(String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        NodeUser nodeRootUsers = horizontalProperty.getNodeRootUsers();
        ArrayList<NodeUser> childList = nodeRootUsers.getChildList();
        for (NodeUser node : childList) {
            Element user = document.createElement(node.getData().getName());
            Element idProperty = document.createElement("ID");
            Text textIdent = document.createTextNode(String.valueOf(node.getId()));
            idProperty.appendChild(textIdent);
            user.appendChild(idProperty);
            for (NodeUser nodeProperties : node.getChildList()) {
                persistence.writeProperty(horizontalProperty.searchPropertyToUser(nodeProperties.getData().getId()), user, document);
            }
            if (user != null) {
                rootXml.appendChild(user);
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out;
    }


    public void addWaterServiceToProperty(int idProperty) {
        horizontalProperty.addServiceToProperty(idProperty);
    }

    public void addElectricityServiceToProperty(int idProperty) {
        horizontalProperty.addElectricityService(idProperty);
    }

    public void addGasServiceToProperty(int idProperty) {
        horizontalProperty.addGasService(idProperty);
    }

    public void addInternetServiceToProperty(int idProperty) {
        horizontalProperty.addInternetService(idProperty);
    }

    public void addWrapperService(String[] data) {
        horizontalProperty.addWrapperService(data);
    }

    public void setRootProperties(NodeProperties nodeRoot) {
        horizontalProperty.setRootProperties(nodeRoot);
    }

    public ByteArrayOutputStream writePropertyInMemory() throws ParserConfigurationException, TransformerException {
        return persistence.writePropertyInMemory(horizontalProperty.getNodeRootProperties(), horizontalProperty.getName());
    }

    public void setCountProperties(AtomicInteger atomicInteger) {
        horizontalProperty.setCountProperties(atomicInteger);
    }

    public NodeUser getNodeRootUsers() {
        return horizontalProperty.getNodeRootUsers();
    }

    public void setRootUsers(NodeUser nodeUsers) {
        horizontalProperty.setNodeRootUsers(nodeUsers);
    }

    public void setCountUsers(AtomicInteger atomicInteger1) {
        horizontalProperty.setCountUsers(atomicInteger1);
    }
}
