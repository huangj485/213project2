/**
 * Company class is an array-based container class that implements the employee database. The array 
 * will store a list of employees, which may include the instances of full-time, part-time and management. The 
 * initial capacity of the container is 4. It will automatically grow the capacity by 4 if the array is full.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Company {
    private Employee[] emplist;
    private int numEmployee;
    private static final int INIT_SIZE = 4;

    /**
     * Default constructor for a new Company. 
     */
    public Company(){
        this.emplist = new Employee[Company.INIT_SIZE];
        this.numEmployee = 0;
    }

    /** Finds an Employee in the Company
     * @param employee The employee to be found
     * @return An integer - the index if successfully found, -1 if not
     */
    private int find(Employee employee) { 
        for (int i = 0; i < this.numEmployee; i++){
            if (employee.equals(this.emplist[i])){
                return i;
            }
        }
        return -1; //-1 for not found in array
    }

    /** Adds more space in the list of Employees
     */
    private void grow() { 
        Employee[] tempList = new Employee[this.numEmployee + Company.INIT_SIZE];
        for (int i = 0; i < this.numEmployee; i++){
            tempList[i] = this.emplist[i];
        }
        this.emplist = tempList;
    }

    /**
     * Adds an employee to theCompany if they nare not already present
     * @param employee The employee to be added to the Company
     * @return True if employee is added successfully, false otherwise.
     */
    public boolean add(Employee employee) { 
        if (this.find(employee) != -1){
            return false;
        }

        if (this.numEmployee == this.emplist.length){
            this.grow();
        }
        this.emplist[this.numEmployee] = employee;
        this.numEmployee++;
        return true;
    } 

    /** Removes an employee from the Company
     * @param employee The employee to be removed
     * @return A boolean - true if successfully changed, false if not
     */
    public boolean remove(Employee employee) { 
        int index = this.find(employee);
        if (index == -1){
            return false;
        }
        for (int i = index; i < numEmployee - 1; i++){
            this.emplist[i] = this.emplist[i+1];
        }
        this.emplist[numEmployee-1] = null;
        this.numEmployee--;
        return true;
    } 

    /** Sets the working hours of a Parttime Employee
     * @param employee The employee whose part time hours are to be set
     * @return A boolean - true if successfully changed, false if not
     */
    public boolean setHours(Employee employee) { 
        int employeeIndex = find(employee);
        if(employeeIndex == -1)
            return false;
        
        Parttime tempParttime = (Parttime) employee;
        Parttime existingParttime = (Parttime) emplist[employeeIndex];
        existingParttime.setHours(tempParttime.getHours());
        return true;
    } 

    /** Calculates payments for all Employees in Company
     */
    public void processPayments() { 
        for (int i = 0; i < this.numEmployee; i++){
            this.emplist[i].calculatePayment();
        }
    } 

    /** Prints earning statements for all employees 
     */
    public String print() {
        String toReturn = "";
        for(int i = 0; i < this.numEmployee; i++){
            toReturn += this.emplist[i].toString() + "\n";
        }
        return toReturn;
    }

    /** Prints earning statements for all employees organizing by department
     */
    public String printByDepartment() {
        String toReturn = "";
        int [] sorted = sortByDepartment();
        for(int i = 0; i < numEmployee; i++){
            toReturn += this.emplist[sorted[i]].toString() + "\n";
        }
        return toReturn;
    } 

    /**
     * Helper method to sort by department
     * @return An integer array containing the indices of the original array in ascending order
     */
    private int[] sortByDepartment(){
        int [] sorted = new int[this.numEmployee]; //returns array of indices from min to max
        String [] strings = new String[this.numEmployee];
        
        for(int i = 0; i < this.numEmployee; i++){
            strings[i] = this.emplist[i].getProfile().getDepartment();
        }

        for(int i = 0; i < this.numEmployee; i++){
            sorted[i] = i;
        }
        
        for(int i = 1; i < this.numEmployee; i++){
            String current = strings[i];
            int j = i - 1;
            while(j >= 0 && current.compareTo(strings[j]) < 0){
                strings[j+1] = strings[j];
                sorted[j+1] = sorted[j];
                j--;
            }
            strings[j+1] = current;
            sorted[j+1] = i;
        }
        
        return sorted;
    }

    /** Prints earning statements by date hired
     */
    public String printByDate() {
        String toReturn = "";
        int [] sorted = sortByDate();
        for(int i = 0; i < numEmployee; i++){
            toReturn += this.emplist[sorted[i]].toString() + "\n";
        }
        return toReturn;
    }

    /**
     * Getter method for numEmployee
     * @return returns int numEmployee
     */
    public int getNumEmployees(){
        return this.numEmployee;
    }

    /**
     * Helper method to sort by date
     * @return An integer array containing 
     */
    private int[] sortByDate(){
        int [] sorted = new int[this.numEmployee]; //returns array of indices from min to max
        Date [] dates = new Date[this.numEmployee];
        
        for(int i = 0; i < this.numEmployee; i++){
            dates[i] = this.emplist[i].getProfile().getDateHired();
        }

        for(int i = 0; i < this.numEmployee; i++){
            sorted[i] = i;
        }
        
        for(int i = 1; i < this.numEmployee; i++){
            Date current = dates[i];
            int j = i - 1;
            while(j >= 0 && current.compareTo(dates[j]) < 0){
                dates[j+1] = dates[j];
                sorted[j+1] = sorted[j];
                j--;
            }
            dates[j+1] = current;
            sorted[j+1] = i;
        }
        
        return sorted;
    }
}
