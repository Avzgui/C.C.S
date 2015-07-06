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
 * The enumeration CardinalPoint is used to know the position of
 * an Environment's element compared to an another, or to know 
 * a zone in an Infrastructure.
 * 
 * @author Antoine "Avzgui" Richard
 */
public enum CardinalPoint {
    NORTH,
    SOUTH,
    EAST,
    WEST;
    
    /**
     * Returns the cardinal point at the left of the
     * current cardinal point.
     * 
     * @return The cardinal point at the left
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
     * Returns the cardinal point at the right of the
     * current cardinal point.
     * 
     * @return The cardinal point at the right
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
     * Returns the cardinal point at the front of the
     * current cardinal point.
     * 
     * @return The cardinal point at the front
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
     * Returns if the current cardinal point is vertical or not.
     * 
     * @return if it's vertical or not
     */
    public boolean isVertical(){
        return (this == NORTH || this == SOUTH);
    }
    
    /**
     * Returns if the current cardinal point is horizontal or not.
     * 
     * @return if it's horizontal or not 
     */
    public boolean isHorizontal(){
        return (this == EAST || this == WEST);
    }
    
    /**
     * Returns the current CardinalPoint on his String format.
     * 
     * @return the string format of the current cardinal point
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
