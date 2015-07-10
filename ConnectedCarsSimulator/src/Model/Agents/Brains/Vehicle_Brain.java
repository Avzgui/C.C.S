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
package Model.Agents.Brains;

import Model.Agents.Bodies.A_Body;
import Model.Agents.Bodies.Infrastructure_Body;
import Model.Agents.Bodies.Vehicle_Body;
import Model.Environment.Cell;
import Model.Environment.Intersection;
import Model.Environment.Way;
import Model.Messages.M_Hello;
import Model.Messages.M_Welcome;
import Model.Messages.Message;
import Utility.CardinalPoint;
import Utility.Flow;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * The class Vehicle_Brain represents the behavior layer of a vehicle agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Vehicle_Brain extends A_Brain {

    protected Way way;
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
        this.way = null;
        this.final_goal = goal;
        this.intermediate_goals = new ArrayList<>();
    }
    
    @Override
    public void setBody(A_Body body){
        this.body = body;
        
        //Use the body to determinate the intermediate goals
        Vehicle_Body v_body = (Vehicle_Body) this.body;
        determineIntermediateGoals(v_body.getInfrastructure(), v_body.getPosition());
        
        //Send the creation
        M_Hello mess = new M_Hello(this.id, v_body.getInfrastructure().getId(),
                                    v_body.getPosition(),
                                    this.intermediate_goals.get(this.intermediate_goals.size()-1));
        this.body.sendMessage(mess);
    }

    /**
     * Returns the way the agent have to follow.
     * 
     * @return the way of the agent.
     */
    public Way getWay() {
        return way;
    }

    /**
     * Changes the way of the agent.
     * 
     * @param way the new way to set.
     */
    public void setWay(Way way) {
        this.way = way;
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
     * Warning !! Not finished !! Doesn't work for several infrastructure.
     */
    private CardinalPoint determineIntermediateGoals(Infrastructure_Body current, Cell pos){
        
        if(current != null){
            //If Infrastructure contains the goal
            Intersection inf = (Intersection) current.getInfrastructure();
            if(inf.getCells().contains(this.final_goal)){
                //Determine the good way
                Table<CardinalPoint, Integer, Way> ways = inf.getWays();
                for(CardinalPoint c : ways.rowKeySet()){
                    for(Entry<Integer, Way> entry : ways.row(c).entrySet()){
                        int id = entry.getKey();
                        Way way = entry.getValue();
                        
                        //When the good way is determinate
                        if(way.getCells().contains(pos) && way.getCells().contains(this.final_goal)){
                            //Switch the id, add the good Cardinal point
                            if(id >= 0 && id < inf.getNb_ways().get(Flow.IN, c))
                                this.intermediate_goals.add(c.getFront());
                            else if(id == inf.getNb_ways().get(Flow.IN, c))
                                this.intermediate_goals.add(c.getRight());
                            else
                                this.intermediate_goals.add(c.getLeft());
                            
                            //Return the begin point
                            return c.getFront();
                        }
                    }
                }
            }
            else{
                //For each neighbors
                for(Infrastructure_Body body : current.getNeighbors()){
                    //TODO !!
                }
            }
        }
                
        //Default break point
        return null;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void processMessage(Message mess){
        if(mess instanceof M_Welcome){
            System.out.println("Vehicle process M_Welcome");
            
            //Get the message
            M_Welcome m = (M_Welcome) mess;
            Vehicle_Body v_body = (Vehicle_Body) this.body;
            
            //Get the way
            if(m.getDatum().get(0) != null){
                this.way = new Way((Way) m.getDatum().get(0));
                while(!this.way.isEmpty() 
                        && !v_body.getPosition().equals(this.way.pop()));
            }
        }
        else
            super.processMessage(mess);
    }
    
    /**
     * Reasoning layer's function to update the direction of the agent.
     */
    private void updateDirection(){
        if(this.way != null){
            Vehicle_Body v_body = (Vehicle_Body) this.body;
            if(v_body.getDirection() == null && !this.way.isEmpty())
                v_body.setDirection(this.way.pop());
        }
    }
    
    private void updateSpeed(){
        //By default, speed set to zero
        Vehicle_Body v_body = (Vehicle_Body) this.body;
        v_body.setSpeed(0.0);
        
        if(v_body.lookIfCellIsFree(v_body.getDirection()))
            v_body.setSpeed(1.0);
    }

    @Override
    public void run(){
        
        //Process all the messages
        while(!this.messages_memory.isEmpty()){
            processMessage(this.messages_memory.get(0));
            this.messages_memory.remove(0);
        }
        
        //Update direction
        updateDirection();
        
        //Update speed
        updateSpeed();
    }
}
