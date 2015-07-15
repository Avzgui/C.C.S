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

import Model.Agents.Brains.Road_Brain;
import Model.Environment.Environment;
import Model.Environment.Road;
import Model.Messages.Message;

/**
 * The class Road_Body, inherited by Infrastructure_Body, represents the body
 * of a road agent.
 * 
 * (TODO)
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Road_Body extends Infrastructure_Body {

    public Road_Body(int id, Environment env, Road_Brain brain, Road road) {
        super(id, env, brain, road);
    }

    @Override
    public void addVehicle(Vehicle_Body vehicle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendMessage(Message mess) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
