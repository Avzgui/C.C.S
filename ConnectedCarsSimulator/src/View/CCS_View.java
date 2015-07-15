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

package View;

import Model.Agents.Bodies.Vehicle_Body;
import Model.CCS_Model;
import Model.Environment.Cell;
import Model.Environment.Environment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The view class of the MVC design.
 * 
 * @author Antoine "Avzgui" Richard
 */
public class CCS_View extends Thread {
    
    private final CCS_Model model;
    private final JFrame frame;
    private final JPanel central_panel;
    private final int cell_size;
    /**
     * Constructor
     * 
     * @param model link to the model.
     * @param cell_size size of the cells to draw.
     */
    public CCS_View(final CCS_Model model, final int cell_size){
        
        //Init link to the model
        this.model = model;

        //Init cells' size
        this.cell_size = cell_size;
        
        //Init central panel
        this.central_panel = new JPanel(){
          @Override
          public void paintComponent(Graphics g){
              
              //Cast Graphics g to a Graphics2D object
              Graphics2D g2 = (Graphics2D) g;

              //Set the background of the panel
              g2.setColor(Color.LIGHT_GRAY);
              g2.fillRect(0, 0, this.getWidth(), this.getHeight());
              
              //Get the environment
              Environment env = model.getEnvironment();
              int mid_x = this.getWidth() / 3;
              int mid_y = this.getHeight() / 3;

              //For each cells in the environment
              for(Cell cell : env.getCells()){
                  g2.setColor(Color.DARK_GRAY);
                  g2.fillRect(mid_x + cell.getX()*cell_size - 1, mid_y + cell.getY()*cell_size - 1, cell_size+2, cell_size+2);
              }
              for(Cell cell : env.getCells()){
                  g2.setColor(Color.GRAY);
                  g2.fillRect(mid_x + cell.getX()*cell_size + 1, mid_y + cell.getY()*cell_size + 1, cell_size - 2, cell_size - 2);
              }
              
              //Paint the vehicules
              Random rand = new Random();
              for(Vehicle_Body body : env.getVehicles()){
                  g2.setColor(new Color(body.getId()));
                  g2.fillOval(mid_x + body.getPosition().getX()*cell_size + 1, mid_y + body.getPosition().getY()*cell_size + 1, cell_size - 2, cell_size - 2);
              }
              
              //Paint the infrastructure agents
              for(Cell cell : env.getInfrastructuresPosition()){
                  g2.setColor(Color.BLUE);
                  g2.fillRect(mid_x + cell.getX()*cell_size, mid_y + cell.getY()*cell_size, cell_size, cell_size);
              }
              
              //Paint ticks
              g2.setColor(Color.RED);
              g2.drawString("tick : "+model.getTicks(), 10, 20);
          }
        };
        this.central_panel.setPreferredSize(new Dimension(800, 600));
        
        //Init Jframe
        this.frame = new JFrame("C.C.S : Connected Cars' Simulator");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(800, 600));
        this.frame.setResizable(true);
        
        //add components
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.central_panel, BorderLayout.CENTER);
        
        //Pack and show
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    @Override
    public void run(){
        
        while(this.model.getCollision() == 0){
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(CCS_View.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Repaint the central panel
            this.central_panel.repaint();
            
            //Check the state of collision
            switch(this.model.getCollision()){
                case 1:
                    JOptionPane.showMessageDialog(this.frame,
                            "Two Vehicles in the same cell !!",
                            "Crash !!",
                            JOptionPane.ERROR_MESSAGE);
                break;
                case 2:
                    JOptionPane.showMessageDialog(this.frame,
                            "Several vehicles are locked each other !!",
                            "Crash !!",
                            JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
}
