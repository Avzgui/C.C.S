/*
 * Copyright (C) 2015 Antoine "Avzgui" Richard and collaborators
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package App;

import Model.CCS_Model;
import View.CCS_View;
import java.io.File;

/**
 * The main class of the application.
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
        model.loadEnvironmentFromXML(new File("save/environments/ASimpleIntersection.xml"));
        model.start();
        
        //Init view
        CCS_View view = new CCS_View(model, 15);
        view.start();
    }
}
