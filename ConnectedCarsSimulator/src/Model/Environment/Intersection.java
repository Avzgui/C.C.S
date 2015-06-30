/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import static Model.Environment.CardinalPoint.EAST;
import static Model.Environment.CardinalPoint.NORTH;
import static Model.Environment.CardinalPoint.SOUTH;
import static Model.Environment.CardinalPoint.WEST;
import com.google.common.collect.Table;
import java.util.HashMap;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Intersection extends Infrastructure {

    // IN : 0, OUT : 1
    private Table<Flow, CardinalPoint, Integer> nb_ways;
    private Table<Flow, CardinalPoint, Integer> ways_size;
    private HashMap<CardinalPoint, Integer> conflict_zone_size;
    
    /**
     * Intersection's Constructor
     * @param x
     * @param y
     * @param nb_ways
     * @param ways_size
     */
    @SuppressWarnings("empty-statement")
    public Intersection(int x, int y, 
            Table<Flow, CardinalPoint, Integer> nb_ways,
            Table<Flow, CardinalPoint, Integer> ways_size) {
        super(x, y);
        this.nb_ways = nb_ways;
        this.ways_size = ways_size;
        this.conflict_zone_size = new HashMap<CardinalPoint, Integer>();
        
        //Initialize conflict zone
        updateConflictZone();
    }

    /**
     * 
     * @return 
     */
    public Table<Flow, CardinalPoint, Integer> getNb_ways() {
        return nb_ways;
    }

    /**
     * 
     * @param nb_ways 
     */
    public void setNb_ways(Table<Flow, CardinalPoint, Integer> nb_ways) {
        this.nb_ways = nb_ways;
        updateConflictZone();
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param nb_way 
     */
    public void setNb_way(Flow flow, CardinalPoint point, int nb_way) {
        this.nb_ways.put(flow, point, nb_way);
        updateConflictZone();
    }

    /**
     * 
     * @return 
     */
    public Table<Flow, CardinalPoint, Integer> getWays_size() {
        return ways_size;
    }

    /**
     * 
     * @param ways_size 
     */
    public void setWays_size(Table<Flow, CardinalPoint, Integer> ways_size) {
        this.ways_size = ways_size;
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param size 
     */
    public void setWay_Size(Flow flow, CardinalPoint point, int size) {
        this.ways_size.put(flow, point, size);
    }
    
    /**
     * 
     * @param begin 
     */
    public void createWays(CardinalPoint begin){
       
    }
    
    private void updateConflictZone(){
        //Initialize conflict zone
        this.conflict_zone_size.put(CardinalPoint.NORTH, 0);
        this.conflict_zone_size.put(CardinalPoint.SOUTH, 0);
        this.conflict_zone_size.put(CardinalPoint.WEST, 0);
        this.conflict_zone_size.put(CardinalPoint.EAST, 0);
        
        //Get max for each IN/OUT
        for(Flow flow : this.nb_ways.rowKeySet()){
            for(CardinalPoint point : this.nb_ways.columnKeySet()){
                //If exist
                if(this.nb_ways.get(flow, point) != null){
                    //Get value
                    int value = this.nb_ways.get(flow, point);
                    
                    //Get the zone to test (a little ugly)
                    CardinalPoint zone = getZone(point, flow);
                    
                    //If value > max
                    if(value > this.conflict_zone_size.get(zone))
                        this.conflict_zone_size.put(zone, value);
                }
            }
        }
    }
    
    /**
     * 
     * @param flow
     * @return 
     */
    private CardinalPoint getZone(CardinalPoint point, Flow flow){
        if(flow == Flow.IN){
            switch(point){
                case NORTH :
                    return WEST;
                case SOUTH :
                    return EAST;
                case WEST : 
                    return NORTH;
                case EAST :
                    return SOUTH; 
            }
        }
        else{
            switch(point){
                case NORTH :
                    return EAST;
                case SOUTH :
                    return WEST;
                case WEST : 
                    return SOUTH;
                case EAST :
                    return NORTH; 
            }
        }
        
        return null;
    }
}
