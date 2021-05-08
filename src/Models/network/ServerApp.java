package Models.network;

import Models.mangerUser.Client;
import Models.ServiceApp;
import Models.mangerUser.PropertyNodeUser;
import Models.persistence.Persistence;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {
    public static final int PORT = 8000;
    private static final String NEW_CONNECTION = "New Connection";
    public static final String REGISTER_USER = "REGISTER_USER";
    public static final String CREATE_HORIZONTAL_PROPERTY = "CREATE_HORIZONTAL_PROPERTY";
    public static final String NEW_HOUSE = "NEW_HOUSE";
    public static final String NEW_BUILDING = "NEW_BUILDING";
    public static final String NEW_APARTMENT = "NEW_APARTMENT";
    public static final String SHOW_PROPERTIES = "SHOW_PROPERTIES";
    public static final String ADD_HOUSE_USER = "ADD_HOUSE_USER";
    public static final String ADD_APARTMENT_USER = "ADD_APARTMENT_USER";
    public static final String NEW_POOL = "NEW_POOL";
    public static final String NEW_FIELD = "NEW_FIELD";
    public static final String NEW_ADD_COMMON_ROOM = "NEW_ADD_COMMON_ROOM";


    private ArrayList<Connection> listConnections;
    private ServerSocket serverSocket;
    private ServiceApp serviceApp;
    private Persistence persistence;

    public ServerApp() throws IOException {
        serviceApp = new ServiceApp();
        serverSocket = new ServerSocket(PORT);
        listConnections = new ArrayList<>();
        persistence = new Persistence();
        acceptConnections();
        readRequests();
    }

    private void acceptConnections() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    Logger.getGlobal().log(Level.INFO, NEW_CONNECTION);
                    listConnections.add(new Connection(connection));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void readRequests() {
        new Thread(() -> {
            while (!serverSocket.isClosed()) {
                readRequest();
            }
        }).start();
    }

    private void readRequest() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator<Connection> iterator = listConnections.iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            try {
                if (connection.available()) {
                    String request = connection.readUTF();
                    switch (request) {
                        case CREATE_HORIZONTAL_PROPERTY:
                            String nameHorizontalProperty = connection.readUTF();
                            serviceApp.setNameHorizontalProperty(nameHorizontalProperty);
//                            persistence.saveUsersXml( nameHorizontalProperty.replace(" ", ""));
                            AtomicInteger atomicInteger = new AtomicInteger();
                            AtomicInteger atomicInteger1 = new AtomicInteger();
                            try {
                                serviceApp.setRootProperties(persistence.readXml(atomicInteger));
                                serviceApp.setCountProperties(atomicInteger);
                                serviceApp.setRootUsers(persistence.readUsers(atomicInteger1));
                                serviceApp.setCountUsers(atomicInteger1);
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), nameHorizontalProperty);
                            break;
                        case REGISTER_USER:
                            String nameUser = connection.readUTF();
                            boolean isAddSucces = serviceApp.addUser(new Client(nameUser));
                            connection.writeUTF(REGISTER_USER);
                            connection.writeBoolean(isAddSucces);
                            connection.writeUTF(nameUser);
                            connection.writeInt(serviceApp.getCountUsers());
//                            persistence.writeUsers(serviceApp.getNodeRootUsers(),"Ciudadela");
                            break;
                        case NEW_HOUSE:
                            connection.writeUTF(NEW_HOUSE);
                            serviceApp.addHouse();
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_BUILDING:
                            connection.writeUTF(NEW_BUILDING);
                            serviceApp.addBuilding();
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_APARTMENT:
                            connection.writeUTF(NEW_APARTMENT);
                            serviceApp.addApartment(connection.readInt());
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case SHOW_PROPERTIES:
                            connection.writeUTF(SHOW_PROPERTIES);
                            connection.sendFile(serviceApp.writePropertyInMemory());
                            break;
                        case ADD_HOUSE_USER:
                            connection.writeUTF(ADD_HOUSE_USER);
                            int idFather = connection.readInt();
                            serviceApp.addPropertyToUser(idFather, new PropertyNodeUser(serviceApp.getCountProperties(), "House"));
                            connection.writeInt(serviceApp.getCountProperties());
                            break;
                        case ADD_APARTMENT_USER:
                            connection.writeUTF(ADD_APARTMENT_USER);
                            int idFatherApartment = connection.readInt();
                            serviceApp.addPropertyToUser(idFatherApartment, new PropertyNodeUser(serviceApp.getCountProperties(), "Apartment"));
                            connection.writeInt(serviceApp.getCountProperties());
                            break;
                        case "SET_PROPERTY_TO_USER":
                            int idUser = connection.readInt();
                            int idProperty = connection.readInt();
                            serviceApp.addPropertyToUser(idUser, new PropertyNodeUser(idProperty, connection.readUTF()));
                            break;
                        case NEW_POOL:
                            connection.writeUTF(NEW_POOL);
                            serviceApp.addPool();
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_FIELD:
                            connection.writeUTF(NEW_FIELD);
                            serviceApp.addNewField();
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_ADD_COMMON_ROOM:
                            connection.writeUTF(NEW_ADD_COMMON_ROOM);
                            serviceApp.addNewCommonRoom();
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
//                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case "DELETE_PROPERTY":
                            int idPropertyToDelete = connection.readInt();
                            serviceApp.deleteProperty(idPropertyToDelete);
                            serviceApp.printTreeProperties();
                            serviceApp.deletePropertyToUser(idPropertyToDelete);
                            serviceApp.printTreeUsers();
                            break;
                        case "DELETE_USER":
                            int idUserToDelete = connection.readInt();
                            serviceApp.deleteUser(idUserToDelete);
                            serviceApp.printTreeUsers();
                            break;
                        case "DELETE_PROPERTY_TO_USER":
                            idUserToDelete = connection.readInt();
                            serviceApp.deletePropertyToUser(idUserToDelete);
                            serviceApp.printTreeUsers();
                            break;

                        ///Client Request
                        case "NEW_CLIENT":
                            nameUser = connection.readUTF();
                            boolean isExistUser = serviceApp.checkIsExitsUser(nameUser);
                            connection.writeUTF("NEW_CLIENT");
                            connection.writeBoolean(isExistUser);
                            connection.writeUTF(nameUser);
                            if (isExistUser) {
                                ByteArrayOutputStream xmlToUser = serviceApp.createXmlToUser(nameUser);
                                connection.sendFile(xmlToUser);
                            }
                            break;
                        case "REFRESH_PROPERTIES_USER":
                            connection.writeUTF("REFRESH_PROPERTIES_USER");
                            ByteArrayOutputStream xmlToUser = serviceApp.createXmlToUser(connection.readUTF());
                            connection.sendFile(xmlToUser);
                            break;

                        case "ADD_SERVICE_WATER":
                            connection.writeUTF("ADD_SERVICE_WATER");
                            idProperty = connection.readInt();
                            serviceApp.addWaterServiceToProperty(idProperty);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
                            break;

                        case "ADD_SERVICE_ELECTRICITY":
                            connection.writeUTF("ADD_SERVICE_ELECTRICITY");
                            idProperty = connection.readInt();
                            serviceApp.addElectricityServiceToProperty(idProperty);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
                            break;

                        case "ADD_SERVICE_GAS":
                            connection.writeUTF("ADD_SERVICE_GAS");
                            idProperty = connection.readInt();
                            serviceApp.addGasServiceToProperty(idProperty);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
                            break;

                        case "ADD_SERVICE_INTERNET":
                            connection.writeUTF("ADD_SERVICE_INTERNET");
                            idProperty = connection.readInt();
                            serviceApp.addInternetServiceToProperty(idProperty);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
                            break;
                        case "ADD_NEW_BILL":
                            connection.writeUTF("ADD_NEW_BILL");
                            String[] data = connection.readUTF().split("#");
                            serviceApp.addWrapperService(data);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.printTreeProperties();
                            break;


                    }
                }
            } catch (IOException | ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

}
