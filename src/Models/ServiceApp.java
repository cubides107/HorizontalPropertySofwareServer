package Models;

import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.PropertyNodeUser;

public class ServiceApp {

    private HorizontalProperty horizontalProperty;

    public ServiceApp() {
        horizontalProperty = new HorizontalProperty();
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

    public void printTreeUsers(){
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
}
