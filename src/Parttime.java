/**
 * Parttime class extends the Employee class and includes the hourly rate and hours worked of a part-time employee, as well as relevant functions.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Parttime extends Employee{
    private static final int MAX_HOURS_PER_PAY_PERIOD = 100;
    private static final int OVERTIME_HOURS = 80; //greater than 80 counts as overtime
    private static final double OVERTIME_MULTIPLIER = 1.5;

    private double hourlyRate;
    private int hours;

    /** Creates a Parttime employee from profile, hourlyRate, hours
     * @param profile Profile to be set
     * @param hourlyRate hourlyRate to be set
     * @param hours hours to be set
     */
    public Parttime(Profile profile, double hourlyRate, int hours){
        super(profile);
        this.hourlyRate = hourlyRate;
        this.hours = hours;
    }

    /** Creates a Parttime employee from profile, hourlyRate
     * @param profile Profile to be set
     * @param hourlyRate hourlyRate to be set
     */
    public Parttime(Profile profile, double hourlyRate){
        super(profile);
        this.hourlyRate = hourlyRate;
    }

    /** Creates a Parttime employee from profile, hourlyRate
     * @param profile Profile to be set
     * @param hours hours to be set
     */
    public Parttime(Profile profile, int hours){
        super(profile);
        this.hours = hours;
    }

    /** Setter for hours
     * @param hours hours to be set
     */
    public void setHours(int hours){
        this.hours = hours;
    }

    /** Getter for hours
     * @return An int - hours
     */
    public int getHours(){
        return this.hours;
    }

    /** Function to calculate payment
     */
    @Override
    public void calculatePayment() {
        double finalPayment = 0;
        int relevantHours = this.hours;

        if(relevantHours > MAX_HOURS_PER_PAY_PERIOD){
            //too many hours
            relevantHours = MAX_HOURS_PER_PAY_PERIOD;
        }

        if(relevantHours > OVERTIME_HOURS){
            finalPayment += (relevantHours-OVERTIME_HOURS)*OVERTIME_MULTIPLIER*this.hourlyRate;
            relevantHours = OVERTIME_HOURS;
        }
        
        finalPayment += relevantHours * hourlyRate;

        this.payment = finalPayment;
    }

    /**
     * 
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Parttime){
            if (obj.getProfile().equals(super.getProfile()) && obj.hourlyRate == this.hourlyRate){
                //on purposely does not check for hours!
                return true;
            }
        }
        return false;
    } 

    @Override
    public String toString(){
        String ret_str = "";
        ret_str += super.toString() + "::";
        ret_str += "PART TIME::";
        ret_str += "Hourly Rate $" + DECIMAL_FORMAT.format(this.hourlyRate) + "::";
        ret_str += "Hours worked this period: " + String.valueOf(this.hours);
        return ret_str;
    }
}
