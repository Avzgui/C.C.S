/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Way {
    
    private ArrayList<Cell> cells;

    /**
     * Way's Constructor
     */
    public Way() {
        this.cells = new ArrayList<>();
    }
    
    /**
     * Way's copy constructor
     * @param other 
     */
    public Way(Way other){
        this.cells = new ArrayList<>();
        for(Cell cell : other.getCells())
            this.cells.add(new Cell(cell));
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * 
     * @param cells 
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * 
     * @param cell 
     */
    public void addCell(Cell cell){
        if(!this.cells.contains(cell))
            this.cells.add(cell);
    }
    
    /**
     * 
     * @param cell 
     */
    public void removeCell(Cell cell){
       if(this.cells.contains(cell))
           this.cells.remove(cell);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.cells);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Way other = (Way) obj;
        if (!Objects.equals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }
}
