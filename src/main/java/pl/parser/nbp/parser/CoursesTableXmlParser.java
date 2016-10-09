package pl.parser.nbp.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.parser.nbp.type.CourseType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class CoursesTableXmlParser implements IParser {

    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document document;

    public CoursesTableXmlParser(String xml) {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getCurrencyRate(String currencyCode, CourseType courseType) {
        double sellingRate = 0;
        NodeList positionChildNodes = getPositionNodeForCurrencyCode(currencyCode).getChildNodes();

        for (int i = 0; i < positionChildNodes.getLength(); i++) {
            if (positionChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE &&
                    positionChildNodes.item(i).getNodeName().equals(courseType.toString())) {
                return Double.parseDouble(positionChildNodes.item(i).getTextContent().replace(',', '.'));
            }
        }
        return sellingRate;
    }

    public Node getPositionNodeForCurrencyCode(String currencyCode) {
        NodeList positionNodes = document.getElementsByTagName("pozycja");
        Node searchNode = null;
        NodeList positionChildNodes;

        for (int i = 0; i < positionNodes.getLength(); i++) {
            positionChildNodes = positionNodes.item(i).getChildNodes();
            for (int j = 0; j < positionChildNodes.getLength(); j++) {
                if (positionChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE &&
                        positionChildNodes.item(j).getTextContent().equals(currencyCode)) {
                    return positionNodes.item(i);
                }
            }
        }
        return searchNode;
    }
}
