package com.laststandstudio.java.engine.src.world;

import com.laststandstudio.java.engine.src.Engine;
import com.laststandstudio.java.engine.src.RenderUtils;
import com.laststandstudio.java.engine.src.images.Texture;
import org.lwjgl.system.libffi.Closure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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
                                if (imageElem.getAttribute("type").equalsIgnoreCase("file")) {
                                    saveImageToRender(imageElem);
                                }else{
                                    saveImageToRenderOt(imageElem);

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
        try {
            Texture texture = new Texture(new File(imageElem.getElementsByTagName("link").item(0).getTextContent()).toURI());
            int x = Integer.parseInt(imageElem.getElementsByTagName("x").item(0).getTextContent());
            int y = Integer.parseInt(imageElem.getElementsByTagName("y").item(0).getTextContent());
            NodeList widthL = imageElem.getElementsByTagName("width");
            NodeList heightL = imageElem.getElementsByTagName("height");
            int width = widthL.getLength() > 0 ? Integer.parseInt(widthL.item(0).getTextContent()) : texture.width;
            int height = heightL.getLength() > 0 ? Integer.parseInt(heightL.item(0).getTextContent()) : texture.height;

            Engine.getInstance().addRender(1, () -> {
                RenderUtils.renderSprite(texture, x, y, width, height);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveImageToRenderOt(Element imageElem) {
        try {
            Texture texture = new Texture(new URL(imageElem.getElementsByTagName("link").item(0).getTextContent()).toURI());
            int x = Integer.parseInt(imageElem.getElementsByTagName("x").item(0).getTextContent());
            int y = Integer.parseInt(imageElem.getElementsByTagName("y").item(0).getTextContent());
            NodeList widthL = imageElem.getElementsByTagName("width");
            NodeList heightL = imageElem.getElementsByTagName("height");
            int width = widthL.getLength() > 0 ? Integer.parseInt(widthL.item(0).getTextContent()) : texture.width;
            int height = heightL.getLength() > 0 ? Integer.parseInt(heightL.item(0).getTextContent()) : texture.height;

            Engine.getInstance().addRender(1, () -> {
                RenderUtils.renderSprite(texture, x, y, width, height);
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void renderWorld() {
        parseFromXML("LevelSpec.xml");
    }

}
