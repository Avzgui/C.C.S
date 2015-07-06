/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Model.CCS_Model;
import View.CCS_View;
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
        //Init model
        CCS_Model model = new CCS_Model();
        model.loadEnvironmentFromXML(new File("save/environments/Grid3x3.xml"));
        
        //Init view
        CCS_View view = new CCS_View(model, 8);
        view.start();
    }
}
