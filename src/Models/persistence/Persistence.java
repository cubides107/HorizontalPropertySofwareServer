package Models.persistence;

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
            Result result = new StreamResult(new File(nameFile + ".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

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

    public static void main(String[] args) {
        Persistence persistence = new Persistence();
        persistence.saveUsersXml("CiudadelaComfaboy");

        persistence.loadXml();
    }

}
