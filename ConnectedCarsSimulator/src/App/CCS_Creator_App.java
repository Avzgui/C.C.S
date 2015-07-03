/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import View.CCS_Creator_View;
import java.io.File;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Antoine "Avzgui" Richard
 */
public class CCS_Creator_App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CCS_Creator_View view = new CCS_Creator_View(800, 600);
        view.loadFromXML(new File("save/environments/Grid3x3.xml"));
    }
}
