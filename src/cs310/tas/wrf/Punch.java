package cs310.tas.wrf;

import java.sql.Timestamp;
import java.util.*;

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
        //if we set a class variable of type GregorianCalendar , we can just use a command to "getTheTimeAndDate" from "timestamp" and return that or print it.
        //I think we need to also have a class that returns the "timestamp" itself as a gregorian calendar object. like  "public GregorianCalendare methodname(){pass the caller the gregorianCalendar object}
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(originalTimeStamp.getTime());
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

