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

package Model.Environment;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The class Trajectory represents a path that can take a vehicle, in an
 * Infrastructure. A Way is composed of several cells.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Trajectory {
    
    private ArrayList<Cell> cells;
    private Cell whereToStop;

    /**
     * Constructor
     */
    public Trajectory() {
        this.cells = new ArrayList<>();
        this.whereToStop = null;
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another way.
     */
    public Trajectory(Trajectory other){
        this.cells = new ArrayList<>();
        for(Cell cell : other.getCells())
            this.cells.add(new Cell(cell));
        
        this.whereToStop = null;
        if(other.getWhereToStop() != null)
            this.whereToStop = new Cell(other.getWhereToStop());
    }

    /**
     * Returns the array of all cells who compose the trajectory.
     * 
     * @return an array of cells.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Changes the array of cells who compose the trajectory.
     * 
     * @param cells an array of cells.
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * Adds a cell to the array of cells.
     * 
     * @param cell a new cell for the trajectory.
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
     * Returns and removes the first cell of the trajectory.
     * 
     * @return the first cell of the trajectory.
     */
    public Cell pop(){
        Cell c = this.cells.get(0);
        this.cells.remove(0);
        return c;
    }

    /**
     * Returns the cell where to stop in the trajectory.
     * 
     * @return the cell where to stop.
     */
    public Cell getWhereToStop() {
        return whereToStop;
    }

    /**
     * Changes the cell where to stop.
     * 
     * @param whereToStop the new cell.
     */
    public void setWhereToStop(Cell whereToStop) {
        if(this.cells.contains(whereToStop))
            this.whereToStop = whereToStop;
    }
    
    /**
     * Returns if the trajectory have no more cells or not.
     * 
     * @return if the trajectory is empty or not.
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
        final Trajectory other = (Trajectory) obj;
        if (!Objects.equals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }
}
