/**
 * Fulltime class extends the Employee class and includes specific data and operations to a full-time employee.
 * @author Jerry Huang, Adrian Thamburaj
 */
import java.text.DecimalFormat;
public class Fulltime extends Employee {
    private static final int PAY_PERIODS_PER_YEAR = 26;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###,##0.00");

    protected int annualSalary;

    /** Creates a Fulltime Employee from profile, salary
     * @param profile Profile to be set
     * @param salary salary to be set
     */
    public Fulltime (Profile profile, int salary){
        super(profile);
        this.annualSalary = salary;
    }

    /**
     * Function to calculate payment for Fulltime
     */
    @Override
    public void calculatePayment(){
        this.payment = ((double)annualSalary) / PAY_PERIODS_PER_YEAR;
    }

    /**
     * equals function for Fulltime
     * @param obj Object to be compared to Fulltime instance
     * @return Boolean, true if equivalent, false if not
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Fulltime){
            Fulltime obj2 = (Fulltime) obj;
            if (obj2.getProfile().equals(super.getProfile())){
                return true;
            }
        }
        return false;
    } 

    /**
     * toString function for Fulltime
     * @return A string containing all the relevant information for Fulltime instance
     */
    @Override
    public String toString(){
        String ret_str = "";
        ret_str += super.toString() + "::";
        ret_str += "FULL TIME::";
        ret_str += "Annual Salary $" + DECIMAL_FORMAT.format(annualSalary);
        return ret_str;
    }
}
