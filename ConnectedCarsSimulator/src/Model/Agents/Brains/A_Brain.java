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

import Model.Agents.Bodies.A_Body;
import Model.Messages.Message;
import java.util.ArrayList;

/**
 * The class A_Brain represents the behavior layer of an agent construction.
 * 
 * It's this class who implements the main loop of an agent, and the attributes
 * and methods in common between all the agents' bevahior.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class A_Brain {
 
    protected final A_Body body;
    
    protected final ArrayList<Class<? extends Message>> messages_memory;
    
    /**
     * Constructor
     * 
     * @param body link to the body of the agent
     */
    public A_Brain(A_Body body){
        //Init link to the body
        this.body = body;
        
        //Init the messages memory
        this.messages_memory = new ArrayList<>();
    }

    /**
     * Returns the link to the body of the agent.
     * 
     * @return the body linked to the brain.
     */
    public A_Body getBody(){
        return body;
    }

    /**
     * Returns the array of message stored in the memory of the agent.
     * 
     * @return the messages stored in memory.
     */
    public ArrayList<Class<? extends Message>> getMessages_memory() {
        return messages_memory;
    }
    
    /**
     * Stores a message in memory.
     * 
     * By default all the messages are unsupported.
     * 
     * For store a type of message, the children of A_Brain
     * needs to surchage this method.
     * 
     * @param mess message to store.
     */
    public void storeMessage(Class<? extends Message> mess){
        System.out.println("Message of class " + mess.getClass() 
                + " is unsupported by A_Brain::storeMessage");
    }
}
