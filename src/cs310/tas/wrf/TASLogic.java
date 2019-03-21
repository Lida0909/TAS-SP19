package cs310.tas.wrf;

import java.util.ArrayList;

/**
 * The TASLogic class contains functions that will be needed for use by  
 * payroll, mangers, etc.
 * @author War Room F
 */
public class TASLogic {
    
    /**
     * Constant variable for the punch type of a clock-in.
     */
    public static final int CLOCKIN = 1;

    /**
     * Constant variable for the punch type of a clock-out.
     */
    public static final int CLOCKOUT = 0;
    
    /**
     * Calculates the total minutes accrued by an employee on a single shift.
     * @param dailypunchlist an ArrayList of Punch objects for a shift
     * @param shift a shift object containing the shift rules
     * @return the total amount of minutes accrued in one shift as an int
     */
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, 
            Shift shift) {

        int totalMin = 0;
        long totalMillis = 0;
        long inTime = 0;
        long outTime = 0;
        int punchCounter = 0;
        int lunchTime = 30;
        
        for(int i = 0; i < dailypunchlist.size(); i++) {
            
           if (dailypunchlist.get(i).getPunchtypeid() == CLOCKIN) {
               
               inTime = dailypunchlist.get(i).getAdjustedTimeStamp().getTime();
               punchCounter++;
               continue;
               
           }

           if (dailypunchlist.get(i).getPunchtypeid() == CLOCKOUT) {
               
               outTime = dailypunchlist.get(i).getAdjustedTimeStamp().getTime();
               punchCounter++;
               
           }
           
           if (inTime != 0 && outTime != 0) {
               
               totalMillis += outTime - inTime;
               
           }
           
           
           inTime = 0;
           outTime = 0;
           
        }
        
        if (totalMillis != 0) {
            
            totalMin = (int) (totalMillis/60000);
            
        }
        
        if (totalMin > shift.getlunchDeduct() && punchCounter <= 3) {
            
            totalMin -= lunchTime;
            
        }
        
        return totalMin;
        
    }    
    
}
