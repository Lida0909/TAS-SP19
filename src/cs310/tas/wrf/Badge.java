
package cs310.tas.wrf;

/**
 * The Badge class is an abstraction of an employee's id badge. This badge is used to identify which employee has clocked in or out.
 * @author War Room F
 * 
 */
public class Badge {
    
    private String id;
    private String name;
    
    
    /**
     * @param id   a string that ...DESCRIPTION
     * @param name a string that ...DESCRIPTION
    */
    public Badge(String id, String name) {
        
        this.id = id;
        this.name = name;
            
    }
    /**
     * @return     the string description of the badge as an id number and name
     */
    @Override

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("#").append(id).append(" (").append(name).append(")");
        return s.toString();
    }
    
    /**
     * 
     * @return the badge id as a String
     */
    public String getBadgeid(){
        return id;
    }
    
    /**
     * 
     * @return  @return the badge name as a String
     */
    public String getName(){
        return name;
    }
}
