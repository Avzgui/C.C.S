/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_View extends Thread {
    
    private final JFrame frame;
    private final JPanel panel;
    private final JToolBar tools;
    
    public CCS_View(int height, int width){
        super();
        
        //Frame initialization
        this.frame = new JFrame("Connected Cars Simulator");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(height, width));
        this.frame.setResizable(true);
        
        //Panel initialization
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(1000, 1000));
        this.panel.setDoubleBuffered(true);
        this.panel.setLayout(null);
        
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
        
        //pack and show
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    @Override
    public void run(){
    }
}
