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

import Utility.Crossing_Configuration;

/**
 * The class M_Offer, inherited by M_Conf, is sent by a vehicle who propose
 * a new configuration.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class M_Offer extends M_Conf {

    public M_Offer(int sender_id, int receiver_id, 
            Crossing_Configuration current, Crossing_Configuration _new) {
        super(sender_id, receiver_id, current);
        this.datum.add(new Crossing_Configuration(_new));
    }
    
}
