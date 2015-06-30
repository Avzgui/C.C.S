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
    private final HashMap<CardinalPoint, Integer> conflict_zone_size;
    
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
        this.conflict_zone_size = new HashMap<>();
        
        //Initialize conflict zone
        updateConflictZone();
        
        //Initialize the ways
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
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
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
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
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
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
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param size 
     */
    public void setWay_Size(Flow flow, CardinalPoint point, int size) {
        this.ways_size.put(flow, point, size);
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
    }
    
    /**
     * 
     * @param begin 
     */
    private void createWays(CardinalPoint begin){
       
        //For each way beginning at the Cardinal Point
        for(int i = 0 ; i < this.nb_ways.get(Flow.IN, begin) ; i++){
            
            
            //If the way already exist, delete it
            if(this.ways.contains(begin, i))
                this.ways.remove(begin, i);
            
            //Create and initialize the future way
            Way way = new Way();
            
            //Get the zone in the conflict zone, dx and dy
            CardinalPoint zone = getZone(begin, Flow.IN);
            int zone_size = this.conflict_zone_size.get(zone);
            int begin_zone_size = this.conflict_zone_size.get(begin);
            int front_zone_size = this.conflict_zone_size.get(begin.getFront());
            int dx = getDX(begin, Flow.IN);
            int dy = getDY(begin, Flow.IN);
                
            //For each cell of the way before the conflict zone
            for(int j = this.ways_size.get(Flow.IN, begin) ; j > 0 ; j--){

                //Determine coordinate of the cell
                int cell_x;
                int cell_y;

                //If begin is West or East
                if(begin.isHorizontal()){
                    //X depends of begin zone size, dx and j
                    cell_x = this.x + dx * (begin_zone_size + j);

                    //Y depends of zone size, dy and i
                    cell_y = this.y + dy * (zone_size - i);
                }
                //If begin is North or South
                else{
                    //X depends of zone size, dx and i
                    cell_x = this.x + dx * (zone_size - i);

                    //Y depends of begin zone size, dy and j
                    cell_y = this.y + dy * (begin_zone_size + j);
                }

                //Add cell to the future way
                way.addCell(new Cell(cell_x, cell_y));
            }
            
            /* ----- General Case : front way ----- */
            
            //If the front way can be create
            if(i < this.nb_ways.get(Flow.OUT, begin.getFront())){

                //For each cell of the way in the conflict zone
                for(int j = 0 ; j < begin_zone_size + front_zone_size + 1 ; j++){
                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    if(begin.isHorizontal()){
                        // X depends of begin zone size, j and dx
                        cell_x = this.x + dx * (begin_zone_size - j);

                        // Y depends of zone size, i and dy
                        cell_y = this.y + dy * (zone_size - i);
                    }
                    else{
                        // X depends of zone size, i and dx
                        cell_x = this.x + dx * (zone_size - i);

                        // Y depends of begin zone size, j and dy
                        cell_y = this.y + dy * (begin_zone_size - j);
                    }

                    //Add cell to the future way
                    way.addCell(new Cell(cell_x, cell_y));
                }

                //For each cell of the way after the conflict zone
                for(int j = 1 ; j <= this.ways_size.get(Flow.OUT, begin.getFront()) ; j++){

                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    //If begin is West or East
                    if(begin.isHorizontal()){
                        //X depends of front zone size, dx and j
                        cell_x = this.x - dx * (front_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.y + dy * (zone_size - i);
                    }
                    
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.x + dx * (zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.y - dy * (front_zone_size + j);
                    }

                    //Add cell to the future way
                    way.addCell(new Cell(cell_x, cell_y));
                }
            }
            
            //Add the way to the array of ways
            this.ways.put(begin, i, way);
        }
    }
    
    /**
     * 
     */
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
     * @param point
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
                    return SOUTH;
                case EAST :
                    return NORTH; 
            }
        }
        else{
            switch(point){
                case NORTH :
                    return EAST;
                case SOUTH :
                    return WEST;
                case WEST : 
                    return NORTH;
                case EAST :
                    return SOUTH; 
            }
        }
        
        return null;
    }
    
    /**
     * 
     * @param point
     * @param flow
     * @return 
     */
    private int getDX(CardinalPoint point, Flow flow){
        if(flow == Flow.IN){
            switch(point){
                case NORTH :
                    return -1;
                case SOUTH :
                    return 1;
                case WEST : 
                    return -1;
                case EAST :
                    return 1; 
            }
        }
        else{
            switch(point){
                case NORTH :
                    return 1;
                case SOUTH :
                    return -1;
                case WEST : 
                    return -1;
                case EAST :
                    return 1; 
            }
        }
        
        return 0;
    }
    
    /**
     * 
     * @param point
     * @param flow
     * @return 
     */
    private int getDY(CardinalPoint point, Flow flow){
        if(flow == Flow.IN){
            switch(point){
                case NORTH :
                    return -1;
                case SOUTH :
                    return 1;
                case WEST : 
                    return 1;
                case EAST :
                    return -1; 
            }
        }
        else{
            switch(point){
                case NORTH :
                    return -1;
                case SOUTH :
                    return 1;
                case WEST : 
                    return -1;
                case EAST :
                    return 1; 
            }
        }
        
        return 0;
    }
}
