/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Intersection extends Infrastructure {

    // IN : 0, OUT : 1
    private HashMap<Entry<Flow, CardinalPoint>, Integer> nb_ways;
    private HashMap<Entry<Flow, CardinalPoint>, Integer> ways_size;
    
    /**
     * Intersection's Constructor
     * @param x
     * @param y
     * @param nb_ways
     * @param ways_size
     */
    public Intersection(int x, int y, 
            HashMap<Entry<Flow, CardinalPoint>, Integer> nb_ways,
            HashMap<Entry<Flow, CardinalPoint>, Integer> ways_size) {
        super(x, y);
        this.nb_ways = nb_ways;
        this.ways_size = ways_size;
    }

    /**
     * 
     * @return 
     */
    public HashMap<Entry<Flow, CardinalPoint>, Integer> getNb_ways() {
        return nb_ways;
    }

    /**
     * 
     * @param nb_ways 
     */
    public void setNb_ways(HashMap<Entry<Flow, CardinalPoint>, Integer> nb_ways) {
        this.nb_ways = nb_ways;
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param nb_way 
     */
    public void setNb_way(Flow flow, CardinalPoint point, int nb_way) {
        Entry<Flow, CardinalPoint> key = new SimpleEntry<>(flow, point);
        this.nb_ways.put(key, nb_way);
    }

    /**
     * 
     * @return 
     */
    public HashMap<Entry<Flow, CardinalPoint>, Integer> getWays_size() {
        return ways_size;
    }

    /**
     * 
     * @param ways_size 
     */
    public void setWays_size(HashMap<Entry<Flow, CardinalPoint>, Integer> ways_size) {
        this.ways_size = ways_size;
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param size 
     */
    public void setWay_Size(Flow flow, CardinalPoint point, int size) {
        Entry<Flow, CardinalPoint> key = new SimpleEntry<>(flow, point);
        this.nb_ways.put(key, size);
    }
    
    /**
     * 
     * @param begin 
     */
    public void createWays(CardinalPoint begin){
       //For each way IN
       Entry<Flow, CardinalPoint> in_key = new SimpleEntry<>(Flow.IN, begin); 
       for(int i = 0 ; i < this.nb_ways.get(in_key) ; i++){
           
           /* ----- General Case : the front way ----- */
           
           //If an out way exist.
           Entry<Flow, CardinalPoint> out_key = new SimpleEntry<>(Flow.OUT, begin.getFront());
           if(i < this.nb_ways.get(out_key)){
               //Remove the existing way
               Entry<CardinalPoint, Integer> way_key = new SimpleEntry<>(begin, i);
               if(this.ways.containsKey(way_key))
                   this.ways.remove(way_key);
               
               //Create a new Way
               Way way = new Way();
               // TODO !!
               for(int j = 0 ; j < this.ways_size.get(in_key) ; j++){
                   
               }
           }               
       }
    }
}
