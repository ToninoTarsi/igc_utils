/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

/**
 *
 * @author jfrese
 */
public class ThermalPattern {

    public static final ThermalPattern STANDARD = new ThermalPattern(8, false);
    
    public static final ThermalPattern LIFTONLY = new ThermalPattern(8, true);
    
    private int anzLogs;
    
    private int maxThermalSpeed=60;
    
    private boolean liftOnly;
    
    public ThermalPattern(int anzLogs, boolean liftOnly) {
        this.anzLogs = anzLogs;
        this.liftOnly = liftOnly;
    }

    public int getAnzLogs() {
        return anzLogs;
    }

    public boolean isLiftOnly() {
        return liftOnly;
    }

    public int getMaxThermalSpeed() {
        return maxThermalSpeed;
    }

    public void setMaxThermalSpeed(int maxThermalSpeed) {
        this.maxThermalSpeed = maxThermalSpeed;
    }
    
}
