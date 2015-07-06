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

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Way {
    
    private ArrayList<Cell> cells;

    /**
     * Way's Constructor
     */
    public Way() {
        this.cells = new ArrayList<>();
    }
    
    /**
     * Way's copy constructor
     * @param other 
     */
    public Way(Way other){
        this.cells = new ArrayList<>();
        for(Cell cell : other.getCells())
            this.cells.add(new Cell(cell));
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * 
     * @param cells 
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * 
     * @param cell 
     */
    public void addCell(Cell cell){
        if(!this.cells.contains(cell))
            this.cells.add(cell);
    }
    
    /**
     * 
     * @param cell 
     */
    public void removeCell(Cell cell){
       if(this.cells.contains(cell))
           this.cells.remove(cell);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.cells);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Way other = (Way) obj;
        if (!Objects.equals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }
}
