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
package Model.Messages;

import Model.Environment.Trajectory;

/**
 * The class M_Welcome, inhered by Message, is used by the infrastructure agents
 * to tell to their new vehicules their way and configuration.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class M_Welcome extends Message {
    
    /**
     * Constructor
     * 
     * @param sender_id ID of the sender.
     * @param receiver_id ID of the receiver.
     * @param trajectory trajectory of the vehicle in the intersection.
     * @param tick the tick when the vehicle can cross the intersection.
     */
    public M_Welcome(int sender_id, int receiver_id, 
            Trajectory trajectory, int tick) {
        super(sender_id, receiver_id);
        this.datum.add(trajectory);
        this.datum.add(tick);
    }
}
