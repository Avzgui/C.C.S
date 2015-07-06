/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

import Utility.CardinalPoint;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class Road extends Infrastructure{

    /**
     * Road's Constructor
     * @param x
     * @param y 
     */
    public Road(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell getCellForAnotherInfrastructure(CardinalPoint position, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
