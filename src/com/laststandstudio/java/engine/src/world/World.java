package com.laststandstudio.java.engine.src.world;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by joshu on 10/2/2015.
 */
public class World {



    private static void parseFromXML(String name) {
        try {

            File fXmlFile = new File(name);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList staticParts = doc.getDocumentElement().getElementsByTagName("static");

            if (staticParts.getLength() > 0) {
                for (int i = 0; i < staticParts.getLength(); i++) {
                    if (staticParts.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList imageList = ((Element) staticParts.item(i)).getElementsByTagName("image");
                        for (int j = 0; j < imageList.getLength(); j++) {
                            if (imageList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element imageElem = (Element) imageList.item(j);
                                if(imageElem.getAttribute("type").equalsIgnoreCase("file")){
                                    saveImageToRender(imageElem);
                                }
//                                System.out.println("Type: " + imageElem.getAttribute("type"));
//                                System.out.println("Link: " + imageElem.getElementsByTagName("link").item(0).getTextContent());
//                                System.out.println("X: " + imageElem.getElementsByTagName("x").item(0).getTextContent());
//                                System.out.println("Y: " + imageElem.getElementsByTagName("y").item(0).getTextContent());
//                                NodeList widthL = imageElem.getElementsByTagName("width");
//                                NodeList heightL = imageElem.getElementsByTagName("height");
//                                System.out.println("Width: " + (widthL.getLength() > 0 ? widthL.item(0).getTextContent() : ""));
//                                System.out.println("Height: " + (heightL.getLength() > 0 ? heightL.item(0).getTextContent() : ""));
//                                System.out.println("\n\n");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveImageToRender(Element imageElem) {

    }

    public void renderWorld() {
        parseFromXML("LevelSpec.xml");
    }

}
