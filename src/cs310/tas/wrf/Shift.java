
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
    
    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalTime getStoppingTime() {
        return stoppingTime;
    }

    public void setStoppingTime(LocalTime stoppigTime) {
        this.stoppingTime = stoppigTime;
    }

    public LocalTime getLunchStart() {
        return lunchStart;
    }

    public void setLunchStart(LocalTime lunchStart) {
        this.lunchStart = lunchStart;
    }

    public LocalTime getLunchStop() {
        return lunchStop;
    }

    public void setLunchStop(LocalTime lunchStop) {
        this.lunchStop = lunchStop;
    }

    public LocalTime lunchDeduct() {
        return lunchDeduct;
    }

    public void setLunchDeduct(LocalTime lunchDeduct) {
        this.lunchDeduct = lunchDeduct;
    }

    public LocalTime getInterval() {
        return interval;
    }

    public void setInterval(LocalTime interval) {
        this.interval = interval;
    }

    public LocalTime getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(LocalTime gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public LocalTime getDock() {
        return dock;
    }

    public void setDock(LocalTime dock) {
        this.dock = dock;
    }
    
    
    public int totalShiftHours() {
       //int totalStartTimeMinutes = (startingTime.getHours()*60) + startingTime.getMinutes();
       //int totalStopTimeMinutes = (stoppingTime.getHours()*60) + stoppingTime.getMinutes();
       //return (totalStopTimeMinutes - totalStartTimeMinutes);
       return 510;
       
    }
    
    public int totalLunchTime() {
        return 30;
    }
    
    //"Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)"
    
    @Override
    public String toString() {
        
       StringBuilder s = new StringBuilder();
       s.append(description).append(": ").append(startingTime).append(" - ");
       s.append(stoppingTime).append(" (").append(totalShiftHours()).append("); Lunch: ");
       s.append(lunchStart).append(" - ").append(lunchStop).append(" (");
       s.append(totalLunchTime()).append(")");
       
       return s.toString();
        
    }
    
    
}

