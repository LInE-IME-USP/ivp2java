package usp.ime.line.ivprog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ilm.framework.domain.DomainConverter;

public class IVPDomainConverter implements DomainConverter {
    public Vector convertStringToObject(String objectListDescription) {
        return new Vector();
    }
    
    public String convertObjectToString(Vector objectList) {
        return "";
    }
    
    public Vector convertStringToAction(String actionListDescription) {
        Document doc = null;
        try{
            doc = loadXMLFromString("<gambi>"+actionListDescription+"</gambi>");
        }catch(Exception e){
            e.printStackTrace();
        }
        Element node = doc.getDocumentElement();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            String value = currentNode.getNodeName().trim();
            NodeList nodes = currentNode.getChildNodes();
            for (int j = 0; j < nodes.getLength(); j++) {
                Node cnode = nodes.item(j);
                System.out.println(cnode.getNodeName());
            }
        }
        return new Vector();
    }
    
    public String convertActionToString(Vector actionList) {
        String str = "";
        for(int i = 0; i < actionList.size(); i++){
            str += actionList.get(i).toString();
        }
        return str;
    }
    
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
    
}
