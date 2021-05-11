package Models;

import Models.Services.WrapperService;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import Models.persistence.Persistence;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
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
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        if (horizontalProperty.getName() == null) {
//            persistence.
            return Persistence.getNameHorizontalPropertyToFile();
        } else {
            return horizontalProperty.getName();
        }
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
        if (nodeRootUsers != null) {
            ArrayList<NodeUser> childList = nodeRootUsers.getChildList();
            for (NodeUser node : childList) {
                Element user = document.createElement(node.getData().getName());
                Element idProperty = document.createElement("ID");
                Text textIdent = document.createTextNode(String.valueOf(node.getId()));
                idProperty.appendChild(textIdent);
                user.appendChild(idProperty);
                for (NodeUser nodeProperties : node.getChildList()) {
                    persistence.writePropertyUserInMemory(horizontalProperty.searchPropertyToUser(nodeProperties.getData().getId()), user, document);
                }
                if (user != null) {
                    rootXml.appendChild(user);
                }
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out;
    }


    public ByteArrayOutputStream createPDF(int[] valuesService) throws FileNotFoundException, DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//        FileOutputStream pdfReport = new FileOutputStream("fichero.pdf");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();
        PdfPTable table = new PdfPTable(2);
        String[] services = new String[4];
        services[0] = "Gas";
        services[1] = "Electricidad";
        services[2] = "Internet";
        services[3] = "Agua";

        table.addCell("Recibo");
        table.addCell("Valor($)");
        table.addCell("Gas");
        table.addCell(String.valueOf(valuesService[0]));
        table.addCell("Electricidad");
        table.addCell(String.valueOf(valuesService[1]));
        table.addCell("Internet");
        table.addCell(String.valueOf(valuesService[2]));
        table.addCell("Agua");
        table.addCell(String.valueOf(valuesService[3]));
        table.addCell("Total Servicios");
        table.addCell(String.valueOf(calculateTotalServices(valuesService)));
        document.add(table);

        DefaultPieDataset dataSet = new DefaultPieDataset();
        for (int i = 0; i < valuesService.length; i++) {
            dataSet.setValue(services[i], valuesService[i]);
        }
        JFreeChart chart = ChartFactory.createPieChart("Servicos", dataSet, true, true, true);
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(300, 300);
        Graphics2D graphics2d = template.createGraphics(300, 300, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 300, 300);
        chart.draw(graphics2d, rectangle2d);
        graphics2d.dispose();
        contentByte.addTemplate(template, 150, 350);
        document.close();
        return out;
    }

    public int calculateTotalServices(int[] values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }


    public ByteArrayOutputStream calculateAllService(LocalDate date, String nameUser) throws DocumentException, FileNotFoundException {
        int[] valuesService = new int[4];
        NodeUser nodeUser = horizontalProperty.searchByNameUser(nameUser);
        ArrayList<NodeUser> childList = nodeUser.getChildList();
        valuesService[0] = calculateOneRegister(date, childList, "GasService");
        valuesService[1] = calculateOneRegister(date, childList, "ElectricityService");
        valuesService[2] = calculateOneRegister(date, childList, "InternetService");
        valuesService[3] = calculateOneRegister(date, childList, "WaterService");
        return createPDF(valuesService);

    }


    public int calculateOneRegister(LocalDate date, ArrayList<NodeUser> childList, String serviceName) {
        ArrayList<NodeProperties> listAllService = new ArrayList<>();
        int value = 0;
        for (NodeUser node : childList) {
            ArrayList<NodeProperties> calculate = calculate(horizontalProperty.searchPropertyToUser(node.getData().getId()), serviceName);
            if (calculate != null) {
                listAllService.addAll(calculate);
            }
        }
        for (NodeProperties nodeProperties : listAllService) {
            WrapperService wraperAux = (WrapperService) nodeProperties.getData();
            if (wraperAux.getDate().isAfter(date)) {
                value += wraperAux.getValue();
            }
        }
        return value;
    }

    private ArrayList<NodeProperties> calculate(NodeProperties node, String nameClass) {
        ArrayList<NodeProperties> childList = node.getChildList();
        for (NodeProperties nodeProperties : childList) {
            if (nodeProperties.getData().getClass().getSimpleName().equals(nameClass)) {
                return nodeProperties.getChildList();
            }
        }
        return null;
    }

    public ArrayList<Integer> calculateNodesToReport3(LocalDate dateOne, LocalDate dateTwo) {
        NodeProperties nodeRootProperties = horizontalProperty.getNodeRootProperties();
        ArrayList<Integer> idNodes = new ArrayList<>();
        calculateNodes(nodeRootProperties,dateOne,dateTwo,idNodes);
        return idNodes;
    }

    private void calculateNodes(NodeProperties actual,LocalDate dateStart, LocalDate dateEnd,ArrayList<Integer> idNodes) {
        if(actual.getData().getClass().getSimpleName().equals("WrapperService")){
            WrapperService data = (WrapperService) actual.getData();
            if(data.getDate().isAfter(dateStart) && data.getDate().isBefore(dateEnd) || (data.getDate().isEqual(dateStart) || data.getDate().isEqual(dateEnd))){
              if(actual.getFather().getFather().getFather().getData().getClass().getSimpleName().equals("Building")){
                  idNodes.add(actual.getFather().getFather().getFather().getId());
              }
                idNodes.add(actual.getFather().getFather().getId());
                idNodes.add(actual.getFather().getId());
                idNodes.add(actual.getId());
            }
        }
        for (NodeProperties node : actual.getChildList() ) {
            calculateNodes(node,dateStart,dateEnd,idNodes);
        }
    }


    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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


    public LocalDate convertDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(text, formatter);
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

    public void setNameToUser(int idUser, String emailUser) {
        horizontalProperty.setNameToUser(idUser,emailUser);
    }

    public void deleteInTreeUser(int idUser) {
      horizontalProperty.deleteProperty(idUser);
    }
}
