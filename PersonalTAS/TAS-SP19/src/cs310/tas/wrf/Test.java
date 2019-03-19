package cs310.tas.wrf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Adam Stith
 */
public class Test {
    
    public static void main(String[] args) throws ParseException {
        
        TASDatabase db = new TASDatabase();

        /* Get Punch */
        
        Punch p = db.getPunch(1087);
        Badge b = db.getBadge(p.getBadgeid());
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginaltimestamp());
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
		
        /* Compute Pay Period Total */
        
        int m = TASLogic.calculateTotalMinutes(dailypunchlist, s);
		
        /* Compare to Expected Value */
        
        assertEquals(360, m);
        
    }
    
}    