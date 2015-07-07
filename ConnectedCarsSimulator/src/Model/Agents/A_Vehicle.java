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
package Model.Agents;

import Model.Agents.Bodies.Vehicle_Body;
import Model.Agents.Brains.Vehicle_Brain;
import Model.Environment.Environment;
import java.util.ArrayList;

/**
 * The A_Vehicle is the model's representation of a vehicle agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class A_Vehicle extends Agent {

    public A_Vehicle(int id, Environment env) {
        super(id, new Vehicle_Body(id, env, null, 1.0), new Vehicle_Brain(id, null));
        this.body.setBrain(this.brain);
        this.brain.setBody(this.body);
    }

    @Override
    public ArrayList getStatistics() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
