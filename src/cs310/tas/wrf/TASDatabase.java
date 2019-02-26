package cs310.tas.wrf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Adam Stith
 */
public class TASDatabase {
    
    Connection conn;
    
    String server = ("jdbc:mysql://localhost/tas");
    String username = "root";
    String password = "CS310";
    
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;
        
    String query, key, value;
    
    boolean hasresults;
    int resultCount, columnCount, updateCount = 0;
    
    
    public TASDatabase()   {
        

        try {

            System.out.println("Connecting to " + server + "...");
            
            /* Load the MySQL JDBC Driver */
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);

            /* Test Connection */
            
            if (conn.isValid(0)) {
                
                /* Connection Open! */
                
                System.out.println("Connected Successfully!");
        
            }
            
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        } 
        
    }
    
    public void close() {
        
        try {
  
            conn.close();
            
            System.out.println("Connection Closed!");
            
        }  
        
        catch (Exception e) {
            
            System.err.println(e.toString());
        
        }
        
    }
    
    public Badge getBadge(String badgeID) {
        
        String id = null;
        String description = null;
        
        try {
            
            /* Prepare Select Query */
                
            query = "SELECT id,description FROM badge WHERE id = '"+ badgeID +"'";

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount();    
            /* Get Results */
                
            System.out.println("Getting Results ...");
                
            while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                if ( hasresults ) {
                        
                    /* Get ResultSet */
                        
                    resultset = pstSelect.getResultSet();
                    
                    while(resultset.next()) {
                        
                        id = resultset.getString(1);
                        description = resultset.getString(2);
                        
                    }
                        
                }

                else {

                    resultCount = pstSelect.getUpdateCount();  

                    if ( resultCount == -1 ) {
                        break;
                    }
                        
                }
                   
                /* Check for More Data */

                hasresults = pstSelect.getMoreResults();

            }
            
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; 
            } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; 
            } catch (Exception e) {} }
            
        }
        
        Badge b = new Badge(id, description);
        
        return b;
        
    }
    
    public Punch getPunch(int punchID) {
        
        int id = 0, terminalID = 0, punchTypeID = 0;
        String badgeID = "";
        Timestamp originalTimestamp = null;
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT id,terminalid,badgeid,originaltimestamp,"
                    + "punchtypeid FROM tas.punch WHERE id = " + punchID;
            
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 
            
            /* Get Results */
                
            System.out.println("Getting Results ...");
                
            while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                if ( hasresults ) {
                        
                    /* Get ResultSet */
                        
                    resultset = pstSelect.getResultSet();
                    
                    while(resultset.next()) {
                         
                        id = resultset.getInt(1);
                        terminalID = resultset.getInt(2);
                        badgeID = resultset.getString(3);
                        originalTimestamp = resultset.getTimestamp(4);
                        punchTypeID = resultset.getInt(5);
  
                    }
                        
                }

                else {

                    resultCount = pstSelect.getUpdateCount();  

                    if ( resultCount == -1 ) {
                        break;
                    }
                        
                }
                   
                /* Check for More Data */

                hasresults = pstSelect.getMoreResults();

            }

        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; 
            } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; 
            } catch (Exception e) {} }
            
        }
        
        Punch p = new Punch(id, terminalID, badgeID, originalTimestamp, punchTypeID);
        
        return p;
        
    }
    
    public Shift getShift(int shiftID) {
        
        String description = null; 
        Time startingTime = null;
        Time stoppingTime = null;
        Time lunchStart = null;
        Time lunchStop = null;

        int lunchDeduct = 0;
        int interval = 0;
        int gracePeriod = 0;
        int dock = 0;
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT description,start,stop,`interval`,graceperiod,"
                    + "lunchstart,lunchstop,lunchdeduct FROM tas.shift WHERE id"
                    + " = "+ shiftID;

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount();
            
            /* Get Results */
                
            System.out.println("Getting Results ...");
                
            while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                if ( hasresults ) {
                        
                    /* Get ResultSet */
                        
                    resultset = pstSelect.getResultSet();
                    
                    while(resultset.next()) {
                        
                       description = resultset.getString(1);
                       startingTime = resultset.getTime(2);
                       stoppingTime = resultset.getTime(3);
                       interval = resultset.getInt(4);
                       gracePeriod = resultset.getInt(5);                                           
                       lunchStart = resultset.getTime(6);
                       lunchStop = resultset.getTime(7);
                       lunchDeduct = resultset.getInt(8);
                       
                    }
                        
                }

                else {

                    resultCount = pstSelect.getUpdateCount();  

                    if ( resultCount == -1 ) {
                        break;
                    }
                        
                }
                   
                /* Check for More Data */

                hasresults = pstSelect.getMoreResults();

            }

        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; 
            } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; 
            } catch (Exception e) {} }
            
        }
        
        Shift s = new Shift(description, startingTime, interval, gracePeriod, dock, stoppingTime, lunchStart, lunchStop, lunchDeduct);
        
        return s;
        
    }
    
    public Shift getShift(Badge badge) {
        
        String description = null; 
        Time startingTime = null;
        Time stoppingTime = null;
        Time lunchStart = null;
        Time lunchStop = null;

        int lunchDeduct = 0;
        int interval = 0;
        int gracePeriod = 0;
        int dock = 0;
        
        Shift s = null;
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT shiftid FROM tas.employee WHERE badgeid = '"
                    + badge.getID() + "'";

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            //System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 
            
            /* Get Results */
   
                    /* Get ResultSet */
                        
                    resultset = pstSelect.getResultSet();
                    
                    resultset.next();
   
                    s = getShift(resultset.getInt(1));    

                    }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; 
            } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; 
            } catch (Exception e) {} }
            
        }
        
        return s;
        
    }
    

    
}    