package Models;

import Models.Services.*;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.mangerUser.TreeUsers;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HorizontalProperty {

    private String name;
    private String ID;
    private int countUsers;
    private int countProperties;
    private TreeProperties treeProperties;
    private TreeUsers treeUsers;

    public HorizontalProperty() {
        countUsers = 1;
        countProperties = 1;
        treeUsers = new TreeUsers();
        treeProperties = new TreeProperties();
    }

    public boolean addUser(Client user) {
        boolean isExistUser = treeUsers.checkExistUser(user.getNameUser());
        if (!isExistUser) {
            treeUsers.add(0, new NodeUser(countUsers++, user));
            treeUsers.printBreadth();
            return true;
        }
        treeUsers.printBreadth();
        return false;
    }

    public void addPropertyToUser(int idFather, PropertyNodeUser propertyNodeUser) {
        treeUsers.add(idFather, new NodeUser(countUsers++, propertyNodeUser));
        treeUsers.print();
    }


    public void setNameHorizontalProperty(String name) {
        this.name = name;
        HorizontalPropertyNode horizontalProperty = new HorizontalPropertyNode(0);
        treeProperties.setRoot(new NodeProperties(0, horizontalProperty));
        treeUsers.setRoot(new NodeUser(0, new Client(name)));
    }

    public void addPropertyToClient(int idFather, int idProperty) {
//        treeUsers.addUser(new NodeUser(idFather,new PropertyNodeUser(idProperty)));
    }

    public NodeProperties getNodeRootProperties() {
        return treeProperties.getRoot();
    }

    public void addHouse() {
        treeProperties.add(0, new NodeProperties(countProperties, new House(countProperties)));
        countProperties++;
    }

    public void addBuilding() {
        treeProperties.add(0, new NodeProperties(countProperties, new Building(countProperties)));
        countProperties++;
    }

    public void addPool() {
        treeProperties.add(0, new NodeProperties(countProperties, new Pool(countProperties)));
        countProperties++;
    }

    public void addApartment(int idFather) {
        NodeProperties nodeAux = treeProperties.search(idFather);
        treeProperties.add(nodeAux.getId(), new NodeProperties(countProperties, new Apartment(countProperties)));
        countProperties++;
    }

    public void printTreeProperties() {
        treeProperties.print();
    }

    public String getName() {
        return name;
    }

    public int getCountProperties() {
        return countProperties;
    }

    public int getCountUsers() {
        return countUsers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addField() {
        treeProperties.add(0, new NodeProperties(countProperties, new Field(countProperties)));
        countProperties++;
    }

    public void addCommonRoom() {
        treeProperties.add(0, new NodeProperties(countProperties, new CommonRoom(countProperties)));
        countProperties++;
    }

    public void deleteProperty(int idProperty) {
        treeProperties.remove(idProperty);
    }

    public void deleteUser(int idUserToDelete) {
        treeUsers.remove(idUserToDelete);
    }

    public void printTreeUsers() {
        treeUsers.print();
    }

    public void deletePropertyToUser(int idProperty) {
        treeUsers.deletePropertyToUser(idProperty);
    }

    public boolean checkIsExitsUser(String name) {
        return treeUsers.checkExistUser(name);
    }

    public NodeUser searchByNameUser(String nameUser) {
        return treeUsers.searchByNameUser(nameUser);
    }

    public void addServiceToProperty(int idProperty) {
        treeProperties.add(idProperty, new NodeProperties(countProperties, new WaterService(countProperties)));
        countProperties++;
    }

    public void addElectricityService(int idProperty) {
        treeProperties.add(idProperty, new NodeProperties(countProperties, new ElectricityService(countProperties)));
        countProperties++;
    }

    public void addGasService(int idProperty) {
        treeProperties.add(idProperty, new NodeProperties(countProperties, new GasService(countProperties)));
        countProperties++;
    }

    public void addInternetService(int idProperty) {
        treeProperties.add(idProperty, new NodeProperties(countProperties, new InternetService(countProperties)));
        countProperties++;
    }

    public void addWrapperService(String[] data) {
        treeProperties.add(Integer.parseInt(data[2]), new NodeProperties(countProperties, new WrapperService(convertDate(data[0]), Double.parseDouble(data[1]))));
        countProperties++;
    }

    private LocalDate convertDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(text, formatter);
    }
}
