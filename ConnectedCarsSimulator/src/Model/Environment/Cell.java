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

/**
 * The class Cell is the smallest element who compose the Environment.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Cell {
    
    private int x;
    private int y;

    /**
     * Constructor
     * 
     * @param x coordinate x of the cell in the environnment.
     * @param y coordinate y of the cell in the environnment.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another cell.
     */
    public Cell(Cell other){
        this.x = other.getX();
        this.y = other.getY();
    }

    /**
     * Returns the coordinates x of the current Cell.
     * 
     * @return the coordinate x.
     */
    public int getX() {
        return x;
    }

    /**
     * Changes the value of the coordinate x of the current Cell.
     * 
     * @param x coordinate x in the environment.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the coordinates y of the current Cell.
     * 
     * @return the coordinates y.
     */
    public int getY() {
        return y;
    }

    /**
     * Changes the value of the coordinate y of the current Cell.
     * 
     * @param y coordinate y in the environment.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returns the euclidian distance between the current cell and another.
     * 
     * @return the euclidian distance between two cells.
     */
    public double getDistance(Cell other){
        return Math.sqrt(Math.pow(this.x - other.x, 2.0) 
                        + Math.pow(this.y - other.y, 2.0));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
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
        final Cell other = (Cell) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
