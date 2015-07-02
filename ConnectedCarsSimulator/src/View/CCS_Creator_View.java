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
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_View{
    
    private final JFrame frame;
    private final CCS_Creator_Central_Panel central_panel;
    private final CCS_Creator_Left_Panel left_panel;
    private final JToolBar tools;
    
    public CCS_Creator_View(int height, int width){
        super();
        
        //Init datum for intersection
        Table<Flow, CardinalPoint, Integer> nb_ways = HashBasedTable.create();
        int nb = 3;
        nb_ways.put(Flow.IN, CardinalPoint.EAST, nb);
        nb_ways.put(Flow.IN, CardinalPoint.WEST, nb);
        nb_ways.put(Flow.IN, CardinalPoint.NORTH, nb);
        nb_ways.put(Flow.IN, CardinalPoint.SOUTH, nb);
        
        nb_ways.put(Flow.OUT, CardinalPoint.EAST, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.WEST, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.NORTH, nb);
        nb_ways.put(Flow.OUT, CardinalPoint.SOUTH, nb);
        
        Table<Flow, CardinalPoint, Integer> ways_size = HashBasedTable.create();
        int size = 8;
        ways_size.put(Flow.IN, CardinalPoint.EAST, size);
        ways_size.put(Flow.IN, CardinalPoint.WEST, size);
        ways_size.put(Flow.IN, CardinalPoint.NORTH, size);
        ways_size.put(Flow.IN, CardinalPoint.SOUTH, size);
        
        ways_size.put(Flow.OUT, CardinalPoint.EAST, size);
        ways_size.put(Flow.OUT, CardinalPoint.WEST, size);
        ways_size.put(Flow.OUT, CardinalPoint.NORTH, size);
        ways_size.put(Flow.OUT, CardinalPoint.SOUTH, size);
        
        Intersection intersection = new Intersection(0, 0, nb_ways, ways_size, true);
        
        //Frame initialization
        this.frame = new JFrame("CCS Creator");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(height, width));
        this.frame.setResizable(true);
        
        //Left Panel initialization
        this.left_panel = new CCS_Creator_Left_Panel(this);
        
        //Central Panel initialization
        this.central_panel = new CCS_Creator_Central_Panel(this, 5, intersection);
        
        //Make the central panel scrollable
        JScrollPane scrollPane = new JScrollPane(this.central_panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, height, width);

        //ToolBar initialization
        this.tools = new JToolBar();
        
        //Elements layout
        this.frame.setLayout(new BorderLayout());
        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(this.left_panel, BorderLayout.WEST);
        this.frame.add(tools, BorderLayout.NORTH);
        
        //pack and show
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public CCS_Creator_Central_Panel getCentral_panel() {
        return central_panel;
    }

    public CCS_Creator_Left_Panel getLeft_panel() {
        return left_panel;
    }

    public JToolBar getTools() {
        return tools;
    }
}
