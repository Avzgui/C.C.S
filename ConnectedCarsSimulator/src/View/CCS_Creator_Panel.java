/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CCS_Creator_Model;
import Model.Environment.Cell;
import Model.Environment.Infrastructure;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author avzgui
 */
public class CCS_Creator_Panel extends JPanel implements MouseListener, MouseMotionListener {

    private final CCS_Creator_View view;
    
    private int cell_size;
    
    /**
     * 
     * @param view
     * @param cell_size 
     */
    CCS_Creator_Panel(CCS_Creator_View view, int cell_size){
        super();
        
        //Initialise as a mouse listener
        addMouseMotionListener(this);
        addMouseListener(this);
        
        //Init link to the view
        this.view = view;
        
        //Init cell size
        this.cell_size = cell_size;
        
        setPreferredSize(new Dimension(1000, 1000));
        setDoubleBuffered(true);
        setLayout(null);
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
    public void paintComponent(Graphics g) {
        
        //Cast Graphics g to a Graphics2D object
        Graphics2D g2 = (Graphics2D) g;
        
        //For each Infrastructure
        for(Infrastructure infrastructure : this.view.getModel().getEnvironment().values()){
            //For each cell
            for(Cell cell : infrastructure.getCells()){
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(cell.getX()*this.cell_size + 1, cell.getY()*this.cell_size + 1, this.cell_size - 2, this.cell_size - 2);
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }   

    @Override
    public void mouseClicked(MouseEvent me) {
        
        CCS_Creator_Model model = this.view.getModel();
        model.addIntersection(me.getX() / this.cell_size, me.getY() / this.cell_size, 
                                this.view.getIntersection_nb_ways(),
                                this.view.getIntersection_ways_size());
        
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
