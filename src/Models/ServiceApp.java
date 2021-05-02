package Models;

import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.TreeUsers;
import org.w3c.dom.Node;

public class ServiceApp {

    private TreeUsers treeUsers;
    private TreeProperties treeProperties;
    private HorizontalProperty horizontalProperty;
    private int countUsers;
    private int countProperties;

    public ServiceApp() {
        countUsers = 1;
        countProperties = 1;
        horizontalProperty = new HorizontalProperty();
        treeUsers = new TreeUsers();
        treeProperties = new TreeProperties();
    }

    public void setNameHorizontalProperty(String name){
        horizontalProperty.setName(name);
        treeProperties.setRoot(new NodePropeties(0,horizontalProperty));
        treeUsers.setRoot(new NodeUser( 0,new Client(name)));
        System.out.println(horizontalProperty.getName());
    }

    public String getHorizontalProperty() {
      return horizontalProperty.getName();
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

    public int getCountProperties() {
        return countProperties;
    }

    public void addHouse(){
        treeProperties.add(0,new NodePropeties(countProperties,new House(countProperties)));
        countProperties++;
        treeProperties.printBreadth();
    }

    public void addBuilding(){
        treeProperties.add(0, new NodePropeties(countProperties, new Building(countProperties)));
        countProperties++;
        treeProperties.printBreadth();
    }

    public void addApartment(int idFather){
        NodePropeties nodeAux = treeProperties.search(idFather);
        treeProperties.add(nodeAux.getId(),new NodePropeties(countProperties, new Apartment(countProperties)));
        countProperties++;
        treeProperties.printBreadth();
    }
}
