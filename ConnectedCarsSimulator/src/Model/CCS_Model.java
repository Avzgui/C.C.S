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

import Model.Agents.A_Infrastructure;
import Model.Agents.A_Intersection;
import Model.Agents.A_Vehicle;
import Model.Agents.Bodies.Infrastructure_Body;
import Model.Agents.Bodies.Intersection_Body;
import Model.Agents.Bodies.Vehicle_Body;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Environment.Intersection;
import Utility.CardinalPoint;
import Utility.Flow;
import View.CCS_View;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Initialize the environment and the agents of the MAS.
 * Update the agents and get some statistics.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Model extends Thread {

    private final Environment env;
    private final ArrayList<A_Vehicle> vehicles;
    private final ArrayList<A_Infrastructure> infrastructures;
    private int nb_agents;
    private int ticks;
    
    /**
     * Constructor
     */
    public CCS_Model() {
        this.env = new Environment();
        this.vehicles = new ArrayList<>();
        this.infrastructures = new ArrayList<>();
        this.nb_agents = 0;
        this.ticks = 0;
    }

    /**
     * Returns the link to the environment.
     * 
     * @return a the link to the environment.
     */
    public Environment getEnvironment(){
        return this.env;
    }

    /**
     * Returns the array of vehicle agents in the MSA.
     * 
     * @return the array of vehicles.
     */
    public ArrayList<A_Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Returns the array of infrastructure agents in the MSA.
     * 
     * @return the array of infrastructures.
     */
    public ArrayList<A_Infrastructure> getInfrastructures() {
        return infrastructures;
    }

    /**
     * Returns the number of agents in the MSA.
     * 
     * @return the number of agents in the MSA.
     */
    public int getNb_agents() {
        return nb_agents;
    }

    /**
     * Returns the number of ticks.
     * 
     * @return the number of ticks.
     */
    public int getTicks() {
        return ticks;
    }
    
    /**
     * Removes all vehicles that have reached their goal.
     */
    public void removeVehicles(){
        ArrayList<Integer> index = new ArrayList<>();
        
        //For each vehicle
        for(A_Vehicle vehicle : this.vehicles){
            //If vehicle's goal reached, save his index and delete it of the env.
            if(vehicle.goalReached()){
                index.add(this.vehicles.indexOf(vehicle));
                this.env.removeVehicle((Vehicle_Body) vehicle.getBody());
            }
        }
        
        //Delete all vehicles indexed
        for(int i : index)
            this.vehicles.remove(i);
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
            
            //Clear agents
            this.ticks = 0;
            this.nb_agents = 0;
            this.vehicles.clear();
            this.infrastructures.clear();
            
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
                    
                    //Create a new intersection agent
                    A_Intersection agent = new A_Intersection(++this.nb_agents, this.env, 
                                            (Intersection) this.env.getMap().get(x, y));
                    
                    //Add the body of this agent in the environment.
                    this.env.addInfrastructure((Infrastructure_Body) agent.getBody());
                    
                    //Add the agent the array of infrastructure agent.
                    this.infrastructures.add(agent);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException | DOMException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run(){
        //Init agents
        A_Vehicle vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(12, 22), new Cell(0, 10));
        this.vehicles.add(vehicle);
        
        //Link
        Intersection_Body inter = (Intersection_Body) this.infrastructures.get(0).getBody();
        inter.addVehicle((Vehicle_Body) vehicle.getBody());
        
        /*
        vehicle = new A_Vehicle(++this.nb_agents, this.env);
        this.env.addVehicle(new Cell(0, 13), (Vehicle_Body) vehicle.getBody());
        this.vehicles.add(vehicle);
        
        Intersection_Body inter = (Intersection_Body) this.infrastructures.get(0).getBody();
        inter.addVehicle((Vehicle_Body) vehicle.getBody());
        //*/
        
        /*
        vehicle = new A_Vehicle(++this.nb_agents, this.env);
        this.env.addVehicle(new Cell(0, 12), (Vehicle_Body) vehicle.getBody());
        this.vehicles.add(vehicle);
        
        Intersection_Body inter = (Intersection_Body) this.infrastructures.get(0).getBody();
        inter.addVehicle((Vehicle_Body) vehicle.getBody());
        //*/
        
        //Run the simulation while there is vehicles
        while(!this.vehicles.isEmpty() && this.ticks < 100){
            //*
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(CCS_View.class.getName()).log(Level.SEVERE, null, ex);
            }
            //*/
            
            //Remove all the vehicles who was in their goal.
            removeVehicles();
            
            //Generate new vehicles
            
            //Update the infrastructures
            for(A_Infrastructure i : this.infrastructures)
                i.update();
            
            //Update the vehicles
            for(A_Vehicle v : this.vehicles)
                v.update();

            //Update the environment
            this.env.update();
            
            //Increment the ticks
            this.ticks++;
        }
    }
}
