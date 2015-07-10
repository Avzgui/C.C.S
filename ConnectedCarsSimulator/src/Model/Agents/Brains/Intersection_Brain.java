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

import Model.Agents.Bodies.Intersection_Body;
import Model.Environment.Cell;
import Model.Environment.Infrastructure;
import Model.Environment.Intersection;
import Model.Environment.Way;
import Model.Messages.M_Hello;
import Model.Messages.M_Welcome;
import Model.Messages.Message;
import Utility.CardinalPoint;
import Utility.Flow;
import com.google.common.collect.Table;
import java.util.Map.Entry;

/**
 * The class Intersection_Brain, inherited by Infrastructure_Brain,
 * represents the behavior of an intersection agent.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class Intersection_Brain extends Infrastructure_Brain {

    /**
     * Constructor
     * 
     * @param id ID of the brain (by default, the same as the agent)
     * @param body the body of the agent.
     */
    public Intersection_Brain(int id, Intersection_Body body) {
        super(id, body);
    }
    
    
    @Override
    public void processMessage(Message mess){
        if(mess instanceof M_Hello){
            //Check the id
            if(mess.getReceiver_id() == this.id){
                System.out.println("Intersection process M_Hello");
                M_Hello m = (M_Hello) mess;
                Cell pos = (Cell) m.getDatum().get(0);
                CardinalPoint goal = (CardinalPoint) m.getDatum().get(1);
                
                //Get the good way.
                Intersection_Body i_body = (Intersection_Body) this.body;
                Intersection inter = (Intersection) i_body.getInfrastructure();
                Table<CardinalPoint, Integer, Way> ways = inter.getWays();
                Way way = null;
                int w_id = -1;
                boolean ok = false;
                for(CardinalPoint c : ways.rowKeySet()){
                    for(Entry<Integer, Way> entry : ways.row(c).entrySet()){
                        w_id = entry.getKey();
                        Way w = entry.getValue();
                        //If the way contains the position
                        if(w.getCells().contains(pos)){
                            if(w_id >= 0 && w_id < inter.getNb_ways().get(Flow.IN, c)){
                                if(c.getFront() == goal){
                                    way = w;
                                    ok = true;
                                }
                            }
                            else if(w_id == inter.getNb_ways().get(Flow.IN, c)){
                                if(c.getRight() == goal){
                                    way = w;
                                    ok = true;
                                }
                            }
                            else{
                                if(c.getLeft() == goal){
                                    way = w;
                                    ok = true;
                                }
                            }
                        }
                    }
                    if(ok) break;
                }
                
                //Get the first cell of the neighbor.
                if(i_body.getNeighbors().containsKey(goal)){
                    Infrastructure neighbor = i_body.getNeighbors().get(goal).getInfrastructure();
                    Way w = neighbor.getWays().get(goal.getFront(), w_id);
                    if(w != null && !w.isEmpty()){
                        Cell next = w.getCells().get(0);
                        if(next != null && way != null)
                            way.addCell(next);
                    }
                }
                
                //Send the way to the vehicle
                this.body.sendMessage(new M_Welcome(this.id, m.getSender_id(), way));
            }
        }
        else
            super.processMessage(mess);
    }

    @Override
    public void run() {
        //Process all the messages
        while(!this.messages_memory.isEmpty()){
            processMessage(this.messages_memory.get(0));
            this.messages_memory.remove(0);
        }
    }
    
}
