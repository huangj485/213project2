import java.util.Scanner;

public class PayrollProcessing {
    private static final int ADD_COMMAND_LENGTH = 2;
    private static final int MIN_MANAGEMENT_CODE = 1;
    private static final int MAX_MANAGEMENT_CODE = 3;

    public PayrollProcessing(){}

    public void run(){
        Company company = new Company();
        Scanner sc = new Scanner(System.in);

        System.out.println("Payroll Processing starts.");

        String line = sc.nextLine();
        while (line.charAt(0) != 'Q'){
            try {
                if (!line.equals("")){
                    throw new InputMismatchException("Command \'\' not supported!");
                }

                String[] components = line.split(" ");
                String command = components[0]; //first component is the command

                //Any and all add commands
                if (command.equals("AP") || command.equals("AF") || command.equals("AM")){

                    //Common elements for all add commands.
                    String name = components[1];
                    String dept = components[2];
                    Date date = new Date(components[3]);            

                    //checking to see the name
                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new InputMismatchException("invalid department code.");
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
                        int salary = Integer.parseInt(components[4]);
                        if (salary < 0){
                            throw new NumberFormatException("Salary cannot be negative.");
                        }
                        int code = Integer.parseInt(components[5]);
                        if (code < MIN_MANAGEMENT_CODE || code > MAX_MANAGEMENT_CODE){
                            throw new NumberFormatException("Invalid management code.")
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
                }
                //S command
                else if (command.equals("S")){
                    String name = components[1];
                    String dept = components[2];
                    Date date = new Date(components[3]);

                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new InputMismatchException("Invalid department code.");
                    }
                    else if (!date.isValid()){
                        throw new NumberFormatException(components[3] + " is not a valid date!");
                    }

                    int hours = components[4];
                    if (hours < 0){
                        throw new NumberFormatException("Working hours cannot be negative.");
                    }

                    Profile prof = new Profile(name, dept, date);
                    Employee emp = new Parttime(prof, hours);
                    if (company.setHours(emp)){
                        System.out.println("Working hours set.");
                    }
                    else {
                        System.out.println("Employee does not exist.")
                    }
                }
                //P command
                else if (command.equals("PA") || command.equals("PH") || command.equals("PD")){
                    if (company.getNumEmployees() == 0){
                        System.out.println("Employee database is empty.")
                    }
                }


            }
            catch (InputMismatchException ime){
                //print corresponding statement for "bad command"
                System.out.println(ime.getMessage());
            }
            catch (NumberFormatException nfe){
                System.out.println(nfe.getMessage());
            }
            
        }
    }
}
