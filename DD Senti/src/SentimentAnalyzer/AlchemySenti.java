package SentimentAnalyzer;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

public class AlchemySenti {
    public static void main(String[] args) throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException {
		// Create an AlchemyAPI object.
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("src/com/alchemyapi/api/api_key.txt");

        // Extract sentiment for a text string.
        Document doc = alchemyObj.TextGetTextSentiment(
            "That hat is ridiculous, Charles.");
        
        NodeList list = doc.getElementsByTagName("docSentiment").item(0).getChildNodes();
        int iter = list.getLength();
        
        for (int i = 0; i < iter; i++) {
        	if (list.item(i).getNodeName().equals("score"))
        		System.out.println("Score = " + list.item(i).getTextContent());
        	else if (list.item(i).getNodeName().equals("type"))
        		System.out.println("Eval = " + list.item(i).getTextContent());
        }
        
        System.out.println("-----------");
        
        System.out.println(getStringFromDocument(doc));
        
    }

    // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
