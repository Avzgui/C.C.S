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
package Model.Agents.Brains;

import Model.Agents.Bodies.Intersection_Body;
import Model.Agents.Bodies.Vehicle_Body;
import Model.CCS_Model;
import Model.Environment.Cell;
import Model.Environment.Infrastructure;
import Model.Environment.Intersection;
import Model.Environment.Trajectory;
import Model.Messages.M_Bye;
import Model.Messages.M_Hello;
import Model.Messages.M_Welcome;
import Model.Messages.Message;
import Utility.CardinalPoint;
import Utility.Crossing_Configuration;
import Utility.Flow;
import Utility.Reservation;
import com.google.common.collect.Table;
import java.util.Map.Entry;
import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

/**
 * The class Intersection_Brain, inherited by Infrastructure_Brain,
 * represents the behavior of an intersection agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Intersection_Brain extends Infrastructure_Brain {

    private Crossing_Configuration configuration;
    
    /**
     * Constructor
     * 
     * @param id ID of the brain (by default, the same as the agent)
     * @param body the body of the agent.
     */
    public Intersection_Brain(int id, Intersection_Body body) {
        super(id, body);
        this.configuration = new Crossing_Configuration(0, this.id);
    }

    /**
     * Returns the current crossing configuration.
     * 
     * @return the crossing configuration.
     */
    public Crossing_Configuration getConfiguration() {
        return new Crossing_Configuration(this.configuration);
    }

    /**
     * Changes the current configuration.
     * 
     * @param configuration the new crossing configuration.
     */
    public void setConfiguration(Crossing_Configuration configuration) {
        this.configuration = new Crossing_Configuration(configuration);
    }
    
    /**
     * Determine the crossing tick for a new vehicle in the intersection
     * with a First Comes First Served algorithm.
     * 
     * @param trajectory trajectory of the vehicle.
     * @param pos current position of the vehicle.
     * @param whereStop position where the vehicle will stop.
     * 
     * @return The crossing tick of the vehicle. (-1 if nothing found).
     */
    private int FCFS(Trajectory trajectory, Cell pos, Cell whereStop){
        //Solver creation
        Solver solver = new Solver("FCFS");
        
        //Variables creation
        IntVar x = VariableFactory.enumerated("X", 0, VariableFactory.MAX_INT_BOUND, solver);
        IntVar offset = VariableFactory.fixed(2, solver);
        
        //Create constraints
        
        /* ----- Constraint 1 : x is sup to actual tick adding to the distance ----- */
        solver.post(IntConstraintFactory.arithm(x, ">=", CCS_Model.ticks + (int) pos.getDistance(whereStop)));
        
        //For each réservation
        for(Reservation r : configuration.getReservations().values()){
            /* ----- Constraint 2 : x is sup to the other ticks already reserved  ----- */
            solver.post(IntConstraintFactory.arithm(x, ">=", r.getCrossing_tick()));
            
            /* ----- Constraint 3 : 
                for each cell of the trajectory reserved creates conflict with trajectory,
                    |(x+dist(cell)) - (tick+dist(cell))| >= offset
            ----- */
            Trajectory t = r.getTrajectory();
            for(Cell c : t.getCells()){
                // Cell in conflict
                if(trajectory.getCells().contains(c)){
                    
                    //(tick+dist(cell))
                    int dist_1 = whereStop.getDistance(c);
                    
                    //(tick+dist(cell))
                    int dist_2 = r.getCrossing_tick() + t.getWhereToStop().getDistance(c);
                    
                    solver.post(IntConstraintFactory.arithm(x, "-", offset, ">=", Math.abs(dist_2 - dist_1)));
                }
            }
        }
        
        //Find solution
        solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, x);
        
        return x.getValue();
    }
    
    @Override
    protected Message processMessage(Message mess){
        if(mess instanceof M_Hello){
            //Check the id
            if(mess.getReceiver_id() == this.id){
                System.out.println("Intersection " + this.id + " process M_Hello");
                M_Hello m = (M_Hello) mess;
                Cell pos = (Cell) m.getDatum().get(0);
                CardinalPoint goal = (CardinalPoint) m.getDatum().get(1);
                
                //Get the good trajectory.
                Intersection_Body i_body = (Intersection_Body) this.body;
                Intersection inter = (Intersection) i_body.getInfrastructure();
                Table<CardinalPoint, Integer, Trajectory> ways = inter.getTrajectories();
                Trajectory trajectory = null;
                int t_id = -1;
                CardinalPoint t_cp = null;
                boolean ok = false;
                for(CardinalPoint c : ways.rowKeySet()){
                    for(Entry<Integer, Trajectory> entry : ways.row(c).entrySet()){
                        int _id = entry.getKey();
                        Trajectory w = entry.getValue();
                        //If the trajectory contains the position
                        if(w.getCells().contains(pos)){
                            if(_id >= 0 && _id < inter.getNb_ways().get(Flow.IN, c)){
                                if(c.getFront() == goal){
                                    trajectory = w;
                                    t_id = _id;
                                    t_cp = c;
                                    ok = true;
                                }
                            }
                            else if(_id == inter.getNb_ways().get(Flow.IN, c)){
                                if(c.getRight() == goal){
                                    trajectory = w;
                                    t_id = _id;
                                    t_cp = c;
                                    ok = true;
                                }
                            }
                            else{
                                if(c.getLeft() == goal){
                                    trajectory = w;
                                    t_id = _id;
                                    t_cp = c;
                                    ok = true;
                                }
                            }
                        }
                    }
                    if(ok) break;
                }
                
                if(ok){
                    for(Vehicle_Body v : i_body.getEnvironment().getVehicles()){
                        if(v.getId() == m.getSender_id()){
                            i_body.addVehicle(v);
                            break;
                        }
                    }
                    //Get the first cell of the neighbor.
                    //*
                    if(i_body.getNeighbors().containsKey(goal)){
                        Infrastructure neighbor = i_body.getNeighbors().get(goal).getInfrastructure();
                        Trajectory w = neighbor.getTrajectories().get(goal.getFront(), t_id);
                        if(w != null && !w.isEmpty()){
                            Cell next = w.getCells().get(0);
                            if(next != null && trajectory != null)
                                trajectory.addCell(next);
                        }
                    }
                    //*/
                    
                    //Determine the cell where the vehicle will wait
                    Cell whereStop = null;
                    if(t_cp != null){
                        Intersection i = (Intersection) i_body.getInfrastructure();
                        if(trajectory != null && trajectory.getCells() != null && !trajectory.getCells().isEmpty())
                            whereStop = trajectory.getCells().get(i.getWays_size().get(Flow.IN, t_cp) - 1);
                    }
                    
                    if(trajectory != null)
                        trajectory.setWhereToStop(whereStop);
                    
                    //FCFS deterlination of the crossing tick TODO
                    int tick = FCFS(trajectory, pos, whereStop);
                    System.out.println("FCFS : " + tick);
                    
                    //Create the new reservation and add it to the configuration
                    Reservation reserv = new Reservation(m.getSender_id(), this.id, trajectory, tick);
                    this.configuration.addReservation(m.getSender_id(), reserv);
                    
                    //Send the trajectory to the vehicle
                    return new M_Welcome(this.id, m.getSender_id(),trajectory, tick);
                }
            }
        }
        else if(mess instanceof M_Bye){
            if(mess.getReceiver_id() == this.id){
                System.out.println("Intersection " + this.id + " process M_Bye");
                //Search and remove the vehicle
                Intersection_Body i_body = (Intersection_Body) this.body;
                Vehicle_Body toRemove = null;
                for(Vehicle_Body v : i_body.getVehicles()){
                    if(v.getId() == mess.getSender_id()){
                        toRemove = v;
                        break;
                    }
                }
                
                if(toRemove != null)
                    i_body.getVehicles().remove(toRemove);
            }
        }
        else
            super.processMessage(mess);
        
        return null;
    }

    @Override
    public void run() {
        //Process all the messages
        while(!this.messages_memory.isEmpty()){
            Message m = processMessage(this.messages_memory.get(0));
            this.messages_memory.remove(0);
            
            if(m != null)
                this.body.sendMessage(m);
        }
        
        //Update configuration (check the vote)
    }
}
