package cs310.tas.wrf;

import java.time.LocalTime;

/**
 * The Shift class is an abstraction of a shift. This class is used to determine 
 * how the starting and stopping time for a particular shift and the rules that
 * determine how to adjust the punchs' timestamps.
 * @author War Room F
 */

public class Shift {
    
    private DailySchedule defaultschedule;
    private String description; 
    private int id;
    /*private LocalTime startingTime;
    private LocalTime stoppingTime;
    private LocalTime lunchStart;
    private LocalTime lunchStop;
    
    private int lunchDeduct;
    private int interval;
    private int gracePeriod;
    private int dock;
    */
    
    /**
     * Sets the description, start hour, start minute, interval, grace period,
     * dock, stop hour, stop minute, lunch start hour, lunch start minute, lunch
     * stop hour, lunch stop minute, and lunch deduct
     * @param description a String that represents the shift type
     * @param startHour an int that represents the start hour of the shift
     * @param startMin an int that represents the start minute of the shift
     * @param interval an int that represents the number of minutes that will
     * be adjusted before and after a shift
     * @param gracePeriod an int that represents the grace period for a shift
     * @param dock an int that represents the dock period for a shift
     * @param stopHour an int that represents the stop hour of a shift
     * @param stopMin an int that represents the stop minute of a shift
     * @param lunchStartHour an int that represents the hour the lunch starts
     * @param lunchStartMin an int that represents the minute the lunch starts
     * @param lunchStopHour an int that represents the hour the lunch stops
     * @param lunchStopMin an int that represents the minute the lunch stops
     * @param lunchDeduct an int that represents the number of minutes you have
     * to work during a day
     */
    public Shift(String description, int id, DailySchedule defaultschedule) {
        
        this.description = description;
        this.id = id;
        this.defaultschedule = defaultschedule;
        /*this.startingTime = LocalTime.of(startHour, startMin);
        this.stoppingTime = LocalTime.of(stopHour, stopMin);
        this.lunchStart = LocalTime.of(lunchStartHour, lunchStartMin);
        this.lunchStop = LocalTime.of(lunchStopHour, lunchStopMin);
        this.lunchDeduct = lunchDeduct;
        this.interval = interval;
        this.gracePeriod = gracePeriod;
        this.dock = dock;*/
        
    }
    //int startHour, int startMin,int interval, int gracePeriod,
            //int dock, int stopHour, int stopMin, int lunchStartHour, int lunchStartMin,
            //int lunchStopHour, int lunchStopMin, int lunchDeduct
    
    /* Getter Methods */
    
    /**
     * Fetches the starting time of a specific shift
     * @return the Starting time or the shift as a LocalTime object
     */
    public LocalTime getStartingTime() {
        return defaultschedule.getShiftStart();
    }
    
    /**
     * Fetches the starting time hour of a specific shift
     * @return the starting hour of the shift as an int
     */
    public int getStartingTimeHour() {
        return getStartingTime().getHour();
    }
    
    /**
     * Fetches the starting time minutes of a specific shift
     * @return the starting minutes of the shift as an int
     */
    public int getStartingTimeMinutes() {
        return getStartingTime().getMinute();
    }
    
    /**
     * Fetches the stopping time of a specific shift
     * @return the stopping time of the shift as a LocalTime
     */
    public LocalTime getStoppingTime() {
        return defaultschedule.getShiftStop();
    }
    
    /**
     * Fetches the stopping time hour of a specific shift
     * @return the stopping hour of the shift as an int
     */
    public int getStoppingTimeHour() {
        return getStoppingTime().getHour();
    }
    
    /**
     * Fetches the stopping time minute of a specific shift
     * @return the stopping minutes of the shift as an int
     */
    public int getStoppingTimeMinutes() {
        return getStoppingTime().getMinute();
    }
    
    /**
     * Fetches the lunch starting time of a specific shift
     * @return the start of lunch as a LocalTime
     */
    public LocalTime getLunchStart() {
        return defaultschedule.getLunchStart();
    }
    
    /**
     * Fetches the lunch stopping time of a shift
     * @return the stopping time of lunch as a LocalTime
     */
    public LocalTime getLunchStop() {
        return defaultschedule.getLunchStop();
    }
    
    /**
     * Fetches the interval of time you have before and after a specific shift
     * to determine the time that should be pushed to the beginning or end of a 
     * shift
     * @return the interval of time you have before and after a shift to
     * determine if the time should be pushed to the beginning or end of a shift
     * as an int
     */

