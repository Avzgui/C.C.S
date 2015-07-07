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

import Model.Agents.Brains.A_Brain;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Messages.Message;

/**
 * The class Vehicle_Body, inherited by A_Body, represents the body of an agent
 * vehicle.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Vehicle_Body extends A_Body {
    
    protected Cell position;
    protected Cell direction;
    
    protected double speed;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env environment of the agent.
     * @param brain brain of the agent.
     * @param speed speed of the vehicle.
     */
    public Vehicle_Body(int id, Environment env, A_Brain brain, int speed) {
        super(id, env, brain);
        this.position = null;
        this.direction = null;
        this.speed = 1.0;
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
    public void sendMessage(Class<? extends Message> mess) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
