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

import Utility.Flow;
import Utility.CardinalPoint;
import static Utility.CardinalPoint.EAST;
import static Utility.CardinalPoint.NORTH;
import static Utility.CardinalPoint.SOUTH;
import static Utility.CardinalPoint.WEST;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class Intersection, inherited of Infrastructure, is the environment's 
 * representation of an intersection. Composed by maximum eight input and output.
 * Generate each ways during construction and updates.
 * 
 * @author Antoine "Avzgui" Richard
 * 
 * @see Utility.Flow
 * @see Utility.CardinalPoint
 */
public class Intersection extends Infrastructure {

    // IN : 0, OUT : 1
    private Table<Flow, CardinalPoint, Integer> nb_ways;
    private Table<Flow, CardinalPoint, Integer> ways_size;
    private final HashMap<CardinalPoint, Integer> conflict_zone_size;
    private int center_x;
    private int center_y;
    private boolean indonesian_cross;
    
    /**
     * Constructor
     * 
     * @param x coordinate x
     * @param y coordinate y
     * @param nb_ways numbers of ways for each input and output of the intersection.
     * @param ways_size sizes of ways for each input and output of the intersection.
     * @param indonesian_cross to know the type of the intersection.
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
        
        //Initialize conflict zone, ways and sizes
        update();
        
    }
    
    /**
     * Copy Constructor
     * 
     * @param other an another intersection 
     */
    public Intersection(Intersection other) {
        
        super(other);
        this.nb_ways = HashBasedTable.create(other.nb_ways);
        this.ways_size = HashBasedTable.create(other.ways_size);
        this.conflict_zone_size = new HashMap<>(other.conflict_zone_size);
        this.indonesian_cross = other.indonesian_cross;
        
        //Initialize conflict zone, ways and sizes
        update();
    }
    
    @Override
    protected void update(){
        
        //Initialize conflict zone
        updateConflictZone();
        
        //Set width and center x
        int max1 = Math.max(this.ways_size.get(Flow.IN, CardinalPoint.WEST),
                        this.ways_size.get(Flow.OUT, CardinalPoint.WEST));
        int max2 = Math.max(this.ways_size.get(Flow.IN, CardinalPoint.EAST),
                        this.ways_size.get(Flow.OUT, CardinalPoint.EAST));
        int max3 = this.conflict_zone_size.get(CardinalPoint.WEST);
        int max4 = this.conflict_zone_size.get(CardinalPoint.EAST);
        this.width = max1+max2+max3+max4+1;
        this.center_x = this.x+max1+max3;
        
        //Set height and center y
        max1 = Math.max(this.ways_size.get(Flow.IN, CardinalPoint.NORTH),
                        this.ways_size.get(Flow.OUT, CardinalPoint.NORTH));
        max2 = Math.max(this.ways_size.get(Flow.IN, CardinalPoint.SOUTH),
                        this.ways_size.get(Flow.OUT, CardinalPoint.SOUTH));
        max3 = this.conflict_zone_size.get(CardinalPoint.NORTH);
        max4 = this.conflict_zone_size.get(CardinalPoint.SOUTH);
        this.height = max1+max2+max3+max4+1;
        this.center_y = this.y+max1+max3;
        
        //Initialize the ways
        createWays(CardinalPoint.NORTH);
        createWays(CardinalPoint.SOUTH);
        createWays(CardinalPoint.WEST);
        createWays(CardinalPoint.EAST);
    }
    
    @Override
    public void setX(int x){
        this.x = x;
        update();
    }
    
    @Override
    public void setY(int y){
        this.y = y;
        update();
    }
    
