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
public enum ThermalEncounterLevel {
    LOW("low"),
    
    MEDIUM("medium"),
    
    HIGH("high");

    private final String string;
    
    ThermalEncounterLevel(String s) {
        string = s;
    }
    
    public String getString(){
        return string;
    }
    
    
}
