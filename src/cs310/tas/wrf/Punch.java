package cs310.tas.wrf;

import java.sql.Timestamp;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The Punch class is an abstraction of a time clock punch. This class is used
 * to store the information from a time clock punch and to adjust it according
 * to a given shifts rules
 * @author War Room F
 */
public class Punch {
    
    private String badgeID;
    private int terminalID;
    private int punchTypeID;
    private int id;
    private Timestamp originalTimeStamp;
    private Timestamp adjustedTimeStamp;
    private String adjustMessage;
    
    /**
     *
     * @param id an int that represents the id of a punch
     * @param terminalID an int that represents which terminal a punch was made
     * @param badgeID a String that represents the id of a specific badge
     * @param originalTimeStamp a Timestamp that represents the time a punch was
     * made
     * @param punchTypeID an int that represents whether someone clocked in or 
     * out of a shift or timed out of a shift
     */
    public Punch (int id, int terminalID, String badgeID, Timestamp originalTimeStamp, int punchTypeID) {
        
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        
    }

    /**
     *
     * @param b a Badge that represents a specific employee's badge id and name
     * @param terminalID an int that represents which terminal a punch was made
     * @param punchTypeID an int that represents whether someone clocked in or 
     * out of a shift or timed out of a shift
     */
    public Punch(Badge b, int terminalID, int punchTypeID){
        this(-1, terminalID, b.getBadgeid(), new Timestamp(new GregorianCalendar().getTimeInMillis()), punchTypeID);
    }
    
    /**
     *
     * @return the original timestamp of a punch as a string
     */
    public String printOriginalTimestamp() {
        
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(originalTimeStamp.getTime());
        
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
    
    /**
     *
     * @return the adjusted timestamp of a punch as a string
     */
    public String printAdjustedTimestamp() {
        String punchResults = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(adjustedTimeStamp.getTime());
        
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
          
        }   
        
        String pattern = "EEE MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(cal.getTime()).toUpperCase();
        String originalTimestamptoString = "#" + getBadgeid() + " " + punchResults + " " + formattedDate + " (" + adjustMessage + ")";
            
        return originalTimestamptoString;
    }

    // Getter Methods
    
    /**
     *
     * @return the badge id as a string
     */
        
    public String getBadgeid() {
        return badgeID;
    }

    /**
     *
     * @return the terminal id as an int
     */
    public int getTerminalid() {
        return terminalID;
    }

    /**
     *
     * @return the punch type id as an int
     */
    public int getPunchtypeid() {
        return punchTypeID;
    }

    /**
     *
     * @return the id as an int
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the original timestamp a long
     */
    public long getOriginaltimestamp() {
        return originalTimeStamp.getTime() / 1000;
    }
    
    /**
     *
     * @return the original timestamp as a Timestamp
     */
    public Timestamp getOriginaltimestamp2() {
        return originalTimeStamp;
    }
    
    // Setter Methods

    /**
     *
     * @param badgeID a String that represents the id of a specific badge
     */
    
    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    /**
     *
     * @param terminalID an int that represents which terminal a punch was made
     */
    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    /**
     *
     * @param punchTypeID an int that represents whether someone clocked in or 
     * out of a shift or timed out of a shift
     */
    public void setPunchTypeID(int punchTypeID) {
        this.punchTypeID = punchTypeID;
    }

    /**
     *
     * @param id an int that represents the id of a punch
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param originalTimeStamp a Timestamp that represents the time a punch was
     * made
     */
    public void setOriginalTimeStamp(Timestamp originalTimeStamp) {
        this.originalTimeStamp = originalTimeStamp;
    }
    
