/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Environment.CardinalPoint;
import Model.Environment.Flow;
import Model.Environment.Infrastructure;
import Model.Environment.Intersection;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Model {
    
    private Table<Integer, Integer, Infrastructure> environment;
    
    /**
     * 
     */
    public CCS_Creator_Model(){
        this.environment = TreeBasedTable.create();
    }

    /**
     * 
     * @return 
     */
    public Table<Integer, Integer, Infrastructure> getEnvironment() {
        return environment;
    }

    /**
     * 
     * @param environment 
     */
    public void setEnvironment(Table<Integer, Integer, Infrastructure> environment) {
        this.environment = environment;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param nb_ways
     * @param ways_size
     * @param indonesian 
     */
    public void addIntersection(int x, int y,
            Table<Flow, CardinalPoint, Integer> nb_ways,
            Table<Flow, CardinalPoint, Integer> ways_size,
            boolean indonesian){
        Intersection intersection = new Intersection(x, y, nb_ways, ways_size, indonesian);
        addInfrastructure(0, 0, intersection);
    }
    
    /**
     * 
     * @param x
     * @param y 
     */
    public void removeInfrastructure(int x, int y){
        if(this.environment.contains(x, y))
            this.environment.remove(x, y);
    }
    
    /**
     * 
     */
    private int addInfrastructure(int x, int y, Infrastructure i){
        System.out.println("addInfrastructure X : " + x + " Y : " + y);
        //Break clause
        if(!this.environment.contains(x, y)){
            System.out.println("Infrastructure put in pos (" + x + ", " + y + ")");
            this.environment.put(x, y, i);
            return 1;
        }
        
        //Get the infrastructure in this place
        Infrastructure tmp = this.environment.get(x, y);
        int dx = 0;
        int dy = 0;
        
        if(i.getX() > tmp.getX())
            dx++;
        else if(i.getX() < tmp.getX())
            dx--;
        
        if(i.getY() > tmp.getY())
            dy++;
        else if(i.getY() < tmp.getY())
            dy--;
        
        return addInfrastructure(x+dx, y+dy, i);
    }
}
