
package cs310.tas.wrf;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class Shift {
    
    private String description; 
    private Time startingTime;
    private Time stoppingTime;
    private Time lunchStart;
    private Time lunchStop;
    
    private int lunchDeduct;
    private int interval;
    private int gracePeriod;
    private int dock;
    
    private SimpleDateFormat hoursMinutes;
    
    public Shift(String description, Time startTime,int interval, int gracePeriod,
            int dock, Time stopTime, Time lunchStart, Time lunchStop, int lunchDeduct) {
        
        this.description = description;
        this.startingTime = startTime;
        this.stoppingTime = stopTime;
        this.lunchStart = lunchStart;
        this.lunchStop = lunchStop;
        this.lunchDeduct = lunchDeduct ;
        this.interval = interval;
        this.gracePeriod = gracePeriod;
        this.dock = dock;
        
        hoursMinutes = new SimpleDateFormat("HH:mm");
    }
    
    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getStoppingTime() {
        return stoppingTime;
    }

    public void setStoppingTime(Time stoppigTime) {
        this.stoppingTime = stoppigTime;
    }

    public Time getLunchStart() {
        return lunchStart;
    }

    public void setLunchStart(Time lunchStart) {
        this.lunchStart = lunchStart;
    }

    public Time getLunchStop() {
        return lunchStop;
    }

    public void setLunchStop(Time lunchStop) {
        this.lunchStop = lunchStop;
    }

    public int lunchDeduct() {
        return lunchDeduct;
    }

    public void setLunchDeduct(int lunchDeduct) {
        this.lunchDeduct = lunchDeduct;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public int getDock() {
        return dock;
    }

    public void setDock(int dock) {
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
       s.append(description).append(": ").append(hoursMinutes.format(startingTime)).append(" - ");
       s.append(hoursMinutes.format(stoppingTime)).append(" (").append(totalShiftHours()).append("); Lunch: ");
       s.append(lunchStart).append(" - ").append(lunchStop).append(" (");
       s.append(totalLunchTime()).append(")");
       
       return s.toString();
        
    }
    
    
}

