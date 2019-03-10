package cs310.tas.wrf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * The TASDatabase class is used by the TAS to connect to the database
 * and to input or retrieve information from the database.
 * @author War Room F
 */
public class TASDatabase {
    
    Connection conn;
    
    String server = ("jdbc:mysql://localhost/tas");
    String username = "tasuser";
    String password = "CS310";
    
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;
        
    String query, key, value;
    
    boolean hasresults;
    int resultCount, columnCount, updateCount = 0;
    
    /**
     * Constructor for TASDatabase. Opens a new connection to the SQL sever.
     * uses the connection variables (server, username, password) to initiate 
     * the connection.
     */
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
    
    /**
     * Closes the connection to the SQL sever.
     */
    public void close() {
        
        try {
  
            conn.close();
            
            System.out.println("Connection Closed!");
            
        }  
        
        catch (Exception e) {
            
            System.err.println(e.toString());
        
        }
        
    }
    
    /**
     *
     * @param badgeID a String that represents the ID number for an employee
     * @return a new Badge object created from the database information based
     * On the given badgeID.
     */
    public Badge getBadge(String badgeID) {
        
        String id = null;
        String description = null;
        
        try {
            
            /* Prepare Select Query */
                
            query = "SELECT id,description FROM badge WHERE id = '"
                    + badgeID +"'";

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
    
    /**
     *
     * @param punchID
     * @return
     */
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
        
        Punch p = new Punch(id, terminalID, badgeID, originalTimestamp,
                punchTypeID);
        
        
        return p;
        
    }
    
    /**
     *
     * @param shiftID
     * @return
     */
    public Shift getShift(int shiftID) {
        
        String description = null; 
        String[] startingTime = null;
        String[] stoppingTime = null;
        String[] lunchStart = null;
        String[] lunchStop = null;

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
                       startingTime = resultset.getTime(2).toString().split(":");
                       stoppingTime = resultset.getTime(3).toString().split(":");
                       interval = resultset.getInt(4);
                       gracePeriod = resultset.getInt(5);                                           
                       lunchStart = resultset.getTime(6).toString().split(":");
                       lunchStop = resultset.getTime(7).toString().split(":");
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
        
        int shiftStartHour = Integer.parseInt(startingTime[0]);
        int shiftStartMinute = Integer.parseInt(startingTime[1]);
        int shiftStopHour = Integer.parseInt(stoppingTime[0]);
        int shiftStopMinute = Integer.parseInt(stoppingTime[1]);
        int lunchStartHour = Integer.parseInt(lunchStart[0]);
        int lunchStartMinute = Integer.parseInt(lunchStart[1]);
        int lunchStopHour = Integer.parseInt(lunchStop[0]);
        int lunchStopMinute = Integer.parseInt(lunchStop[1]);
        
        Shift s = new Shift(description, shiftStartHour, shiftStartMinute, 
                interval, gracePeriod, dock, shiftStopHour, shiftStopMinute,
                lunchStartHour, lunchStartMinute, lunchStopHour,
                lunchStopMinute, lunchDeduct);
        
        return s;
        
    }
    
    /**
     *
     * @param badge
     * @return
     */
    public Shift getShift(Badge badge) {
        
        Shift s = null;
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT shiftid FROM tas.employee WHERE badgeid = '"
                    + badge.getBadgeid() + "'";

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
    
    /**
     *
     * @param p
     * @return
     */
    public int insertPunch(Punch p) {
        
        String badgeID = p.getBadgeid();
        int terminalID = p.getTerminalid();
        int punchTypeID = p.getPunchtypeid();
        int newPunchID = p.getId();
        Timestamp originalTimeStamp = p.getOriginaltimestamp2();
        
        try {
            
            /* Prepare Insert Query */

            query = "INSERT INTO punch (terminalid,badgeid,originaltimestamp,"
                    + "punchtypeid) VALUES('" + terminalID 
                    + "','" + badgeID + "','" + originalTimeStamp
                    + "','" + punchTypeID + "')";

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();
            
            System.out.println("Punch Inserted!");
       
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {

            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
        }
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT id FROM punch ORDER BY id DESC";
                    

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 
            
            /* Get Results */
   
            System.out.println("Getting Results ...");    
            
                    /* Get ResultSet */

                    resultset = pstSelect.getResultSet();
                    resultset.next();
                    newPunchID = resultset.getInt(1);

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
        
        return newPunchID;
        
    }
    
    /**
     *
     * @param b
     * @param ts
     * @return
     */
    public ArrayList getDailyPunchList(Badge b, long ts) {
        
        ArrayList list = new ArrayList();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(ts);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());        

        try {
        
            /* Prepare Select Query */
            
            query = "SELECT id,terminalid,badgeid,originaltimestamp,"
                    + "punchtypeid FROM tas.punch WHERE badgeid = '"
                    + b.getBadgeid() + "' AND originaltimestamp LIKE '%"
                    + date + "%'";
            
            

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            //System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 
            
            /* Get Results */
   
            System.out.println("Getting Results ...");
            
                    /* Get ResultSet */
                        
                    resultset = pstSelect.getResultSet();                    
                    
                    
                    for(int i = 1; i < columnCount; i++) {
                        
                        resultset.next();
                        list.add(new Punch(resultset.getInt(1)
                                ,resultset.getInt(2),resultset.getString(3)
                                ,resultset.getTimestamp(4)
                                ,resultset.getInt(5)));
                    
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
        
        return list;
        
    }
        
}
