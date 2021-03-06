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
 * An infrastructure is composed by several trajectories, each way being ranked 
 * by an input CardinalPoint and an ID.
 * 
 * @author Antoine "Avzgui" Richard
 * 
 * @see Utility.CardinalPoint
 */
abstract public class Infrastructure {
    
    protected int x;
    protected int y;
    protected final Table<CardinalPoint, Integer, Trajectory> trajectories;
    protected final Table<CardinalPoint, CardinalPoint, Boolean> available_flows;
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
        this.trajectories = HashBasedTable.create();
        
        //Init the available flows
        this.available_flows = HashBasedTable.create();
        
        this.available_flows.put(CardinalPoint.NORTH, CardinalPoint.NORTH, false);
        this.available_flows.put(CardinalPoint.NORTH, CardinalPoint.EAST, false);
        this.available_flows.put(CardinalPoint.NORTH, CardinalPoint.SOUTH, false);
        this.available_flows.put(CardinalPoint.NORTH, CardinalPoint.WEST, false);
        
        this.available_flows.put(CardinalPoint.EAST, CardinalPoint.NORTH, false);
        this.available_flows.put(CardinalPoint.EAST, CardinalPoint.EAST, false);
        this.available_flows.put(CardinalPoint.EAST, CardinalPoint.SOUTH, false);
        this.available_flows.put(CardinalPoint.EAST, CardinalPoint.WEST, false);
        
        this.available_flows.put(CardinalPoint.SOUTH, CardinalPoint.NORTH, false);
        this.available_flows.put(CardinalPoint.SOUTH, CardinalPoint.EAST, false);
        this.available_flows.put(CardinalPoint.SOUTH, CardinalPoint.SOUTH, false);
        this.available_flows.put(CardinalPoint.SOUTH, CardinalPoint.WEST, false);
        
        this.available_flows.put(CardinalPoint.WEST, CardinalPoint.NORTH, false);
        this.available_flows.put(CardinalPoint.WEST, CardinalPoint.EAST, false);
        this.available_flows.put(CardinalPoint.WEST, CardinalPoint.SOUTH, false);
        this.available_flows.put(CardinalPoint.WEST, CardinalPoint.WEST, false);
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another infrastructure.
     */
    public Infrastructure(Infrastructure other){
        this.x = other.getX();
        this.y = other.getY();
        this.trajectories = HashBasedTable.create();
        
        Table<CardinalPoint, Integer, Trajectory> tmp = other.getTrajectories();
        for(CardinalPoint point : tmp.rowKeySet()){
            for(Entry<Integer, Trajectory> entry : tmp.row(point).entrySet()){
                if(tmp.contains(point, entry.getKey())
                    && !this.trajectories.contains(point, entry.getKey()))
                {
                    this.trajectories.put(point, entry.getKey(), entry.getValue());
                }
            }
        }
        
        this.available_flows = HashBasedTable.create();
        
        Table<CardinalPoint, CardinalPoint, Boolean> tmp2 = other.getAvailable_flows();
        for(CardinalPoint point : tmp2.rowKeySet()){
            for(Entry<CardinalPoint, Boolean> entry : tmp2.row(point).entrySet()){
                if(tmp2.contains(point, entry.getKey())
                    && !this.available_flows.contains(point, entry.getKey()))
                {
                    this.available_flows.put(point, entry.getKey(), entry.getValue());
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
     * Returns the trajectories of the infrastructure. Ranked by their input cardinal
     * point and their ID.
     * 
     * @return the table of trajectories of the infrastructure.
     */
    public Table<CardinalPoint, Integer, Trajectory> getTrajectories() {
        return this.trajectories;
    }

    /**
     * Returns the table of available flows in the infrastructure
     * 
     * @return the table of flows available
     * @deprecated use {@link #isAnAvailableFlow(CardinalPoint, CardinalPoint) isAnAvailableFlow}
     */
    public Table<CardinalPoint, CardinalPoint, Boolean> getAvailable_flows() {
        return this.available_flows;
    }
    
    /**
     * Returns if the flow begin -> end is available for this infrastructure.
     * 
     * @param begin cardinal point where the flow begin.
     * @param end cardinal point where the flow finish.
     * @return the availability of the flow begin->end for this infrastructure.
     */
    public boolean isAnAvailableFlow(CardinalPoint begin, CardinalPoint end){
        return this.available_flows.get(begin, end);
    }
    
    /**
     * Adds a way to the table of trajectories.
     * 
     * @param begin the input cardinal point of the way.
     * @param id the id of the way.
     * @param way the way to add.
     */
    public void addWay(CardinalPoint begin, int id, Trajectory way){
        this.trajectories.put(begin, id, way);
    }
    
    /**
     * Removes a way of the table of trajectories.
     * 
     * @param begin the input cardinal point of the way.
     * @param id the id of the way.
     */
    public void removeWay(CardinalPoint begin, int id){
        if(this.trajectories.contains(begin, id))
            this.trajectories.remove(begin, id);
    }
    
    /**
     * Returns all the cells who compose the infrastructure.
     * 
     * @return an array of cells.
     */
    public ArrayList<Cell> getCells(){
       ArrayList<Cell> cells = new ArrayList<>();
       
       for(Trajectory way : this.trajectories.values()){
           for(Cell cell : way.getCells()){
                if(!cells.contains(cell))
                    cells.add(cell);
           }
       }
       
       return cells;
    }
    
    /**
     * Returns if the infrastructure have the cell c.
     * 
     * @param c cell tested.
     * @return if the infrastructure have the cell c or not.
     */
    public boolean haveCell(Cell c){
        for(Trajectory w : this.trajectories.values()){
            if(w.getCells().contains(c))
                return true;
        }
        
        return false;
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
     * Update the trajectories, the height and the width of the infrastructure.
     */
    abstract protected void update();
    
    /**
     * Private method to create all the trajectories who begins at the cardinal point.
     * 
     * @param begin cardinal point where begins the trajectories to create.
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
