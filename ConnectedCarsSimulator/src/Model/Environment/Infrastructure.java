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
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * The abstract class Infrastructure is the environment's representation of an
 * infrastructure (road or intersection). It groups all of the attributes and
 * methods in common between his children class.
 * 
 * An infrastructure is composed by several ways, each way being ranked by 
 * an input CardinalPoint and an ID.
 * 
 * @author Antoine "Avzgui" Richard
 * 
 * @see Utility.CardinalPoint
 */
abstract public class Infrastructure {
    
    protected int x;
    protected int y;
    protected Table<CardinalPoint, Integer, Way> ways;
    protected int height;
    protected int width;
    
    /**
     * Constructor
     * 
     * @param x coordinate x of the infrastructure.
     * @param y coordinate y of the infrastructure.
     */
    public Infrastructure(int x, int y){
        this.x = x;
        this.y = y;
        this.ways = HashBasedTable.create();
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another infrastructure.
     */
    public Infrastructure(Infrastructure other){
        this.x = other.getX();
        this.y = other.getY();
        this.ways = HashBasedTable.create();
        
        Table<CardinalPoint, Integer, Way> tmp = other.getWays();
        for(CardinalPoint point : tmp.rowKeySet()){
            for(Entry<Integer, Way> entry : tmp.row(point).entrySet()){
                if(tmp.contains(point, entry.getKey())
                    && !this.ways.contains(point, entry.getKey()))
                {
                    this.ways.put(point, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * Returns the coordinate x of the infrastructure.
     * 
     * @return the coordinate x.
     */
    public int getX() {
        return x;
    }

    /**
     * Changes the coordinate x of the infrastructure.
     * 
     * @param x a new coordinate x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the coordinate y of the infrastructure.
     * 
     * @return the coordinate y.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Changes the coordinate x of the infrastructure.
     * 
     * @param y a new coordinate y.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Changes the coordinates x and y of the infrastructure.
     * 
     * @param x a new coordinate x.
     * @param y a new coordinate y.
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the height of the infrastructure. determinated during construction.
     * 
     * @return the height of the infrastructure.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Changes the height of the infrastructure.
     * 
     * @param height a new height for the infrastructure.
     * 
     * @deprecated use {@link #update() update}
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the width of the infrastructure. determinated during construction.
     * 
     * @return the width of the infrastructure.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Changes the width of the infrastructure.
     * 
     * @param width a new width for the infrastructure.
     * 
     * @deprecated use {@link #update() update}
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the ways of the infrastructure. Ranked by their input cardinal
     * point and their ID.
     * 
     * @return the table of ways of the infrastructure.
     */
    public Table<CardinalPoint, Integer, Way> getWays() {
        return ways;
    }

    /**
     * Changes the ways of the infrastructure.
     * 
     * @param ways 
     * 
     * @deprecated use {@link #addWay(CardinalPoint, int, Way) addWay} and {@link #removeWay(CardinalPoint, int) removeWay}
     */
    public void setWays(Table<CardinalPoint, Integer, Way> ways) {
        this.ways = ways;
    }
    
    /**
     * Adds a way to the table of ways.
     * 
     * @param begin the input cardinal point of the way.
     * @param id the id of the way.
     * @param way the way to add.
     */
    public void addWay(CardinalPoint begin, int id, Way way){
        this.ways.put(begin, id, way);
    }
    
    /**
     * Removes a way of the table of ways.
     * 
     * @param begin the input cardinal point of the way.
     * @param id the id of the way.
     */
    public void removeWay(CardinalPoint begin, int id){
        if(this.ways.contains(begin, id))
            this.ways.remove(begin, id);
    }
    
    /**
     * Returns all the cells who compose the infrastructure.
     * 
     * @return an array of cells.
     */
    public ArrayList<Cell> getCells(){
       ArrayList<Cell> cells = new ArrayList<>();
       
       for(Way way : this.ways.values()){
           for(Cell cell : way.getCells()){
                if(!cells.contains(cell))
                    cells.add(cell);
           }
       }
       
       return cells;
    }
    
    /**
     * Returns a position where build an another infrastructure.
     * 
     * @param position cardinal point where the other infrastructure should to be build.
     * @param width width of the other infrastructure.
     * @param height height of the other infrastructure.
     * 
     * @return a cell where build the other infrastructure. 
     */
    abstract public Cell getCellForAnotherInfrastructure(
            CardinalPoint position, int width, int height);
    
    /**
     * Private method to update the infrastructure after any changement.
     * 
     * Update the ways, the height and the width of the infrastructure.
     */
    abstract protected void update();
    
    /**
     * Private method to create all the ways who begins at the cardinal point.
     * 
     * @param begin cardinal point where begins the ways to create.
     */
    abstract protected void createWays(CardinalPoint begin);

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Infrastructure other = (Infrastructure) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
