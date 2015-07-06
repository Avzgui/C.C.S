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

import Utility.CardinalPoint;
import Utility.Flow;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Environment {

    private Table<Integer, Integer, Infrastructure> map;
    // private ArrayList<Vehiclar_Body>

    /**
     * Environment's constructor
     */
    public Environment() {
        this.map = TreeBasedTable.create();
    }

    /**
     *
     * @return
     */
    public Table<Integer, Integer, Infrastructure> getMap() {
        return map;
    }

    /**
     *
     * @param map
     */
    public void setMap(Table<Integer, Integer, Infrastructure> map) {
        this.map = map;
    }

    /**
     *
     * @param x
     * @param y
     * @param nb_ways
     * @param ways_size
     * @param indonesian_cross
     */
    public void addIntersection(int x, int y,
            Table<Flow, CardinalPoint, Integer> nb_ways,
            Table<Flow, CardinalPoint, Integer> ways_size,
            boolean indonesian_cross) {

        //Intersection creation, real position not defined yet
        Intersection intersection = new Intersection(0, 0, nb_ways, ways_size, indonesian_cross);

        //If the map is empty and x = 0 and y = 0
        if (this.map.isEmpty()) {
            if (x == 0 && y == 0) {
                this.map.put(x, y, intersection);
            } else {
                System.out.println("The first intersection should be in position [0, 0] not ["
                        + x + ", " + y + "]");
            }
        } else {

            //Future position
            Cell cell = null;

            //To determinate the new Intersection's position, check his future neighbors
            HashMap<CardinalPoint, Infrastructure> neighbors = new HashMap<>();
            neighbors.put(CardinalPoint.NORTH, this.map.get(x, y - 1));
            neighbors.put(CardinalPoint.EAST, this.map.get(x + 1, y));
            neighbors.put(CardinalPoint.SOUTH, this.map.get(x, y + 1));
            neighbors.put(CardinalPoint.WEST, this.map.get(x - 1, y));

            //For each neighbor not null
            for (Entry<CardinalPoint, Infrastructure> entry : neighbors.entrySet()) {
                if (entry.getValue() != null) {
                    //Get the the cell determinate by the neighbor
                    Cell c = entry.getValue().getCellForAnotherInfrastructure(
                            entry.getKey().getFront(),
                            intersection.getWidth(),
                            intersection.getHeight());

                    //If cell is null, affect c
                    if (cell == null) {
                        cell = c;
                    } else if (c.getX() != cell.getX() && c.getY() != cell.getY()) {
                        System.out.println("The intersection you want to add in"
                                + " position [" + x + ", " + y
                                + "] haven't good size");
                    }
                }
            }

            //If cell is not null, update intersection's position and add it to the map
            if (cell != null) {
                intersection.setPosition(cell.getX(), cell.getY());
                this.map.put(x, y, intersection);
            } else {
                System.out.println("The intersection need neighbors");
            }
        }
    }

    /**
     *
     * @return An array of all the cells in the map
     */
    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();

        //For each infrastructure in the map
        for (Infrastructure i : this.map.values()) {
            //Add cells
            cells.addAll(i.getCells());
        }

        return cells;
    }

    /**
     *
     */
    public void removeAll() {
        this.map.clear();
    }
}