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

import Model.Agents.Bodies.Infrastructure_Body;
import Model.Agents.Brains.Infrastructure_Brain;
import java.util.ArrayList;

/**
 * The class A_Infrastructure is the model's representation
 * of an infrastructure agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class A_Infrastructure extends Agent {

    /**
     * Constructor
     * 
     * @param id ID of the agent.
     * @param body Body of the agent.
     * @param brain Behavior of the agent.
     */
    public A_Infrastructure(int id, Infrastructure_Body body, 
            Infrastructure_Brain brain) {
        super(id, body, brain);
    }
}
