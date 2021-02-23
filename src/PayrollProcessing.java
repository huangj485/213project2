import java.util.Scanner;
import java.util.InputMismatchException;


/**
 * The PayrollProcessing class acts as a user interface to interact with a particular instance of Company.
 * @author Jerry Huang, Adrian Thamburaj 
 */
public class PayrollProcessing {
    private static final int MIN_MANAGEMENT_CODE = 1;
    private static final int MAX_MANAGEMENT_CODE = 3;
    private static final int MAX_HOURS = 100;

    private static final int MIN_A_CMD_LEN = 5;
    private static final int MAX_A_CMD_LEN = 6;
    private static final int R_CMD_LEN = 4;
    private static final int S_CMD_LEN = 5; 

    /**
     * Constructor to create a PayrollProcessing instance
     */
    public PayrollProcessing(){}

    /**
     * Function to begin running the user interface to interact with a new Company
     */
    public void run(){
        Company company = new Company();
        Scanner sc = new Scanner(System.in);

        System.out.println("Payroll Processing starts.");

        String line = "";
        while ((line = sc.nextLine()).isEmpty()){
            System.out.println("Command \'\' not supported!");
        }

        while (line.charAt(0) != 'Q'){
            try {
                String[] components = line.split(" ");
                String command = components[0]; //first component is the command

                //Any and all add commands
                if (command.equals("AP") || command.equals("AF") || command.equals("AM")){

                    if (components.length < MIN_A_CMD_LEN){
                        throw new InputMismatchException("Too few arguments provided.");
                    }
                    //Common elements for all add commands.
                    String name = components[1];
                    String dept = components[2];
                    Date date = new Date(components[3]);            

                    //checking to see the name
                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new InputMismatchException("\'" + dept + "\' is not a valid department code.");
                    }
                    else if (!date.isValid()){
                        throw new NumberFormatException(components[3] + " is not a valid date!");
                    }

                    Profile prof = new Profile(name, dept, date);
                    Employee newEmp = null;

                    if (command.charAt(1) == 'P'){

                        double rate = Double.parseDouble(components[4]);
                        if (rate < 0){
                            throw new NumberFormatException("Pay rate cannot be negative.");
                        }
                        newEmp = new Parttime(prof, rate);
                    }
                    else if (command.charAt(1) == 'F'){
                        int salary = Integer.parseInt(components[4]);
                        if (salary < 0){
                            throw new NumberFormatException("Salary cannot be negative.");
                        }
                        newEmp = new Fulltime(prof, salary);
                    }
                    else {
                        if (components.length != MAX_A_CMD_LEN){
                            throw new InputMismatchException("Wrong number of arguments provided.");
                        }
                        int salary = Integer.parseInt(components[4]);
                        if (salary < 0){
                            throw new NumberFormatException("Salary cannot be negative.");
                        }
                        int code = Integer.parseInt(components[5]);
                        if (code < MIN_MANAGEMENT_CODE || code > MAX_MANAGEMENT_CODE){
                            throw new NumberFormatException("Invalid management code.");
                        }
                        newEmp = new Management(prof, salary, code);
                    } 

                    if (company.add(newEmp)){
                        System.out.println("Employee added.");
                    }
                    else {
                        System.out.println("Employee is already in the list.");
                    }     
                }
                //the R command
                else if (command.equals("R")){
                    if (company.getNumEmployees() == 0){
                        throw new InputMismatchException("Employee database is empty.");
                    }
                    if (components.length != R_CMD_LEN){
                        throw new InputMismatchException("Wrong number of arguments provided.");
                    }

                    String name = components[1];
                    String dept = components[2];
                    Date date = new Date(components[3]);

                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new InputMismatchException("Invalid department code.");
                    }
                    else if (!date.isValid()){
                        throw new NumberFormatException(components[3] + " is not a valid date!");
                    }

                    Profile prof = new Profile(name, dept, date);
                    Employee toRemove = new Employee(prof);
                    if (company.remove(toRemove)){
                        System.out.println("Employee removed.");
                    }
                    else {
                        System.out.println("Employee does not exist.");
                    }
                }
                //C command
                else if (command.equals("C")){
                    if (company.getNumEmployees() == 0){
                        throw new InputMismatchException("Employee database is empty.");
                    }
                    company.processPayments();
                    System.out.println("Calculation of employee payments is done.");
                }
                //S command
                else if (command.equals("S")){
                    if (company.getNumEmployees() == 0){
                        throw new InputMismatchException("Employee database is empty.");
                    }
                    if (components.length != S_CMD_LEN){
                        throw new InputMismatchException("Wrong number of arguments provided.");
                    }

                    String name = components[1];
                    String dept = components[2];
                    Date date = new Date(components[3]);

                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new InputMismatchException("Invalid department code.");
                    }
                    else if (!date.isValid()){
                        throw new NumberFormatException(components[3] + " is not a valid date!");
                    }

                    int hours = Integer.parseInt(components[4]);
                    if (hours < 0){
                        throw new NumberFormatException("Working hours cannot be negative.");
                    }
                    else if (hours > MAX_HOURS){
                        throw new NumberFormatException("Invalid Hours: over 100.");
                    }

                    Profile prof = new Profile(name, dept, date);
                    Employee emp = new Parttime(prof, hours);
                    if (company.setHours(emp)){
                        System.out.println("Working hours set.");
                    }
                    else {
                        System.out.println("Employee does not exist.");
                    }
                }
                //P command
                else if (command.equals("PA") || command.equals("PH") || command.equals("PD")){
                    if (company.getNumEmployees() == 0){
                        System.out.println("Employee database is empty.");
                    }
                    else if (command.charAt(1) == 'A'){
                        System.out.println("--Printing earning statements for all employees--");
                        company.print();
                    }
                    else if (command.charAt(1) == 'H'){
                        System.out.println("--Printing earning statements by date hired--");
                        company.printByDate();
                    }
                    else if (command.charAt(1) == 'D'){
                        System.out.println("--Printing earning statements by department--");
                        company.printByDepartment();
                    }
                }
                else {
                    throw new InputMismatchException("Command \'" + command + "\' not supported!");
                }
            }
            catch (InputMismatchException ime){
                //print corresponding statement for "bad command"
                System.out.println(ime.getMessage());
            }
            catch (NumberFormatException nfe){
                System.out.println(nfe.getMessage());
            }
            finally {
                while ((line = sc.nextLine()).isEmpty()){
                    System.out.println("Command \'\' not supported!");
                }
            }         
        }

        sc.close();
        System.out.println("Payroll Processing completed.");
    }
}
