package ru.zapped.tgbot.quickbuild;

import org.apache.commons.io.IOUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;

public class QuickbuildClient {
    public List<QuickbuildConfig> listConfigurations(String arguments) {
        List<QuickbuildConfig> result = new ArrayList<QuickbuildConfig>();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://172.20.140.80:8810")
                .path("rest/configurations")
                .queryParam("recursive", "true");
        Response response = target.request().get();
        String responseXML = response.readEntity(String.class);
        InputStream responseXMLStream = IOUtils.toInputStream(responseXML);
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(responseXMLStream);
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xpath.compile("//com.pmease.quickbuild.model.Configuration/name");
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                result.add(new QuickbuildConfig(nodeList.item(i).getFirstChild().getNodeValue()));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
