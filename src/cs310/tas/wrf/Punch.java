package cs310.tas.wrf;

import java.sql.Timestamp;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
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
    private String adjustMessage;
    

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
        //#BE51FA92 CLOCKED IN: WED 08/01/2018 07:00:00 (Shift Start)
        String originalTimestamptoString = "#" + getBadgeid() + " " + punchResults + " " + formattedDate + " (" + adjustMessage + ")";
            
        return originalTimestamptoString;
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
        boolean weekend =  (punchTimeStamp.getDayOfWeek().toString().equals("SATURDAY") || punchTimeStamp.getDayOfWeek().toString().equals("SUNDAY") );
        
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
                        
                        else if ( (totalshiftStopMinutes - totalpunchTimeMinutes) > gracePeriod && (totalshiftStopMinutes - totalpunchTimeMinutes) <= dock ) {
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
                            //punchTime = shiftStop;
                            punchTimeStamp = punchTimeStamp.withHour(shiftStop.getHour());
                            punchTimeStamp = punchTimeStamp.withMinute(shiftStop.getMinute());
                            punchTimeStamp = punchTimeStamp.withSecond(0);
                            adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                        }
                        
                        else if ( (totalshiftStopMinutes - totalpunchTimeMinutes) > gracePeriod && (totalshiftStopMinutes - totalpunchTimeMinutes) <= dock ) {
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
                    
                    // CHECKS IF THE PUNCH IS RIGHT ON THE SHIFT END 
                    
                    else {
                        adjustMessage = "None";
                        punchTimeStamp = punchTimeStamp.withHour(shiftStop.getHour());
                        punchTimeStamp = punchTimeStamp.withMinute(shiftStop.getMinute());
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
                
                // CHECKS IF THE PUNCH IS RIGHT ON THE SHIFT END 
                    
                else {
                    adjustMessage = "None";
                    punchTimeStamp = punchTimeStamp.withHour(shiftStart.getHour());
                    punchTimeStamp = punchTimeStamp.withMinute(shiftStart.getMinute());
                    punchTimeStamp = punchTimeStamp.withSecond(0);
                    adjustedTimeStamp = Timestamp.valueOf(punchTimeStamp);
                }
                
                break;
                
                
            default:
                System.out.println("ERROR");
                
                
        }
        
    }
      
}