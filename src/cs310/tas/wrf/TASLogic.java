package cs310.tas.wrf;

import java.util.ArrayList;

/**
 *
 * @author War Room F
 */
public class TASLogic {
    
    public static final int CLOCKIN = 1;
    public static final int CLOCKOUT = 0;
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, 
            Shift shift) {

        int totalMin = 0;
        long totalMillis = 0;
        long inTime = 0;
        long outTime = 0;
        int punchCounter = 0;
        
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
            
            totalMin -= 30;
            
        }
        
        return totalMin;
        
    }
    
}
