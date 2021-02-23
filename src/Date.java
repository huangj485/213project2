import java.util.Calendar;
/** Date: this class defines the properties of a Date object, specifically year, month, day.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;
    private static final int MIN_YEAR = 1900; //date must be above 1900
    private static final int [] MAX_DAYS_OF_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int FEBRUARY = 2; //FEBRUARY IS THE SECOND MONTH.
    private static final int NUM_DAYS_IN_LEAP_YEAR = 29;
    private static final int MONTHS_IN_A_YEAR = 12;
    private static final int FOUR_CENTURIES = 400;
    private static final int CENTURY = 100;
    private static final int LEAP_YEAR_INTERVAL = 4;

    /** Creates a Date from a given String
     * @param date A String containing a date formated as "mm/dd/yyyy"
     */
    public Date(String date) { //taking mm/dd/yyyy and create a Date object
        String [] components = date.split("/"); //month, date, year

        this.month  = Integer.parseInt(components[0]);
        this.day    = Integer.parseInt(components[1]);
        this.year   = Integer.parseInt(components[2]);

        //do we check whether is valid in here?
    } 

    /** Default - creates today's date.
     */
    public Date() {//create an object with today’s date (see Calendar class)
        Calendar calendar = Calendar.getInstance();
        this.year   = calendar.get(Calendar.YEAR);
        this.month  = calendar.get(Calendar.MONTH)+1; //0-indexed months.
        this.day    = calendar.get(Calendar.DATE);
    } 

    /** Checks to make sure that a given date is valid
     * @return A boolean - true if valid, false if not
     */
    public boolean isValid() {
        //Check for negatives
        if(this.year < 0 || this.month < 0 || this.day < 0){
            return false;
        }
        
        //check whether past current date
        Calendar calendar = Calendar.getInstance();
        int currentYear  = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1; //0-indexed months.
        int currentDay   = calendar.get(Calendar.DATE);
        if (this.year > currentYear){
            return false; //year too far
        } else if(this.year == currentYear){ //year equal
            if (this.month > currentMonth){
                return false; //month too far
            } else if(this.month == currentMonth && this.day > currentDay){ //year equal already, checking month and day
                return false; //day too far
            }
        }

        //check whether too old (year too low)
        if(this.year < Date.MIN_YEAR){
            return false;
        }

        //check whether month too big
        if(this.month > Date.MONTHS_IN_A_YEAR){
            return false;
        }

        //check whether leapyear
        if(this.month == Date.FEBRUARY && checkLeapYear()){
            if(this.day > Date.NUM_DAYS_IN_LEAP_YEAR){
                return false;
            }
        } else{ //check whether day too big
            if(this.day > Date.MAX_DAYS_OF_MONTH[this.month-1]){
                return false;
            }
        }

        return true;
    }

    //HELPER METHODS

    /** Checks if the year is a leap year
     * @return A boolean - true if a leap year, false if not
     */
    private boolean checkLeapYear(){ //returns true if leap year
        if(this.year % Date.LEAP_YEAR_INTERVAL == 0){
            if(this.year % Date.CENTURY == 0){
                if(this.year % Date.FOUR_CENTURIES == 0){
                    return true;
                } else{
                    return false;
                }
            } else{
                return true;
            }
        } else{
            return false;
        }
    }

    //GETTER METHODS

    /** Returns Date in the format mm/dd/yyyy
     * @return A String representation of Date
     */
    @Override
    public String toString(){
        String toReturn = "";
        toReturn += String.valueOf(this.month) + "/";
        toReturn += String.valueOf(this.day) + "/";
        toReturn += String.valueOf(this.year);
        return toReturn;
    }

    /** Gets the year
     * @return A four-digit integer representing the year.
     */
    public int getYear(){
        return this.year;
    }

    /** Gets the month of the Date object
     * @return An integer between 1 and 12 representing the month (from January to December, respectively)
     */
    public int getMonth(){
        return this.month;
    }

    /** Gets the day of the date
     * @return An integer between 1 and 31 representing the day of the month.
     */
    public int getDay(){
        return this.day;
    }

    /** compareTo function override
     * Overrides the compareTo function to compare the values of Dates. -1 if lower, 0 if the same, 1 if higher.
     * @param date Date to be compared
     */
    @Override
    public int compareTo(Date date){
        if(this.year > date.getYear()){
            return 1;
        }

        if(this.year < date.getYear()){
            return -1;
        }

        //year is equal

        if(this.month > date.getMonth()){
            return 1;
        }

        if(this.month < date.getMonth()){
            return -1;
        }

        //month is equal

        if(this.day > date.getDay()){
            return 1;
        }

        if(this.day < date.getDay()){
            return -1;
        }

        //day is equal

        return 0;
    }

    /**
     * equals function for Date
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Date){
            Date otherDate = (Date) obj;
            if (otherDate.compareTo(this) == 0){
                return true;
            }
        }
        return false;
    }
}