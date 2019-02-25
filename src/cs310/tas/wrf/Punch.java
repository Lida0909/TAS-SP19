/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.tas.wrf;

import cs310.tas.wrf.Badge;
import java.util.*;
import java.text.*;

/**
 *
 * @author Matt Denman and William Penwell
 */
public class Punch {
    
    private Badge badge;// = new Badge(String, String);
    private int terminalID;
    private int punchTypeID;
    private int id;
    private String description;
    private long originalTimeStamp;
   
    //make a private Gregoriancalendar object "timestamp" or "cal"  here
        
    Punch (Badge badge, int terminalID, int punchTypeID, long startingTimeStamp){
        
        this.badge = badge;
        this.terminalID = terminalID;
        this.punchTypeID = punchTypeID;
        this.originalTimeStamp = originalTimeStamp;
        /*
        instantiate our private GregorianCalendar object here by setting it to the current time
        according to https://www.geeksforgeeks.org/java-util-gregoriancalendar-class-java/ :
        it looks as if using the constructor with empty paremeters is correct.
        However, if you declare the Gregorian calendar object above, then you don't need to re-delcare the class type here so you can just use
        "cal = new GregorianCalendar()" or "timestamp = new GregorianCalendar()"
        */
     
        
    }
    
    public String printOriginalTimeSstamp() {
        //if we set a class variable of type GregorianCalendar , we can just use a command to "getTheTimeAndDate" from "timestamp" and return that or print it.
        //I think we need to also have a class that returns the "timestamp" itself as a gregorian calendar object. like  "public GregorianCalendare methodname(){pass the caller the gregorianCalendar object}
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(this.originalTimeStamp);
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
        
        String originalTimestamptoString = "#" + this.badge + punchResults + " " + date;
        return originalTimestamptoString;
         
    }
    
    public String printAdjustedTimeStamp() {
        return "";
    }
    
    Punch (int id, String description) {
        
        this.id = id;
        this.description = description;
    }
    
}

