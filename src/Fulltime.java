/**
 * Fulltime class extends the Employee class and includes specific data and operations to a full-time employee.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Fulltime extends Employee {
    protected int annualSalary;

    /** Creates a Fulltime Employee from profile, salary
     * @param profile Profile to be set
     * @param salary salary to be set
     */
    public Fulltime (Profile profile, int salary){
        super(profile);
        this.annualSalary = salary;
    }

    @Override
    public void calculatePayment(){
        this.payment = ((double)annualSalary) / PAY_PERIODS_PER_YEAR;
    }

    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Fulltime){
            if (obj.getProfile().equals(super.getProfile())){
                return true;
            }
        }
        return false;
    } 

    @Override
    public String toString(){
        String ret_str = "";
        ret_str += super.toString() + "::";
        ret_str += "FULL TIME::";
        ret_str += "Annual Salary $" + DECIMAL_FORMAT.format(annualSalary);
        return ret_str;
    }
}
