/**
 * Profile class defines the name, department, and date hired to be used in the Employee class.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Profile {
    private String name; //employee’s name in the form “lastname,firstname”
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

    @Override
    public String toString() { 
        String ret_str = "";
        ret_str += this.name + "::";
        ret_str += this.department + "::";
        ret_str += dateHired.toString();
        return ret_str;
    }

    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Profile){
            if (obj.getName().equals(this.name) && obj.getDepartment().equals(this.department) && obj.getDateHired().equals(this.dateHired)){
                return true;
            }
        }
        return false;
    } //compare name, department and dateHired

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
