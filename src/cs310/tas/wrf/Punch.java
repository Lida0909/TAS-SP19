/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.tas.wrf;

import cs310.tas.wrf.Badge;
import java.util.*;

/**
 *
 * @author Matt Denman and William Penwell
 */
public class Punch {
    
    private Badge badge = new Badge(String, String);
    private int terminalID;
    private int punchTypeID;
    private int id;
    private String description;
        
    Punch (Badge badge, int terminalID, int punchTypeID){
        
        this.badge = badge;
        this.terminalID = terminalID;
        this.punchTypeID = punchTypeID;
        
        GregorianCalendar Cal = new GregorianCalendar();
     
        
    }
    
    public String printOriginalTimeSstamp() {
        
        GregorianCalendar Cal = new GregorianCalendar();
        
         
        return "";
    }
    
    public String printAdjustedTimeStamp() {
        return "";
    }
    
    Punch (int id, String description) {
        
        this.id = id;
        this.description = description;
    }
    
}

