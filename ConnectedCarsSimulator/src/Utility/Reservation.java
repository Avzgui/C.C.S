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

import Model.Environment.Trajectory;
import java.util.Objects;

/**
 * The class Reservation contains the datum used to know the reservation
 * of a vehicle agent on an intersection.
 * 
 * The vehicle travels the intersection with a trajectory, at a step.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Reservation {
    private final int vehicle_id;
    private final int intersection_id;
    private final Trajectory trajectory;
    private final int step;
    
    /**
     * Constructor
     * 
     * @param vehicle_id ID of the vehicle who reserved.
     * @param intersection_id ID of the intersection.
     * @param trajectory Trajectory of the vehicle.
     * @param step Step reserved by the vehicle to cross the intersection.
     */
    public Reservation(int vehicle_id, int intersection_id,
            Trajectory trajectory, int step){
        this.vehicle_id = vehicle_id;
        this.intersection_id = intersection_id;
        this.trajectory = trajectory;
        this.step = step;
    }
    
    /**
     * Copy constructor
     * 
     * @param other The reservation to copy.
     */
    public Reservation(Reservation other){
        this.vehicle_id = other.getVehicle_id();
        this.intersection_id = other.getIntersection_id();
        this.trajectory = new Trajectory(other.getTrajectory());
        this.step = other.getStep();
    }

    /**
     * Returns the ID of the vehicle who reserved.
     * 
     * @return the ID of the vehicle.
     */
    public int getVehicle_id() {
        return vehicle_id;
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
     * Returns the trajectory of vehicle on the intersection.
     * 
     * @return the trajectory of the vehicle. 
     */
    public Trajectory getTrajectory() {
        return trajectory;
    }

    /**
     * Returns the step reserved by the vehicle to cross the intersection.
     * 
     * @return the step reserved by the vehicle
     */
    public int getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "Reservation{" + "\n\tvehicle_id=" + vehicle_id + ",\n\tintersection_id=" + intersection_id + ",\n\ttrajectory=" + trajectory + ",\n\tstep=" + step + "\n}";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.vehicle_id;
        hash = 53 * hash + this.intersection_id;
        hash = 53 * hash + Objects.hashCode(this.trajectory);
        hash = 53 * hash + this.step;
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
        final Reservation other = (Reservation) obj;
        if (this.vehicle_id != other.vehicle_id) {
            return false;
        }
        if (this.intersection_id != other.intersection_id) {
            return false;
        }
        if (!Objects.equals(this.trajectory, other.trajectory)) {
            return false;
        }
        if (this.step != other.step) {
            return false;
        }
        return true;
    }
}
