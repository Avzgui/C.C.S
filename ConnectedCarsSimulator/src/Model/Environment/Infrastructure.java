/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Antoine "Avzgui" Richard
 */
public class Infrastructure {
    
    protected int x;
    protected int y;
    protected HashMap<Entry<CardinalPoint, Integer>, Way> ways;
    
    /**
     * Infrastructure's Constructor
     * 
     * @param x
     * @param y
     */
    public Infrastructure(int x, int y){
        this.x = x;
        this.y = y;
        this.ways = new HashMap<>();
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
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 
     * @return 
     */
    public HashMap<Entry<CardinalPoint, Integer>, Way> getWays() {
        return ways;
    }

    /**
     * 
     * @param ways 
     */
    public void setWays(HashMap<Entry<CardinalPoint, Integer>, Way> ways) {
        this.ways = ways;
    }
    
    /**
     * 
     * @param begin
     * @param end
     * @param way 
     */
    public void addWay(CardinalPoint begin, Integer id, Way way){
        Entry<CardinalPoint, Integer> key = new SimpleEntry<>(begin, id);
        this.ways.put(key, way);
    }
    
    /**
     * 
     * @param begin
     * @param end 
     */
    public void removeWay(CardinalPoint begin, CardinalPoint end){
        Entry<CardinalPoint, CardinalPoint> key = new SimpleEntry<>(begin, end);
        if(this.ways.containsKey(key))
            this.ways.remove(key);
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Cell> getCells(){
       ArrayList<Cell> cells = new ArrayList<>();
       
       //For each Way
       for(Entry<Entry<CardinalPoint, Integer>, Way> entry : this.ways.entrySet()){
           //For each cell
           for(Cell cell : entry.getValue().getCells()){
               if(!cells.contains(cell))
                   cells.add(cell);
           }
       }
       
       return cells;
    }

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
