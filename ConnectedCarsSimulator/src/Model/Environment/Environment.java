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

package Model.Environment;

import Model.Agents.Bodies.Infrastructure_Body;
import Model.Agents.Bodies.Vehicle_Body;
import Utility.CardinalPoint;
import Utility.Flow;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The class Environment represents the environment layer of the MAS.
 * 
 * It have the map of the differents infrastructures and the bodies of
 * the differents agents.
 * 
 * The environment guarantees the functioning of its physical.
 * 
 * @author Antoine "Avzgui" Richard
 * @see Infrastructure
 * @see Model.Agents.Bodies.Vehicle_Body
 * @see Model.Agents.Bodies.Infrastructure_Body
 */
public class Environment {

    private final Table<Integer, Integer, Infrastructure> map;
    private final ArrayList<Vehicle_Body> vehicles;
    private final ArrayList<Infrastructure_Body> infrastructures;
    public static int time;
            
    /**
     * Constructor
     */
    public Environment() {
        this.map = TreeBasedTable.create();
        this.vehicles = new ArrayList<>();
        this.infrastructures = new ArrayList<>();
    }

    /**
     * Returns the map of the infrastructures.
     * @return the map of infrastructure.
     */
    public Table<Integer, Integer, Infrastructure> getMap() {
        return map;
    }
    
    /**
     * Returns all cells of all infrastructure in the map.
     * 
     * @return the array of all the cells in the map.
     */
    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();

        //For each infrastructure in the map
        for (Infrastructure i : this.map.values()) {
            //Add cells
            cells.addAll(i.getCells());
        }

