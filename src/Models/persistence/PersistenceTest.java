package Models.persistence;

import Models.HorizontalProperty;
import Models.managerProperties.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PersistenceTest {


    public void finalMetodo(String nameFile) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(nameFile);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        doc.getDocumentElement().normalize();

        printXML(doc.getDocumentElement());

    }

    private void printXML(Element root) {
//        printXMLnodes((Node) root);
    }


    public static NodeProperties builtJtree(Node nodeRoot, NodeProperties nodePropertiesRoot, ArrayList<NodeProperties> apartmentList) {
        NodeList nodeList = nodeRoot.getChildNodes();
//        System.out.println(nodeRoot.getNodeName());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeProperties actualProperties = null;
                switch (tempNode.getNodeName()) {
                    case "Building":
                        actualProperties = new NodeProperties(new Building());
                        break;
                    case "Apartment":
                        actualProperties = new NodeProperties(new Apartment());
                        break;
                    case "House":
                        actualProperties = new NodeProperties(new House());
                        break;
                }
                if (tempNode.hasChildNodes()) {
                    if (actualProperties != null) {
                        builtJtree(tempNode, actualProperties, apartmentList);
                    } else {
                        builtJtree(tempNode, nodePropertiesRoot, apartmentList);
                    }
                }else{
                    nodePropertiesRoot.add(actualProperties);
                }
            } else if (tempNode.getNodeType() == Node.TEXT_NODE) {
                String text = tempNode.getNodeValue();
                if (text.trim().length() > 0) {
                    nodePropertiesRoot.getData().setId(Integer.parseInt(text));
                    nodePropertiesRoot.setId(Integer.parseInt(text));
//                System.out.println(tempNode.getParentNode().getParentNode().getNodeName()+ " "+ text);
//                    switch (nodeRoot.getParentNode().getNodeName()) {
//                        case "Apartment":
//                            Apartment apartment = new Apartment(Integer.parseInt(text));
//                            apartmentList.add(new NodeProperties(Integer.parseInt(text),apartment));
////                            System.out.println(nodeRoot.getParentNode().getNodeName());
//                            break;
//                        case "Building":
//                            Building building = new Building(Integer.parseInt(text));
//                            NodeProperties buildingNode = new NodeProperties(Integer.parseInt(text), building);
//                            for (NodeProperties apartmentAux:apartmentList) {
//                                buildingNode.add(apartmentAux);
//                            }
//                            apartmentList.clear();
//                            nodePropertiesRoot.add(buildingNode);
//                            break;
//                    }
                }
            }
        }

        return nodePropertiesRoot;
    }


    private static void printXMLnodes(Node node, int indent, TreeProperties treeProperties) {
        System.out.println();
        for (int i = 0; i < indent; i++) System.out.print("\t");
//        System.out.print(node.getNodeName());
//        System.out.print(": ");

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            if (node.getChildNodes().item(i).getParentNode().isSameNode(node)) {
                if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {

                    printXMLnodes(node.getChildNodes().item(i), indent + 1, treeProperties);

                } else if (node.getChildNodes().item(i).getNodeType() == Node.TEXT_NODE) {


                    String text = node.getChildNodes().item(i).getNodeValue();

                    if (text.trim().length() > 0) {
//                        System.out.println(node.getParentNode().getNodeName());
//                        System.out.print(text);

                        switch (node.getParentNode().getNodeName()) {
                            case "HorizontalProperty":
                                HorizontalPropertyNode horizontalProperty = new HorizontalPropertyNode();
                                NodeProperties nodePropeties = new NodeProperties(0, horizontalProperty);
                                treeProperties.setRoot(nodePropeties);
                                break;
                            case "Building":
                                Building building = new Building(Integer.parseInt(text));
                                treeProperties.add(0, new NodeProperties(Integer.parseInt(text), building));
                                break;
                            case "House":
                                House house = new House(Integer.parseInt(text));
                                treeProperties.add(0, new NodeProperties(Integer.parseInt(text), house));

                                break;

                        }


                    }
                }
            }
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(new File("data/" + "HorizontalProperty" + ".xml"));
        NodeList listProperty = documento.getElementsByTagName("Property");
        Node nodo = listProperty.item(0).getParentNode();

        TreeProperties treeProperties = new TreeProperties();
        HorizontalPropertyNode horizontalPropertyNode = new HorizontalPropertyNode(0);
        treeProperties.setRoot(new NodeProperties(0, horizontalPropertyNode));
        ArrayList<NodeProperties> list = new ArrayList<>();
        builtJtree(nodo, treeProperties.getRoot(), list);
        treeProperties.print();


//        printXMLnodes(nodo, 0, treeProperties);
//        treeProperties.printBreadth();
    }
}
