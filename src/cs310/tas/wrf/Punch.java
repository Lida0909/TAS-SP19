package cs310.tas.wrf;

import java.sql.Timestamp;
import java.util.*;
import java.text.*;
import java.time.LocalTime;

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
    private Timestamp adjustedTimeStamp;
    

    public Punch (int id, int terminalID, String badgeID, Timestamp originalTimeStamp, int punchTypeID) {
        
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        
    }

    public Punch(Badge b, int terminalID, int punchTypeID){
        this(-1, terminalID, b.getBadgeid(), new Timestamp(new GregorianCalendar().getTimeInMillis()), punchTypeID);
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
        
        /*
        A pattern is created for the format according to the documentation on SimpleDateFormat. 
        Then an output string is constructed using the cal.getTime(). Then the output string is built,
        .toUppercase() is used to ensure the day of the week is capitalized.
        */
        
        String pattern = "EEE MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(cal.getTime()).toUpperCase();
        
        String originalTimestamptoString = "#" + getBadgeid() + " " + punchResults + " " + formattedDate;
            
        return originalTimestamptoString;
         
    }
    
    public String printAdjustedTimeStamp() {
        return "";
    }

    // Getter Methods
    
    public String getBadgeid() {
        return badgeID;
    }

    public int getTerminalid() {
        return terminalID;
    }

    public int getPunchtypeid() {
        return punchTypeID;
    }

    public int getId() {
        return id;
    }

    public long getOriginaltimestamp() {
        return originalTimeStamp.getTime() / 1000;
    }
    
    public Timestamp getOriginaltimestamp2() {
        return originalTimeStamp;
    }
    
    // Setter Methods

    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public void setPunchTypeID(int punchTypeID) {
        this.punchTypeID = punchTypeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTimeStamp(Timestamp originalTimeStamp) {
        this.originalTimeStamp = originalTimeStamp;
    }
    
    public void adjust(Shift s){
        
        LocalTime shiftStart = s.getStartingTime();
        LocalTime shiftStop = s.getStoppingTime();
        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchStop = s.getLunchStop();
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(originalTimeStamp.getTime());
        int punchHour = cal.HOUR;
        int punchMinutes = cal.MINUTE;
        LocalTime punchTime = LocalTime.of(punchHour, punchMinutes);
        adjustedTimeStamp = originalTimeStamp;
        
        switch(this.punchTypeID){
            case 0:
                
                // CHECKS IF THE PUNCH IS CLOCKOUT FOR THE LUNCH BREAK
                
                if( punchTime.isAfter(shiftStart) && punchTime.isBefore(lunchStop) ) {
                    if( punchTime.isBefore(lunchStart) ) {
                        // "#28DC3FB8 CLOCKED OUT: FRI 09/07/2018 12:00:00 (Lunch Start)
                        
                        
                        
                    }
                    
                }
                break;
            case 1:
                break;
            default:
                System.out.println("ERROR");
        }
        
    }
      
}


     

