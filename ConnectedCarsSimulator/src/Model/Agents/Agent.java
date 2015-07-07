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

import Model.Agents.Bodies.A_Body;
import Model.Agents.Brains.A_Brain;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The class Agent an agent for the Model. This class is used to create the
 * differents layers of the agent (here an A_Body for the intercation layer
 * and an A_Brain for the layers behavior, reasoning and memory).
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class Agent {
    
    protected int id;
    protected final A_Body body;
    protected final A_Brain brain;
    
    /**
     * Constructor
     * 
     * @param id ID of the agent.
     * @param body body of the agent.
     * @param brain brain of the agent.
     */
    public Agent(int id, A_Body body, A_Brain brain){
       this.id = id;
       this.body = body;
       this.brain = brain;
    }
    
    /**
     * Asks agent to update. 
     * 
     * By default : run and join the thread brain.
     */
    public void update(){
        if(this.brain != null){
            this.brain.start();
            try {
                this.brain.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Returns an array of statistics concerning the agent.
     * 
     * @return several statistics on an array.
     */
    abstract public ArrayList getStatistics();
}
