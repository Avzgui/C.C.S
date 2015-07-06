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

package Model.Environment;

import Utility.CardinalPoint;

/**
 * The class Road, inherited of Infrastructure, is the environment's 
 * representation of a road between two intersections.
 * 
 * (TODO)
 * 
 * @author Antoine "Avzgui" Richard
 * 
 * @see Infrastructure
 */
public class Road extends Infrastructure{

    /**
     * Constructor
     * 
     * @param x coordinate x
     * @param y coordinate y
     */
    public Road(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell getCellForAnotherInfrastructure(CardinalPoint position, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
