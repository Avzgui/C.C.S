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
 
    /** Brain ID. */
    protected final int id;
    
    /** Link to the agent body. */
    protected A_Body body;
    
    /** Memory of the agent. */
    protected final ArrayList<Message> messages_memory;
    
    /**
     * Constructor
     * 
     * @param id ID of the brain (by default, the same as the agent).
     * @param body link to the body of the agent.
     */
    public A_Brain(int id, A_Body body){
        //Init id
        this.id = id;
        
        //Init link to the body
        this.body = body;
        
        //Init the messages memory
        this.messages_memory = new ArrayList<>();
    }

    /**
     * Returns the ID of the brain.
     * 
     * @return the ID of the brain.
     */
    public int getId() {
        return this.id;
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
     * Changes the body linked to the brain.
     * 
     * @param body the new body to link to the brain.
     */
    public void setBody(A_Body body) {
        this.body = body;
    }

    /**
     * Returns the array of message stored in the memory of the agent.
     * 
     * @return the messages stored in memory.
     */
    public ArrayList<Message> getMessages_memory() {
        return messages_memory;
    }
    
    /**
     * Stores a message in memory.
     * 
     * @param mess message to store.
     */
    public void storeMessage(Message mess){
        this.messages_memory.add(mess);
    }
    
    /**
     * Reasonning layer's method to process a message of the message memory.
     * 
     * By default all messages are unsupported.
     * 
     * Children classes should surchage this methods
     * to could processa type of message, and how they 
     * process it.
     * 
     * @param mess 
     * @return A message to send.
     */
    protected Message processMessage(Message mess){
        throw new UnsupportedOperationException("Message of type : " 
                + mess.getClass() + ", is unsupported.");
    }
    
    /** 
     * Implements the agent's behaviour.
     */
    abstract public void run();
}
