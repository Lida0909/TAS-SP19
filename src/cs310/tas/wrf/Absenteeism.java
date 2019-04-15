
package cs310.tas.wrf;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Absenteeism class is an abstraction of absenteeism percentage of an
 * employee for a pay period. This class is used to calculate the absenteeism of
 * an employee.
 * @author War Room F
 */
public class Absenteeism {
    
    private String badgeid;
    private long payperiod;
    private double percentage;
    
    /**
     * 
     * @param badgeid a String that represents the id of a specific badge
     * @param payperiod a long that represents the pay period for an employee
     * @param percentage a double that represents the absenteeism percentage of
     * a pay period for an employee
     */
    public Absenteeism(String badgeid, long payperiod, double percentage) {
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(payperiod);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        
        this.badgeid = badgeid;
        this.payperiod = cal.getTimeInMillis();
        this.percentage = percentage;
        
    }

    /**
     * 
     * @return the Badgeid of an employee as a String
     */
    public String getBadgeid() {
        return badgeid;
    }

    /**
     * 
     * @param badgeid a String that represents the id of a specific badge
     */
    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    /**
     * 
     * @return the pay period for an employee as a long
     */
    public long getPayperiod() {
        return payperiod;
    }

    /**
     * 
     * @param payperiod a long that represents the pay period of an employee
     */
    public void setPayperiod(long payperiod) {
        this.payperiod = payperiod;
    }

    /**
     * 
     * @return the absenteeism percentage as a double
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * 
     * @param percentage a double that represents the absenteeism percentage of 
     * a pay period for an employee
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    
    @Override
    public String toString() {
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(getPayperiod());
        String payperiod = (new SimpleDateFormat("MM-dd-yyyy")).format(cal.getTime());
        String a = String.format("%.2f", getPercentage());
      
        StringBuilder s = new StringBuilder();
        s.append("#").append(getBadgeid()).append(" (Pay Period Starting ").append(payperiod);
        s.append("): ").append(a).append("%");
        return s.toString();
        
    }
     
}

