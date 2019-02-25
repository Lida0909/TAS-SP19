/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.tas.wrf;

/**
 *
 * @author MattD
 */
public class Badge {
    
    private String id;
    private String name;
    
    public Badge(String id, String name) {
        
        this.id = id;
        this.name = name;
            
    }
    
    @Override

    public String toString() {
        return ("#" + id + "(" + name + ")");
    }
    
    public String getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }
}
