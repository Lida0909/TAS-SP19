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
   
   
        
    Punch (int id, int terminalID, String badgeID, Timestamp originalTimeStamp, int punchTypeID) {
        
        this.id = id;
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
     
        
    }
    
    public String printOriginalTimestamp() {
        
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(originalTimeStamp.getTime());
        //Date date = cal.getTime();
        
        switch(this.punchTypeID){
            case 0:
                punchResults = "CLOCKED OUT:";
                break;
            case 1:
                punchResults = "CLOCKED IN:";
                break;
            case 2:
                punchResults = "TIMED OUT:";
                break;
            default:
                System.out.println("ERROR");
        }
        
        //String originalTimestamptoString = "#" + getBadgeID() + punchResults + " " + date;
        
        /*
        A pattern is created for the format according to the documentation on SimpleDateFormat. 
        Then an output string is constructed using the cal.getTime(). Then the output string is built,
        .toUppercase() is used to ensure the day of the week is capitalized.
        */
        String pattern = "EEE MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(cal.getTime()).toUpperCase();
        
        String originalTimestamptoString = "#" + getBadgeID() + " " + punchResults + " " + formattedDate;

        return originalTimestamptoString;
         
    }
    
    public String printAdjustedTimeStamp() {
        return "";
    }

    public String getBadgeID() {
        return badgeID;
    }
    
    
    
}