    @Override
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        update();
    }

    /**
     * Returns the numbers of ways for each input and output.
     * 
     * @return the table of numbers of ways.
     */
    public Table<Flow, CardinalPoint, Integer> getNb_ways() {
        return nb_ways;
    }

    /**
     * Changes the numbers of ways for each input and output.
     * 
     * @param nb_ways a table of numbers of ways
     */
    public void setNb_ways(Table<Flow, CardinalPoint, Integer> nb_ways) {
        this.nb_ways = nb_ways;
        update();
    }
    
    /**
     * Changes a number of ways.
     * 
     * @param flow flow key of the number of way.
     * @param point cardinal point of the number of way.
     * @param nb_way number of way to set.
     */
    public void setNb_way(Flow flow, CardinalPoint point, int nb_way) {
        this.nb_ways.put(flow, point, nb_way);
        update();
    }

    /**
     * Returns the sizes of the ways for each input and output.
     * 
     * @return the table of sizes of the ways.
     */
    public Table<Flow, CardinalPoint, Integer> getWays_size() {
        return ways_size;
    }

    /**
     * Changes the sizes of the ways for each input and output.
     * 
     * @param ways_size a table of size for each intput and output
     */
    public void setWays_size(Table<Flow, CardinalPoint, Integer> ways_size) {
        this.ways_size = ways_size;
        update();
    }
    
    /**
     * Changes a number of ways.
     * 
     * @param flow flow key of the number of way.
     * @param point cardinal point of the number of way.
     * @param size the size to set.
     */
    public void setWay_Size(Flow flow, CardinalPoint point, int size) {
        this.ways_size.put(flow, point, size);
        update();
    }

    /**
     * Returns if the intersection is an indonesian intersection or not.
     * 
     * @return the type of the intersection.
     */
    /*
     * indonesian | not indonesian
     *   |        |       |
     *   |        |       |
     *   -->      |       |<-
     *   ---      |       | |
     *   <--      |       ->|
     *      |     |         |
     *      |     |         |
     */
    public boolean isIndonesian_cross() {
        return indonesian_cross;
    }

    /**
     * Changes the type of the intersection.
     * 
     * @param indonesian_cross the new type of the intersection.
     */
    public void setIndonesian_cross(boolean indonesian_cross) {
        this.indonesian_cross = indonesian_cross;
        update();
    }
    
    
    @Override
    protected void createWays(CardinalPoint begin){
       
        //Clear all the ways
        this.ways.row(begin).clear();
        
        //For each way beginning at the Cardinal Point
        for(int i = 0 ; i < this.nb_ways.get(Flow.IN, begin) ; i++){
            
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
                    cell_x = this.center_x + dx * (begin_zone_size + j);

                    //Y depends of zone size, dy and i
                    cell_y = this.center_y + dy * (zone_size - i);
                }
                //If begin is North or South
                else{
                    //X depends of zone size, dx and i
                    cell_x = this.center_x + dx * (zone_size - i);

                    //Y depends of begin zone size, dy and j
                    cell_y = this.center_y + dy * (begin_zone_size + j);
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
                        cell_x = this.center_x + right_dx * (right_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.center_y + right_dy * (right_zone_size - i);
                    }
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.center_x + right_dx * (right_zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.center_y + right_dy * (right_zone_size + j);
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
                    tmp.add(new Cell(this.center_x, this.center_y + dy));
                    tmp.add(new Cell(this.center_x + left_dx, this.center_y));
                }
                else{
                    n_in = begin_zone_size;
                    n_out = left_zone_size;
                    tmp.add(new Cell(this.center_x + dx, this.center_y));
                    tmp.add(new Cell(this.center_x + dx, this.center_y + left_dy));
                    tmp.add(new Cell(this.center_x, this.center_y + left_dy));
                }
                
                for(int j = 0 ; j < n_in ; j++){
                    //Determine coordinate of the cell
                    int cell_x;
                    int cell_y;

                    if(begin.isHorizontal()){
                        // X depends of begin zone size, j and dx
                        cell_x = this.center_x + dx * (begin_zone_size - j);

                        // Y depends of zone size, i and dy
                        cell_y = this.center_y + dy * (zone_size - i);
                    }
                    else{
                        // X depends of zone size, i and dx
                        cell_x = this.center_x + dx * (zone_size - i);

                        // Y depends of begin zone size, j and dy
                        cell_y = this.center_y + dy * (begin_zone_size - j);
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
                        cell_x = this.center_x + left_dx * (left_zone_size - j);

                        // Y depends of zone size, i and dy
                        cell_y = this.center_y + left_dy * (left_zone_size - i);
                    }
                    else{
                        // X depends of zone size, i and dx
                        cell_x = this.center_x + left_dx * (left_zone_size - i);

                        // Y depends of begin zone size, j and dy
                        cell_y = this.center_y + left_dy * (left_zone_size - j);
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
                        cell_x = this.center_x + left_dx * (left_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.center_y + left_dy * (left_zone_size - i);
                    }
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.center_x + left_dx * (left_zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.center_y + left_dy * (left_zone_size + j);
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
                        cell_x = this.center_x + dx * (begin_zone_size - j);

                        // Y depends of zone size, i and dy
                        cell_y = this.center_y + dy * (zone_size - i);
                    }
                    else{
                        // X depends of zone size, i and dx
                        cell_x = this.center_x + dx * (zone_size - i);

                        // Y depends of begin zone size, j and dy
                        cell_y = this.center_y + dy * (begin_zone_size - j);
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
                        cell_x = this.center_x - dx * (front_zone_size + j);

                        //Y depends of zone size, dy and i
                        cell_y = this.center_y + dy * (zone_size - i);
                    }
                    
                    //If begin is North or South
                    else{
                        //X depends of zone size, dx and i
                        cell_x = this.center_x + dx * (zone_size - i);

                        //Y depends of front zone size, dy and j
                        cell_y = this.center_y - dy * (front_zone_size + j);
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
     * Private method to update the size of the conflict zone.
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
     * Private method to know where is an intput/output compared 
     * to the conflict zone.
     * 
     * @param point cardinal point of the input/output.
     * @param flow flow of the input/output.
     * @return the zone of the input/output in the conflict zone.
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
     * Private method to know the dx of an intput/output compared 
     * to the conflict zone.
     * 
     * @param point cardinal point of the input/output.
     * @param flow flow of the input/output.
     * @return the dx of the input/output in the conflict zone.
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
     * Private method to know the dy of an intput/output compared 
     * to the conflict zone.
     * 
     * @param point cardinal point of the input/output.
     * @param flow flow of the input/output.
     * @return the dy of the input/output in the conflict zone.
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

    @Override
    public Cell getCellForAnotherInfrastructure(CardinalPoint position, int width, int height) {
        int cell_x = 0;
        int cell_y = 0;
        
        //Switch the position of the another infrastructure
        switch(position){
            case NORTH :
                cell_x = this.x;
                cell_y = this.center_y - this.conflict_zone_size.get(CardinalPoint.NORTH)
                        - this.ways_size.get(Flow.OUT, CardinalPoint.NORTH) - height;
            break;
            case EAST :
                cell_x = this.center_x + this.conflict_zone_size.get(CardinalPoint.EAST)
                        + this.ways_size.get(Flow.OUT, CardinalPoint.EAST) + 1;
                cell_y = this.y;
            break;
            case SOUTH :
                cell_x = this.x;
                cell_y = this.center_y + this.conflict_zone_size.get(CardinalPoint.SOUTH)
                        + this.ways_size.get(Flow.OUT, CardinalPoint.SOUTH) + 1;
            break;
            case WEST :
                cell_x = this.center_x - this.conflict_zone_size.get(CardinalPoint.WEST)
                        - this.ways_size.get(Flow.OUT, CardinalPoint.WEST) - width;
                cell_y = this.y;
            break;
        }
        
        return new Cell(cell_x, cell_y);
    }
}
