/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CCS_Model;
import Model.Environment.Cell;
import Model.Environment.Environment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_View extends Thread {
    
    private final CCS_Model model;
    private final JPanel central_panel;
    private int cell_size;
    
    /**
     * 
     * @param model 
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
          }
        };
        this.central_panel.setPreferredSize(new Dimension(800, 600));
        
        //Init Jframe
        JFrame frame = new JFrame("C.C.S : Connected Cars' Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(true);
        
        //add components
        frame.setLayout(new BorderLayout());
        frame.add(this.central_panel, BorderLayout.CENTER);
        
        //Pack and show
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void run(){
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(CCS_View.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.central_panel.repaint();
    }
}
