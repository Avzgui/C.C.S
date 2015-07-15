/*
 * Copyright (C) 2015 Antoine "Avzgui" Richard and collaborators
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

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * The class Crossing_Configuration is used to know the differents reservation
 * of each vehicle in an intersection.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Crossing_Configuration {
    private int id;
    private final int intersection_id;
    private final HashMap<Integer, Reservation> reservations;
    
    /**
     * Constructor
     * 
     * @param id ID of the configuration.
     * @param intersection_id ID of the intersection who use this configuration.
     * @param reservations Array of the differents reservations done by the vehicles.
     */
    public Crossing_Configuration(int id, int intersection_id,
            HashMap<Integer, Reservation> reservations){
        this.id = id;
        this.intersection_id = intersection_id;
        this.reservations = new HashMap<>(reservations);
    }

    /**
     * Constructor
     * 
     * @param id ID of the configuration.
     * @param intersection_id ID of the intersection who use this configuration.
     */
    public Crossing_Configuration(int id, int intersection_id) {
        this.id = id;
        this.intersection_id = intersection_id;
        this.reservations = new HashMap<>();
    }
    
    /**
     * Copy Constructor
     * 
     * @param other the other configuration to copy.
     */
    public Crossing_Configuration(Crossing_Configuration other){
        this.id = other.getId();
        this.intersection_id = other.getIntersection_id();
        this.reservations = new HashMap<>();
        
        for(Entry<Integer, Reservation> entry : other.getReservations().entrySet())
            this.reservations.put(entry.getKey(), new Reservation(entry.getValue()));
    }

    /**
     * Returns the ID of the configuration.
     * 
     * @return the ID of the configuration.
     */
    public int getId() {
        return id;
    }

    /**
     * Changes the ID of the configuration.
     * 
     * @param id the new ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the intersection.
     * 
     * @return the ID of the intersection.
     */
    public int getIntersection_id() {
        return intersection_id;
    }

    /**
     * Returns all the reservations of the configuration.
     * 
     * @return the reservations of the configuration.
     */
    public HashMap<Integer, Reservation> getReservations() {
        return reservations;
    }
    
    /**
     * Returns the reservation of a vehicle agent.
     * 
     * @param vehicle_id ID of the vehicle.
     * 
     * @return the reservation of the vehicle.
     */
    public Reservation getReservation(int vehicle_id){
        return reservations.get(vehicle_id);
    }
    
    /**
     * Adds a reservation to the other reservations of the configuration.
     * 
     * @param vehicle_id ID of the vehicle.
     * @param reservation The reservation to add.
     */
    public void addReservation(int vehicle_id, Reservation reservation){
        this.reservations.put(vehicle_id, reservation);
    }
    
    /**
     * Removes the reservation of a vehicle.
     * 
     * @param vehicle_id ID of the vehicle.
     */
    public void removeReservation(int vehicle_id){
        this.reservations.remove(vehicle_id);
    }

    @Override
    public String toString() {
        return "Crossing_Configuration{" + "\n\tid=" + id + ",\n\tintersection_id=" + intersection_id + ",\n\treservations=" + reservations + "\n}";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.intersection_id;
        hash = 47 * hash + Objects.hashCode(this.reservations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Crossing_Configuration other = (Crossing_Configuration) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.intersection_id != other.intersection_id) {
            return false;
        }
        if (!Objects.equals(this.reservations, other.reservations)) {
            return false;
        }
        return true;
    }
}
