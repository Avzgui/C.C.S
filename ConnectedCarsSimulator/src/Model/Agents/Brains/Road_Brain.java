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
package Model.Agents.Brains;

import Model.Agents.Bodies.Road_Body;

/**
 * The class Road_Brain, inherited by Infrastructure_Brain, represents 
 * the behavior of a road agent.
 * 
 * (TODO)
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Road_Brain extends Infrastructure_Brain {

    /**
     * Constructor
     * 
     * @param id ID of the brain (by default, the same as the agent)
     * @param body the body of the agent.
     */
    public Road_Brain(int id, Road_Body body) {
        super(id, body);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
