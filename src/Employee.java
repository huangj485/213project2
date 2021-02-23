import java.text.DecimalFormat;
/**
 * Employee class defines the common data and operations for all employee types; each employee has a profile that uniquely identifies the employee.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Employee {
    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###,##0.00");

    private Profile profile;
    protected double payment;

    /**
     * Creates an employee from profile
     * @param profile Profile of the Employee to set
     */
    public Employee(Profile profile){
        this.profile = profile;
        this.payment = 0;
    }

    /** Function to calculate payment
     * Since the calculate payment of just an employee will never be called, this function contains nothing; it only exists to be overriden.
     */
    public void calculatePayment() {} //i exist only because i am told to 

    /** equals function for Employee
     * @param obj The object to be compared to Employee
     * @return A boolean, true if equivalent, false if not
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Employee){
            Employee obj2 = (Employee) obj;
            if (obj2.getProfile().equals(this.profile)){
                return true;
            }
        }
        return false;
    } 

    /** toString function for Employee
     * @return A string, representing the contents of Employee
     */
    @Override
    public String toString(){
        String ret_str = "";
        ret_str += profile.toString() + "::"; 
        ret_str += "Payment $" + DECIMAL_FORMAT.format(payment); 
        return ret_str;
    }

    /**
     * Getter method for profile
     * @return Profile of the Employee
     */
    public Profile getProfile(){
        return this.profile;
    }
}
