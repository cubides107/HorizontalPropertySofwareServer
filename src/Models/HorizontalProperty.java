package Models;

import Models.Services.*;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.mangerUser.TreeUsers;
import Models.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class HorizontalProperty {

    private String name;
    private int countUsers;
    private int countProperties;
    private TreeProperties treeProperties;
    private TreeUsers treeUsers;

    public HorizontalProperty() {
        countUsers = 0;
        countProperties = 0;
        treeUsers = new TreeUsers();
        treeProperties = new TreeProperties();
    }

    public boolean addUser(Client user) {
//        if(treeUsers.getRoot() != null) {
            boolean isExistUser = treeUsers.checkExistUser(user.getNameUser());
            if (!isExistUser) {
                treeUsers.add(0, new NodeUser(++countUsers, user));
//            treeUsers.printBreadth();
                return true;
            }
            treeUsers.printBreadth();
//        }
        return false;
    }

    public void addPropertyToUser(int idFather, PropertyNodeUser propertyNodeUser) {
        treeUsers.add(idFather, new NodeUser(++countUsers, propertyNodeUser));
        treeUsers.print();
    }


    public void setNameHorizontalProperty(String name) {
        this.name = name;
        HorizontalPropertyNode horizontalProperty = new HorizontalPropertyNode(0);
        treeProperties.setRoot(new NodeProperties(0, horizontalProperty));
        treeUsers.setRoot(new NodeUser(0, new Client("root")));
    }



    public void addPropertyToClient(int idFather, int idProperty) {
//        treeUsers.addUser(new NodeUser(idFather,new PropertyNodeUser(idProperty)));
    }

    public NodeProperties getNodeRootProperties() {
        return treeProperties.getRoot();
    }

    public void addHouse() {
        countProperties++;
        treeProperties.add(0, new NodeProperties(countProperties, new House(countProperties)));
    }

    public void addBuilding() {
        countProperties++;
        treeProperties.add(0, new NodeProperties(countProperties, new Building(countProperties)));
    }

    public void addPool() {
        countProperties++;
        treeProperties.add(0, new NodeProperties(countProperties, new Pool(countProperties)));
    }

    public void addApartment(int idFather) {
        NodeProperties nodeAux = treeProperties.search(idFather);
        countProperties++;
        treeProperties.add(nodeAux.getId(), new NodeProperties(countProperties, new Apartment(countProperties)));
    }

    public void printTreeProperties() {
        treeProperties.print();
    }

    public String getName() {
        if(name == null){
            return Persistence.getNameHorizontalPropertyToFile();
        }else{
        return name;
        }
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
        countProperties++;
        treeProperties.add(0, new NodeProperties(countProperties, new Field(countProperties)));
    }

    public void addCommonRoom() {
        countProperties++;
        treeProperties.add(0, new NodeProperties(countProperties, new CommonRoom(countProperties)));
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
        countProperties++;
        treeProperties.add(idProperty, new NodeProperties(countProperties, new WaterService(countProperties)));
    }

    public void addElectricityService(int idProperty) {
        countProperties++;
        treeProperties.add(idProperty, new NodeProperties(countProperties, new ElectricityService(countProperties)));
    }

    public void addGasService(int idProperty) {
        countProperties++;
        treeProperties.add(idProperty, new NodeProperties(countProperties, new GasService(countProperties)));
    }

    public void addInternetService(int idProperty) {
        countProperties++;
        treeProperties.add(idProperty, new NodeProperties(countProperties, new InternetService(countProperties)));
    }

    public void addWrapperService(String[] data) {
        countProperties++;
        treeProperties.add(Integer.parseInt(data[2]), new NodeProperties(countProperties, new WrapperService(convertDate(data[0]), Double.parseDouble(data[1]))));
    }

    private LocalDate convertDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(text, formatter);
    }

    public void setRootProperties(NodeProperties nodeRoot) {
        treeProperties.setRoot(nodeRoot);
    }

    public void setCountProperties(AtomicInteger atomicInteger) {
        countProperties = atomicInteger.get();
    }

    public NodeProperties searchPropertyToUser(int idProperty) {
        return treeProperties.search(idProperty);
    }

    public NodeUser getNodeRootUsers() {
        return treeUsers.getRoot();
    }

    public void setNodeRootUsers(NodeUser nodeUsers) {
        treeUsers.setRoot(nodeUsers);
    }

    public void setCountUsers(AtomicInteger atomicInteger1) {
        countUsers = atomicInteger1.get();
    }
}
