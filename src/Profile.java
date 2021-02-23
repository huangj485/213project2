/**
 * Profile class defines the name, department, and date hired to be used in the Employee class.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Profile {
    private String name; 
    private String department; //department code: CS, ECE, IT
    private Date dateHired;

    /**
     * Creates new Profile from name, department, dateHired
     * @param name String representing the name of the Employee
     * @param department String representing the department of the Employee
     * @param dateHired Date representing the date hired
     */
    public Profile(String name, String department, Date dateHired){
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }

    /**
     * toString function for Profile
     * @return String that contains all relevant information for a Profile
     */
    @Override
    public String toString() { 
        String ret_str = "";
        ret_str += this.name + "::";
        ret_str += this.department + "::";
        ret_str += dateHired.toString();
        return ret_str; 
    }

    /**
     * equals function for Profile
     * @param obj Object to be compared with profile
     * @return Boolean, true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Profile){
            Profile profile = (Profile) obj;
            if (profile.getName().equals(this.name) && profile.getDepartment().equals(this.department) && profile.getDateHired().equals(this.dateHired)){
                return true;
            }
        }
        return false;
    } 

    /**
     * Getter method for name
     * @return A String containing the name 
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter method for department
     * @return A String containing the department
     */
    public String getDepartment(){
        return this.department;
    }

    /**
     * Getter method for dateHired
     * @return A Date of the date hired
     */
    public Date getDateHired(){
        return this.dateHired;
    }
}
