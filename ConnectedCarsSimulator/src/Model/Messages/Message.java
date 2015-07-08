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
package Model.Messages;

import java.util.ArrayList;

/**
 * The class Message implements the methods and attributes of all message that
 * can be posted by the agents between us.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class Message {
    
    protected final int sender_id;
    protected final int receiver_id;
    protected final ArrayList datum;
    
    /**
     * Constructor
     * 
     * @param sender_id ID of the agent who send the missive.
     * @param receiver_id ID of the agent who will receive the missive.
     */
    public Message(int sender_id, int receiver_id){
       this.sender_id = sender_id;
       this.receiver_id = receiver_id;
       this.datum = new ArrayList<>();
    }

    /**
     * Returns the ID of the sender agent.
     * 
     * @return the ID of the sender.
     */
    public int getSender_id() {
        return this.sender_id;
    }

    /**
     * Returns the ID of the receiver agent.
     * 
     * @return the ID of the receiver.
     */
    public int getReceiver_id() {
        return this.receiver_id;
    }
    
    /**
     * Returns the array of data of the message.
     * 
     * @return the datum of the message in an array.
     */
    public ArrayList getDatum(){
        return this.datum;
    }
}
