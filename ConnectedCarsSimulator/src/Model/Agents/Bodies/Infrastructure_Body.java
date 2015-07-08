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
import Model.Environment.Environment;
import Model.Environment.Infrastructure;

/**
 * The class Infrastructure_Body represents the body of an infrastructure agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class Infrastructure_Body extends A_Body {

    protected final Infrastructure infrastructure;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env environment of the body.
     * @param brain brain linked to the body.
     * @param infrastructure infrastructure that the infrastructure agent is responsible.
     */
    public Infrastructure_Body(int id, Environment env, A_Brain brain, Infrastructure infrastructure) {
        super(id, env, brain);
        this.infrastructure = infrastructure;
    }

    /**
     * Returns the infrastructure that the infrastructure agent is responsible.
     * 
     * @return the infrastructure linked to the body.
     */
    public Infrastructure getInfrastructure() {
        return infrastructure;
    }
    
}
