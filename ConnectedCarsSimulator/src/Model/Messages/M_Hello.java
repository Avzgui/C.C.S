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

import Model.Environment.Cell;
import Utility.CardinalPoint;

/**
 * The classe M_Hello represents the message sended by a vehicule agent to
 * an infrastructure agent to presents himself.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class M_Hello extends Message {
    
    /**
     * Constructor
     * 
     * @param sender_id ID of the sender.
     * @param receiver_id ID of the receiver.
     * @param pos pos data.
     * @param goal goal data.
     */
    public M_Hello(int sender_id, int receiver_id, Cell pos, CardinalPoint goal) {
        super(sender_id, receiver_id);
        this.datum.add(pos);
        this.datum.add(goal);
    }
    
}
