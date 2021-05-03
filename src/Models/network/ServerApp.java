package Models.network;

import Models.mangerUser.Client;
import Models.ServiceApp;
import Models.persistence.Persistence;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
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
                            persistence.writeProperty(serviceApp.getNodeRootProperties(),nameHorizontalProperty);
                            break;
                        case REGISTER_USER:
                            String nameUser = connection.readUTF();
                            boolean isAddSucces = serviceApp.addUser(new Client(nameUser));
                            connection.writeUTF(REGISTER_USER);
                            connection.writeBoolean(isAddSucces);
                            connection.writeUTF(nameUser);
                            connection.writeInt(serviceApp.getCountUsers()-1);
                            break;
                        case NEW_HOUSE:
                            connection.writeUTF(NEW_HOUSE);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.addHouse();
                            serviceApp.printTreeProperties();
                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_BUILDING:
                            connection.writeUTF(NEW_BUILDING);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.addBuilding();
                            serviceApp.printTreeProperties();
                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case NEW_APARTMENT:
                            connection.writeUTF(NEW_APARTMENT);
                            connection.writeInt(serviceApp.getCountProperties());
                            serviceApp.addApartment(connection.readInt());
                            serviceApp.printTreeProperties();
                            persistence.writeProperty(serviceApp.getNodeRootProperties(), serviceApp.getHorizontalProperty());
                            break;
                        case SHOW_PROPERTIES:

                            break;

                    }
                }
            } catch (IOException | ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

}
