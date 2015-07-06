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
 * The enumeration Flow is used to know on which "flow"
 * is a vehicular in an Infrastructure.
 * 
 * @author Antoine "Avzgui" Richard
 */
public enum Flow {
    IN,
    OUT;
    
    /**
     * blablabla
     * 
     * @return 
     */
    public Flow getOpposite(){
        switch(this){
            case IN :
                return OUT;
            case OUT :
                return IN;
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public String toString(){
        switch(this){
            case IN :
                return "IN";
            case OUT :
                return "OUT";
        }
        return null;
    } 
}
