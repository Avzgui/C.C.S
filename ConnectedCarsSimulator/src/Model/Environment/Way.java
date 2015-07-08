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
 * The class Way represents the differents ways that can take a vehicle, in an
 * Infrastructure. A Way is composed of several cells.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Way {
    
    private ArrayList<Cell> cells;

    /**
     * Constructor
     */
    public Way() {
        this.cells = new ArrayList<>();
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another way.
     */
    public Way(Way other){
        this.cells = new ArrayList<>();
        for(Cell cell : other.getCells())
            this.cells.add(new Cell(cell));
    }

    /**
     * Returns the array of all cells who compose the way.
     * 
     * @return an array of cells.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Changes the array of cells who compose the way.
     * 
     * @param cells an array of cells.
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * Adds a cell to the array of cells.
     * 
     * @param cell a new cell for the way.
     */
    public void addCell(Cell cell){
        if(!this.cells.contains(cell))
            this.cells.add(cell);
    }
    
    /**
     * Removes a cell in the array of cells.
     * 
     * @param cell a cell to remove.
     */
    public void removeCell(Cell cell){
       if(this.cells.contains(cell))
           this.cells.remove(cell);
    }
    
    /**
     * Returns and removes the first cell of the way.
     * 
     * @return the first cell of the way.
     */
    public Cell pop(){
        Cell c = this.cells.get(0);
        this.cells.remove(0);
        return c;
    }
    
    /**
     * Returns if the way have no more cells or not.
     * 
     * @return if the way is empty or not.
     */
    public boolean isEmpty(){
        return this.cells.isEmpty();
    }
    
    @Override
    public String toString(){
        String s = "";
        for(Cell c : this.cells)
            s += c;
        return s;
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
