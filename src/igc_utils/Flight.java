/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jfrese
 */
public class Flight {

    private String name;
    private Date start, end;
    private double averageLift, maxLift, minLift;
    private List<String> fileContent, bRecs;
    private List<InFlightPosition> flightPositions;

    public Flight(List<String> fC, String n) {
        fileContent = fC;
        bRecs = new ArrayList<>();
        flightPositions = new ArrayList<>();
        name = n;
        start = new Date(0);
        end = new Date(0);
        initFlight();
    }

    private void initFlight() {
        for (String line : fileContent) {
            if (line.charAt(0) == 'B') {
                bRecs.add(line);
            }
        }
        start.setTime(IgcDateParser.parseNameToDate(name).getTime() + IgcDateParser.parseBRecToDate(bRecs.get(0)).getTime());
        end.setTime(IgcDateParser.parseNameToDate(name).getTime() + IgcDateParser.parseBRecToDate(bRecs.get(bRecs.size() - 1)).getTime());

        for (String line : bRecs) {
            flightPositions.add(new InFlightPosition(line));
            if (flightPositions.size() > 1) {
                flightPositions.get(flightPositions.size() - 1).setPrev(flightPositions.get(flightPositions.size() - 2));
                flightPositions.get(flightPositions.size() - 2).setNext(flightPositions.get(flightPositions.size() - 1));
            }
        }
    }

    public List<Thermal> getThermals(ThermalPattern tp) {
        List<Thermal> thermals = new ArrayList<>();
        List<InFlightPosition> temp = new ArrayList<>();
        int counter = 0, counter2 = 0;
        for (InFlightPosition ifp : flightPositions) {

            if ((ifp.getLiftIntGps() > 0 || ifp.getLiftIntQnh() > 0) && ifp.getPrev() != null) {
                if (counter < tp.getAnzLogs()) {
                    counter++;
                }
                if (counter2 != 0) {
                    counter2 = 0;
                }
            } else {
                if (counter != 0) {
                    counter2++;
                    if (counter2 == tp.getAnzLogs() &&(tp.isLiftOnly() ||(flightPositions.get(flightPositions.indexOf(ifp)).getPoint().calcDistance(flightPositions.get(flightPositions.indexOf(ifp)-8).getPoint()))*1000 >= 500)) {
                        counter = 0;
                        counter2 = 0;
                        if (temp.size() > 0) {
                            for (int a = 0; a < tp.getAnzLogs(); a++) {
                                temp.remove(temp.size() - 1);
                            }
                            for (InFlightPosition tempPos : temp) {
                                tempPos.setThermaling(true);
                            }
                            thermals.add(new Thermal(temp));
                            temp = new ArrayList<>();
                        }
                    }
                }
            }

            if (counter == tp.getAnzLogs() && (tp.isLiftOnly() || (flightPositions.get(flightPositions.indexOf(ifp)).getPoint().calcDistance(flightPositions.get(flightPositions.indexOf(ifp)-8).getPoint()))*1000 <= 500)) {
                if (temp.isEmpty()) {
                    InFlightPosition morePrev = ifp.getPrev();
                    for (int a = 0; a < tp.getAnzLogs() && morePrev != null; a++, morePrev = morePrev.getPrev()) {
                        temp.add(0, morePrev);
                    }
                }
                temp.add(ifp);
            }

        }
        return thermals;
    }

    public List<Thermal> getThermals() {
        return getThermals(ThermalPattern.STANDARD);
    }

    public String getName() {
        return name;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public double getAverageLift() {
        return averageLift;
    }

    public double getMaxLift() {
        return maxLift;
    }

    public double getMinLift() {
        return minLift;
    }

    public List<String> getbRecs() {
        return bRecs;
    }

    public List<InFlightPosition> getFlightPositions() {
        return flightPositions;
    }

    @Override
    //Update with relevant Information later
    public String toString() {
        return "Von " + start + " bis " + end + "\n" + name;
    }
}
