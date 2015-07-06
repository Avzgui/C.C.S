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

package Utility;

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
    public boolean isVertical(){
        return (this == NORTH || this == SOUTH);
    }
    
    /**
     * 
     * @return 
     */
    public boolean isHorizontal(){
        return (this == EAST || this == WEST);
    }
    
    /**
     * 
     * @return 
     */
    public String toString(){
        switch(this){
            case NORTH :
                return "NORTH";
            case SOUTH :
                return "SOUTH";
            case WEST : 
                return "WEST";
            case EAST :
                return "EAST"; 
        }
        return null;
    }
}
