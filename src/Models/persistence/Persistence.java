package Models.persistence;

import Models.managerProperties.NodeProperties;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

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


    public void writeProperty(NodeProperties root, String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "HorizontalProperty", null);
        document.setXmlVersion("1.0");
        Element rootXml = document.getDocumentElement();
        Element properties = document.createElement("Propiedades");
        properties = writeProperty(name, root, properties, document);
        rootXml.appendChild(properties);
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("data/" + "HorizontalProperty" + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    private Element writeProperty(String name, NodeProperties actual, Element properties, Document document) {
        Element propertyTags = document.createElement("Property");
        Element property = document.createElement(actual.getData().getClass().getSimpleName());
        if (property.getTagName().equals("HorizontalPropertyNode")) {
            Element nameProperty = document.createElement("Name");
            Text textIdent = document.createTextNode(name);
            nameProperty.appendChild(textIdent);
            property.appendChild(nameProperty);
        }
        Element idProperty = document.createElement("ID");
        Text textIdent = document.createTextNode(String.valueOf(actual.getData().getID()));
        idProperty.appendChild(textIdent);
        property.appendChild(idProperty);
        if (actual.getData().getClass().getSimpleName().equals("HorizontalPropertyNode") || actual.getData().getClass().getSimpleName().equals("Apartment")) {
            properties.appendChild(property);
        } else {
            propertyTags.appendChild(property);
            properties.appendChild(propertyTags);
        }
        for (NodeProperties child : actual.getChildList()) {
            writeProperty(name, child, property, document);
        }
        return properties;
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
                            System.out.println("  " +item1.getTextContent());
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
        try {
            new Persistence().readProperty();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
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
