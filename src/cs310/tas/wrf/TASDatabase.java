package cs310.tas.wrf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.HashMap;

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
     * Opens a new connection to the SQL sever that
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
     * Retrieves a Badge from the database.
     * @param badgeID a String that represents the ID number for an employee
     * @return a new Badge object created from the database information based
     * on the given badgeID.
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
     * Retrieves a Punch from the database.
     * @param punchID an int that represents the ID number of a Punch
     * @return a new Punch object created from the database information based
     * on the given punchID.
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
        

        Punch p = new Punch(id, terminalID, badgeID, originalTimestamp, punchTypeID);
        return p;
    }

        public DailySchedule getDailySchedule(int dsID) {
        
        int id = 0;
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

            query = "SELECT * FROM dailyschedule where id = '" + dsID + "'";

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
                        
                       //description = resultset.getString(1);
                       startingTime = resultset.getTime(2).toString().split(":");
                       stoppingTime = resultset.getTime(3).toString().split(":");
                       interval = resultset.getInt(4);
                       gracePeriod = resultset.getInt(5);  
                       dock = resultset.getInt(6);
                       lunchStart = resultset.getTime(7).toString().split(":");
                       lunchStop = resultset.getTime(8).toString().split(":");
                       lunchDeduct = resultset.getInt(9);
                       
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
            System.out.println("catch during getDailySchedule(int dsID)");
            
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
       
        DailySchedule dailySchedule = new DailySchedule(shiftStartHour, shiftStartMinute,
                shiftStopHour, shiftStopMinute,interval, gracePeriod, dock, lunchStartHour, 
                lunchStartMinute, lunchStopHour,lunchStopMinute, lunchDeduct);
        
        return dailySchedule;
        }
    
    /**
     * Retrieves a Shift from the database.
     * @param shiftID an int that represents the ID number of a Shift
     * @return a new Shift object created from the database information based
     * on the given shiftID
     */
    public Shift getShift(int shiftID) {
        
        int id = 0;
        String description = null; 
        String[] startingTime = null;
        String[] stoppingTime = null;
        String[] lunchStart = null;
        String[] lunchStop = null;

        int lunchDeduct = 0;
        int interval = 0;
        int gracePeriod = 0;
        int dock = 0;
        
        
        DailySchedule defaultSchedule = getDailySchedule(shiftID);
/*        
        try {
        
            // Prepare Select Query 

            query = "SELECT * FROM dailyschedule where id = '" + shiftID + "'";

            pstSelect = conn.prepareStatement(query);
                
            // Execute Select Query 
                
            System.out.println("Submitting Query ...");
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount();
            
            // Get Results 
                
            System.out.println("Getting Results ...");
                
            while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                if ( hasresults ) {
                        
                    // Get ResultSet 
                        
                    resultset = pstSelect.getResultSet();
                    
                    while(resultset.next()) {
                        
                       //description = resultset.getString(1);
                       startingTime = resultset.getTime(2).toString().split(":");
                       stoppingTime = resultset.getTime(3).toString().split(":");
                       interval = resultset.getInt(4);
                       gracePeriod = resultset.getInt(5);  
                       dock = resultset.getInt(6);
                       lunchStart = resultset.getTime(7).toString().split(":");
                       lunchStop = resultset.getTime(8).toString().split(":");
                       lunchDeduct = resultset.getInt(9);
                       
                    }
                        
                }

                else {

                    resultCount = pstSelect.getUpdateCount();  

                    if ( resultCount == -1 ) {
                        break;
                    }
                        
                }
                   
                // Check for More Data 

                hasresults = pstSelect.getMoreResults();

            }

        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        // Close Other Database Objects 
        
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
       
        DailySchedule dailyschedule = new DailySchedule(shiftStartHour, shiftStartMinute,
                shiftStopHour, shiftStopMinute,interval, gracePeriod, dock, lunchStartHour, 
                lunchStartMinute, lunchStopHour,lunchStopMinute, lunchDeduct);
        */
        try {
        
            /* Prepare Select Query */
            
            query = "SELECT * FROM shift WHERE id = '" + shiftID + "'";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    

            while(resultset.next()) {
                id = resultset.getInt(1);
                description = resultset.getString(2);
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
        
        Shift s = new Shift(description, id, defaultSchedule);
        System.out.println(s.toString());
        return s;
        
    }
    
    /**
     * Retrieves a Shift from the database.
     * @param badge a Badge object that represents an employee's badge
     * @return a new Shift object created from the database information based
     * on the given Badge object
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
                    //call to get shift with a shift ID
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
    
    public Shift getShift(Badge b, long ts){
        ResultSet recurring_all;
        ResultSet recurring_this;
        ResultSet temporary_all;
        ResultSet temporary_this;
        Shift s = getShift(b);
        HashMap exceptions = new HashMap<Integer, DailySchedule>();
        
        /*
       SQL
        */
        
        
        //get recurring overrides for all employees
        try {
        
            /* Prepare Select Query */
            //select recurring overrides for all employees.
            query = "SELECT * FROM scheduleoverride WHERE badgeid is null and end is null";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    
            recurring_all = resultset;
            
            /*
            while(resultset.next()) {
                
                //id = resultset.getInt(1);
                //description = resultset.getString(2);
            }*/
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
        try {
        
            /* Prepare Select Query */
            //select recurring overrides for this employee.
            query = "SELECT * FROM scheduleoverride WHERE badgeid is '" + b.getBadgeid() + "' and end is null";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    
            recurring_this = resultset;
            
            /*
            while(resultset.next()) {
                
                //id = resultset.getInt(1);
                //description = resultset.getString(2);
            }*/
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
        
        try {
        
            /* Prepare Select Query */
            //select temporary overrides for all employees.
            //REPLACE 'val' WITH THE TIME STAMP IN A FORMAT THAT SQL CAN UNDERSTAND.
            //can val just be left as a long integer??
            query = "SELECT * FROM scheduleoverride where start <= convert(long, " + ts + ") and convert(long, " + ts + ") <= end and badgeid is null;";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    
            temporary_all = resultset;
            
            /*
            while(resultset.next()) {
                
                //id = resultset.getInt(1);
                //description = resultset.getString(2);
            }*/
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
        try {
        
            /* Prepare Select Query */
            //select temporary overrides for this employees.
            //REPLACE 'val' WITH THE TIME STAMP IN A FORMAT THAT SQL CAN UNDERSTAND.
            //can val just be left as a long integer??
            query = "SELECT * FROM scheduleoverride where start <= convert(long, " + ts + ") and convert(long, " + ts + ") <= end; and badgeid = '" + b.getBadgeid() + "';";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    
            temporary_this = resultset;
            
            /*
            while(resultset.next()) {
                
                //id = resultset.getInt(1);
                //description = resultset.getString(2);
            }*/
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
        
        
        
        Date d = new Date(ts);
        d.getDay();
        
        
        
        //s.setDailySchecule();
        
        return s;
    }
    
    
    /**
     * Inserts a new Punch into the database.
     * @param p a Punch object that represents an individual time clock punch.
     * @return the newPunchID that is assigned by the database when a new punch 
     * is inserted
     */
    public int insertPunch(Punch p) {
        
        String badgeID = p.getBadgeid();
        int terminalID = p.getTerminalid();
        int punchTypeID = p.getPunchtypeid();
        int newPunchID = p.getId();
        long originalTimeStampLong = p.getOriginaltimestamp();
        
        Timestamp originalTimeStamp = new Timestamp(originalTimeStampLong);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedots  = sdf.format(originalTimeStamp);
        
        try {
            
            /* Prepare Insert Query */

            query = "INSERT INTO punch (terminalid,badgeid,originaltimestamp,"
                    + "punchtypeid) VALUES('" + terminalID 
                    + "','" + badgeID + "','" + formattedots
                    + "','" + punchTypeID + "')";

            System.out.println(query);
            
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
     * Retrieves a list of Punches from a single day from the database.
     * @param b a Badge object that represents an employee's badge
     * @param ts a long that represents the amount of milliseconds from a 
     * timestamp 
     * @return an ArrayList object that contains all of the punches from the
     * date represented in the 'ts' parameter
     */
    public ArrayList getDailyPunchList(Badge b, long ts) {
        
        ArrayList list = new ArrayList();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(ts);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());
        cal.add( Calendar.DATE, 1 );
        String datePlus1 = format.format(cal.getTime());
        int lastPunchType = 0;
        
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
                        
                        if (resultset.isLast()) {
                            lastPunchType = resultset.getInt(5);
                            break;  
                            
                        }
                        
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
        
        try {
        
            /* Prepare Select Query */
            
            query = "SELECT id,terminalid,badgeid,originaltimestamp,"
                    + "punchtypeid FROM tas.punch WHERE badgeid = '"
                    + b.getBadgeid() + "' AND originaltimestamp LIKE '%"
                    + datePlus1 + "%'";
            
            

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
                        
                        if (resultset.isLast() ) {
                            
                            break;  
                            
                        }
                        
                        resultset.next(); 
                        
                        if (resultset.getInt(5) == TASLogic.CLOCKOUT && lastPunchType == TASLogic.CLOCKIN) {
                                                 
                        list.add(new Punch(resultset.getInt(1)
                                ,resultset.getInt(2),resultset.getString(3)
                                ,resultset.getTimestamp(4)
                                ,resultset.getInt(5)));
                        
                        }
                        
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
    
    /**
     * 
     * @param timestamp a long that represents the amount of milliseconds of a 
     * timestamp 
     * @return a long that returns the adjusted timestamp in milliseconds
     */
     private long adjust(long timestamp) {
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        
        return cal.getTimeInMillis();
        
    }
    
     /**
      * Retrieves a list of Punches from a single pay period from the database.
      * @param b a Badge object that represents an employee's badge
      * @param timestamp a long that represents the amount of milliseconds from
      * a timestamp 
      * @return an ArrayList object that contains all of the punches from the
      * pay period represented in the timestamp parameter
      */
    public ArrayList getPayPeriodPunchList(Badge b, long timestamp) {
        
        ArrayList<Punch> list = new ArrayList<Punch>();
        GregorianCalendar cal1 = new GregorianCalendar();
        cal1.setTimeInMillis(adjust(timestamp));
        
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.setTimeInMillis(adjust(timestamp));
        int dayofMonth = cal2.get(Calendar.DATE);
        cal2.set(Calendar.DATE, dayofMonth+7);
        
        try {
        
            /* Prepare Select Query */
            
            query = "SELECT * FROM punch WHERE badgeid = '" + b.getBadgeid() + "'";
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */

            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 

            /* Get ResultSet */

            resultset = pstSelect.getResultSet();                    

            while(resultset.next()) {
                
                Timestamp ts = resultset.getTimestamp(4);
                GregorianCalendar cal3 = new GregorianCalendar();
                cal3.setTimeInMillis(ts.getTime());
                
                if(cal3.after(cal1) && cal3.before(cal2)) {
                    list.add(new Punch(resultset.getInt(1),resultset.getInt(2),resultset.getString(3)
                        ,resultset.getTimestamp(4),resultset.getInt(5)));
                }

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
    
    /**
     * 
     * @param badgeid a String object that represents the employee's badge id
     * @param timestamp a long that represents the amount of milliseconds from
     * a timestamp 
     * @return an Absenteeism object representing the absentee percentage of an 
     * individual employee
     */
    public Absenteeism getAbsenteeism(String badgeid, long timestamp) {
        
        String badgeID = "";
        long ts = 0;
        double percentage = 0;
        Absenteeism a = null;
        //boolean hasData = false;
        
        Timestamp payperiod = new Timestamp(adjust(timestamp));
        String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(payperiod);
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT * FROM absenteeism WHERE badgeid = '" + badgeid + "' AND payperiod = '" + s + "'"; 
            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            hasresults = pstSelect.execute();                
            resultset = pstSelect.getResultSet();
            metadata = resultset.getMetaData();
            columnCount = metadata.getColumnCount(); 
            
            /* Get Results */
                
            while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                if ( hasresults ) {
  
                    resultset = pstSelect.getResultSet();  
                    
                    //hasData = resultset.next();
                    
                    while(resultset.next()) {                 
                        badgeID = resultset.getString(1);
                        Timestamp t = resultset.getTimestamp(2);
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTimeInMillis(t.getTime());
                        ts = cal.getTimeInMillis();
                        percentage = resultset.getDouble(3);
                        
                        a = new Absenteeism(badgeid, ts, percentage);
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
       
        return a;
        
    }
    
    /**
     * inserts the absenteeism of an employee into the database
     * @param a an Absenteeism object that represents the percentage an employee
     * is absent
     */
    public void insertAbsenteeism(Absenteeism a) {
        
        String badgeID = a.getBadgeid();
        long ts = a.getPayperiod();
        double percentage = a.getPercentage();
        
        Timestamp payperiod = new Timestamp(ts);
        String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(payperiod);
        String pay = (new SimpleDateFormat("MM-dd-yyyy")).format(adjust(ts));
        String st = "#" + badgeID + " (Pay Period Starting " + pay + "): " + percentage;
        
        try {
            
            /* Prepare Insert/Update Query */
            
            if(getAbsenteeism(badgeID, ts) == null) {
                query = "INSERT INTO absenteeism (badgeid, payperiod, percentage) VALUES('" + badgeID
                    + "', '" + s + "', '" + percentage + "')";
            }
            else {
                query = "UPDATE absenteeism SET percentage = '" + percentage + 
                        "' WHERE badgeid = '" + badgeID + "' AND payperiod = '" + s + "'";
            }

            pstSelect = conn.prepareStatement(query);
                
            /* Execute Select Query */
                
            hasresults = pstSelect.execute();
       
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
        /* Close Other Database Objects */
        
        finally {

            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; 
            } catch (Exception e) {} }
            
        }
        
    }
        
}
        
