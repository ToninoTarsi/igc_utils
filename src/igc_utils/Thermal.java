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

    private List<InFlightPosition> positions = new ArrayList<>();
    private List<Point> centers = new ArrayList<>();
    private double[] minLift, maxLift, averageLift, minLiftInt, maxLiftInt, averageLiftInt;

    public Thermal() {

    }

    public Thermal(List<InFlightPosition> pos) {
        positions = pos;
        minLift = new double[2];
        maxLift = new double[2];
        averageLift = new double[2];
        minLiftInt = new double[2];
        maxLiftInt = new double[2];
        averageLiftInt = new double[2];
        initThermal();
    }

    public void initThermal() {
        if (positions.get(0) != null) {
            InFlightPosition p0 = positions.get(0);
            minLift[0] = p0.getLiftQnh();
            minLift[1] = p0.getLiftGps();
            maxLift[0] = p0.getLiftQnh();
            maxLift[1] = p0.getLiftGps();
            minLiftInt[0] = p0.getLiftIntQnh();
            minLiftInt[1] = p0.getLiftIntGps();
            maxLiftInt[0] = p0.getLiftIntQnh();
            maxLiftInt[1] = p0.getLiftIntGps();
        }
        for (InFlightPosition ifp : positions) {
            if (minLift[0] > ifp.getLiftQnh()) {
                minLift[0] = ifp.getLiftQnh();
            }
            if (minLift[1] > ifp.getLiftGps()) {
                minLift[1] = ifp.getLiftGps();
            }
            if (maxLift[0] < ifp.getLiftQnh()) {
                maxLift[0] = ifp.getLiftQnh();
            }
            if (maxLift[1] < ifp.getLiftGps()) {
                maxLift[1] = ifp.getLiftGps();
            }
            if (minLiftInt[0] > ifp.getLiftIntQnh()) {
                minLiftInt[0] = ifp.getLiftIntQnh();
            }
            if (minLiftInt[1] > ifp.getLiftIntGps()) {
                minLiftInt[1] = ifp.getLiftIntGps();
            }
            if (maxLiftInt[0] < ifp.getLiftIntQnh()) {
                maxLiftInt[0] = ifp.getLiftIntQnh();
            }
            if (maxLiftInt[1] < ifp.getLiftIntGps()) {
                maxLiftInt[1] = ifp.getLiftIntGps();
            }
            averageLift[0] += ifp.getLiftQnh();
            averageLift[1] += ifp.getLiftGps();
            averageLiftInt[0] += ifp.getLiftIntQnh();
            averageLiftInt[1] += ifp.getLiftIntGps();

            if (positions.indexOf(ifp) > 2) {

                centers.add(Point.calcCenter(ifp.getPoint(), ifp.getPrev().getPoint(), ifp.getPrev().getPrev().getPoint()));
            }

        }
        averageLift[0] /= positions.size();
        averageLift[1] /= positions.size();
        averageLiftInt[0] /= positions.size();
        averageLiftInt[1] /= positions.size();
    }

    public List<ThermalCenterPoint> getCentersByEvaluation(int numberOfPoints) {
        List<ThermalCenterPoint> centers = new ArrayList<>();

        double x = 0.0, y = 0.0;

        for (InFlightPosition ifp : positions) {
            x += ifp.getPoint().getLon();
            y += ifp.getPoint().getLat();
            System.out.println(ifp.getPoint());
            if (positions.indexOf(ifp) % numberOfPoints == 0 && positions.indexOf(ifp) > 0) {
                x /= numberOfPoints;
                y /= numberOfPoints;
                System.out.println(x+"|"+y);
                centers.add(new ThermalCenterPoint(y, x));
            }
        }

        return centers;
    }

    public List<InFlightPosition> getPositions() {
        return positions;
    }

    public List<Point> getCenters() {
        return centers;
    }

    public double[] getMinLift() {
        return minLift;
    }

    public double[] getMaxLift() {
        return maxLift;
    }

    public double[] getAverageLift() {
        return averageLift;
    }

    public double[] getMinLiftInt() {
        return minLiftInt;
    }

    public double[] getMaxLiftInt() {
        return maxLiftInt;
    }

    public double[] getAverageLiftInt() {
        return averageLiftInt;
    }

    public String toString() {
        return "----------\nSteigen nach QNH:\nMinimal: " + minLift[0] + "\nMaximal: " + maxLift[0] + "\nDurchscnittlich:\nNormal: " + averageLift[0] + "\nIntegriert: " + averageLiftInt[0] + "\nSteigen nach GPS\nMinimal: " + minLift[1] + "\nMaximal: " + maxLift[1] + "\nDurchscnittlich:\nNormal: " + averageLift[1] + "\nIntegriert: " + averageLiftInt[1];
    }

}
