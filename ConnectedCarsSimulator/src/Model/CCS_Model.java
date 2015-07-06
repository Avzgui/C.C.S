/*
 * Copyright (C) 2015 Antoine "Avzgui" Richard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package Model;

import Model.Environment.Environment;
import Utility.CardinalPoint;
import Utility.Flow;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The model class of the MVC design.
 * 
 * Initialize the environment and the agents of the SMA.
 * Update the agents and get some statistics.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Model {

    private final Environment env;

    /**
     * Constructor
     */
    public CCS_Model() {
        this.env = new Environment();
    }

    /**
     * Returns the link to the environment.
     * 
     * @return a the link to the environment.
     */
    public Environment getEnvironment() {
        return this.env;
    }

    /**
     * XML parser to load an environment's save.
     * 
     * @param file a xml file
     */
    public void loadEnvironmentFromXML(File file) {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            dFactory.setValidating(true);
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

            //DTD validation
            dBuilder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                }
            });
            Document doc = dBuilder.parse(file);

            //Clear environment
            this.env.removeAll();
            
            //For each intersection
            NodeList intersectionList = doc.getElementsByTagName("intersection");
            for (int i = 0; i < intersectionList.getLength(); i++) {
                Node iNode = intersectionList.item(i);
                Element iElement = (Element) iNode;
                int x = Integer.parseInt(iElement.getAttribute("x"));
                int y = Integer.parseInt(iElement.getAttribute("y"));
                if (iNode.getNodeName().equals("intersection")) {

                    //Get the indonesian parameter
                    Node tmp = iElement.getElementsByTagName("indonesian").item(0);
                    boolean indonesian = Boolean.parseBoolean(tmp.getTextContent());

                    //Get the ways and their size
                    Table<Flow, CardinalPoint, Integer> nb_ways = HashBasedTable.create();
                    Table<Flow, CardinalPoint, Integer> ways_size = HashBasedTable.create();

                    //Get the cardinal points
                    NodeList cardinalList = iElement.getElementsByTagName("cardinal");
                    for (int j = 0; j < cardinalList.getLength(); j++) {
                        Node cNode = cardinalList.item(j);
                        Element cElement = (Element) cNode;

                        //Get the flows
                        NodeList flowList = cElement.getElementsByTagName("flow");
                        for (int k = 0; k < flowList.getLength(); k++) {
                            Node fNode = flowList.item(k);
                            Element fElement = (Element) fNode;
                            
                            //Get cardinal type
                            CardinalPoint point = null;
                            switch (cElement.getAttribute("type")) {
                                case "north":
                                    point = CardinalPoint.NORTH;
                                    break;
                                case "east":
                                    point = CardinalPoint.EAST;
                                    break;
                                case "south":
                                    point = CardinalPoint.SOUTH;
                                    break;
                                case "west":
                                    point = CardinalPoint.WEST;
                                    break;
                            }
                            
                            //Get Flow type
                            Flow flow = null;
                            switch (fElement.getAttribute("type")) {
                                case "in":
                                    flow = Flow.IN;
                                    break;
                                case "out":
                                    flow = Flow.OUT;
                                    break;
                            }
                            
                            //Get number of ways
                            tmp = fElement.getElementsByTagName("ways").item(0);
                            int ways = Integer.parseInt(tmp.getTextContent());
                            
                            //Get size of ways
                            tmp = fElement.getElementsByTagName("size").item(0);
                            int size = Integer.parseInt(tmp.getTextContent());
                            
                            //Add to the tables
                            nb_ways.put(flow, point, ways);
                            ways_size.put(flow, point, size);
                        }
                    }
                    
                    //Add the intersection to the environment
                    this.env.addIntersection(x, y, nb_ways, ways_size, indonesian);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException | DOMException e) {
            System.out.println(e.getMessage());
        }
    }
}
