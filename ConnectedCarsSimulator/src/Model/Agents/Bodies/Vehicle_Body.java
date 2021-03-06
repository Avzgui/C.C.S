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

import Model.Agents.Brains.Vehicle_Brain;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Messages.M_Conf;
import Model.Messages.M_Welcome;
import Model.Messages.Message;

/**
 * The class Vehicle_Body, inherited by A_Body, represents the body of an agent
 * vehicle.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Vehicle_Body extends A_Body {
    
    /** Current infrastructure */
    protected Infrastructure_Body infrastructure;
    
    /** Next cell where the vehicle will go */
    protected Cell direction;
    
    /** Maximal speed of the vehicle */
    protected final double max_speed;
    
    /** Current speed of the vehicle */
    protected double speed;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env environment of the agent.
     * @param pos position of the agent.
     * @param brain brain of the agent.
     * @param max_speed the max speed of the vehicle.
     */
    public Vehicle_Body(int id, Environment env, Cell pos, Vehicle_Brain brain, double max_speed) {
        super(id, env, brain);
        
        //Init attributes
        this.position = pos;
        this.infrastructure = this.env.getInfrastructureWithCell(pos);
        this.direction = null;
        this.max_speed = max_speed;
        this.speed = 0.0;
    }

    /**
     * Returns the current infrastructure in dialog with the vehicle.
     * 
     * @return the infrastructure in dialog with the vehicle.
     */
    public Infrastructure_Body getInfrastructure() {
        return infrastructure;
    }

    /**
     * Changes the current infrastructure.
     * 
     * @param infrastructure the new infrastructure
     */
    public void setInfrastructure(Infrastructure_Body infrastructure) {
        this.infrastructure = infrastructure;
    }

    /**
     * Returns the direction of the vehicle.
     * 
     * @return the direction of the vehicle.
     */
    public Cell getDirection() {
        return this.direction;
    }

    /**
     * Changes the direction of the vehicle.
     * 
     * @param direction the new direction of the vehicle.
     */
    public void setDirection(Cell direction) {
        this.direction = direction;
    }

    /**
     * Returns the speed maximum of the vehicle.
     * 
     * @return the max speed of the vehicle.
     */
    public double getMax_speed() {
        return max_speed;
    }

    /**
     * Returns the speed of the vehicle.
     * 
     * @return the speed of the vehicle.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Changes the speed of the vehicle.
     * 
     * @param speed the new speed of the vehicle.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void sendMessage(Message mess) {
        this.infrastructure.receiveMessage(mess);
    }
    
    @Override
    public void receiveMessage(Message mess){
        /* It's ugly
         * but i don't know how to do specialization with java's generics
         * like we can do in C++
         */
        if(mess instanceof M_Welcome){
            System.out.println("Vehicle " + this.id + " receive M_Welcome");
            this.brain.storeMessage(mess);
        }
        else if(mess instanceof M_Conf){
            System.out.println("Vehicle " + this.id + " receive M_Conf");
            this.brain.storeMessage(mess);
        }
        else
            super.receiveMessage(mess);
    }
    
    /**
     * Motor/Sensor method to look if a cell is free, or not.
     * 
     * n.b : i don't if it's a motor function (agent to envronment)
     * or a sensor function (environment to agent). 'Cause it does
     * the both : agent to environment and environment to agent.
     * 
     * @param c cell tested.
     * @return if a cell looks free or not.
     */
    public boolean lookIfCellIsFree(Cell c){
        return !this.infrastructure.haveVehicleOnCell(c);
    }
    
    /**
     * Motor/Sensor method to look if a vehicle on a cell is stopped, or not.
     * 
     * @param c cell tested
     * 
     * @return if the vehicle on the cell looks stopped or not 
     */
    public boolean lookIfVehicleOnCellIsStopped(Cell c){
       
       if(this.infrastructure.haveVehicleOnCell(c)){
           Vehicle_Body v_body = this.infrastructure.getVehicleOnCell(c);
           if(v_body != null) //just in case
               return (v_body.getSpeed() == 0.0);
       }
       
       return false; 
    }
}
