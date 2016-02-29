package sentimentanalyzer;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

public class AlchemySa implements SentiAnalyzerI {
    private final String CONFIG_FILE = "src/com/alchemyapi/api/api_key.txt";
	
    public static void main(String[] args) {
		new AlchemySa().test();
	}    
    
    private void test() {
//    	SaResult result = this.getSentiment("That hat is ridiculous, Charles.");
    	SaResult result = this.getSentiment("I have to come back.");
    	System.out.println("Sentiment: " + result.getSentiment());
    	System.out.println("Score: " + result.getScore());
    }
    
    
    @Override
	public SaResult getSentiment(String text) {
		double score = 0.0;
		Sentiment sentiment = Sentiment.NEUTRAL;  
		
		try {
			// Create an AlchemyAPI object.
			AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile(CONFIG_FILE);
			
			// Extract sentiment for a text string.
			Document doc = alchemyObj.TextGetTextSentiment(text);
			NodeList list = doc.getElementsByTagName("docSentiment").item(0).getChildNodes();
	        
			for (int i = 0; i < list.getLength(); i++) {
	        	if (list.item(i).getNodeName().equals("score")) {
	        		score = Double.valueOf( list.item(i).getTextContent() );
	        	} else if (list.item(i).getNodeName().equals("type")) {
	        		String sentimentVal = list.item(i).getTextContent();
	        		sentiment = Sentiment.getSentiment(sentimentVal);
	        	}
	        }
			
			System.out.println(AlchemySa.getStringFromDocument(doc));
		} catch (XPathExpressionException | IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	    
		return new SaResult(sentiment, score);
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
