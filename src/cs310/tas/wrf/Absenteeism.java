
package cs310.tas.wrf;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Absenteeism {
    
    private String badgeid;
    private long payperiod;
    private double percentage;
    
    public Absenteeism(String badgeid, long payperiod, double percentage) {
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(payperiod);
        Timestamp t = new Timestamp(payperiod);
        LocalDateTime l = t.toLocalDateTime();
        String day = l.getDayOfWeek().toString();
        int dayofMonth = cal.get(Calendar.DATE);
        switch(day) {
            case "MONDAY":
                cal.set(Calendar.DATE, dayofMonth-1);
                break;
            case "TUESDAY":
                cal.set(Calendar.DATE, dayofMonth-2);
                break;
            case "WEDNESDAY":
                cal.set(Calendar.DATE, dayofMonth-3);
                break;
            case "THURSDAY":
                cal.set(Calendar.DATE, dayofMonth-4);
                break;
            case "FRIDAY":
                cal.set(Calendar.DATE, dayofMonth-5);
                break;
            case "SATURDAY":
                cal.set(Calendar.DATE, dayofMonth-6);
                break;
        } 
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        
        this.badgeid = badgeid;
        this.payperiod = cal.getTimeInMillis();
        this.percentage = percentage;
        
    }

    public String getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    public long getPayperiod() {
        return payperiod;
    }

    public void setPayperiod(long payperiod) {
        this.payperiod = payperiod;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    
    @Override
    public String toString() {
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(getPayperiod());
        String payperiod = (new SimpleDateFormat("MM-dd-yyyy")).format(cal.getTime());
        String pattern = "###.##";
        DecimalFormat percentage = new DecimalFormat(pattern);
        String a = String.format("%.2f", getPercentage());
      
        StringBuilder s = new StringBuilder();
        s.append("#").append(getBadgeid()).append(" (Pay Period Starting ").append(payperiod);
        s.append("): ").append(a).append("%");
        return s.toString();
        
    }
     
}

