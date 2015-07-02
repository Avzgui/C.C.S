/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Environment.Infrastructure;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Panel extends JPanel{

    private final CCS_Creator_View view;
    
    private Infrastructure default_infrastructure;
    private int cell_size;
    
    /**
     * 
     * @param view
     * @param cell_size 
     */
    CCS_Creator_Panel(CCS_Creator_View view, int cell_size, Infrastructure infrastructure){
        super();
        
        //Init link to the view
        this.view = view;
        
        //Init cell size
        this.cell_size = cell_size;
        
        //Init the default infrastructure
        this.default_infrastructure = infrastructure;
        
        //Init properties
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        int width = this.default_infrastructure.getWidth()*this.cell_size;
        int height = this.default_infrastructure.getHeight()*this.cell_size;
        JButton button = new CCS_Creator_Panel_Button(10, 10, width, height, this);
        this.add(button);
        
        //Intersection initial in the center
        /*CCS_Creator_Model model = this.view.getModel();
        model.addIntersection(400 / this.cell_size,
                                300 / this.cell_size, 
                                this.view.getIntersection_nb_ways(),
                                this.view.getIntersection_ways_size(), true);*/
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

    /**
     * 
     * @return 
     */
    public Infrastructure getDefault_infrastructure() {
        return default_infrastructure;
    }

    /**
     * 
     * @param default_infrastructure 
     */
    public void setDefault_infrastructure(Infrastructure default_infrastructure) {
        this.default_infrastructure = default_infrastructure;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        //Cast Graphics g to a Graphics2D object
        Graphics2D g2 = (Graphics2D) g;
        
        //Set the background of the panel
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
