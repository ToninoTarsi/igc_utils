/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jfrese
 */
public class Thermal {

    List<InFlightPosition> positions = new ArrayList<>();
    List<Point> centers = new ArrayList<>();
    double maxLift = 0.0, minLift = 0.0, averageLift = 0.0, maxLiftInt = 0.0, minLiftInt = 0.0, averageLiftInt = 0.0;
    
    
    public Thermal(){
        
    }
    
    public Thermal(List<InFlightPosition> pos){
        positions = pos;
        initThermal();
    }
    
    public void initThermal(){
        
    }
    
    
    public String toString(){
        return ""+positions;
    }
    
}
