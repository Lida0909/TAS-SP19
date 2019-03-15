package cs310.tas.wrf;

/**
 * The Badge class is an abstraction of an employee's id badge. This badge is
 * used to identify which employee has clocked in or out.
 * @author War Room F
 * 
 */
public class Badge {
    
    private String id;
    private String name;
    
    
    /**
     * Used to create a new badge object.
     * @param id   a string that represents the badge id
     * @param name a string that represents the badge name
    */
    public Badge(String id, String name) {
        
        this.id = id;
        this.name = name;
            
    }
    /**
     * The string description of the badge.
     * @return the string description of the badge as an id number and name
     */
    @Override

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("#").append(id).append(" (").append(name).append(")");
        return s.toString();
    }
    
    /**
     * Getter method for id.
     *
     * @return the badge id as a String
     */
    public String getBadgeid(){
        return id;
    }
    
    /**
     * Getter method for name.
     * @return the badge name as a String
     */
    public String getName(){
        return name;
    }
}
