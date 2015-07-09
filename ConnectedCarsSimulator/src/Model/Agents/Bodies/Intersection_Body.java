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

import Model.Agents.Brains.Intersection_Brain;
import Model.Environment.Environment;
import Model.Environment.Intersection;
import Model.Messages.M_Welcome;
import Model.Messages.Message;
import Utility.CardinalPoint;

/**
 * The class Intersection_Body, inherited by Infrastructure_Body,
 * represents the body of an intersection agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Intersection_Body extends Infrastructure_Body {

    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env Environment of the agent.
     * @param brain brain of the agent.
     * @param intersection intersection linked to the agent.
     */
    public Intersection_Body(int id, Environment env, Intersection_Brain brain,
            Intersection intersection) {
        super(id, env, brain, intersection);
        this.position = intersection.getCenter();
    }

    @Override
    public void addVehicle(Vehicle_Body vehicle) {
        this.vehicles.add(vehicle);
    }

    @Override
    public void sendMessage(Message mess) {
        for(Vehicle_Body vehicle : this.vehicles){
            if(vehicle.getId() == mess.getReceiver_id())
                vehicle.receiveMessage( mess);
        }
    }
}
