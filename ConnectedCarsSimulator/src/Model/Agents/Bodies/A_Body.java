package Model.Agents.Bodies;

import Model.Agents.Brains.A_Brain;
import Model.Environment.Infrastructure;

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

/**
 * The class A_Body represent the body of an agents in the MSA, the interaction
 * layer. Used by the environment who only know the agents by their bodies. 
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class A_Body {
    
    protected final A_Brain brain;
    protected Infrastructure current;
    
    /**
     * Constructor
     * 
     * @param brain link to the behavior of the agent.
     * @param infrastructure the infrastructure with the body is connected.
     */
    public A_Body(A_Brain brain, Infrastructure infrastructure){
        
        //Init link to the brain
        this.brain = brain;
        
        //Init link to the current infrastructure
        this.current = infrastructure;
    }

    /**
     * Returns the current infrastructure associate to the body of the agent.
     * 
     * @return the current infrastructure with the body is linked.
     */
    public Infrastructure getCurrent() {
        return this.current;
    }

    /**
     * Changes the current infrastructure.
     * 
     * @param infrastructure the new infrastructure to link to the body.
     */
    public void setCurrent(Infrastructure infrastructure) {
        this.current = infrastructure;
    }

    /**
     * Returns the link to the brain associate to the body.
     * 
     * @return the brain associate to the body.
     */
    public A_Brain getBrain() {
        return brain;
    }
}
