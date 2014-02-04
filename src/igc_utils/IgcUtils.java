/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import igc_utils.exceptions.NoIgcFileException;
import igc_utils.helperWindows.StdFlightWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class exists ONLY for testing during the developement process.
 *
 * @author jfrese
 */
public class IgcUtils {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IgcFile iFile;
        try {
            iFile = new IgcFile("341dsq71.igc");
            Flight flight = iFile.getFligth();
            StdFlightWindow sfw = new StdFlightWindow(1680, 1050);
            sfw.drawFlight(flight, ThermalPattern.STANDARD);
            for (int a = 0; a < flight.getThermals().size(); a++) {
                System.out.println(flight.getThermals().get(a));
            }
        } catch (NoIgcFileException ex) {
            Logger.getLogger(IgcUtils.class.getName()).log(Level.SEVERE, "No valid Igc-File. " + ex.getPathName() + " must contain '.igc'.", ex);
        }
    }

}
