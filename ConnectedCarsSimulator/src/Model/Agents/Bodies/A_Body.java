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
import Model.Messages.Message;

/**
 * The class A_Body represent the body of an agents in the MSA, the interaction
 * layer. Used by the environment who only know the agents by their bodies. 
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class A_Body {
    
    protected final Environment env;
    protected final A_Brain brain;
    
    /**
     * Constructor
     * 
     * @param env link to the environment of the agent.
     * @param brain link to the behavior of the agent.
     */
    public A_Body(Environment env, A_Brain brain){
        
        //Init link to the environment
        this.env = env;
        
        //Init link to the brain
        this.brain = brain;
    }

    /**
     * Returns the link to the brain associate to the body.
     * 
     * @return the brain associate to the body.
     */
    public A_Brain getBrain() {
        return brain;
    }
    
    /**
     * Returns the environment with which the body is linked.
     * 
     * @return the link to the environment.
     */
    public Environment getEnvironment(){
        return this.env;
    }
    
    /**
     * Sensor function of the body to receive a message from the other agents.
     * 
     * @param mess message received.
     */
    public void receiveMessage(Class<? extends Message> mess){
        this.brain.storeMessage(mess);
    }
    
    /**
     * Motor function of the body to send a message to an other agent.
     * 
     * @param mess message to send.
     */
    abstract public void sendMessage(Class<? extends Message> mess);
}
