/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utility.CardinalPoint;
import Utility.Flow;
import Model.Environment.Intersection;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
    private final JLabel name;
    private int x, y;
    
    /**
     * 
     * @param view 
     */
    public CCS_Creator_Left_Panel(final CCS_Creator_View view){
        //Init link to the view
        this.view = view;
        
        //Init elements
        this.x = 0;
        this.y = 0;
        this.nb_ways = HashBasedTable.create();
        this.ways_size = HashBasedTable.create();
        this.indonesian_cross = new JCheckBox("Indonesian Cross");
        this.indonesian_cross.setSelected(true);
        this.indonesian_cross.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JCheckBox check = (JCheckBox) ae.getSource();
                Intersection inter = (Intersection) view.getCentral_panel().getMap().get(x, y).getInfrastructure();
                inter.setIndonesian_cross(check.isSelected());
                view.getCentral_panel().getMap().get(x, y).repaint();
            }
        });
        this.name = new JLabel("Intersection [0, 0]");
        
        //Set propertities
        this.setPreferredSize(new Dimension(200, 600));
        
        JSpinner spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.IN, CardinalPoint.NORTH, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.IN, CardinalPoint.NORTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.IN, CardinalPoint.SOUTH, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.IN, CardinalPoint.SOUTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.IN, CardinalPoint.WEST, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.IN, CardinalPoint.WEST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.IN, CardinalPoint.EAST, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.IN, CardinalPoint.EAST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.OUT, CardinalPoint.NORTH, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.OUT, CardinalPoint.NORTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.OUT, CardinalPoint.SOUTH, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.OUT, CardinalPoint.SOUTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.OUT, CardinalPoint.WEST, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.OUT, CardinalPoint.WEST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(3, 0, 5, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewNb_Ways(Flow.OUT, CardinalPoint.EAST, (int) spin.getValue());
            }
        });
        this.nb_ways.put(Flow.OUT, CardinalPoint.EAST, spin);
        
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.IN, CardinalPoint.NORTH, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.IN, CardinalPoint.NORTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.IN, CardinalPoint.SOUTH, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.IN, CardinalPoint.SOUTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.IN, CardinalPoint.WEST, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.IN, CardinalPoint.WEST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.IN, CardinalPoint.EAST, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.IN, CardinalPoint.EAST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.OUT, CardinalPoint.NORTH, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.OUT, CardinalPoint.NORTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.OUT, CardinalPoint.SOUTH, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.OUT, CardinalPoint.SOUTH, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.OUT, CardinalPoint.WEST, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.OUT, CardinalPoint.WEST, spin);
        
        spin = new JSpinner(new SpinnerNumberModel(8, 1, 15, 1));
        spin.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSpinner spin = (JSpinner) ce.getSource();
                view.getCentral_panel().getMap().get(x, y).setNewWays_Size(Flow.OUT, CardinalPoint.EAST, (int) spin.getValue());
            }
        });
        this.ways_size.put(Flow.OUT, CardinalPoint.EAST, spin);
        
        
        //Init Panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(this.name);
        this.add(this.indonesian_cross);
        this.add(new JLabel(CardinalPoint.NORTH + " " + Flow.IN));
        this.add(this.nb_ways.get(Flow.IN, CardinalPoint.NORTH));
        this.add(this.ways_size.get(Flow.IN, CardinalPoint.NORTH));
        this.add(new JLabel(CardinalPoint.NORTH + " " + Flow.OUT));
        this.add(this.nb_ways.get(Flow.OUT, CardinalPoint.NORTH));
        this.add(this.ways_size.get(Flow.OUT, CardinalPoint.NORTH));
        
        this.add(new JLabel(CardinalPoint.WEST + " " + Flow.IN));
        this.add(this.nb_ways.get(Flow.IN, CardinalPoint.WEST));
        this.add(this.ways_size.get(Flow.IN, CardinalPoint.WEST));
        this.add(new JLabel(CardinalPoint.WEST + " " + Flow.OUT));
        this.add(this.nb_ways.get(Flow.OUT, CardinalPoint.WEST));
        this.add(this.ways_size.get(Flow.OUT, CardinalPoint.WEST));
        
        this.add(new JLabel(CardinalPoint.EAST + " " + Flow.IN));
        this.add(this.nb_ways.get(Flow.IN, CardinalPoint.EAST));
        this.add(this.ways_size.get(Flow.IN, CardinalPoint.EAST));
        this.add(new JLabel(CardinalPoint.EAST + " " + Flow.OUT));
        this.add(this.nb_ways.get(Flow.OUT, CardinalPoint.EAST));
        this.add(this.ways_size.get(Flow.OUT, CardinalPoint.EAST));
        
        this.add(new JLabel(CardinalPoint.SOUTH + " " + Flow.IN));
        this.add(this.nb_ways.get(Flow.IN, CardinalPoint.SOUTH));
        this.add(this.ways_size.get(Flow.IN, CardinalPoint.SOUTH));
        this.add(new JLabel(CardinalPoint.SOUTH + " " + Flow.OUT));
        this.add(this.nb_ways.get(Flow.OUT, CardinalPoint.SOUTH));
        this.add(this.ways_size.get(Flow.OUT, CardinalPoint.SOUTH));
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param intersection 
     */
    public void setSelectedIntersection(int x, int y, Intersection intersection){
        
        //Set pos and label
        this.x = x;
        this.y = y;
        this.name.setText("Intersection [" + this.x + "," + this.y + "]");
        
        //Set checkbox
        this.indonesian_cross.setSelected(intersection.isIndonesian_cross());
        
        //Set nb_ways
        JSpinner spin = this.nb_ways.get(Flow.IN, CardinalPoint.NORTH);
        spin.setValue(intersection.getNb_ways().get(Flow.IN, CardinalPoint.NORTH));
        spin = this.nb_ways.get(Flow.OUT, CardinalPoint.NORTH);
        spin.setValue(intersection.getNb_ways().get(Flow.OUT, CardinalPoint.NORTH));
        
        spin = this.nb_ways.get(Flow.IN, CardinalPoint.WEST);
        spin.setValue(intersection.getNb_ways().get(Flow.IN, CardinalPoint.WEST));
        spin = this.nb_ways.get(Flow.OUT, CardinalPoint.WEST);
        spin.setValue(intersection.getNb_ways().get(Flow.OUT, CardinalPoint.WEST));
        
        spin = this.nb_ways.get(Flow.IN, CardinalPoint.EAST);
        spin.setValue(intersection.getNb_ways().get(Flow.IN, CardinalPoint.EAST));
        spin = this.nb_ways.get(Flow.OUT, CardinalPoint.EAST);
        spin.setValue(intersection.getNb_ways().get(Flow.OUT, CardinalPoint.EAST));
        
        spin = this.nb_ways.get(Flow.IN, CardinalPoint.SOUTH);
        spin.setValue(intersection.getNb_ways().get(Flow.IN, CardinalPoint.SOUTH));
        spin = this.nb_ways.get(Flow.OUT, CardinalPoint.SOUTH);
        spin.setValue(intersection.getNb_ways().get(Flow.OUT, CardinalPoint.SOUTH));
        
        //Set ways_size
        spin = this.ways_size.get(Flow.IN, CardinalPoint.NORTH);
        spin.setValue(intersection.getWays_size().get(Flow.IN, CardinalPoint.NORTH));
        spin = this.ways_size.get(Flow.OUT, CardinalPoint.NORTH);
        spin.setValue(intersection.getWays_size().get(Flow.OUT, CardinalPoint.NORTH));
        
        spin = this.ways_size.get(Flow.IN, CardinalPoint.WEST);
        spin.setValue(intersection.getWays_size().get(Flow.IN, CardinalPoint.WEST));
        spin = this.ways_size.get(Flow.OUT, CardinalPoint.WEST);
        spin.setValue(intersection.getWays_size().get(Flow.OUT, CardinalPoint.WEST));
        
        spin = this.ways_size.get(Flow.IN, CardinalPoint.EAST);
        spin.setValue(intersection.getWays_size().get(Flow.IN, CardinalPoint.EAST));
        spin = this.ways_size.get(Flow.OUT, CardinalPoint.EAST);
        spin.setValue(intersection.getWays_size().get(Flow.OUT, CardinalPoint.EAST));
        
        spin = this.ways_size.get(Flow.IN, CardinalPoint.SOUTH);
        spin.setValue(intersection.getWays_size().get(Flow.IN, CardinalPoint.SOUTH));
        spin = this.ways_size.get(Flow.OUT, CardinalPoint.SOUTH);
        spin.setValue(intersection.getWays_size().get(Flow.OUT, CardinalPoint.SOUTH));
    }
}
