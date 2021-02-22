import java.text.DecimalFormat;
/**
 * Management class extends the Fulltime class and includes specific data and operations to a full-time employee with a management role.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Management extends Fulltime{
    private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;

    private static final int MANAGER_COMPENSATION = 5000;
    private static final int DEPARTMENT_HEAD_COMPENSATION = 9500;
    private static final int DIRECTOR_COMPENSATION = 12000;

    private String roleString; //role as a string
    private int additionalCompensation;

    public Management(Profile profile, int salary, int role){
        super(profile, salary);
        
        this.setAdditionalCompensation(role); //also sets roleString
    }

    /** Helper method to set additionalCompensation and roleString
     * @param role role to find appropriate additionalCompensation and roleString to set
     */
    private void setAdditionalCompensation(int role){
        switch(role){
            case MANAGER:
                this.additionalCompensation = Management.MANAGER_COMPENSATION;
                this.roleString = "Manager";
                break;
            case DEPARTMENT_HEAD:
                this.additionalCompensation = Management.DEPARTMENT_HEAD_COMPENSATION;
                this.roleString = "Department Head";
                break;
            case DIRECTOR:
                this.additionalCompensation = Management.DIRECTOR_COMPENSATION;
                this.roleString = "Director";
                break;
            default:
                //THROW AN EXCEPTION

        }
    }

    @Override
    public void calculatePayment(){
        this.payment = ((double)(this.additionalCompensation + super.annualSalary)) / PAY_PERIODS_PER_YEAR;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Management){
            if(obj.getProfile().equals(super.getProfile())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        String ret_str = "";
        ret_str += super.toString() + "::";
        ret_str += roleString + " Compensation + $" + DECIMAL_FORMAT.format(additionalCompensation) + "::";
        return ret_str;
    }
}