        return cells;
    }

    /**
     * Add an Infrastructure, of type Intersection, to the map.
     * 
     * @param x coordinate x on the map of the new Intersection.
     * @param y coordinate x on the map of the new Intersection.
     * @param nb_ways numbers of ways of the new Intersection.
     * @param ways_size sizes of ways of the new Intersection.
     * @param indonesian_cross type of the new Intersection.
     */
    public void addIntersection(int x, int y,
            Table<Flow, CardinalPoint, Integer> nb_ways,
            Table<Flow, CardinalPoint, Integer> ways_size,
            boolean indonesian_cross) {

        //Intersection creation, real position not defined yet
        Intersection intersection = new Intersection(0, 0, nb_ways, ways_size, indonesian_cross);

        //If the map is empty and x = 0 and y = 0
        if (this.map.isEmpty()) {
            if (x == 0 && y == 0) {
                this.map.put(x, y, intersection);
            } else {
                System.out.println("The first intersection should be in position [0, 0] not ["
                        + x + ", " + y + "]");
            }
        } else {

            //Future position
            Cell cell = null;

            //To determinate the new Intersection's position, check his future neighbors
            HashMap<CardinalPoint, Infrastructure> neighbors = new HashMap<>();
            neighbors.put(CardinalPoint.NORTH, this.map.get(x, y - 1));
            neighbors.put(CardinalPoint.EAST, this.map.get(x + 1, y));
            neighbors.put(CardinalPoint.SOUTH, this.map.get(x, y + 1));
            neighbors.put(CardinalPoint.WEST, this.map.get(x - 1, y));

            //For each neighbor not null
            for (Entry<CardinalPoint, Infrastructure> entry : neighbors.entrySet()) {
                if (entry.getValue() != null) {
                    //Get the the cell determinate by the neighbor
                    Cell c = entry.getValue().getCellForAnotherInfrastructure(
                            entry.getKey().getFront(),
                            intersection.getWidth(),
                            intersection.getHeight());

                    //If cell is null, affect c
                    if (cell == null) {
                        cell = c;
                    } else if (c.getX() != cell.getX() && c.getY() != cell.getY()) {
                        System.out.println("The intersection you want to add in"
                                + " position [" + x + ", " + y
                                + "] haven't good size");
                    }
                }
            }

            //If cell is not null, update intersection's position and add it to the map
            if (cell != null) {
                intersection.setPosition(cell.getX(), cell.getY());
                this.map.put(x, y, intersection);
            } else {
                System.out.println("The intersection need neighbors");
            }
        }
    }
    
    /**
     * Returns the infrastructure body who has the cell c.
     * 
     * @param c a cell.
     * @return the infrastructure who has this cell.
     */
    public Infrastructure_Body getInfrastructureWithCell(Cell c){
        
        //For each infrastructure body in the environment
        for(Infrastructure_Body body : this.infrastructures){
            //if one have the cell c, return body
            Infrastructure inf = body.getInfrastructure();
            if(inf.getCells().contains(c))
                return body;
        }
        
        //Else return null
        return null;
    }

    /**
     * Returns the collection of the bodies of the vehicle agents.
     * 
     * @return the collection of the vehicles' bodies.
     */
    public ArrayList<Vehicle_Body> getVehicles() {
        return this.vehicles;
    }
    
    /**
     * Returns the set of the positions of the vehicles' bodies
     * 
     * @return the set of the position of the vehicles.
     */
    public ArrayList<Cell> getVehiclesPosition(){
        ArrayList<Cell> pos = new ArrayList<>();
        
        for(Vehicle_Body v : this.vehicles)
                pos.add(v.getPosition());
        
        return pos;
    }
    
    /**
     * Adds a vehicle's body in the environment.
     * 
     * @param position the position where to put the vehicle.
     * @param vehicle the vehicle to add.
     */
    public void addVehicle(Cell position, Vehicle_Body vehicle){
        boolean ok = false;
        //For each infrastructure of the environment
        for(Infrastructure i : this.map.values()){
            for(Cell c : i.getCells()){
                //If the cell exist
                if(c.getX() == position.getX() && c.getY() == position.getY()){
                    ok = true;
                    break;
                }
            }
            if(ok) break;
        }

        if(ok){
            //Then add the vehicle
            this.vehicles.add(vehicle);
        }
        else
            System.out.println("The position [" + position.getX() + ", " 
                    + position.getY() + "]" + "doesn't exist");
    }
    
    /**
     * Removes a vehicle in the array of vehicles.
     * 
     * @param vehicle the vehicle to remove.
     */
    public void removeVehicle(Vehicle_Body vehicle){
        this.vehicles.remove(vehicle);
    }

    /**
     * Returns the array of infrastructures in the environment.
     * 
     * @return the array of infrastructure.
     */
    public ArrayList<Infrastructure_Body> getInfrastructures() {
        return this.infrastructures;
    }
    
    /**
     * Adds an infrastructure to the array.
     * 
     * @param infrastructure
     */
    public void addInfrastructure(Infrastructure_Body infrastructure){
        this.infrastructures.add(infrastructure);
    }
    
    /**
     * Returns the array of the positions of the infrastructures' bodies
     * 
     * @return the array of the position of the infrastructures.
     */
    public ArrayList<Cell> getInfrastructuresPosition(){
        ArrayList<Cell> pos = new ArrayList<>();
        
        for(Infrastructure_Body i : this.infrastructures)
                pos.add(i.getPosition());
        
        return pos;
    }

    /**
     * Removes all infrastructures and bodies in the environment
     */
    public void removeAll() {
        this.map.clear();
        this.vehicles.clear();
        this.infrastructures.clear();
    }
    
    /**
     * Updates the state of the environment and his bodies.
     * 
     * Physical gestion.
     */
    public void update(){
        //Move vehicules
        for(Vehicle_Body body : this.vehicles){
            
            //Get the direction
            Cell direction = body.getDirection();
            
            
            if(direction != null 
                    && body.getSpeed() > 0
                    ){
                //update the vehicle's position
                body.setPosition(new Cell(direction));
                body.setDirection(null);
            }
        }
        
        //Increment time
        Environment.time++;
    }
    
    /**
     * Function to know if there is collisions or not.
     * 
     * 0 : everything is awesome.
     * 1 : two vehicles on the same cell.
     * 2 : loop of vehicles.
     * 
     * @return code of the collision.
     */
    public int collisionManager(){
        
        /* Check if there is two vehicle is the same. 
        for(Vehicle_Body v1 : this.vehicles){
            for(Vehicle_Body v2 : this.vehicles){
                if(!v1.equals(v2) && v1.getPosition().equals(v2.getPosition())){
                    System.out.println("Vehicle " + v1.getId() + " and Vehicle " + v2.getId() + " both in the cell " + v2.getPosition() );
                    return 1;
                }
            }
        }
        //*/
        
        //* Check if there is a loop of vehicle.
        for(Vehicle_Body v1 : this.vehicles){
            //Get the infrastructure of the vehicle
            Infrastructure_Body infrastructure = v1.getInfrastructure();

            //Get the direction
            Cell direction = v1.getDirection();
            
            //While the direction is lock, get the vehicle who on the cell
            while(direction != null && infrastructure.getVehicleOnCell(direction) != null){
                Vehicle_Body v2 = infrastructure.getVehicleOnCell(direction);

                //If a loop is dicovered
                if(v2.equals(v1))
                    return 2;
                else
                    direction = v2.getDirection();
            }
        }
        //*/
        
        return 0;
    }
}
