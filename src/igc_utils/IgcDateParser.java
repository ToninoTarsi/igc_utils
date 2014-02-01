/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 0.1
 * @author jfrese
 */
public class IgcDateParser {

    public static final String[] igcNameConstants;

    static {
        igcNameConstants = new String[35];
        for (int a = 0; a < 35; a++) {
            if (a < 9) {
                igcNameConstants[a] = "" + (a + 1);
            } else {
                igcNameConstants[a] = "" + (char) (55 + (a + 1));
            }
        }
    }

    public static Date parseNameToDate(String name) {
        String date = "";
        date = "" + parseChar(name.charAt(2)) + ".";
        date += parseChar(name.charAt(1)) + ".";
        date += ("201" + name.charAt(0)); //Must be replaced with an universal solution

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date flightDate = new Date(0);
        try {
            flightDate = df.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(IgcDateParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flightDate;
    }

    private static String parseChar(char c) {
        c = (("" + c).toUpperCase()).charAt(0);
        String toReturn = "";
        for (int a = 0; a < 35; a++) {
            if (("" + c).equals(igcNameConstants[a])) {
                return "" + (a + 1);
            }
        }
        return toReturn;
    }

    public static Date parseBRecToDate(String bRec) {
        Date date = new Date(0);
        bRec = bRec.substring(1, 7);
        DateFormat df = new SimpleDateFormat("hhmmss");
        try {
            date.setTime(date.getTime() + df.parse(bRec).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(IgcDateParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
}
