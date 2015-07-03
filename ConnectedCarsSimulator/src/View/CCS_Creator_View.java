/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utility.CardinalPoint;
import Utility.Flow;
import Model.Environment.Intersection;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_View{
    
    private final JFrame frame;
    private final CCS_Creator_Central_Panel central_panel;
    private final CCS_Creator_Left_Panel left_panel;
    private final JToolBar tools;
    
    public CCS_Creator_View(int height, int width){
        super();
        
        //Init datum for intersection
        Table<Flow, CardinalPoint, Integer> nb_ways = HashBasedTable.create();
        int nb = 3;
        nb_ways.put(Flow.IN, CardinalPoint.EAST, nb);
        nb_ways.put(Flow.IN, CardinalPoint.WEST, nb);
        nb_ways.put(Flow.IN, CardinalPoint.NORTH, nb);
        nb_ways.put(Flow.IN, CardinalPoint.SOUTH, nb);
        
        nb_ways.put(Flow.OUT, CardinalPoint.EAST, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.WEST, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.NORTH, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.SOUTH, nb);
        
        Table<Flow, CardinalPoint, Integer> ways_size = HashBasedTable.create();
        int size = 8;
        ways_size.put(Flow.IN, CardinalPoint.EAST, size);
        ways_size.put(Flow.IN, CardinalPoint.WEST, size);
        ways_size.put(Flow.IN, CardinalPoint.NORTH, size);
        ways_size.put(Flow.IN, CardinalPoint.SOUTH, size);
        
        ways_size.put(Flow.OUT, CardinalPoint.EAST, size);
        ways_size.put(Flow.OUT, CardinalPoint.WEST, size);
        ways_size.put(Flow.OUT, CardinalPoint.NORTH, size);
        ways_size.put(Flow.OUT, CardinalPoint.SOUTH, size);
        
        Intersection intersection = new Intersection(0, 0, nb_ways, ways_size, true);
        
        //Frame initialization
        this.frame = new JFrame("CCS Creator");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(height, width));
        this.frame.setResizable(true);
        
        //Left Panel initialization
        this.left_panel = new CCS_Creator_Left_Panel(this);
        
        //Central Panel initialization
        this.central_panel = new CCS_Creator_Central_Panel(this, 5, intersection);
        
        //Make the central panel scrollable
        JScrollPane scrollPane = new JScrollPane(this.central_panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, height, width);

        //ToolBar initialization
        this.tools = new JToolBar();
        
        //Elements layout
        this.frame.setLayout(new BorderLayout());
        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(this.left_panel, BorderLayout.WEST);
        this.frame.add(tools, BorderLayout.NORTH);
        
        //pack and show
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public CCS_Creator_Central_Panel getCentral_panel() {
        return central_panel;
    }

    public CCS_Creator_Left_Panel getLeft_panel() {
        return left_panel;
    }

    public JToolBar getTools() {
        return tools;
    }
    
    public void loadFromXML(File file){
        
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            dFactory.setValidating(true);
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            //DTD validation
            dBuilder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    // do something more useful in each of these handlers
                    exception.printStackTrace();
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }
            });

            Document doc = dBuilder.parse(file);

            //If we are here, the file is DTD validate
            this.central_panel.getMap().clear();
            this.central_panel.removeAll();
            this.central_panel.repaint();

            NodeList intersectionList = doc.getElementsByTagName("intersection");
            
            for (int i = 0; i < intersectionList.getLength(); i++) {
 
                Node iNode = intersectionList.item(i);
                Element iElement = (Element) iNode;
                int x = Integer.parseInt(iElement.getAttribute("x"));
                int y = Integer.parseInt(iElement.getAttribute("y"));
                
                if(iNode.getNodeName().equals("intersection")){
                    //Get the indonesian parameter
                    Node tmp = iElement.getElementsByTagName("indonesian").item(0);
                    boolean indonesian = Boolean.parseBoolean(tmp.getTextContent());
                    
                    System.out.println("\nCurrent Element :" + iNode.getNodeName() 
                            + "[" + x + ", " + y + "]" + " indonesian : " + indonesian);

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
                            switch(cElement.getAttribute("type")){
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
                            switch(fElement.getAttribute("type")){
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
                            
                            System.out.println("Cardinal : " + point
                                                + " Flow : " + flow
                                                + " Nb Ways : " + ways
                                                + " Size : " + size);
                            
                            //Add to the tables
                            nb_ways.put(flow, point, ways);
                            ways_size.put(flow, point, size);
                        }
                    }
                    
                    //Create Intersection
                    Intersection intersection = new Intersection(0, 0, nb_ways, ways_size, true);
                    this.central_panel.addInfrastructure(x, y, intersection);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
