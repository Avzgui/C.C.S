/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Environment.Cell;
import Model.Environment.Infrastructure;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Panel_Button extends JButton implements ActionListener {
    
    private final CCS_Creator_Panel container;
    private Infrastructure infrastructure;
    private int cell_size;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param container
     */
    public CCS_Creator_Panel_Button(int x, int y, int width, int height, CCS_Creator_Panel container){
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
        this.infrastructure = infrastructure;
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
        System.out.println("Clic !");
        if(this.infrastructure == null){
            System.out.println("Paint infrastructure !");
            this.infrastructure = this.container.getDefault_infrastructure();
        }
    }
}
