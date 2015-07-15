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

import Model.Agents.Brains.A_Brain;
import Model.Environment.Cell;
import Model.Environment.Environment;
import Model.Messages.Message;

/**
 * The class A_Body represent the body of an agents in the MSA, the interaction
 * layer. Used by the environment who only know the agents by their bodies. 
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class A_Body {
    
    protected final int id;
    protected Cell position;
    protected final Environment env;
    protected A_Brain brain;
    
    /**
     * Constructor
     * 
     * @param id ID of the body (by default, the same as the agent).
     * @param env link to the environment of the agent.
     * @param brain link to the behavior of the agent.
     */
    public A_Body(int id, Environment env, A_Brain brain){
        
        //Init id
        this.id = id;
        
        //Init link to the environment
        this.env = env;
        
        //Init link to the brain
        this.brain = brain;
    }

    /**
     * Returns the ID of the body.
     * 
     * @return the ID of the body.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the position of the agent in the environment.
     * 
     * @return the position of the agent.
     */
    public Cell getPosition() {
        return position;
    }
    
    /**
     * Changes the position of the agent in the environment.
     * 
     * @param position the new position.
     */
    public void setPosition(Cell position) {
        this.position = position;
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
     * Changes the brain linked to the body.
     * 
     * @param brain the new brain to link to body.
     */
    public void setBrain(A_Brain brain) {
        this.brain = brain;
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
     * By default all messages are unsupported.
     * 
     * Children classes should surchage this message to tell
     * which messages a body can receive (with this sensor),
     * and how he receive it, whats happens next.
     * 
     * @param mess message received.
     */
    public void receiveMessage(Message mess){
        throw new UnsupportedOperationException("Message of type : " 
                + mess.getClass() + ", is unsupported.");
    }
    
    /**
     * Motor function of the body to send a message to an other agent.
     * 
     * @param mess message to send.
     */
    abstract public void sendMessage(Message mess);
}
