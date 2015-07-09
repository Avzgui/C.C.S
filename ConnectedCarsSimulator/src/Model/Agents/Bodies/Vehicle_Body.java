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
package Model.Agents.Bodies;

import Model.Agents.Brains.Vehicle_Brain;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Messages.M_Welcome;
import Model.Messages.Message;

/**
 * The class Vehicle_Body, inherited by A_Body, represents the body of an agent
 * vehicle.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Vehicle_Body extends A_Body {
    
    protected Infrastructure_Body infrastructure;
    protected Cell position;
    protected Cell direction;
    protected final double max_speed;
    protected double speed;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env environment of the agent.
     * @param brain brain of the agent.
     * @param max_speed the max speed of the vehicle.
     */
    public Vehicle_Body(int id, Environment env, Vehicle_Brain brain, double max_speed) {
        super(id, env, brain);
        
        //Init attributes
        this.infrastructure = null;
        this.position = null;
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
     * Returns the position of the vehicle in the environment.
     * 
     * @return the position of the position.
     */
    public Cell getPosition() {
        return this.position;
    }

    /**
     * Changes the position of the vehicle in the environment.
     * 
     * @param position the new position of environment.
     */
    public void setPosition(Cell position) {
        this.position = position;
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
         * like we can do in 
         */
        if(mess instanceof M_Welcome){
            System.out.println("Vehicle receive M_Welcome");
            this.brain.storeMessage(mess);
        }
    }
}
