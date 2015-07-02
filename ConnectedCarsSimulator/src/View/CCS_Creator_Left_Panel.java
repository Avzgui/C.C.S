/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Environment.CardinalPoint;
import Model.Environment.Flow;
import Model.Environment.Intersection;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author avzgui
 */
public class CCS_Creator_Left_Panel extends JPanel {
    
    //Elements
    private final CCS_Creator_View view;
    private final Table<Flow, CardinalPoint, JSpinner> nb_ways;
    private final Table<Flow, CardinalPoint, JSpinner> ways_size;
    private final JCheckBox indonesian_cross;
    private int x;
    private int y;
    
    /**
     * 
     * @param view 
     */
    public CCS_Creator_Left_Panel(CCS_Creator_View view){
        //Init link to the view
        this.view = view;
        
        //Init elements
        this.nb_ways = HashBasedTable.create();
        this.ways_size = HashBasedTable.create();
        this.indonesian_cross = new JCheckBox("Indonesian ? ");
        
        //Set propertities
        this.x = -1;
        this.y = -1;
        this.setPreferredSize(new Dimension(300, 600));
        
        SpinnerModel spin_model = new SpinnerNumberModel(3, 1, 5, 1);
        this.nb_ways.put(Flow.IN, CardinalPoint.NORTH, new JSpinner(spin_model));
        this.nb_ways.put(Flow.IN, CardinalPoint.SOUTH, new JSpinner(spin_model));
        this.nb_ways.put(Flow.IN, CardinalPoint.WEST, new JSpinner(spin_model));
        this.nb_ways.put(Flow.IN, CardinalPoint.EAST, new JSpinner(spin_model));
        
        this.nb_ways.put(Flow.OUT, CardinalPoint.NORTH, new JSpinner(spin_model));
        this.nb_ways.put(Flow.OUT, CardinalPoint.SOUTH, new JSpinner(spin_model));
        this.nb_ways.put(Flow.OUT, CardinalPoint.WEST, new JSpinner(spin_model));
        this.nb_ways.put(Flow.OUT, CardinalPoint.EAST, new JSpinner(spin_model));
        
        spin_model = new SpinnerNumberModel(8, 1, 10, 1);
        this.ways_size.put(Flow.IN, CardinalPoint.NORTH, new JSpinner(spin_model));
        this.ways_size.put(Flow.IN, CardinalPoint.SOUTH, new JSpinner(spin_model));
        this.ways_size.put(Flow.IN, CardinalPoint.WEST, new JSpinner(spin_model));
        this.ways_size.put(Flow.IN, CardinalPoint.EAST, new JSpinner(spin_model));
        
        this.ways_size.put(Flow.OUT, CardinalPoint.NORTH, new JSpinner(spin_model));
        this.ways_size.put(Flow.OUT, CardinalPoint.SOUTH, new JSpinner(spin_model));
        this.ways_size.put(Flow.OUT, CardinalPoint.WEST, new JSpinner(spin_model));
        this.ways_size.put(Flow.OUT, CardinalPoint.EAST, new JSpinner(spin_model));
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param intersection 
     */
    public void setSelectedIntersection(int x, int y, Intersection intersection){
        this.x = x;
        this.y = y;
    }
    
}
