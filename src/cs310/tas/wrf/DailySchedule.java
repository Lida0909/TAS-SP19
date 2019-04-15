
package cs310.tas.wrf;

import java.time.LocalTime;

public class DailySchedule {
    
    private LocalTime shiftStart;
    private LocalTime shiftStop;
    private LocalTime lunchStart;
    private LocalTime lunchStop;
    private int interval;
    private int graceperiod;
    private int dockpenalty;
    private int lunchDeductionThreshold;
    
    public DailySchedule(int shiftStartHour, int shiftStartMinute, int shiftStopHour,
            int shiftStopMinute, int interval, int graceperiod, int dockpenalty, 
            int lunchStartHour, int lunchStartMinute, int lunchStopHour, int lunchStopMinute, int lunchDeductionThreshold) {
        this.shiftStart = LocalTime.of(shiftStartHour, shiftStartMinute);
        this.shiftStop = LocalTime.of(shiftStopHour, shiftStopMinute);
        this.lunchStart = LocalTime.of(lunchStartHour, lunchStartMinute);
        this.lunchStop = LocalTime.of(lunchStopHour, lunchStopMinute);
        this.interval = interval;
        this.graceperiod = graceperiod;
        this.dockpenalty = dockpenalty;
        this.lunchDeductionThreshold = lunchDeductionThreshold;
        
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftStop() {
        return shiftStop;
    }

    public void setShiftStop(LocalTime shiftStop) {
        this.shiftStop = shiftStop;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public void setGraceperiod(int graceperiod) {
        this.graceperiod = graceperiod;
    }

    public int getDockpenalty() {
        return dockpenalty;
    }

    public void setDockpenalty(int dockpenalty) {
        this.dockpenalty = dockpenalty;
    }

    public int getLunchDeductionThreshold() {
        return lunchDeductionThreshold;
    }

    public void setLunchDeductionThreshold(int lunchDeductionThreshold) {
        this.lunchDeductionThreshold = lunchDeductionThreshold;
    } 
    
}
