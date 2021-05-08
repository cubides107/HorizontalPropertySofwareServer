package Models.persistence;

import Models.Services.ElectricityService;
import Models.Services.WrapperService;
import Models.managerProperties.*;
import Models.mangerUser.Client;
import Models.mangerUser.NodeUser;
import Models.mangerUser.PropertyNodeUser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Persistence {


    public Persistence() {

    }

    public void saveUsersXml(String nameFile) {
        try {

            File fileProperties = new File("data/" + nameFile);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nameFile, null);
            document.setXmlVersion("1.0");

            Element root = document.getDocumentElement();

            Element user = document.createElement("Usuarios");
            Element emailUser = document.createElement("Email");
            Text textEmailUser = document.createTextNode("cristianCubides");
            emailUser.appendChild(textEmailUser);
            user.appendChild(emailUser);

            root.appendChild(user);

            Source source = new DOMSource(document);
            Result result = new StreamResult(new File("data/" + nameFile + ".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

    }

    public NodeProperties readXml(AtomicInteger countProperties) throws ParserConfigurationException, IOException, SAXException {
        Document document = convertXMLFileToXMLDocument("data/" + "HorizontalProperty" + ".xml");
        Node rootNodeElement = document.getDocumentElement();
//        NodeList horizontalPropertyNode = rootNodeElement.getElementsByTagName("HorizontalPropertyNode");
//        Node item = horizontalPropertyNode.item(0);
        NodeProperties nodeProperties = readXml(rootNodeElement, countProperties);
        System.out.println(countProperties);
        return nodeProperties;
    }

    private NodeProperties readXml(Node rootDocument, AtomicInteger countProperties) throws ParserConfigurationException, IOException, SAXException {
        NodeProperties nodeProperties = null;
        nodeProperties = caseNodeProperties(nodeProperties, rootDocument);
        NodeList nodeList = rootDocument.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
//                    System.out.println(tempNode.getNodeName());
                    if (tempNode.getNodeName().equals("ID")) {
                        int nodeValue = Integer.parseInt(tempNode.getTextContent());
//                        System.out.println(tempNode.getTextContent());
                        nodeProperties.setId(nodeValue);
                        nodeProperties.getData().setId(nodeValue);
                    }
                    if (!tempNode.getNodeName().equals("ID")) {
                        countProperties.addAndGet(1);
                        nodeProperties.add(readXml(tempNode, countProperties));
                    }
                }
            }
        }
        return nodeProperties;
    }

    private NodeProperties caseNodeProperties(NodeProperties node, Node root) {
        switch (root.getNodeName()) {
//            case "HorizontalPropertyNode":
//                node = new NodeProperties(new HorizontalPropertyNode());
//                break;
            case "Building":
                node = new NodeProperties(new Building());
                break;
            case "House":
                node = new NodeProperties(new House());
                break;
            case "CommonRoom":
                node = new NodeProperties(new CommonRoom());
                break;
            case "ElectricityService":
                node = new NodeProperties(new ElectricityService());
                break;
            case "WrapperService":
                node = new NodeProperties(new WrapperService());
                break;
            case "Pool":
                node = new NodeProperties(new Pool());
                break;
            case "Apartment":
                node = new NodeProperties(new Apartment());
                break;
            case "Field":
                node = new NodeProperties(new Field());
                break;
            default:
                node = new NodeProperties(new HorizontalPropertyNode());
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
            writeProperty(node, rootXML, document);
        }
//            rootXML.appendChild(property);

//        }
//        for (NodeUser child : actual.getChildList()) {
//            writeXmlToUser(child, document, rootXML, listPropertiesToUser);
//        }
    }


    public void writeProperty(NodeProperties root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        System.out.println(rootXml.getTagName());
        writeProperty(root, rootXml, document);

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalProperty" + ".xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }


    public void writeProperty(NodeProperties actual, Element actualRoot, Document document) {
        Element property = null;
        if (!(actual.getData().getClass().getSimpleName().equals("HorizontalPropertyNode"))) {
            property = document.createElement(actual.getData().getClass().getSimpleName());
            if(actual.getData().getClass().getSimpleName().equals("WrapperService")){
                WrapperService aClass = (WrapperService) actual.getData();
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
                Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
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

    public void writeUsers(NodeUser root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        writeUsers(root, rootXml, document);

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalPropertyUsers" + ".xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    private void writeUsers(NodeUser actual, Element actualRoot, Document document) {
        Element property = null;
        if (!actual.getData().getName().equals("root")) {
            property = document.createElement(actual.getData().getName());
            Element idProperty = document.createElement("ID");
            Text textIdent = document.createTextNode(String.valueOf(actual.getData().getId()));
            idProperty.appendChild(textIdent);
            property.appendChild(idProperty);
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

    public NodeUser readUsers(AtomicInteger countUsers){
        Document document = convertXMLFileToXMLDocument("data/HorizontalPropertyUsers.xml");
        Node rootNodeElement = document.getDocumentElement();

        NodeUser nodeUser = readUsers(rootNodeElement,countUsers);
        System.out.println(countUsers);
        return nodeUser;
    }

    private NodeUser readUsers(Node rootNodeElement, AtomicInteger countUsers) {
        NodeUser nodeUser = null;
        nodeUser = caseNodeUser(nodeUser,rootNodeElement);
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
        switch(rootNodeElement.getNodeName()){
            case "House":
                nodeUser = new NodeUser(0,new PropertyNodeUser(0,"House"));
                break;
            case "Apartment":
                nodeUser = new NodeUser(0,new PropertyNodeUser(0,"Apartment"));
                break;
            default:
                nodeUser = new NodeUser(0,new Client(rootNodeElement.getNodeName()));
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
            Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
            idProperty.appendChild(textIdent);
            property.appendChild(idProperty);
            rootXml.appendChild(property);
        }
        for (NodeProperties child : actual.getChildList()) {
            if (property != null) {
                writeProperty(child, property, document);
            } else {
                writeProperty(child, rootXml, document);
            }
        }
    }


//    public ByteArrayOutputStream writeUsersInMemory(NodeUser root, String name) throws ParserConfigurationException {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        DOMImplementation implementation = builder.getDOMImplementation();
//        Document document = implementation.createDocument(null, name, null);
//        document.setXmlVersion("1.0");
//        Element rootXml = document.getDocumentElement();
//
//        writeUsersInMemory(root,rootXml,document);
//
//
//
//    }

    private void writeUsersInMemory(NodeUser root, Element rootXml, Document document) {

    }

    public void readProperty() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(new File("data/" + "HorizontalProperty" + ".xml"));
        NodeList listProperty = documento.getElementsByTagName("Property");


        for (int i = 0; i < listProperty.getLength(); i++) {
            Node nodo = listProperty.item(i);
            System.out.println(nodo.getNodeName());
            Node firstChild = nodo.getChildNodes().item(i);
            NodeList item = firstChild.getChildNodes();
            for (int j = 0; j < item.getLength(); j++) {
                Node nodeName = item.item(j);
                if (nodeName.getNodeType() == Node.ELEMENT_NODE) {
                    Element node = (Element) nodeName;
                    NodeList childNodes = node.getChildNodes();
                    for (int k = 0; k < childNodes.getLength(); k++) {
                        Node item1 = childNodes.item(k);
                        if (nodeName.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(" " + nodeName.getNodeName());
                            System.out.println("  " + item1.getTextContent());
                        }
                    }
                }

            }
//            System.out.println(firstChild.getNodeName());
//            NodeList childNodes = firstChild.getChildNodes();
//            String nodeValue = childNodes.item(1).getTextContent();
//            System.out.println(nodeValue);
//            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element) nodo;
//
//                NodeList childProperties = element.getChildNodes();
//                for (int j = 0; j < childProperties.getLength(); j++) {
//                    Node property = childProperties.item(j);
//                    if (property.getNodeType() == Node.ELEMENT_NODE) {
//                        Element e = (Element) property;
//                        System.out.println(e.getNodeName());
////                        System.out.println(e.getTagName());
//                        NodeList childNodes = e.getChildNodes();
//                        for (int h = 0; i < childNodes.getLength(); i++) {
//                            Node item = childNodes.item(h);
//                        }
//                    }
//                }
//            }
        }


    }

    public static void main(String[] args) {
//        try {
////            new Persistence().readXml();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
        AtomicInteger atomicInteger = new AtomicInteger();
        new Persistence().readUsers(atomicInteger);
    }

    public void writeProperties(NodeProperties treeProperties) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "HorizontalProperty", null);
        document.setXmlVersion("1.0");

        Element root = document.getDocumentElement();

        Element properties = document.createElement("Propiedad");

        for (NodeProperties child : treeProperties.getChildList()) {
            Element property = document.createElement(child.getData().getClass().getSimpleName());
            Element idProperty = document.createElement("ID");
            Text textIdent = document.createTextNode(String.valueOf(child.getData().getID()));
            for (NodeProperties childNodes : child.getChildList()) {
                String name = childNodes.getData().getClass().getSimpleName();
            }
        }

        Element user = document.createElement("Usuarios");
        Element emailUser = document.createElement("Email");
        Text textEmailUser = document.createTextNode("cristianCubides");
        emailUser.appendChild(textEmailUser);
        user.appendChild(emailUser);

        root.appendChild(user);

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalProperty" + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    public void loadXml() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("CiudadelaComfaboy.xml"));
            Element root = (Element) document.getDocumentElement().getChildNodes();
            Element email = document.createElement("Email");
            Text text = document.createTextNode("cristianCubides");
            email.appendChild(text);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
