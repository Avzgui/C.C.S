/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Model.CCS_Model;
import java.io.File;

/**
 *
 * @author Antoine "Avzgui" Richard
 */
public class CCS_App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CCS_Model model = new CCS_Model();
        model.loadEnvironmentFromXML(new File("save/environments/Grid3x3.xml"));
    }
}
