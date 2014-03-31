/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils.helperWindows;

import igc_utils.Flight;
import igc_utils.InFlightPosition;
import igc_utils.Point;
import igc_utils.Thermal;
import igc_utils.ThermalCenterPoint;
import igc_utils.ThermalPattern;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author jfrese
 */
public class StdFlightWindow extends JFrame {

    Canvas c;
    Flight flight;
    double scale = 0.0;
    Graphics g;
    ThermalPattern tp;

    public StdFlightWindow(int x, int y) {
        setSize(x + 50, y + 50);
        setTitle("StdFlightWindow");
        c = new Canvas();
        c.setSize(x, y);
        this.add(c);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void drawFlight(Flight f, ThermalPattern tp) {

        this.tp = tp;

        flight = f;
        flight.getThermals(tp);

        /**
         * Workaround. Must be fixed later
         */
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(StdFlightWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * [0]=maxX [1]=minX [2]=maxY [3]=minY *
         */
        double[] extrema = new double[4];

        extrema[0] = -1.0;
        extrema[1] = 999.0;
        extrema[2] = -1.0;
        extrema[3] = 999.0;

        for (InFlightPosition ifp : f.getFlightPositions()) {
            Point momPoint = ifp.getPoint();

            if (momPoint.getLon() > extrema[0]) {
                extrema[0] = momPoint.getLon();
            } else if (momPoint.getLon() < extrema[1]) {
                extrema[1] = momPoint.getLon();
            }

            if (momPoint.getLat() > extrema[2]) {
                extrema[2] = momPoint.getLat();
            } else if (momPoint.getLat() < extrema[3]) {
                extrema[3] = momPoint.getLat();
            }

        }

        double scaleX = extrema[0] - extrema[1];
        double scaleY = extrema[2] - extrema[3];

        if (scaleX > scaleY) {
            scale = scaleX;
        } else {
            scale = scaleY;
        }

        g = c.getGraphics();

        int offset = 25;
        int timeFactor = 4001;
        for (InFlightPosition ifp : flight.getFlightPositions()) {
            if (ifp.isThermaling()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            InFlightPosition prev = ifp.getPrev();
            if (prev != null) {
                Point p = ifp.getPoint(), pOld = prev.getPoint();
                g.drawLine((int) ((p.getLon() - extrema[1]) / (scale / (c.getWidth() - offset))) + 15, getHeight() - (int) ((p.getLat() - extrema[3]) / (scale / (c.getHeight() - offset))) - 50, (int) ((pOld.getLon() - extrema[1]) / (scale / (c.getWidth() - offset))) + 15, getHeight() - (int) ((pOld.getLat() - extrema[3]) / (scale / (c.getHeight() - offset))) - 50);
                //g.fillRect((int) ((p.getLon() - extrema[1]) / (scale / (getWidth() - offset))) + 15, getHeight() - (int) ((p.getLat() - extrema[3]) / (scale / (getHeight() - offset))) - 40,1,1);
                try {
                    Thread.sleep(4000 / (timeFactor));
                } catch (InterruptedException ex) {
                    Logger.getLogger(StdFlightWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        g.setColor(Color.YELLOW);
        for (Thermal t : flight.getThermals(tp)){
            List<ThermalCenterPoint> centers = t.getCentersByEvaluation(6);
            for (Point p : centers) {
                if (t.getCenters().indexOf(p) - 1 > 0) {
                    Point pOld = centers.get(centers.indexOf(p) - 1);
                    g.drawLine((int) ((p.getLon() - extrema[1]) / (scale / (c.getWidth() - offset))) + 15, getHeight() - (int) ((p.getLat() - extrema[3]) / (scale / (c.getHeight() - offset))) - 50, (int) ((pOld.getLon() - extrema[1]) / (scale / (c.getWidth() - offset))) + 15, getHeight() - (int) ((pOld.getLat() - extrema[3]) / (scale / (c.getHeight() - offset))) - 50);

                }
            }
        }
    }
}
