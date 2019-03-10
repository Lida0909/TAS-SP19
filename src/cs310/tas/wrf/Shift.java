
package cs310.tas.wrf;

import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author Sabin Banjara
 */

public class Shift {
    
    private String description; 
    private LocalTime startingTime;
    private LocalTime stoppingTime;
    private LocalTime lunchStart;
    private LocalTime lunchStop;
    
    private int lunchDeduct;
    private int interval;
    private int gracePeriod;
    private int dock;
    
    public Shift(String description, int startHour, int startMin,int interval, int gracePeriod,
            int dock, int stopHour, int stopMin, int lunchStartHour, int lunchStartMin,
            int lunchStopHour, int lunchStopMin, int lunchDeduct) {
        
        this.description = description;
        this.startingTime = LocalTime.of(startHour, startMin);
        this.stoppingTime = LocalTime.of(stopHour, stopMin);
        this.lunchStart = LocalTime.of(lunchStartHour, lunchStartMin);
        this.lunchStop = LocalTime.of(lunchStopHour, lunchStopMin);
        this.lunchDeduct = lunchDeduct;
        this.interval = interval;
        this.gracePeriod = gracePeriod;
        this.dock = dock;
        
    }
    
    
    /* Getter Methods */
    
    public LocalTime getStartingTime() {
        return startingTime;
    }
    
    public int getStartingTimeHour() {
        return getStartingTime().getHour();
    }
    
    public int getStartingTimeMinutes() {
        return getStartingTime().getMinute();
    }
    
    public LocalTime getStoppingTime() {
        return stoppingTime;
    }
    
    public int getStoppingTimeHour() {
        return getStoppingTime().getHour();
    }
    
    public int getStoppingTimeMinutes() {
        return getStoppingTime().getMinute();
    }
    
    public LocalTime getLunchStart() {
        return lunchStart;
    }
    
    public LocalTime getLunchStop() {
        return lunchStop;
    }
    
    public int getInterval() {
        return interval;
    }
    
    public int getlunchDeduct() {
        return lunchDeduct;
    }
    
    public int getGracePeriod() {
        return gracePeriod;
    }
    
    public int getDock() {
        return dock;
    }
    
    
    /* Setter Methods */
    
    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setStoppingTime(LocalTime stoppigTime) {
        this.stoppingTime = stoppigTime;
    }

    public void setLunchStart(LocalTime lunchStart) {
        this.lunchStart = lunchStart;
    }

    public void setLunchStop(LocalTime lunchStop) {
        this.lunchStop = lunchStop;
    }

    public void setLunchDeduct(int lunchDeduct) {
        this.lunchDeduct = lunchDeduct;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public void setDock(int dock) {
        this.dock = dock;
    }
    
    
    public int totalShiftHours() {
        
       int totalStartTimeMinutes = (getStartingTime().getHour()*60) + getStartingTime().getMinute();
       int totalStopTimeMinutes = (getStoppingTime().getHour()*60) + getStoppingTime().getMinute();
       return (totalStopTimeMinutes - totalStartTimeMinutes);
       
    }
    
    public int totalLunchTime() {
        
        int totalLunchStartMinutes = (getLunchStart().getHour()*60) + getLunchStart().getMinute();
        int totalLunchStopMinutes = (getLunchStop().getHour()*60) + getLunchStop().getMinute();
        return (totalLunchStopMinutes - totalLunchStartMinutes);
        
    }
    
    @Override
    public String toString() {
        
       StringBuilder s = new StringBuilder();
       s.append(description).append(": ").append(startingTime.toString()).append(" - ");
       s.append(stoppingTime.toString()).append(" (").append(totalShiftHours()).append(" minutes); Lunch: ");
       s.append(lunchStart.toString()).append(" - ").append(lunchStop.toString()).append(" (");
       s.append(totalLunchTime()).append(" minutes)");
       
       return s.toString();
        
    }   
    
}
