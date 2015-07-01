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
import java.util.ArrayList;
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
    private boolean indonesian_cross;
    
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
            Table<Flow, CardinalPoint, Integer> ways_size,
            boolean indonesian_cross) {
        super(x, y);
        this.nb_ways = nb_ways;
        this.ways_size = ways_size;
        this.conflict_zone_size = new HashMap<>();
        this.indonesian_cross = indonesian_cross;
        
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
     * @return 
     */
    public boolean isIndonesian_cross() {
        return indonesian_cross;
    }

    /**
     * 
     * @param indonesian_cross 
     */
    public void setIndonesian_cross(boolean indonesian_cross) {
        this.indonesian_cross = indonesian_cross;
    }
    
    /**
     * 
     * @param begin 
     */
    protected void createWays(CardinalPoint begin){
       
        //For each way beginning at the Cardinal Point
        for(int i = 0 ; i < this.nb_ways.get(Flow.IN, begin) ; i++){
            
            
            //If the way already exist, delete it
            if(this.ways.contains(begin, i))
                this.ways.remove(begin, i);
            
            //Create the future way
            Way way = new Way();
            
            //Get the zone in the conflict zone, dx and dy
            CardinalPoint zone = getZone(begin, Flow.IN);
            int zone_size = this.conflict_zone_size.get(zone);
            int begin_zone_size = this.conflict_zone_size.get(begin);
            
            int dx = getDX(begin, Flow.IN);
            int dy = getDY(begin, Flow.IN);
                
            //For each cell of the way before the conflict zone
            //*
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
            //*/
            
            
            /* ----- Casual Case : right way ----- */
            //*
            if(i == 0 && this.nb_ways.get(Flow.OUT, begin.getRight()) >= 1){
                
                //Create right way, copy of the beginning of way
                Way right_way = new Way(way);
                
                CardinalPoint right = begin.getRight();
                int right_zone_size = this.conflict_zone_size.get(right);
                int right_dx = getDX(right, Flow.OUT);
                int right_dy = getDY(right, Flow.OUT);
                
                //For each cell of the way in the conflict zone (j = 0, not 1)
                //For each cell of the way after the conflict zone
                for(int j = 0 ; j <= this.ways_size.get(Flow.OUT, begin.getRight()) ; j++){

                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    //If begin is West or East
                    if(right.isHorizontal()){
                        //X depends of front zone size, dx and j
                        cell_x = this.x + right_dx * (right_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.y + right_dy * (right_zone_size - i);
                    }
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.x + right_dx * (right_zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.y + right_dy * (right_zone_size + j);
                    }

                    //Add cell to the future way
                    right_way.addCell(new Cell(cell_x, cell_y));
                }
                
                //Add the way to the array of ways
                this.ways.put(begin, this.nb_ways.get(Flow.IN, begin)+i, right_way);
            }
            //*/
            
            /* ----- Casual Case : left way ----- */
            //*
            if(i == this.nb_ways.get(Flow.IN, begin) - 1
                    && i < this.nb_ways.get(Flow.OUT, begin.getRight())){
                
                //Create right way, copy of the beginning of way
                Way left_way = new Way(way);
                
                CardinalPoint left = begin.getLeft();
                int left_zone_size = this.conflict_zone_size.get(left);
                int left_dx = getDX(left, Flow.OUT);
                int left_dy = getDY(left, Flow.OUT);
                
                //If it an indonesian intersection or not
                int n_in;
                int n_out;
                ArrayList<Cell> tmp = new ArrayList<>();
                if(this.indonesian_cross){
                    n_in = begin_zone_size - 1;
                    n_out = left_zone_size - 1;
                    tmp.add(new Cell(this.x, this.y + dy));
                    tmp.add(new Cell(this.x + left_dx, this.y));
                }
                else{
                    n_in = begin_zone_size;
                    n_out = left_zone_size;
                    tmp.add(new Cell(this.x + dx, this.y));
                    tmp.add(new Cell(this.x + dx, this.y + left_dy));
                    tmp.add(new Cell(this.x, this.y + left_dy));
                }
                
                for(int j = 0 ; j < n_in ; j++){
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
                    left_way.addCell(new Cell(cell_x, cell_y));
                }
                
                for(Cell cell : tmp)
                    left_way.addCell(cell);
                    
                for(int j = n_out-1 ; j >= 0 ; j--){
                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    if(left.isHorizontal()){
                        // X depends of begin zone size, j and dx
                        cell_x = this.x + left_dx * (left_zone_size - j);

                        // Y depends of zone size, i and dy
                        cell_y = this.y + left_dy * (left_zone_size - i);
                    }
                    else{
                        // X depends of zone size, i and dx
                        cell_x = this.x + left_dx * (left_zone_size - i);

                        // Y depends of begin zone size, j and dy
                        cell_y = this.y + left_dy * (left_zone_size - j);
                    }

                    //Add cell to the future way
                    left_way.addCell(new Cell(cell_x, cell_y));
                }
                
                //For each cell of the way after the conflict zone
                for(int j = 1 ; j <= this.ways_size.get(Flow.OUT, begin.getLeft()) ; j++){

                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    //If begin is West or East
                    if(left.isHorizontal()){
                        //X depends of front zone size, dx and j
                        cell_x = this.x + left_dx * (left_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.y + left_dy * (left_zone_size - i);
                    }
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.x + left_dx * (left_zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.y + left_dy * (left_zone_size + j);
                    }

                    //Add cell to the future way
                    left_way.addCell(new Cell(cell_x, cell_y));
                }
                
                //Add the way to the array of ways
                this.ways.put(begin, this.nb_ways.get(Flow.IN, begin)+i, left_way);
            }
            //*/
            
            
            
            /* ----- General Case : front way ----- */
            
            //If the front way can be create
            //*
            if(i < this.nb_ways.get(Flow.OUT, begin.getFront())
                    && !(i == this.nb_ways.get(Flow.IN, begin)-1
                    && this.nb_ways.get(Flow.IN, begin) >= 3)){ // If nb_ways >= 3, left way only go left 

                int front_zone_size = this.conflict_zone_size.get(begin.getFront());
            
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
            //*/
            
            //Add the way to the array of ways
            this.ways.put(begin, i, way);
        }
    }
    
    /**
     * 
     */
    protected void updateConflictZone(){
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
    protected CardinalPoint getZone(CardinalPoint point, Flow flow){
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
    protected int getDX(CardinalPoint point, Flow flow){
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
    protected int getDY(CardinalPoint point, Flow flow){
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
