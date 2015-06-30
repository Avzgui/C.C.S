/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Environment;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public enum Flow {
    IN,
    OUT;
    
    /**
     * 
     * @return 
     */
    public String toString(){
        switch(this){
            case IN :
                return "IN";
            case OUT :
                return "OUT";
        }
        return null;
    } 
}
