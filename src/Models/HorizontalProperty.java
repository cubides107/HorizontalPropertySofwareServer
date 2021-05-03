package Models;

import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.mangerUser.TreeUsers;

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

    public void addPropertyToUser(int idFather, PropertyNodeUser propertyNodeUser){
        treeUsers.add(idFather,new NodeUser(countUsers++,propertyNodeUser));
    }


    public void setNameHorizontalProperty(String name) {
        this.name = name;
        HorizontalPropertyNode horizontalProperty = new HorizontalPropertyNode(0);
        treeProperties.setRoot(new NodeProperties(0, horizontalProperty));
        treeUsers.setRoot(new NodeUser( 0,new Client(name)));
        System.out.println(name);
    }

    public  void addPropertyToClient(int idFather,int idProperty){
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

    public void addApartment(int idFather) {
        NodeProperties nodeAux = treeProperties.search(idFather);
        treeProperties.add(nodeAux.getId(), new NodeProperties(countProperties, new Apartment(countProperties)));
        countProperties++;
    }

    public void printTreeProperties(){
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

}
