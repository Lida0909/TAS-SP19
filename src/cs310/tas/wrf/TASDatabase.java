package cs310.tas.wrf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
    
    public void getBadge(String badgeID) {
        
        
        try {
            
            /* Prepare Select Query */
                
            query = "SELECT id,description FROM badge WHERE id = "+ badgeID;

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
                        
                        for (int i = 1; i <= columnCount; i++) {
                            
                            System.out.println(resultset.getString(i));
                            
                        }
                        
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
        
    }
    
    public void getPunch(String punchID) {
        
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
                        
                        for (int i = 1; i <= columnCount; i++) {
                            
                            System.out.println(resultset.getString(i));
                            
                        }
                        
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
        
    }
    
    public void getShift(int shiftID) {
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT id,description,start,stop,`interval`,graceperiod,"
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
                        
                        for (int i = 1; i <= columnCount; i++) {
                            
                            System.out.println(resultset.getString(i));
                            
                        }
                        
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
        
    }
    
    public void getShift(String badgeID) {
        
        try {
        
            /* Prepare Select Query */
                
            query = "SELECT shiftid FROM tas.employee WHERE badgeid = "
                    + badgeID;

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
   
                    getShift(Integer.parseInt(resultset.getString(1)));   

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
        
    }
    

    
}    