package Models;

import Models.Services.WaterService;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.persistence.Persistence;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;

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
        return persistence.writeXmlToUser(nodeUser);

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
}
