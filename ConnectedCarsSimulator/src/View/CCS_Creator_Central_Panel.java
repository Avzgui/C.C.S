/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Environment.Infrastructure;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_Central_Panel extends JPanel{

    private final CCS_Creator_View view;
    private final Table<Integer, Integer, CCS_Creator_Central_Panel_Button> map;
    private Infrastructure default_infrastructure;
    private int cell_size;
    
    /**
     * 
     * @param view
     * @param cell_size 
     */
    CCS_Creator_Central_Panel(CCS_Creator_View view, int cell_size, Infrastructure infrastructure){
        super();
        
        //Init link to the view
        this.view = view;
        
        //Init cell size
        this.cell_size = cell_size;
        
        //Init the default infrastructure
        this.default_infrastructure = infrastructure;
        
        //Init the map
        this.map = TreeBasedTable.create();
        
        //Init properties
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        int width = this.default_infrastructure.getWidth()*this.cell_size;
        int height = this.default_infrastructure.getHeight()*this.cell_size;
        int pos_x = 400-width/2;
        int pos_y = 300-height/2;
        this.addInfrastructure(pos_x, pos_y, 0, 0);
    }

    public CCS_Creator_View getView() {
        return view;
    }

    public Table<Integer, Integer, CCS_Creator_Central_Panel_Button> getMap() {
        return map;
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
    
    public void addInfrastructure(int x, int y, int x_map, int y_map){
        int width = this.default_infrastructure.getWidth()*this.cell_size;
        int height = this.default_infrastructure.getHeight()*this.cell_size;
        
        //Place the intersection
        if(!this.map.contains(x_map, y_map)){
            JButton button = new CCS_Creator_Central_Panel_Button(x_map, y_map, x, y, this.default_infrastructure, this);
            this.add(button);
            this.map.put(x_map, y_map, (CCS_Creator_Central_Panel_Button) button);
        }
        
        if(!this.map.contains(x_map, y_map-1)){
            JButton buttonNorth = new CCS_Creator_Central_Panel_Button(x_map, y_map-1, x, y-height, width, height, this);
            this.add(buttonNorth);
            this.map.put(x_map, y_map-1, (CCS_Creator_Central_Panel_Button) buttonNorth);
        }
        
        if(!this.map.contains(x_map+1, y_map)){
            JButton buttonEast = new CCS_Creator_Central_Panel_Button(x_map+1, y_map, x+width, y, width, height, this);
            this.add(buttonEast);
            this.map.put(x_map+1, y_map, (CCS_Creator_Central_Panel_Button) buttonEast);
        }
        
        if(!this.map.contains(x_map, y_map+1)){
            JButton buttonSouth = new CCS_Creator_Central_Panel_Button(x_map, y_map+1, x, y+height, width, height, this);
            this.add(buttonSouth);
            this.map.put(x_map, y_map+1, (CCS_Creator_Central_Panel_Button) buttonSouth);
        }
        
        if(!this.map.contains(x_map-1, y_map)){
            JButton buttonWest = new CCS_Creator_Central_Panel_Button(x_map-1, y_map, x-width, y, width, height, this);
            this.add(buttonWest);
            this.map.put(x_map-1, y_map, (CCS_Creator_Central_Panel_Button) buttonWest);
        }
    }
}
