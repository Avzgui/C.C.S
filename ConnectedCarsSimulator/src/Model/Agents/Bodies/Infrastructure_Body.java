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
package Model.Agents.Bodies;

import Model.Agents.Brains.Infrastructure_Brain;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Environment.Infrastructure;
import Model.Messages.M_Bye;
import Model.Messages.M_Hello;
import Model.Messages.Message;
import Utility.CardinalPoint;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class Infrastructure_Body represents the body of an infrastructure agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class Infrastructure_Body extends A_Body {

    /** Link to infrastructure */
    protected final Infrastructure infrastructure;
    
    /** Array of vehicles on the infrastructure */
    protected final ArrayList<Vehicle_Body> vehicles;
    
    /** List of adjacent infrastructure */
    protected final HashMap<CardinalPoint, Infrastructure_Body> neighbors;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env environment of the body.
     * @param brain brain linked to the body.
     * @param infrastructure infrastructure that the infrastructure agent is responsible.
     */
    public Infrastructure_Body(int id, Environment env, 
            Infrastructure_Brain brain, Infrastructure infrastructure) {
        super(id, env, brain);
        this.infrastructure = infrastructure;
        this.vehicles = new ArrayList<>();
        this.neighbors = new HashMap<>();
        this.position = null;
    }

    /**
     * Returns the infrastructure that the infrastructure agent is responsible.
     * 
     * @return the infrastructure linked to the body.
     */
    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    /**
     * Returns the array of vehicles in dialog with the infrastructure.
     * 
     * @return the array of vehicles.
     */
    public ArrayList<Vehicle_Body> getVehicles() {
        return vehicles;
    }
    
    /**
     * Returns the vehicle on a cell.
     * 
     * @param c cell tested.
     * @return the vehicle on a cell, null if there is no vehicle.
     */
    public Vehicle_Body getVehicleOnCell(Cell c){
        
        for(Vehicle_Body v : this.vehicles){
            if(v.getPosition().equals(c))
                return v;
        }
        
        return null;
    }
    
    /**
     * Adds a vehicle to the array of the vehicles int dialog with infrastructure.
     * 
     * Needs to be surcharged by the children class.
     * 
     * @param vehicle the new vehicle to add.
     */
    abstract public void addVehicle(Vehicle_Body vehicle);

    /**
     * Returns the array of the neighbors of the infrastructure.
     * 
     * @return the array of neighbors.
     */
    public HashMap<CardinalPoint, Infrastructure_Body> getNeighbors() {
        return this.neighbors;
    }
    
    /**
     * Adds a infrastructure to the array of neighbors.
     * 
     * @param point the cardinal point where is the neighbor.
     * @param neighbor the new neighbor of the infrastructure.
     */
    public void addNeighbor(CardinalPoint point, Infrastructure_Body neighbor){
        this.neighbors.put(point, neighbor);
    }
    
    /**
     * Returns if there is a vehicle on a cell, or not.
     * 
     * @param c cell tested.
     * @return if there is a vehicle, or not, on a cell.
     */
    public boolean haveVehicleOnCell(Cell c){
        
        for(Vehicle_Body vehicle : this.vehicles){
            if(vehicle.getPosition().equals(c))
                return true;
        }
        
        return false;
    }
    
    @Override
    public void receiveMessage(Message mess){
        if(mess instanceof M_Hello){
            System.out.println("Infrastructure " + this.id + " receive M_Hello");
            this.brain.storeMessage(mess);
        }
        else if (mess instanceof M_Bye){
            System.out.println("Infrastructure " + this.id + " receive M_Bye");
            this.brain.storeMessage(mess);
        }
        else
            super.receiveMessage(mess);
    }
    
    
    /**
     * Sends a message to all of the vehicles in the infrastructure
     * 
     * @param mess 
     */
    public void sendBroadcast(Message mess){
        for(Vehicle_Body vehicle : this.vehicles){
            vehicle.receiveMessage(mess);
        }
    }
}
