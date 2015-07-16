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

import Model.Agents.Bodies.A_Body;
import Model.Agents.Bodies.Infrastructure_Body;
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
import Utility.Flow;
import Utility.Reservation;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The class Vehicle_Brain represents the behavior layer of a vehicle agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Vehicle_Brain extends A_Brain {

    protected Reservation reserv;
    protected final Cell final_goal;
    protected final ArrayList<CardinalPoint> intermediate_goals;
    
    /**
     * Constructor
     * 
     * @param id ID of the brain (by default, the same as the agent).
     * @param body the body of the agent.
     * @param goal the final goal of the agent.
     */
    public Vehicle_Brain(int id, Vehicle_Body body, Cell goal) {
        super(id, body);
        this.reserv = null;
        this.final_goal = goal;
        this.intermediate_goals = new ArrayList<>();
    }
    
    @Override
    public void setBody(A_Body body){
        this.body = body;
        
        //Determine the infrastructure destination's coordinates
        Table<Integer, Integer, Infrastructure> map = this.body.getEnvironment().getMap();
        int dest_x = 0;
        int dest_y = 0;
        int x = 0;
        int y = 0;
        CardinalPoint begin = null;
        boolean ok = false;
        for(int row : map.rowKeySet()){
            for(Entry<Integer, Infrastructure> entry : map.row(row).entrySet()){
                if(entry.getValue().haveCell(this.final_goal)){
                    dest_x = row;
                    dest_y = entry.getKey();
                    ok = true;
                }
                
                if(entry.getValue().haveCell(this.body.getPosition())){
                    //Determine the position of the vehicule in the infrastructure
                    x = row;
                    y = entry.getKey();
                    Infrastructure i = entry.getValue();
                    for(CardinalPoint cp : i.getTrajectories().rowKeySet()){
                        for(Entry<Integer, Trajectory> e : i.getTrajectories().row(cp).entrySet()){
                            if(e.getValue().getCells().get(0).equals(this.body.getPosition()))
                                begin = cp;
                        }
                    }
                }
            }
        }
        
        //Destination founded
        if(ok && begin != null){
            System.out.println("Pos found in " + begin + " of : [" + x + ", " + y + "]");
            System.out.println("Final goal found in : [" + dest_x + ", " + dest_y + "]");
            System.out.println("\nVehicle " + this.id + " intermadiate goals determination : ");
            //Determine the intermediates goals
            this.intermediate_goals.clear();
            this.intermediate_goals.addAll(determineIntermediateGoals(0, begin, x, y, dest_x, dest_y));
            System.out.println("Vehicle " + this.id + " intermadiate goals : " + this.intermediate_goals);
            //Send the creation
            //*
            if(this.intermediate_goals != null && !this.intermediate_goals.isEmpty()){
                Vehicle_Body v_body = (Vehicle_Body) this.body;
                M_Hello mess = new M_Hello(this.id, v_body.getInfrastructure().getId(),
                                            v_body.getPosition(),
                                            this.intermediate_goals.get(this.intermediate_goals.size()-1));
                this.body.sendMessage(mess);
            }
            //*/
        }
    }

    /**
     * Returns the final goal of the agent.
     * 
     * @return the final goal of the agent.
     */
    public Cell getFinal_goal() {
        return final_goal;
    }

    /**
     * Returns the array of intermediate goals of the agent.
     * 
     * @return the array of intermediate goals.
     */
    public ArrayList<CardinalPoint> getIntermediate_goals() {
        return intermediate_goals;
    }
    
    /**
     * Private method used by the vehicle agent to determinate his intermediate
     * goals.
     * 
     * n.b : can be changed to an A*
     */
    private ArrayList<CardinalPoint> determineIntermediateGoals(int depth, CardinalPoint begin, int current_x, int current_y, int dest_x, int dest_y){
        
        System.out.println("\nDetph :" + depth + ", in " + begin + " of pos : [" + current_x + ", " + current_y + "]");
        ArrayList<CardinalPoint> goals = new ArrayList<>();
        
        //Get the current infrastructure
        Infrastructure i = this.body.getEnvironment().getMap().get(current_x, current_y);
        
        //End
        if(current_x == dest_x && current_y == dest_y){
            System.out.println("Destination found");
            for(Entry<Integer, Trajectory> entry : i.getTrajectories().row(begin).entrySet()){
                int w_id = entry.getKey();
                Trajectory w = entry.getValue();
                if(w.getCells().contains(this.final_goal)){
                    System.out.println("trajectory found");
                    if(i instanceof Intersection){
                        //Cast
                        Intersection inter = (Intersection) i;
                        
                        //Switch ID
                        if(w_id >= 0 && w_id < inter.getNb_ways().get(Flow.IN, begin))
                            goals.add(begin.getFront());
                        else if(w_id == inter.getNb_ways().get(Flow.IN, begin))
                            goals.add(begin.getRight());
                        else
                            goals.add(begin.getLeft());
                    }
                }
            }
        }
        else{
            //Check in neighbors
            HashMap<CardinalPoint, ArrayList<CardinalPoint>> neighbors = new HashMap<>();
            
            /* --- NORTH --- */
            if(depth < this.body.getEnvironment().getMap().size()
                    && this.body.getEnvironment().getMap().contains(current_x, current_y-1)
                    && i.isAnAvailableFlow(begin, CardinalPoint.NORTH))
                neighbors.put(CardinalPoint.NORTH, 
                        determineIntermediateGoals(depth+1, CardinalPoint.SOUTH, current_x, current_y-1, dest_x, dest_y));
            
            /* --- EAST --- */
            if(depth < this.body.getEnvironment().getMap().size() 
                    && this.body.getEnvironment().getMap().contains(current_x+1, current_y)
                    && i.isAnAvailableFlow(begin, CardinalPoint.EAST))
                neighbors.put(CardinalPoint.EAST, 
                        determineIntermediateGoals(depth+1, CardinalPoint.WEST, current_x+1, current_y, dest_x, dest_y));
            
            /* --- SOUTH --- */
            if(depth < this.body.getEnvironment().getMap().size()
                    && this.body.getEnvironment().getMap().contains(current_x, current_y+1)
                    && i.isAnAvailableFlow(begin, CardinalPoint.SOUTH))
                neighbors.put(CardinalPoint.SOUTH, 
                        determineIntermediateGoals(depth+1, CardinalPoint.NORTH, current_x, current_y+1, dest_x, dest_y));
            
            /* --- WEST --- */
            if(depth < this.body.getEnvironment().getMap().size()
                    && this.body.getEnvironment().getMap().contains(current_x-1, current_y)
                    && i.isAnAvailableFlow(begin, CardinalPoint.WEST))
                neighbors.put(CardinalPoint.WEST, 
                        determineIntermediateGoals(depth+1, CardinalPoint.EAST, current_x-1, current_y, dest_x, dest_y));
            
            //Get the min
            int min = Integer.MAX_VALUE;
            CardinalPoint c_min = null;
            for(Entry<CardinalPoint, ArrayList<CardinalPoint>> e : neighbors.entrySet()){
                if(!e.getValue().isEmpty() && e.getValue().size() < min){
                    min = e.getValue().size();
                    c_min = e.getKey();
                }
            }
            
            //Add the min array to goals
            if(c_min != null){
                goals.addAll(neighbors.get(c_min));
                goals.add(c_min);
            }
        }
        
        System.out.println("Intermediate state : " + goals);
        
        return goals;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    protected Message processMessage(Message mess){
        if(mess instanceof M_Welcome){
            System.out.println("Vehicle " + this.id + " process M_Welcome");
            
            //Get the message
            M_Welcome m = (M_Welcome) mess;
            Vehicle_Body v_body = (Vehicle_Body) this.body;
            
            //Get the trajectory
            if(m.getDatum().get(0) != null){
                //Get trajectory
                this.reserv = new Reservation((Reservation) m.getDatum().get(0));
                
                while(this.reserv != null
                        && !this.reserv.getTrajectory().isEmpty()
                        && !v_body.getPosition().equals(this.reserv.getTrajectory().pop()));
            }
        }
        else
            super.processMessage(mess);
        
        return null;
    }
    
    /**
     * Reasoning layer's function to update the current infrastructure,
     * signal the coming in the agent and update goals.
     */
    private Message updateInfrastructure(){
        Vehicle_Body v_body = (Vehicle_Body) this.body;
        //If vehicle not on the infrastructure anymore
        if(this.reserv != null && this.reserv.getTrajectory().isEmpty()){
            if(this.intermediate_goals != null && !this.intermediate_goals.isEmpty()){
                //Say bye to the current infrastructure
                this.body.sendMessage(new M_Bye(this.id, v_body.getInfrastructure().getId()));
                
                //Get the current goal
                CardinalPoint cp = this.intermediate_goals.get(this.intermediate_goals.size()-1);

                //Get the neighbor infrastructure
                Infrastructure_Body neighbor = v_body.getInfrastructure().getNeighbors().get(cp);
                if(neighbor != null){
                    //Set the next infrastructure
                    v_body.setInfrastructure(neighbor);

                    //Remove the current goal
                    this.intermediate_goals.remove(this.intermediate_goals.size()-1);

                    //Send a message to the new infrastructure
                    M_Hello mess = new M_Hello(this.id, v_body.getInfrastructure().getId(),
                                                v_body.getDirection(),
                                                this.intermediate_goals.get(this.intermediate_goals.size()-1));
                    return mess;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Reasoning layer's function to update the direction of the agent.
     */
    private void updateDirection(){
        if(this.reserv != null){
            Vehicle_Body v_body = (Vehicle_Body) this.body;
            if(v_body.getDirection() == null && !this.reserv.getTrajectory().isEmpty())
                v_body.setDirection(this.reserv.getTrajectory().pop());
        }
    }
    
    /**
     * Reasoning layer's function to update the speed of the agent.
     */
    private void updateSpeed(){
        //By default, speed set to zero
        Vehicle_Body v_body = (Vehicle_Body) this.body;
        v_body.setSpeed(0.0);
        
        //If we not have to stop and wait
        if(this.reserv != null && (
                this.reserv.getTrajectory().getWhereToStop() == null 
                || !v_body.getPosition().equals(this.reserv.getTrajectory().getWhereToStop())
                || this.reserv.getCrossing_tick() <= CCS_Model.ticks)){
            //If the cell where the vehicle gone is free
            //if(v_body.lookIfCellIsFree(v_body.getDirection()))
                v_body.setSpeed(1.0);
        }
    }

    @Override
    public void run(){
        
        //Process all the messages
        while(!this.messages_memory.isEmpty()){
            Message m = processMessage(this.messages_memory.get(0));
            this.messages_memory.remove(0);
            
            if(m != null)
                this.body.sendMessage(m);
        }
        
        //Update reservation (TODO)
        
        //Vote (TODO)
        
        //Update direction
        updateDirection();
        
        //Update infrastructure
        Message m = updateInfrastructure();
        if(m != null)
                this.body.sendMessage(m);
        
        //Update speed
        updateSpeed();
    }
}
