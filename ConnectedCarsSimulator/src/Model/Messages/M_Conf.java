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
 * The class M_Conf, inherited by Message, represents the parent of all
 * message concerning a crossing configuration dialog.
 * 
 * @author Antoine "Avzgui" Richard
 */
abstract public class M_Conf extends Message {

    public M_Conf(int sender_id, int receiver_id,
            Crossing_Configuration current) {
        super(sender_id, receiver_id);
        this.datum.add(new Crossing_Configuration(current));
    }
    
}
