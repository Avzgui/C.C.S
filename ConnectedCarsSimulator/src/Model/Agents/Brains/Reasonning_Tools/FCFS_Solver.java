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

package Model.Agents.Brains.Reasonning_Tools;

import Model.Agents.Brains.Intersection_Brain;

/**
 * The class FCFS_Solver it used by the intersection to set a crossing time
 * to the new vehicles.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class FCFS_Solver {
    
    private final Intersection_Brain brain;
    
    /**
     * Constructor
     * 
     * @param brain 
     */
    public FCFS_Solver(Intersection_Brain brain){
        this.brain = brain;
    }
}
