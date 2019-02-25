/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.tas.wrf;

import java.sql.Timestamp;
import java.util.*;
import java.text.*;

/**
 *
 * @author Matt Denman and William Penwell, Adam Stith
 */
public class Punch {
    
    private String badgeID;
    private int terminalID;
    private int punchTypeID;
    private int id;
    private Timestamp originalTimeStamp;
   
    //make a private Gregoriancalendar object "timestamp" or "cal"  here
        
    Punch (int id, int terminalID, String badgeID, Timestamp originalTimeStamp, int punchTypeID) {
        
        this.id = id;
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        /*
        instantiate our private GregorianCalendar object here by setting it to the current time
        according to https://www.geeksforgeeks.org/java-util-gregoriancalendar-class-java/ :
        it looks as if using the constructor with empty paremeters is correct.
        However, if you declare the Gregorian calendar object above, then you don't need to re-delcare the class type here so you can just use
        "cal = new GregorianCalendar()" or "timestamp = new GregorianCalendar()"
        */
     
        
    }
    
    public String printOriginalTimestamp() {
        //if we set a class variable of type GregorianCalendar , we can just use a command to "getTheTimeAndDate" from "timestamp" and return that or print it.
        //I think we need to also have a class that returns the "timestamp" itself as a gregorian calendar object. like  "public GregorianCalendare methodname(){pass the caller the gregorianCalendar object}
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        //cal.setTimeInMillis(this.originalTimeStamp);
        Date date = cal.getTime();
        switch(this.punchTypeID){
            case 0:
                punchResults = "CLOCKED OUT: ";
                break;
            case 1:
                punchResults = "CLOCKED IN: ";
                break;
            case 2:
                punchResults = "TIMED OUT: ";
                break;
            default:
                System.out.println("ERROR");
        }
        
        String originalTimestamptoString = "#" + getBadgeID() + punchResults + " " + date;
        return originalTimestamptoString;
         
    }
    
    public String printAdjustedTimeStamp() {
        return "";
    }

    public String getBadgeID() {
        return badgeID;
    }
    
    
    
}

