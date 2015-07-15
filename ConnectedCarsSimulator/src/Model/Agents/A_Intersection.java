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
package Model.Agents;

import Model.Agents.Bodies.Intersection_Body;
import Model.Agents.Brains.Intersection_Brain;
import Model.Environment.Environment;
import Model.Environment.Intersection;
import java.util.ArrayList;

/**
 * The class A_Intersection is the model's representation
 * of an intersection agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class A_Intersection extends A_Infrastructure {

    /**
     * Constructor
     * 
     * @param id ID of the intersection.
     * @param env environment of the agent.
     * @param intersection intersection linked to the body of the agent.
     */
    public A_Intersection(int id, Environment env, Intersection intersection) {
        super(id, new Intersection_Body(id, env, null, intersection), new Intersection_Brain(id, null));
        this.body.setBrain(this.brain);
        this.brain.setBody(this.body);
    }

    @Override
    public ArrayList getStatistics() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
