/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import Utility.CardinalPoint;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * @author Antoine "Avzgui" Richard
 */
abstract public class Infrastructure {
    
    protected int x;
    protected int y;
    protected Table<CardinalPoint, Integer, Way> ways;
    protected int height;
    protected int width;
    
    /**
     * Infrastructure's Constructor
     * 
     * @param x
     * @param y
     */
    public Infrastructure(int x, int y){
        this.x = x;
        this.y = y;
        this.ways = HashBasedTable.create();
    }
    
    /**
     * Infrastructure's Copy Constructeur
     * @param other 
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
     * 
     * @return x coordinate
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
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @return 
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return 
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * 
     * @param x
     * @param y 
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return 
     */
    public Table<CardinalPoint, Integer, Way> getWays() {
        return ways;
    }

    /**
     * 
     * @param ways 
     */
    public void setWays(Table<CardinalPoint, Integer, Way> ways) {
        this.ways = ways;
    }
    
    /**
     * 
     * @param begin
     * @param end
     * @param way 
     */
    public void addWay(CardinalPoint begin, Integer id, Way way){
        this.ways.put(begin, id, way);
    }
    
    /**
     * 
     * @param begin
     * @param end 
     */
    public void removeWay(CardinalPoint begin, Integer id){
        if(this.ways.contains(begin, id))
            this.ways.remove(begin, id);
    }
    
    /**
     * 
     * @return 
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
     * 
     * @param position
     * @param width
     * @param height
     * @return 
     */
    abstract public Cell getCellForAnotherInfrastructure(
            CardinalPoint position, int width, int height);

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