    /**
     *
     * @param s a Shift that represents the shift that should be adjusted
     */
    public void adjust(Shift s){
        
        LocalTime shiftStart = s.getStartingTime();
        LocalTime shiftStop = s.getStoppingTime();
        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchStop = s.getLunchStop();
        
        int gracePeriod = s.getGracePeriod();
        int interval = s.getInterval();
        int dock = s.getDock();
        int lunchDeduct = s.getlunchDeduct();
        
        LocalDateTime punchTimeStamp = originalTimeStamp.toLocalDateTime();
        LocalTime punchTime = punchTimeStamp.toLocalTime();
        int punchTimeSeconds =  punchTime.getSecond();
        int totalpunchTimeMinutes = punchTime.getMinute() + (punchTime.getHour()*60);
        int totalshiftStopMinutes = shiftStop.getMinute() + (punchTime.getHour()*60);
        int totalshiftStartMinutes = shiftStart.getMinute() + (shiftStart.getHour()*60);
        boolean weekend =  (punchTimeStamp.getDayOfWeek().toString().equals("SATURDAY") || 
                punchTimeStamp.getDayOfWeek().toString().equals("SUNDAY") );
        
        adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
        
        switch(this.punchTypeID){
            
            case 0:
                
                // CHECKS IF THE PUNCH IS CLOCKOUT FOR THE LUNCH BREAK
           
                if( punchTime.isAfter(shiftStart) && punchTime.isBefore(lunchStop) && (!weekend) ) {
                  
                    if( punchTime.isAfter(lunchStart) && punchTime.isBefore(lunchStop)) {
                        adjustMessage = "Lunch Start";
                        punchTimeStamp = punchTimeStamp.withHour(lunchStart.getHour());
                        punchTimeStamp = punchTimeStamp.withMinute(lunchStart.getMinute());
                        punchTimeStamp = punchTimeStamp.withSecond(0);
                        adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        
                    }
                    
                }
                
                else if(weekend) {
                    
                   if(punchTime.isBefore(shiftStop) ) {
                        
                        if( (totalshiftStopMinutes - totalpunchTimeMinutes) <= gracePeriod ) {
                            adjustMessage = "Shift Stop";
                            punchTimeStamp = punchTimeStamp.withHour(shiftStop.getHour());
                            punchTimeStamp = punchTimeStamp.withMinute(shiftStop.getMinute());
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                        else if ( (totalshiftStopMinutes - totalpunchTimeMinutes) > gracePeriod && 
                                (totalshiftStopMinutes - totalpunchTimeMinutes) <= dock ) {
                            adjustMessage = "Shift Dock";
                            totalshiftStopMinutes = totalshiftStopMinutes - dock;
                            punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                            punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                        else {
                            adjustMessage = "Interval Round";
                            int timeInterval = totalshiftStopMinutes - totalpunchTimeMinutes;
                            int a = timeInterval/interval;
                            int b = timeInterval%interval;
                            if(b <= 8) {
                                totalshiftStopMinutes = totalshiftStopMinutes - (a*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);   
                            }
                            else {
                                totalshiftStopMinutes = totalshiftStopMinutes - ((a+1)*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);
                            }
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                   }

                }
                
                //CHECKS IF THE PUNCH IS CLOCKOUT FOR THE SHIFT END
                
                else if ( punchTime.isAfter(lunchStop) && (!weekend) ) {
                    
                    //CHECKS IF THE PUNCH IS BEFORE THE SHIFT STOP
                    
                    if(punchTime.isBefore(shiftStop) ) {
                        
                        if( (totalshiftStopMinutes - totalpunchTimeMinutes) <= gracePeriod ) {
                            adjustMessage = "Shift Stop";
                            punchTimeStamp = punchTimeStamp.withHour(shiftStop.getHour());
                            punchTimeStamp = punchTimeStamp.withMinute(shiftStop.getMinute());
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                        else if ( (totalshiftStopMinutes - totalpunchTimeMinutes) > gracePeriod && 
                                (totalshiftStopMinutes - totalpunchTimeMinutes) <= dock ) {
                            adjustMessage = "Shift Dock";
                            totalshiftStopMinutes = totalshiftStopMinutes - dock;
                            punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                            punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                        else {
                            adjustMessage = "Interval Round";
                            int timeInterval = totalshiftStopMinutes - totalpunchTimeMinutes;
                            int a = timeInterval/interval;
                            int b = timeInterval%interval;
                            if(b <= 8) {
                                totalshiftStopMinutes = totalshiftStopMinutes - (a*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);   
                            }
                            else {
                                totalshiftStopMinutes = totalshiftStopMinutes - ((a+1)*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);
                            }
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                    }
                    
                    //CHECKS IF THE PUNCH IS AFTER THE SHIFT STOP
                    
                    else if( punchTime.isAfter(shiftStop) && (!weekend) ) {
                        
                        int timeInterval = totalpunchTimeMinutes - totalshiftStopMinutes;
                        int a = timeInterval/interval;
                        int b = timeInterval%interval;
                        
                        if(timeInterval <= interval) {
                            
                            if( (b == 0) && punchTime.getSecond() < 60) {
                                adjustMessage = "None";
                            }
                            else {
                                adjustMessage = "Shift Stop";
                            }
                            totalpunchTimeMinutes = totalpunchTimeMinutes - (a*interval);
                            punchTimeStamp = punchTimeStamp.withHour(totalshiftStopMinutes/60);
                            punchTimeStamp = punchTimeStamp.withMinute(totalshiftStopMinutes%60);   
                        }
                        else {
                            adjustMessage = "Interval Round";
                            totalpunchTimeMinutes = totalpunchTimeMinutes - ((a+1)*interval);
                            punchTimeStamp = punchTimeStamp.withHour(totalpunchTimeMinutes/60);
                            punchTimeStamp = punchTimeStamp.withMinute(totalpunchTimeMinutes%60);
                        }
                        punchTimeStamp = punchTimeStamp.withSecond(0);
                        adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        
                    }
                                       
                }
                
                break;
                
                
            case 1:
                
                // CHECKS IF THE PUNCH IS CLOCKIN FOR THE LUNCH END
                
                if( punchTime.isAfter(lunchStart) && punchTime.isBefore(shiftStop)) {
                  
                    if( punchTime.isAfter(lunchStart) && punchTime.isBefore(lunchStop)) {
                        adjustMessage = "Lunch Stop";
                        punchTimeStamp = punchTimeStamp.withHour(lunchStop.getHour());
                        punchTimeStamp = punchTimeStamp.withMinute(lunchStop.getMinute());
                        punchTimeStamp = punchTimeStamp.withSecond(0);
                        adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        
                    }
                    
                }
                
                // CHECKS IF THE GIVEN PUNCH IS CLOCK IN FOR THE SHIFT START
                
                else if( punchTime.isBefore(lunchStart) ) {
                    
                    // CHECKS IF THE PUNCH IS BEFORE THE SHIFT START
                    
                    if( punchTime.isBefore(shiftStart) ) {
                        
                        int timeInterval = totalshiftStartMinutes - totalpunchTimeMinutes;
                        int a = timeInterval/interval;
                        int b = timeInterval%interval;
                        
                        
                        if(timeInterval <= interval) {
                            adjustMessage = "Shift Start";
                            punchTimeStamp = punchTimeStamp.withHour(shiftStart.getHour());
                            punchTimeStamp = punchTimeStamp.withMinute(shiftStart.getMinute());   
                        }
                        else {
                            if(b <= 7) {
                                adjustMessage = "Interval Round";
                                totalpunchTimeMinutes = totalshiftStartMinutes - ((a)*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalpunchTimeMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalpunchTimeMinutes%60);
                            }
                            else {
                                adjustMessage = "Interval Round";
                                totalpunchTimeMinutes = totalshiftStartMinutes - ((a+1)*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalpunchTimeMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalpunchTimeMinutes%60);
                            }
                        }
                        punchTimeStamp = punchTimeStamp.withSecond(0);
                        adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        
                    }
                    
                    // CHECKS IF THE PUNCH IS AFTER THE SHIFT START
                    
                    else if( punchTime.isAfter(shiftStart) ) {
                    
                        if( (totalpunchTimeMinutes - totalshiftStartMinutes) <= gracePeriod ) {
                            adjustMessage = "Shift Start";
                            punchTimeStamp = punchTimeStamp.withHour(shiftStart.getHour());
                            punchTimeStamp = punchTimeStamp.withMinute(shiftStart.getMinute());
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }

                        else if ( (totalpunchTimeMinutes - totalshiftStartMinutes) <= dock ) {
                            adjustMessage = "Shift Dock";
                            totalshiftStartMinutes = totalshiftStartMinutes + dock;
                            punchTimeStamp = punchTimeStamp.withHour(totalshiftStartMinutes/60);
                            punchTimeStamp = punchTimeStamp.withMinute(totalshiftStartMinutes%60);
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }

                        else {
                            adjustMessage = "Interval Round";
                            int timeInterval = totalpunchTimeMinutes - totalshiftStartMinutes;
                            int a = timeInterval/interval;
                            int b = timeInterval%interval;
                            if(b <= 7) {
                                totalshiftStartMinutes = totalshiftStartMinutes + (a*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStartMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStartMinutes%60);   
                            }
                            else {
                                totalshiftStartMinutes = totalshiftStartMinutes + ((a+1)*interval);
                                punchTimeStamp = punchTimeStamp.withHour(totalshiftStartMinutes/60);
                                punchTimeStamp = punchTimeStamp.withMinute(totalshiftStartMinutes%60);
                            }
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }

                    }
                    
                }
                
                break;
                
                
            default:
                System.out.println("ERROR");
                               
        }
        
    }
      
}