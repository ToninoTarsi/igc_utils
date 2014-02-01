/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package igc_utils.enumerations;

/**
 *
 * @author jfrese
 */
public enum GpsDirection {
    
    WEST("W"),
    
    EAST("E"),
    
    NORTH("N"),
    
    SOUTH("S");
    
    private final String dir;
    
    GpsDirection(String s){
        dir = s;
    }
    
    public String getDir(){
        return dir;
    }
}
