/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public enum CardinalPoint {
    NORTH,
    SOUTH,
    EAST,
    WEST;
    
    /**
     * @return The cardinal point at the left of this
     */
    public CardinalPoint getLeft(){
        switch(this){
            case NORTH :
                return EAST;
            case SOUTH :
                return WEST;
            case WEST : 
                return NORTH;
            case EAST :
                return SOUTH; 
        }
        return null;
    }
    
    /**
     * @return The cardinal point at the right of this
     */
    public CardinalPoint getRight(){
        switch(this){
            case NORTH :
                return WEST;
            case SOUTH :
                return EAST;
            case WEST : 
                return SOUTH;
            case EAST :
                return NORTH; 
        }
        return null;
    }
    
    /**
     * @return The cardinal point at the front of this
     */
    public CardinalPoint getFront(){
        switch(this){
            case NORTH :
                return SOUTH;
            case SOUTH :
                return NORTH;
            case WEST : 
                return EAST;
            case EAST :
                return WEST; 
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public int getDX(){
        switch(this){
            case NORTH :
                return -1;
            case SOUTH :
                return 1;
            case WEST : 
                return -1;
            case EAST :
                return 1; 
        }
        return 0;
    }
    
    /**
     * 
     * @return 
     */
    public int getDY(){
        switch(this){
            case NORTH :
                return -1;
            case SOUTH :
                return 1;
            case WEST : 
                return 1;
            case EAST :
                return -1; 
        }
        return 0;
    }
}
