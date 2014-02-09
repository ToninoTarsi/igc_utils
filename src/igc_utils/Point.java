/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

/**
 * @version 0.1
 * @author jfrese
 */
public class Point {

    double lat, lon;
    String latDeg, lonDeg;

    public enum GpsAxis {

        LAT, LON;
    }

    public Point() {
        lat = 0;
        lon = 0;
        latDeg = "";
        lonDeg = "";
    }

    public Point(double la, double lo) {
        lat = la;
        lon = lo;
        latDeg = toPartGPSCoord(lat, GpsAxis.LAT);
        lonDeg = toPartGPSCoord(lon, GpsAxis.LON);
    }

    public Point(String la, String lo) {
        latDeg = la;
        lonDeg = lo;
        lat = toDecimal(la);
        lon = toDecimal(lo);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getLatDeg() {
        return latDeg;
    }

    public String getLonDeg() {
        return lonDeg;
    }

    public double calcDistance(Point p) {
        double distance;

        double latTemp = Math.toRadians((lat + p.getLat()) / 2);

        double dx = 111.3 * Math.cos(latTemp) * (lon - p.getLon());
        double dy = 111.3 * (lat - p.getLat());

        distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.round(distance * 1000.0) / 1000.0;

        return distance;
    }

    public static double calcDistance(Point p1, Point p2) {
        double distance;

        double lat = Math.toRadians((p1.getLat() + p2.getLat()) / 2);

        double dx = 111.3 * Math.cos(lat) * (p1.getLon() - p2.getLon());
        double dy = 111.3 * (p1.getLat() - p2.getLat());

        distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.round(distance * 1000.0) / 1000.0;

        return distance;
    }

    public final ThermalCenterPoint calcCenter(Point p1, Point p2, Point p3) {
        ThermalCenterPoint center;
        double x1 = p1.getLon(), x2 = p2.getLon(), x3 = p3.getLon();
        double y1 = p1.getLat(), y2 = p2.getLat(), y3 = p3.getLat();

        double ym = (((Math.pow(x3, 2) - Math.pow(x1, 2) + Math.pow(y3, 2) - Math
                .pow(y1, 2)) * (x2 - x1) - ((Math.pow(x2, 2) - Math.pow(x1, 2)
                + Math.pow(y2, 2) - Math.pow(y1, 2)) * (x3 - x1)))
                / (2 * (((y3 - y1) * (x2 - x1)) - ((y2 - y1) * (x3 - x1)))));

        double xm = (((Math.pow(x2, 2) - Math.pow(x1, 2))
                + (Math.pow(y2, 2) - Math.pow(y1, 2)) - ((2 * ym) * (y2 - y1))) / (2 * (x2 - x1)));

        center = new ThermalCenterPoint(ym, xm);
        
        double r = calcDistance(center, p1);
        center.setRadius(r);
        
        return center;
    }

    public static double toDecimal(String coord) {
        double decimal = 0;
        int index = 0;
        String temp = "";

        if (coord.length() == 7) {
            temp = coord.substring(0, 2);
            index = 2;
        } else if (coord.length() == 8) {
            temp = coord.substring(0, 3);
            index = 3;
        }

        decimal += Float.parseFloat(temp);

        temp = coord.substring(index, index + 2);
        index += 2;

        decimal += Float.parseFloat(temp) / 60;

        temp = coord.substring(index, coord.length());
        index += 2;

        decimal += (Float.parseFloat(temp) / 60000);

        return decimal;
    }

    public static String toPartGPSCoord(double koord, GpsAxis axis) {
        String partGPSCoord = "";
        String temp;

        if (axis == GpsAxis.LAT) {
            temp = "" + (int) koord;
            while (temp.length() < 2) {
                temp = "0" + temp;
            }
        } else {
            temp = "" + (int) koord;
            while (temp.length() < 3) {
                temp = "0" + temp;
            }
        }

        koord = koord - (int) koord;
        koord *= 60;

        partGPSCoord += temp;

        partGPSCoord += "" + (int) koord;

        koord = koord - (int) koord;
        koord *= 1000;

        partGPSCoord += "" + (int) koord;

        return partGPSCoord;
    }

    @Override
    public String toString() {
        return lat + "|" + lon + "||" + latDeg + "|" + lonDeg;
    }
}
