/**
 * Company class. This class is an array-based container class that implements the employee database. The array 
 * will store a list of employees, which may include the instances of full-time, part-time and management. The 
 * initial capacity of the container is 4. It will automatically grow the capacity by 4 if the array is full.
 * @author Jerry Huang, Adrian Thamburaj
 */
public class Company {
    private Employee[] emplist;
    private int numEmployee;
    private static final int INIT_SIZE = 4;

    public Company(){
        this.emplist = new Employee[Company.INIT_SIZE];
        this.numEmployee = 0;
    }

    /** Finds an Employee in the Company
     * @param employee The employee to be found
     * @return An integer - the index if successfully found, -1 if not
     */
    private int find(Employee employee) { 
        for (int i=0; i<this.numEmployee; i++){
            if (emplist[i].equals(employee)){
                return i;
            }
        }
        return -1; //-1 for not found in array
    }

    /** Adds more space in the list of Employees
     */
    private void grow() { 
        Employee[] tempList = new Employee[this.numEmployee + Company.INIT_SIZE];
        for (int i=0; i<this.numEmployee; i++){
            tempList[i] = this.emplist[i];
        }
        this.emplist = tempList;
    }

    public boolean add(Employee employee) { } //check the profile before adding

    /** Removes an employee from the Company
     * @param employee The employee to be removed
     * @return A boolean - true if successfully changed, false if not
     */
    public boolean remove(Employee employee) { 
        int index = this.find(employee);
        if (index == -1){
            return false;
        }
        for (int i=index; i<numEmployee-1; i++){
            this.emplist[i] = this.emplist[i+1];
        }
        this.emplist[numEmployee-1] = null;
        this.numEmployee--;
        return true;
    } //maintain the original sequence

    /** Sets the working hours of a Parttime Employee
     * @return A boolean - true if successfully changed, false if not
     */
    public boolean setHours(Employee employee) { 
        int employeeIndex = find(employee);
        if(employeeIndex == -1)
            return false;
        
        emplist[employeeIndex].setHours(employee.getHours());
        return true;
    } 

    /** Calculates payments for all Employees in Company
     */
    public void processPayments() { 
        for (int i=0; i<this.numEmployee; i++){
            this.emplist[i].calculatePayment();
        }
    } 

    /** Prints earning statements for all employees 
     */
    public void print() { }

    /** Prints earning statements for all employees organizing by department
     */
    public void printByDepartment() { } 

    /** Prints earning statements by date hired
     */
    public void printByDate() { }

    public int getNumEmployees(){
        return this.numEmployee;
    }
}
