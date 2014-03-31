/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import igc_utils.enumerations.GpsDirection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jfrese
 */
public class InFlightPosition {

	private Point point;
	private int qnhAlt, gpsAlt;
	private Date time;
	private InFlightPosition prev, next;
	private GpsDirection lat, lon;
	private double liftQnh = 0.0, liftIntQnh = 0.0, liftGps = 0.0, liftIntGps = 0.0, heading, distToNext;
	private boolean valid = false;
	private boolean thermaling = false;

	private DateFormat df = new SimpleDateFormat("HHmmss");

	public InFlightPosition(String line) {
		String temp1 = "", temp2 = "";
		temp1 = line.substring(1, 7);
		try {
			time = df.parse(temp1);
		} catch (ParseException ex) {
			Logger.getLogger(InFlightPosition.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (line.contains("N")) {
			lat = GpsDirection.NORTH;
		} else {
			lat = GpsDirection.SOUTH;
		}

		if (line.contains("E")) {
			lon = GpsDirection.EAST;
		} else {
			lon = GpsDirection.WEST;
		}

		temp1 = line.substring(7, line.indexOf(lat.getDir()));
		temp2 = line.substring(line.indexOf(lat.getDir()) + 1, line.indexOf(lon.getDir()));

		point = new Point(temp1, temp2);

		if (line.substring(line.indexOf(lon.getDir()), line.length()).contains("A")) {
			valid = true;
			qnhAlt = Integer.parseInt(line.substring(line.indexOf("A") + 1, line.indexOf("A") + 6));
			gpsAlt = Integer.parseInt(line.substring(line.indexOf("A") + 6, line.indexOf("A") + 11));
		} else if (line.substring(line.indexOf(lon.getDir()), line.length()).contains("V")) {
			valid = true;
			qnhAlt = Integer.parseInt(line.substring(line.indexOf("V") + 1, line.indexOf("V") + 6));
			gpsAlt = Integer.parseInt(line.substring(line.indexOf("V") + 6, line.indexOf("V") + 11));
		} else {
			qnhAlt = Integer.parseInt(line.substring(line.indexOf(lon.getDir()) + 1, line.indexOf(lon.getDir()) + 6));
			gpsAlt = Integer.parseInt(line.substring(line.indexOf(lon.getDir()) + 6, line.indexOf(lon.getDir()) + 11));
		}
	}

	private void calcLift() {
		liftQnh = (qnhAlt - prev.getQnhAlt()) / 4.0;
		liftGps = (gpsAlt - prev.getGpsAlt()) / 4.0;
		InFlightPosition morePrev = prev;
		double a = 1;
		double tempQnh = liftQnh, tempGps = liftGps;
		for (; a < 8 && morePrev != null; a++) {
			tempQnh += morePrev.getLiftQnh();
			tempGps += morePrev.getLiftGps();
			morePrev = morePrev.getPrev();
		}
		liftIntQnh = tempQnh / (a);
		liftIntGps = tempGps / (a);
	}

	public void setPrev(InFlightPosition prev) {
		this.prev = prev;
		calcLift();
	}

	public void setNext(InFlightPosition next) {
		this.next = next;
		
		if(next != null){
			Point nextPoint = next.getPoint();
			
			distToNext = point.calcDistance(nextPoint);
		}
		
	}

	public double getHeading() {
		return heading;
	}

	public double getDistToNext() {
		return distToNext;
	}

	public Point getPoint() {
		return point;
	}

	public int getQnhAlt() {
		return qnhAlt;
	}

	public int getGpsAlt() {
		return gpsAlt;
	}

	public Date getTime() {
		return time;
	}

	public InFlightPosition getPrev() {
		return prev;
	}

	public InFlightPosition getNext() {
		return next;
	}

	public GpsDirection getLat() {
		return lat;
	}

	public GpsDirection getLon() {
		return lon;
	}

	public double getLiftQnh() {
		return liftQnh;
	}

	public double getLiftIntQnh() {
		return liftIntQnh;
	}

	public double getLiftGps() {
		return liftGps;
	}

	public double getLiftIntGps() {
		return liftIntGps;
	}

	public boolean isValid() {
		return valid;
	}

	public boolean isThermaling() {
		return thermaling;
	}

	public void setThermaling(boolean thermaling) {
		this.thermaling = thermaling;
	}

	@Override
	public String toString() {
		return "----------\nTime: " + time + ",\nPosition: " + point + ",\nValid: " + valid + ",\nHöhe über 1013,5hpa: " + qnhAlt + "m,\nGps-Höhe: " + gpsAlt + "m,\nSteigen (QNH): " + liftQnh + "m/s,\nSetigen (GPS): " + liftGps + "m/s,\nSteigen (integ.)(Qnh): " + liftIntQnh + "m/s,\nSteigen (integ.)(Gps): " + liftIntGps + "m/s\n----------";
	}
}