    public int getInterval() {
        return defaultschedule.getInterval();
    }
    
    /**
     * Fetches the time that should be deducted for a lunch break for a specific
     * shift
     * @return the deduct time for lunch break as an int
     */
    public int getlunchDeduct() {
        return defaultschedule.getLunchDeductionThreshold();
    }
    

    /**
     * Fetches the grace period for a specific shift which will determine if you
     * will be too late or early when clocking in or clocking out
     * @return the grace period for checking in late or checking out early as an
     * int
     */

    public int getGracePeriod() {
        return defaultschedule.getGraceperiod();
    }
    

    /**
     * Fetches the time after grace period known as the dock which will push you
     * forwards or backwards in the interval round
     * @return the time after grace period that will push you forward or
     * backwards in the interval round as an int
     */

    public int getDock() {
        return defaultschedule.getDockpenalty();
    }
    
    
    /* Setter Methods */

    /**
     * Sets the starting time of a specific shift
     * @param startingTime a LocalTime that represents the start time of a shift
     */
    public void setStartingTime(LocalTime startingTime) {
        this.defaultschedule.setShiftStart(startingTime);
    }

    /**
     * Sets the stopping time of a specific shift
     * @param stoppingTime a LocalTime that represents the stop time of a shift
     */
    public void setStoppingTime(LocalTime stoppingTime) {
        this.defaultschedule.setShiftStop(stoppingTime);
    }

    /**
     * Sets the lunch start time of a specific shift
     * @param lunchStart a LocalTime that represents the start of lunch of a 
     * shift
     */
    public void setLunchStart(LocalTime lunchStart) {
        this.defaultschedule.setLunchStart(lunchStart);
    }

    /**
     * Sets the lunch stopping time of a specific shift
     * @param lunchStop a LocalTime that represents the end of lunch of a 
     * shift
     */
    public void setLunchStop(LocalTime lunchStop) {
        this.defaultschedule.setLunchStart(lunchStop);
    }

    /**
     * Sets the time that should be deducted for a lunch break for a specific
     * shift
     * @param lunchDeduct an int that represents the deducted time of a lunch in
     * a shift
     */
    public void setLunchDeduct(int lunchDeduct) {
        this.defaultschedule.setLunchDeductionThreshold(lunchDeduct);
    }


    /**
     * Sets the interval of time you have before and after a specific shift
     * to determine the time that should be pushed to the beginning or end of a 
     * shift
     * @param interval an int that represents the interval time of a shift
     */

    public void setInterval(int interval) {
        this.defaultschedule.setInterval(interval);
    }


    /**
     * Sets the grace period for a specific shift which will determine if you
     * will be too late or early when clocking in or clocking out
     * @param gracePeriod an int that represents the grace period of a shift
     */

    public void setGracePeriod(int gracePeriod) {
        this.defaultschedule.setGraceperiod(gracePeriod);
    }


    /**
     * Sets the time after grace period known as the dock which will push you
     * forwards or backwards in the interval round
     * @param dock an int that represents the amount of dock time of a shift
     */

    public void setDock(int dock) {
        this.defaultschedule.setDockpenalty(dock);
    }
    
    /**
     * Fetches the total amount of time a specific shift has
     * @return the total amount of time that a shift has as an int
     */
    public int totalShiftHours() {
        
       int totalStartTimeMinutes = (getStartingTime().getHour()*60) + getStartingTime().getMinute();
       int totalStopTimeMinutes = (getStoppingTime().getHour()*60) + getStoppingTime().getMinute();
       return (totalStopTimeMinutes - totalStartTimeMinutes);
       
    }
    
    /**
     * Fetches the total amount of time you have for lunch in a specific shift
     * @return the total amount of time in lunch as an int
     */
    public int totalLunchTime() {
        
        int totalLunchStartMinutes = (getLunchStart().getHour()*60) + getLunchStart().getMinute();
        int totalLunchStopMinutes = (getLunchStop().getHour()*60) + getLunchStop().getMinute();
        return (totalLunchStopMinutes - totalLunchStartMinutes);
        
    }
    
    @Override
    public String toString() {
        
       StringBuilder s = new StringBuilder();
       s.append(description).append(": ").append(getStartingTime().toString()).append(" - ");
       s.append(getStoppingTime().toString()).append(" (").append(totalShiftHours()).append(" minutes); Lunch: ");
       s.append(getLunchStart().toString()).append(" - ").append(getLunchStop().toString()).append(" (");
       s.append(totalLunchTime()).append(" minutes)");
       
       return s.toString();
        
    }   
    
}