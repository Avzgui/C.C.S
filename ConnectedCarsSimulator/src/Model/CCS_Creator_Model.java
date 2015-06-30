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
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Model {
    
    private Table<Integer, Integer, Infrastructure> environment;
    
    public CCS_Creator_Model(){
        this.environment = HashBasedTable.create();
    }

    public Table<Integer, Integer, Infrastructure> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Table<Integer, Integer, Infrastructure> environment) {
        this.environment = environment;
    }
    
    public void addIntersection(int x, int y,
            Table<Flow, CardinalPoint, Integer> nb_ways,
            Table<Flow, CardinalPoint, Integer> ways_size){
        Intersection intersection = new Intersection(x, y, nb_ways, ways_size);
        this.environment.put(x, y, intersection);
    }
    
    public void removeInfrastructure(int x, int y){
        if(this.environment.contains(x, y))
            this.environment.remove(x, y);
    }
}
