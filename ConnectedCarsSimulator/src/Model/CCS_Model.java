/*
 * Copyright (C) 2015 Antoine "Avzgui" Richard and collaborators
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
import Model.Agents.Bodies.Vehicle_Body;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Environment.Infrastructure;
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
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Random;
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
    /** Current tick */
    public static int ticks; 
    private int stepByTick;
    private int collision;
    private File envFile;
    
    /**
     * Constructor
     */
    public CCS_Model() {
        this.env = new Environment();
        this.vehicles = new ArrayList<>();
        this.infrastructures = new ArrayList<>();
        this.nb_agents = 0;
        CCS_Model.ticks = 0;
        this.collision = 0;
        this.envFile = null;
        this.stepByTick = 1;
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
     * Returns the number of model's ticks by environment's time.
     * 
     * @return the number of step by sticks.
     */
    public int getStepByTick() {
        return stepByTick;
    }

    /**
     * Changes the number of model's ticks by environment's time.
     * 
     * @param stepByTick the new number of step by sticks.
     */
    public void setStepByTick(int stepByTick) {
        this.stepByTick = stepByTick;
    }

    /**
     * Returns the collision code of the last simulation.
     * 
     * @return the collision code.
     */
    public int getCollision() {
        return collision;
    }

    /**
     * Returns the file with which the environment was load.
     * 
     * @return the last environment XML file loaded.
     */
    public File getEnvFile() {
        return envFile;
    }
    
    /**
     * Removes all vehicles that have reached their goal.
     */
    public void removeVehicles(){
        ArrayList<A_Vehicle> index = new ArrayList<>();
        
        //For each vehicle
        for(A_Vehicle vehicle : this.vehicles){
            //If vehicle's goal reached, save his index and delete it of the env.
            if(vehicle.goalReached()){
                index.add(vehicle);
                this.env.removeVehicle((Vehicle_Body) vehicle.getBody());
            }
        }
        
        //Delete all vehicles indexed
        this.vehicles.removeAll(index);
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

            //Here the document is in a good format
            this.envFile = file;
            
            //Clear environment
            this.env.removeAll();
            
            //Clear agents
            CCS_Model.ticks = 0;
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
            
            //When all infrastructure are added to the environment, generate neighborhood.
            for(int row : this.env.getMap().rowKeySet()){
                for(Entry<Integer, Infrastructure> entry : this.env.getMap().row(row).entrySet()){
                    int column = entry.getKey();
                    Infrastructure i = entry.getValue();
                    
                    //Get the agent who has the infrastructure i
                    Infrastructure_Body i_body = null;
                    for(Infrastructure_Body body : this.env.getInfrastructures()){
                        if(body.getInfrastructure() == i){
                            i_body = body;
                            break;
                        }
                    }
                    
                    //If the agent was found
                    if(i_body != null){
                        
                        //Get is neighbors
                        
                        /* ---- NORTH ---- */
                        if(this.env.getMap().contains(row, column-1)){
                            //Get the agent who has the infrastructure i
                            i = this.env.getMap().get(row, column-1);
                            Infrastructure_Body north_body = null;
                            for(Infrastructure_Body body : this.env.getInfrastructures()){
                                if(body.getInfrastructure() == i){
                                    north_body = body;
                                    break;
                                }
                            }
                            
                            //If a north neighbor was found
                            if(north_body != null)
                                i_body.addNeighbor(CardinalPoint.NORTH, north_body);
                        }
                        
                        /* ---- EAST ---- */
                        if(this.env.getMap().contains(row+1, column)){
                            //Get the agent who has the infrastructure i
                            i = this.env.getMap().get(row+1, column);
                            Infrastructure_Body east_body = null;
                            for(Infrastructure_Body body : this.env.getInfrastructures()){
                                if(body.getInfrastructure() == i){
                                    east_body = body;
                                    break;
                                }
                            }
                            
                            //If a north neighbor was found
                            if(east_body != null)
                                i_body.addNeighbor(CardinalPoint.EAST, east_body);
                        }
                        
                        /* ---- SOUTH ---- */
                        if(this.env.getMap().contains(row, column+1)){
                            //Get the agent who has the infrastructure i
                            i = this.env.getMap().get(row, column+1);
                            Infrastructure_Body south_body = null;
                            for(Infrastructure_Body body : this.env.getInfrastructures()){
                                if(body.getInfrastructure() == i){
                                    south_body = body;
                                    break;
                                }
                            }
                            
                            //If a north neighbor was found
                            if(south_body != null)
                                i_body.addNeighbor(CardinalPoint.SOUTH, south_body);
                        }
                        
                        /* ---- WEST ---- */
                        if(this.env.getMap().contains(row-1, column)){
                            //Get the agent who has the infrastructure i
                            i = this.env.getMap().get(row-1, column);
                            Infrastructure_Body west_body = null;
                            for(Infrastructure_Body body : this.env.getInfrastructures()){
                                if(body.getInfrastructure() == i){
                                    west_body = body;
                                    break;
                                }
                            }
                            
                            //If a north neighbor was found
                            if(west_body != null)
                                i_body.addNeighbor(CardinalPoint.WEST, west_body);
                        }
                    }
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException | DOMException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * To save a simulation in a XML file.
     * 
     * TODO
     * 
     * @param file file to save the simulation.
     */
    public void saveSimulationToXML(File file){
        // TODO
    }
    
    /**
     * Run simulation
     */
    @Override
    public void run(){
        //Initialization of the simulation
        CCS_Model.ticks = 0;
        this.collision = 0;
        this.nb_agents = 0;
        Environment.time = 0;
        
        /* Init one agent
        A_Vehicle vehicle = null;
        vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(8, 0), new Cell(0, 8));
        this.vehicles.add(vehicle);
        //*/
        
        //*Run the simulation while there is vehicles
        Random rand = new Random();
        while(CCS_Model.ticks == 0
                || (!this.vehicles.isEmpty()
                //&& CCS_Model.ticks < 100
                && this.collision == 0)){
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CCS_View.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            //Increment the ticks
            CCS_Model.ticks++;
            
            System.out.println("\n----- Tick : " + CCS_Model.ticks + " ----- Environment Time : " + Environment.time + " -----\n");
            
            //Update the infrastructures
            for(A_Infrastructure i : this.infrastructures)
                i.update();
            
            //Update the vehicles
            Collections.shuffle(this.vehicles);
            for(A_Vehicle v : this.vehicles)
                v.update();
            
            //Each 10 ticks
            if(CCS_Model.ticks % this.stepByTick == 0){
                
                //Remove all the vehicles who was in their goal.
                removeVehicles();
                
                //Update the environment
                this.env.update();
                
                //Get the collision code
                this.collision = this.env.collisionManager();

                //* Generate new vehicles (TODO properly)
               
                A_Vehicle vehicle = null;
                
                if(Environment.time % 1 == 0){
                    switch(rand.nextInt(16)){
                        case 0 : //North to West
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(8, 0), new Cell(0, 8));
                            this.vehicles.add(vehicle);
                        break;
                        case 1 : //North to South, right lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(8, 0), new Cell(8, 22));
                            this.vehicles.add(vehicle);
                        break;
                        case 2 : //North to South, mid lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(9, 0), new Cell(9, 22));
                            this.vehicles.add(vehicle);
                        break;
                        case 3 : // North to East
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(10, 0), new Cell(22, 12));
                            this.vehicles.add(vehicle);
                        break;
                            
                        case 4 : //East to North
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(22, 8), new Cell(14, 0));
                            this.vehicles.add(vehicle);
                        break;
                        case 5 : //East to West, right lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(22, 8), new Cell(0, 8));
                            this.vehicles.add(vehicle);
                        break;
                        case 6 : //East to West, mid lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(22, 9), new Cell(0, 9));
                            this.vehicles.add(vehicle);
                        break;
                        case 7 : //East to South
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(22, 10), new Cell(10, 22));
                            this.vehicles.add(vehicle);
                        break;
                            
                        case 8 : //South to East
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(14, 22), new Cell(22, 14));
                            this.vehicles.add(vehicle);
                        break;
                        case 9 : //South to North, right lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(14, 22), new Cell(14, 0));
                            this.vehicles.add(vehicle);
                        break;
                        case 10 : //South to North, mid lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(13, 22), new Cell(13, 0));
                            this.vehicles.add(vehicle);
                        break;
                        case 11 : //South to West
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(12, 22), new Cell(0, 10));
                            this.vehicles.add(vehicle);
                        break;
                            
                        case 12 : //West to South
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(0, 14), new Cell(8, 22));
                            this.vehicles.add(vehicle);
                        break;
                        case 13 : //West to East, right lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(0, 14), new Cell(22, 14));
                            this.vehicles.add(vehicle);
                        break;
                        case 14 : //West to East, mid lane
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(0, 13), new Cell(22, 13));
                            this.vehicles.add(vehicle);
                        break;
                        case 15 : //West to North
                            vehicle = new A_Vehicle(++this.nb_agents, this.env, new Cell(0, 12), new Cell(12, 0));
                            this.vehicles.add(vehicle);
                        break;
                    }
                }
                //*/
            }

        }
        //*/
    }
}
