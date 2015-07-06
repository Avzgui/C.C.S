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
 *
 * @author Antoine "Avzgui" Richard
 */
public class Cell {
    
    protected int x;
    protected int y;

    /**
     * Cell's Constructor
     * @param x
     * @param y 
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Cell's Copy Constructor
     * @param other 
     */
    public Cell(Cell other){
        this.x = other.getX();
        this.y = other.getY();
    }

    /**
     * 
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * 
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 
     * @return 
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
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
