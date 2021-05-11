package Models.persistence;

import Models.Services.*;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Persistence {


    public Persistence() {

    }


    public NodeProperties readXml(AtomicInteger countProperties) throws ParserConfigurationException, IOException, SAXException {
        Document document = convertXMLFileToXMLDocument("data/" + "HorizontalProperty" + ".xml");
        Node rootNodeElement = document.getDocumentElement();
//        NodeList horizontalPropertyNode = rootNodeElement.getElementsByTagName("HorizontalPropertyNode");
//        Node item = horizontalPropertyNode.item(0);
        NodeProperties nodeProperties = readXml(rootNodeElement, countProperties);
        return nodeProperties;
    }

    private NodeProperties readXml(Node rootDocument, AtomicInteger countProperties) throws ParserConfigurationException, IOException, SAXException {
        NodeProperties nodeProperties = null;
        nodeProperties = caseNodeProperties(nodeProperties, rootDocument,countProperties);
        NodeList nodeList = rootDocument.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
//                    System.out.println(tempNode.getNodeName());
//                    if (tempNode.getNodeName().equals("ID")) {
//                        int nodeValue = Integer.parseInt(tempNode.getTextContent());
////                        System.out.println(tempNode.getTextContent());
//                        nodeProperties.setId(nodeValue);
//                        nodeProperties.getData().setId(nodeValue);
//                    }

                    if (tempNode.getNodeName().equals("ID")) {
                        int nodeValue = Integer.parseInt(tempNode.getTextContent());
                        nodeProperties.setId(nodeValue);
                        nodeProperties.getData().setId(nodeValue);
                    }
                    if (tempNode.getNodeName().equals("Date")) {
                        WrapperService data = (WrapperService) nodeProperties.getData();
                        data.setDate(LocalDate.parse(tempNode.getTextContent()));
                    }
                    if (tempNode.getNodeName().equals("Value")) {
                        WrapperService data = (WrapperService) nodeProperties.getData();
                        data.setValue(Double.parseDouble(tempNode.getTextContent()));
                    }

//                    if (!tempNode.getNodeName().equals("ID")) {
//                        countProperties.addAndGet(1);
//                        nodeProperties.add(readXml(tempNode, countProperties));
//                    }
                    if (!(tempNode.getNodeName().equals("ID") || tempNode.getNodeName().equals("Date") || tempNode.getNodeName().equals("Value"))) {
                        countProperties.incrementAndGet();
                        nodeProperties.add(readXml(tempNode, countProperties));
                    }
                }
            }
        }
        return nodeProperties;
    }

    private NodeProperties caseNodeProperties(NodeProperties node, Node root,AtomicInteger countProperties) {
        switch (root.getNodeName()) {
//            case "HorizontalPropertyNode":
//                node = new NodeProperties(new HorizontalPropertyNode());
//                break;
            case "Building":
                node = new NodeProperties(new Building());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "House":
                node = new NodeProperties(new House());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "CommonRoom":
                node = new NodeProperties(new CommonRoom());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "ElectricityService":
                node = new NodeProperties(new ElectricityService());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "GasService":
                node = new NodeProperties(new GasService());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "WaterService":
                node = new NodeProperties(new WaterService());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "WrapperService":
                node = new NodeProperties(new WrapperService());
                node.setId(countProperties.incrementAndGet());
                break;
            case "InternetService":
                node = new NodeProperties(new InternetService());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "Pool":
                node = new NodeProperties(new Pool());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "Apartment":
                node = new NodeProperties(new Apartment());
//                node.setId(countProperties.incrementAndGet());
                break;
            case "Field":
                node = new NodeProperties(new Field());
//                node.setId(countProperties.incrementAndGet());
                break;
            default:
                node = new NodeProperties(new HorizontalPropertyNode());
//                node.setId(countProperties.getAndIncrement());
                break;
        }
        return node;
    }

    public static Document convertXMLFileToXMLDocument(String fileName) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document xmlDocument = builder.parse(new File(fileName));
            xmlDocument.getDocumentElement().normalize();
            return xmlDocument;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNameHorizontalPropertyToFile() {
        return convertXMLFileToXMLDocument("data/HorizontalProperty.xml").getDocumentElement().getNodeName();
    }


    public ByteArrayOutputStream writeXmlToUser(NodeUser root, ArrayList<NodeProperties> listPropertiesToUser) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, root.getData().getName(), null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();

        writeXmlToUser(root, document, rootXml, listPropertiesToUser);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out;
    }

    private void writeXmlToUser(NodeUser actual, Document document, Element rootXML, ArrayList<NodeProperties> listPropertiesToUser) {
//        if (!(actual.getData().getClass().getSimpleName()).equals("Client")) {
//            Element property = document.createElement(actual.getData().getName());
//            Element idProperty = document.createElement("ID");
//            Text textIdent = document.createTextNode(String.valueOf(actual.getData().getId()));
//            idProperty.appendChild(textIdent);
//            property.appendChild(idProperty);

        for (NodeProperties node : listPropertiesToUser) {
            writePropertyUserInMemory(node, rootXML, document);
        }
//            rootXML.appendChild(property);

//        }
//        for (NodeUser child : actual.getChildList()) {
//            writeXmlToUser(child, document, rootXML, listPropertiesToUser);
//        }
    }


    public static int calculateNumberCount(String name){
        Document document = convertXMLFileToXMLDocument(name);
        NodeList nodeList = document.getElementsByTagName("ID");
        int numberMax = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            int number = Integer.parseInt(item.getTextContent());
            if(number > numberMax){
                numberMax = number;
            }
        }
        return numberMax;
    }



    public void writeProperty(NodeProperties root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        writeProperty(root, rootXml, document);

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalProperty" + ".xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }


    public void writeProperty(NodeProperties actual, Element actualRoot, Document document) {
        Element property = null;
        if (actual != null) {
            if (!(actual.getData().getClass().getSimpleName().equals("HorizontalPropertyNode"))) {
                property = document.createElement(actual.getData().getClass().getSimpleName());
                if (actual.getData().getClass().getSimpleName().equals("WrapperService")) {
                    WrapperService aClass = (WrapperService) actual.getData();
                    Element idPropertyAux = document.createElement("ID");
//                    Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
                    Text id = document.createTextNode(String.valueOf(actual.getId()));
                    idPropertyAux.appendChild(id);
                    property.appendChild(idPropertyAux);
                    Element date = document.createElement("Date");
                    Text textIdent = document.createTextNode(String.valueOf(aClass.getDate()));
                    date.appendChild(textIdent);
                    property.appendChild(date);
                    Element valueBill = document.createElement("Value");
                    Text textValue = document.createTextNode(String.valueOf(aClass.getValue()));
                    valueBill.appendChild(textValue);
                    property.appendChild(valueBill);
                    actualRoot.appendChild(property);
                    return;
                }

                    Element idProperty = document.createElement("ID");
                    Text textIdent = document.createTextNode(String.valueOf(actual.getId()));

                    idProperty.appendChild(textIdent);
                    property.appendChild(idProperty);

                actualRoot.appendChild(property);
            }

            for (NodeProperties child : actual.getChildList()) {
                if (property != null) {
                    writeProperty(child, property, document);
                } else {
                    writeProperty(child, actualRoot, document);
                }
            }
        }
    }

    public void writePropertyUserInMemory(NodeProperties actual, Element actualRoot, Document document) {
        Element property = null;
        if (actual != null) {
            if (!(actual.getData().getClass().getSimpleName().equals("HorizontalPropertyNode"))) {
                property = document.createElement(actual.getData().getClass().getSimpleName());
                if (actual.getData().getClass().getSimpleName().equals("WrapperService")) {
                    WrapperService aClass = (WrapperService) actual.getData();
                    Element idPropertyAux = document.createElement("ID");
//                    Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
                    Text id = document.createTextNode(String.valueOf(actual.getId()));
                    idPropertyAux.appendChild(id);
                    property.appendChild(idPropertyAux);
                    Element date = document.createElement("Date");
                    Text textIdent = document.createTextNode(String.valueOf(aClass.getDate()));
                    date.appendChild(textIdent);
                    property.appendChild(date);
                    Element valueBill = document.createElement("Value");
                    Text textValue = document.createTextNode(String.valueOf(aClass.getValue()));
                    valueBill.appendChild(textValue);
                    property.appendChild(valueBill);
                    actualRoot.appendChild(property);
                    return;
                }

                Element idProperty = document.createElement("ID");
//                    Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
                Text textIdent = document.createTextNode(String.valueOf(actual.getId()));

                idProperty.appendChild(textIdent);
                property.appendChild(idProperty);

                actualRoot.appendChild(property);
            }

            for (NodeProperties child : actual.getChildList()) {
                if (property != null) {
                    writePropertyUserInMemory(child, property, document);
                } else {
                    writePropertyUserInMemory(child, actualRoot, document);
                }
            }
        }
    }

    public void writeUsers(NodeUser root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        if (root != null) {
            writeUsers(root, rootXml, document);
        }

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalPropertyUsers" + ".xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    private void writeUsers(NodeUser actual, Element actualRoot, Document document) {
        Element property = null;
        if (actual.getId() != 0) {
            property = document.createElement(actual.getData().getName());
//            if(actual.getData().getId() != 0){
            Element idProperty = document.createElement("ID");
            Text textIdent = document.createTextNode(String.valueOf(actual.getData().getId()));
            idProperty.appendChild(textIdent);
            property.appendChild(idProperty);
//            }
            actualRoot.appendChild(property);
        }
        for (NodeUser child : actual.getChildList()) {
            if (property != null) {
                writeUsers(child, property, document);
            } else {
                writeUsers(child, actualRoot, document);
            }
        }
    }

    public NodeUser readUsers(AtomicInteger countUsers) {
        Document document = convertXMLFileToXMLDocument("data/HorizontalPropertyUsers.xml");
        Node rootNodeElement = document.getDocumentElement();

        return readUsers(rootNodeElement, countUsers);
    }

    private NodeUser readUsers(Node rootNodeElement, AtomicInteger countUsers) {
        NodeUser nodeUser = null;
        nodeUser = caseNodeUser(nodeUser, rootNodeElement);
        NodeList nodeList = rootNodeElement.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
                    if (tempNode.getNodeName().equals("ID")) {
                        int nodeValue = Integer.parseInt(tempNode.getTextContent());
                        System.out.println(tempNode.getTextContent());
                        nodeUser.setId(countUsers.incrementAndGet());
                        nodeUser.getData().setId(nodeValue);
                    }
                    if (!tempNode.getNodeName().equals("ID")) {
                        nodeUser.add(readUsers(tempNode, countUsers));
                    }
                }

            }
        }
        return nodeUser;
    }

    private NodeUser caseNodeUser(NodeUser nodeUser, Node rootNodeElement) {
        switch (rootNodeElement.getNodeName()) {
            case "House":
                nodeUser = new NodeUser(0, new PropertyNodeUser(0, "House"));
                break;
            case "Apartment":
                nodeUser = new NodeUser(0, new PropertyNodeUser(0, "Apartment"));
                break;
            default:
                nodeUser = new NodeUser(0, new Client(rootNodeElement.getNodeName()));
                break;

        }
        return nodeUser;
    }

    public ByteArrayOutputStream writePropertyInMemory(NodeProperties root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        writePropertyInMemory(root, rootXml, document);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out;
    }

    private void writePropertyInMemory(NodeProperties actual, Element rootXml, Document document) {
        Element property = null;
        if (!(actual.getData().getClass().getSimpleName().equals("HorizontalPropertyNode"))) {
            property = document.createElement(actual.getData().getClass().getSimpleName());
            Element idProperty = document.createElement("ID");
            Text textIdent = document.createTextNode(String.valueOf(actual.getId()));
            idProperty.appendChild(textIdent);
            property.appendChild(idProperty);
            rootXml.appendChild(property);
        }
        for (NodeProperties child : actual.getChildList()) {
            if (property != null) {
                writePropertyUserInMemory(child, property, document);
            } else {
                writePropertyUserInMemory(child, rootXml, document);
            }
        }
    }
}
