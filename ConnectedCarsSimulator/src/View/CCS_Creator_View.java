/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CCS_Creator_Model;
import Model.Environment.CardinalPoint;
import Model.Environment.Flow;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_View extends Thread {
    
    private final CCS_Creator_Model model;
    
    private final JFrame frame;
    private final CCS_Creator_Panel panel;
    private final JToolBar tools;
    
    private Table<Flow, CardinalPoint, Integer> intersection_nb_ways;
    private Table<Flow, CardinalPoint, Integer> intersection_ways_size;
    
    public CCS_Creator_View(int height, int width, CCS_Creator_Model model){
        super();
        
        //Init link to the model
        this.model = model;
        
        //Init datum for intersection
        this.intersection_nb_ways = HashBasedTable.create();
        int nb_ways = 3;
        this.intersection_nb_ways.put(Flow.IN, CardinalPoint.EAST, nb_ways);
        this.intersection_nb_ways.put(Flow.IN, CardinalPoint.WEST, nb_ways);
        this.intersection_nb_ways.put(Flow.IN, CardinalPoint.NORTH, nb_ways);
        this.intersection_nb_ways.put(Flow.IN, CardinalPoint.SOUTH, nb_ways);
        
        this.intersection_nb_ways.put(Flow.OUT, CardinalPoint.EAST, nb_ways);
        this.intersection_nb_ways.put(Flow.OUT, CardinalPoint.WEST, nb_ways);
        this.intersection_nb_ways.put(Flow.OUT, CardinalPoint.NORTH, nb_ways);
        this.intersection_nb_ways.put(Flow.OUT, CardinalPoint.SOUTH, nb_ways);
        
        this.intersection_ways_size = HashBasedTable.create();
        int way_size = 8;
        this.intersection_ways_size.put(Flow.IN, CardinalPoint.EAST, way_size);
        this.intersection_ways_size.put(Flow.IN, CardinalPoint.WEST, way_size);
        this.intersection_ways_size.put(Flow.IN, CardinalPoint.NORTH, way_size);
        this.intersection_ways_size.put(Flow.IN, CardinalPoint.SOUTH, way_size);
        
        this.intersection_ways_size.put(Flow.OUT, CardinalPoint.EAST, way_size);
        this.intersection_ways_size.put(Flow.OUT, CardinalPoint.WEST, way_size);
        this.intersection_ways_size.put(Flow.OUT, CardinalPoint.NORTH, way_size);
        this.intersection_ways_size.put(Flow.OUT, CardinalPoint.SOUTH, way_size);
        
        //Frame initialization
        this.frame = new JFrame("CCS Creator");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(height, width));
        this.frame.setResizable(true);
        
        //Panel initialization
        this.panel = new CCS_Creator_Panel(this, 10);
        
        //Make the central panel scrollable
        JScrollPane scrollPane = new JScrollPane(this.panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, height, width);
        
        //ToolBar initialization
        this.tools = new JToolBar();
        
        //Elements layout
        this.frame.setLayout(new BorderLayout());
        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(tools, BorderLayout.NORTH);
        
        //pack and show
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public CCS_Creator_Model getModel() {
        return model;
    }

    public JFrame getFrame() {
        return frame;
    }

    public CCS_Creator_Panel getPanel() {
        return panel;
    }

    public JToolBar getTools() {
        return tools;
    }

    public Table<Flow, CardinalPoint, Integer> getIntersection_nb_ways() {
        return intersection_nb_ways;
    }

    public Table<Flow, CardinalPoint, Integer> getIntersection_ways_size() {
        return intersection_ways_size;
    }
    
    @Override
    public void run(){
        while(true){

            try {
                //Every 100ms, repaint the attribute panel
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(CCS_Creator_View.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.panel.repaint();
        }
    }
}
