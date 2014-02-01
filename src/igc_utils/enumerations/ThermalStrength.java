/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils.enumerations;

/**
 *
 * @author Jan
 */
public enum ThermalStrength {
    
    WEAK("weak"),
    
    MODERAT("moderat"),
    
    STRONG("strong"),
    
    EXTREME("extreme");

    private final String string;
    

    ThermalStrength(String s){
        string = s;
    }
    
    public String getString(){
        return string;
    }
}
