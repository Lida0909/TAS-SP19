
package cs310.tas.wrf;

import java.sql.Time;
import java.time.LocalTime;

public class Shift {
    
    private String description; 
    private LocalTime startingTime;
    private LocalTime stoppingTime;
    private LocalTime lunchStart;
    private LocalTime lunchStop;
    
    private LocalTime lunchDeduct;
    private LocalTime interval;
    private LocalTime gracePeriod;
    private LocalTime dock;
    
    public Shift(String description, int startHour, int startMin,int interval, int gracePeriod,
            int dock, int stopHour, int stopMin, int lunchStartHour, int lunchStartMin,
            int lunchStopHour, int lunchStopMin, int lunchDeduct) {
        
        this.description = description;
        this.startingTime = LocalTime.of(startHour, startMin, 0);
        this.stoppingTime = LocalTime.of(stopHour, stopMin, 0);
        this.lunchStart = LocalTime.of(lunchStartHour, lunchStartMin, 0);
        this.lunchStop = LocalTime.of(lunchStopHour, lunchStopMin, 0);
        this.lunchDeduct = LocalTime.of(0,lunchDeduct, 0);
        this.interval = LocalTime.of(0, interval, 0);
        this.gracePeriod = LocalTime.of(0, gracePeriod, 0);
        this.dock = LocalTime.of(0, dock, 0);
        
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
    
    public LocalTime getInterval() {
        return interval;
    }
    
    public LocalTime getlunchDeduct() {
        return lunchDeduct;
    }
    
    public LocalTime getGracePeriod() {
        return gracePeriod;
    }
    
    public LocalTime getDock() {
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

    public void setLunchDeduct(LocalTime lunchDeduct) {
        this.lunchDeduct = lunchDeduct;
    }

    public void setInterval(LocalTime interval) {
        this.interval = interval;
    }

    public void setGracePeriod(LocalTime gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public void setDock(LocalTime dock) {
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
       s.append(stoppingTime.toString()).append(" (").append(totalShiftHours()).append("); Lunch: ");
       s.append(lunchStart.toString()).append(" - ").append(lunchStop.toString()).append(" (");
       s.append(totalLunchTime()).append(")");
       
       return s.toString();
        
    }   
    
}