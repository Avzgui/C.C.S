/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Environment.CardinalPoint;
import Model.Environment.Cell;
import Model.Environment.Flow;
import Model.Environment.Infrastructure;
import Model.Environment.Intersection;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Central_Panel_Button extends JButton implements ActionListener {
    
    private final CCS_Creator_Central_Panel container;
    private Infrastructure infrastructure;
    private int cell_size;
    private final int x_map;
    private final int y_map;
    
    /**
     * 
     * @param x_map
     * @param y_map
     * @param x
     * @param y
     * @param width
     * @param height
     * @param container
     */
    public CCS_Creator_Central_Panel_Button(int x_map, int y_map, int x, int y, int width, int height, CCS_Creator_Central_Panel container){
        //Init identificator
        this.x_map = x_map;
        this.y_map = y_map;

        //Initialize ActionListener
        this.addActionListener(this);

        //Link to the container
        this.container = container;
        
        //Set properties of the button
        this.cell_size = this.container.getCell_size();
        this.infrastructure = null;
        this.setBackground(Color.GREEN);
        this.setBounds(x, y, width, height);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param infrastructure
     * @param container 
     */
    public CCS_Creator_Central_Panel_Button(int x_map, int y_map, int x, int y, Infrastructure infrastructure, CCS_Creator_Central_Panel container){
        //Init identificator
        this.x_map = x_map;
        this.y_map = y_map;

        //Initialize ActionListener
        this.addActionListener(this);

        //Link to the container
        this.container = container;
        
        //Set properties of the button
        this.cell_size = this.container.getCell_size();
        if(infrastructure instanceof Intersection)
            this.infrastructure = new Intersection( (Intersection) infrastructure);
        //else for later
        this.setBackground(Color.GREEN);
        this.setBounds(x, y, this.infrastructure.getWidth()*this.cell_size, this.infrastructure.getHeight()*this.cell_size);
    }

    /**
     * 
     * @return 
     */
    public int getX_map() {
        return x_map;
    }

    /**
     * 
     * @return 
     */
    public int getY_map() {
        return y_map;
    }

    /**
     * 
     * @return 
     */
    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    /**
     * 
     * @param infrastructure 
     */
    public void setInfrastructure(Infrastructure infrastructure) {
        if(infrastructure instanceof Intersection)
            this.infrastructure = new Intersection((Intersection) infrastructure);
        //else for Roads
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param nb_ways
     */
    public void setNewNb_Ways(Flow flow, CardinalPoint point, int nb_ways){
        if(this.infrastructure instanceof Intersection){
            Intersection intersection = (Intersection) this.infrastructure;
            intersection.setNb_way(flow, point, nb_ways);
        }
        else{} //For the road case
        
        this.setBounds(this.getX(), this.getY(), this.infrastructure.getWidth()*this.cell_size, this.infrastructure.getHeight()*this.cell_size);
        repaint();
    }
    
    /**
     * 
     * @param flow
     * @param point
     * @param ways_size
     */
    public void setNewWays_Size(Flow flow, CardinalPoint point, int ways_size){
        if(this.infrastructure instanceof Intersection){
            Intersection intersection = (Intersection) this.infrastructure;
            intersection.setWay_Size(flow, point, ways_size);
        }
        else{} //For the road case
        
        this.setBounds(this.getX(), this.getY(), this.infrastructure.getWidth()*this.cell_size, this.infrastructure.getHeight()*this.cell_size);
        repaint();
    }

    /**
     * 
     * @return 
     */
    public int getCell_size() {
        return cell_size;
    }

    /**
     * 
     * @param cell_size 
     */
    public void setCell_size(int cell_size) {
        this.cell_size = cell_size;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(infrastructure != null){
            //Cast Graphics g to a Graphics2D object
            Graphics2D g2 = (Graphics2D) g;

            //Set the background of the panel
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());

            //Draw cells
            for(Cell cell : infrastructure.getCells()){
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(cell.getX()*cell_size - 1, cell.getY()*cell_size - 1, cell_size+2, cell_size+2);
            }
            for(Cell cell : infrastructure.getCells()){
                g2.setColor(Color.GRAY);
                g2.fillRect(cell.getX()*cell_size + 1, cell.getY()*cell_size + 1, cell_size - 2, cell_size - 2);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.infrastructure == null){
            if(this.container.getDefault_infrastructure() instanceof Intersection)
                this.infrastructure = new Intersection((Intersection) this.container.getDefault_infrastructure());
            this.container.addInfrastructure(this.getX(), this.getY(), this.x_map, this.y_map);
        }
        
        this.container.getView().getLeft_panel().setSelectedIntersection(this.x_map, this.y_map, (Intersection) this.infrastructure);
    }
}
