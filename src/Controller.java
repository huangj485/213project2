package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.util.InputMismatchException;

public class Controller {
    private static final int MAX_HOURS = 100;
    private static final int MAX_INPUT = 100000;
    private static final int MIN_CMD_LEN = 5;
    private static final int MAX_CMD_LEN = 6;   
    private static final int MIN_MANAGEMENT_CODE = 1;
    private static final int MAX_MANAGEMENT_CODE = 3;
    
    private Company company;
    
    public Controller(){
        company = new Company();
    }

	@FXML
	private Button clear_button, add_button, remove_button, set_hours_button, calculate_button, print_button;
	
	@FXML
	private TextField name_field, date_field, salary_field, hours_field, rate_field;
	
	@FXML
	private TextArea output_area;
	
	@FXML
	private ToggleGroup Department, Type, ManagementType, Print;

    @FXML
    private RadioButton manager, dept_head, director;
	
    /**
     * Click handler for full time radio button
     * @param ev - MouseEvent used to trigger function
     */
    @FXML
    void on_fulltime_select(MouseEvent ev){
        manager.setDisable(true);
        dept_head.setDisable(true);
        director.setDisable(true);
        hours_field.setDisable(true);
        rate_field.setDisable(true);
        salary_field.setDisable(false);
    }

    /**
     * Click handler for part time radio button
     * @param ev - MouseEvent used to trigger function
     */
    @FXML
    void on_parttime_select(MouseEvent ev){
        manager.setDisable(true);
        dept_head.setDisable(true);
        director.setDisable(true);
        hours_field.setDisable(false);
        rate_field.setDisable(false);
        salary_field.setDisable(true);
    }

    /**
     * Click handler for management radio button
     * @param ev - MouseEvent used to trigger function
     */
    @FXML
    void on_mgmt_select(MouseEvent ev){
        manager.setDisable(false);
        dept_head.setDisable(false);
        director.setDisable(false);
        hours_field.setDisable(true);
        rate_field.setDisable(true);
        salary_field.setDisable(false);
    }

    /**
     * Click handler for clear button
     * @param ev - MouseEvent used to trigger function
     */
	@FXML
	void clear_onclick(MouseEvent ev) {
		try {
			name_field.clear();
			salary_field.clear();
			hours_field.clear();
			rate_field.clear();
			output_area.clear();
		} catch (Exception ex) {
			output_area.appendText("Not your best work, Chris.\n");
		}
	}
	
    /**
     * Click handler for add button
     * @param ev - MouseEvent used to trigger function
     */
	@FXML
	void add_onclick(MouseEvent ev) {
        //Common elements for all add commands.
        if (name_field.getText().trim().isEmpty() ||
            date_field.getText().trim().isEmpty() 
        ){
            throw new InputMismatchException("Empty field provided.");
        }
        
        String name = name_field.getText();     
        RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
        String dept = selectedRadioButton.getText();   
        Date date = null;
        try {
            date = new Date(date_field.getText());
        } catch (Exception ex){
            throw new NumberFormatException(date_field.getText() + " is not a valid date!");
        }
        //checking to see the name
        if (!date.isValid()){
            throw new NumberFormatException(date_field.getText() + " is not a valid date!");
        }

        Profile prof = new Profile(name, dept, date);
        Employee newEmp = null;

        RadioButton selectedType = (RadioButton) Type.getSelectedToggle();
        String type = selectedType.getText();

        if (type.equals("Part Time")){ 
            double rate;
            try {
                rate = Double.parseDouble(rate_field.getText());
            } catch(NumberFormatException e){
                throw new NumberFormatException("Pay rate entered is not a number.");
            }
            if (rate < 0){
                throw new NumberFormatException("Pay rate cannot be negative.");
            }
            newEmp = new Parttime(prof, rate);
        }

        else if (type.equals("Full Time")){
            int salary;
            try {
                salary = Integer.parseInt(salary_field.getText());
            } catch(NumberFormatException e){
                throw new NumberFormatException("Salary entered is not a number.");
            }
            if (salary < 0){
                throw new NumberFormatException("Salary cannot be negative.");
            }
            newEmp = new Fulltime(prof, salary);
        }
        else {
            int salary;
            try {
                salary = Integer.parseInt(salary_field.getText());
            } catch(NumberFormatException e){
                throw new NumberFormatException("Salary entered is not a number.");
            }
            if (salary < 0){
                throw new NumberFormatException("Salary cannot be negative.");
            }
            RadioButton tempType = (RadioButton) ManagementType.getSelectedToggle();
            String mgmt_type = tempType.getText();
            int code = 3;
            if (mgmt_type.equals("Manager")){
                code = 1;
            }
            else if (mgmt_type.equals("Department Head")){
                code = 2;
            }
            newEmp = new Management(prof, salary, code);
        } 

        if (company.add(newEmp)){
            output_area.appendText("Employee added.\n");
        }
        else {
            output_area.appendText("Employee is already in the list.\n");
        }     
	}
	
    @FXML
    /**
     * Event handler for "Remove" button, attempts to remove an employee
     * @param ev MouseEvent that is being handled
     */
    void remove_onclick(MouseEvent ev){
        try {
            if (company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }

            if (name_field.getText().trim().isEmpty() || 
                date_field.getText().trim().isEmpty() 
            ){
                throw new InputMismatchException("Empty field provided.");
            }
            
            String name = name_field.getText();
            RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
            String dept = selectedRadioButton.getText(); 
            Date date = null;
            try {
                date = new Date(date_field.getText());
            } catch (Exception ex){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }
            
            if (!date.isValid()){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }

            Profile prof = new Profile(name, dept, date);
            Employee toRemove = new Employee(prof);
            if (company.remove(toRemove)){
                output_area.appendText("Employee removed.\n");
            } else {
                output_area.appendText("Employee does not exist.\n");
            }
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
        catch (NumberFormatException nfe){
            output_area.appendText(nfe.getMessage() + "\n");
        }
    }

    @FXML
    /**
     * Event handler for "Set Hours" button, attempts to set hours for the employee
     * @param ev MouseEvent that is being handled
     */
    void hours_onclick(MouseEvent ev){
        try {
            if (company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }  

            if (name_field.getText().trim().isEmpty() || 
                date_field.getText().trim().isEmpty() 
            ){
                throw new InputMismatchException("Empty field provided.");
            }
                
            String name = name_field.getText();
            RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
            String dept = selectedRadioButton.getText();   
            Date date = null;
            try {
                date = new Date(date_field.getText());
            } catch (Exception ex){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }
                
            if (!date.isValid()){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }

            int hours;
            try {
                hours = Integer.parseInt(hours_field.getText());
            } catch(NumberFormatException e){
                throw new NumberFormatException("Hours entered is invalid.");
            }
            if (hours < 0){
                throw new NumberFormatException("Working hours cannot be negative.");
            }
            else if (hours > MAX_HOURS){
                throw new NumberFormatException("Invalid Hours: over 100.");
            }

            Profile prof = new Profile(name, dept, date);
            Employee emp = new Parttime(prof, hours);
            if (company.setHours(emp)){
                output_area.appendText("Working hours set.\n");
            }
            else {
                output_area.appendText("Employee does not exist.\n");
            }
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
        catch (NumberFormatException nfe){
            output_area.appendText(nfe.getMessage() + "\n");
        }
    }

    @FXML
    /**
     * Event handler for "Calculate" button, calculates all payments
     * @param ev MouseEvent being handled
     */
    void calculate_onclick(MouseEvent ev){
        try {
            if (company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }
            company.processPayments();
            output_area.appendText("Calculation of employee payments is done.");
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
    }

    @FXML
    /**
     * Event handler for "Print" button, printing the employees in the output_area
     * @param ev MouseEvent being handled
     */
    void print_onclick(MouseEvent ev){
        try {
            if (company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }

            RadioButton selectedPrint = (RadioButton) Print.getSelectedToggle();
            String Print = selectedPrint.getText();

            if (Print.equals("Print All")){
                output_area.appendText("--Printing earning statements for all employees--\n");
                output_area.appendText(company.print());
            } else if (Print.equals("Print by Date Hired")){
                output_area.appendText("--Printing earning statements by date hired--\n");
                output_area.appendText(company.printByDate());
            } else if (Print.equals("Print by Department")){
                output_area.appendText("--Printing earning statements by department--\n");
                output_area.appendText(company.printByDepartment());
            } else {
                throw new InputMismatchException("Command \'" + Print + "\' not supported! (this should be impossible!)");
            }
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
    }

    @FXML
    /**
     * Event handler for "Import" button, triggering pop-up window to import a file
     * @param ev MouseEvent being handled
     */
    void import_onclick(MouseEvent ev){
        FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Source File for Import");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
        
        char [] database = new char[MAX_INPUT];
        File file = fileChooser.showOpenDialog(stage);
        try {
            if (file != null){
                try {
                    FileReader fileReader = new FileReader(file);
                    fileReader.read(database);
                    fileReader.close();
                } catch (IOException ex) {
                    throw new IOException("Importing has gone wrong.");
                }
            } else{
                throw new IOException("No file selected");
            }

            String command = "";
        
            for(int i = 0; database[i] != 0; i++){
                if (database[i] == '\n'){
                    String[] components = command.split(",");
                    if (components.length < MIN_CMD_LEN || components.length > MAX_CMD_LEN){
                        throw new Exception(": invalid length");
                    }

                    String name = components[1];
                    String dept = components[2];
                    if (!dept.equals("CS") && !dept.equals("ECE") && !dept.equals("IT")){
                        throw new Exception(": invalid dept");
                    }
                    Date date = null;
                    try {
                        date = new Date(components[3]);
                    } catch(Exception ex){
                        throw new Exception(": invalid date format");
                    }
                    if (!date.isValid()){
                        throw new Exception(": invalid date");
                    }

                    Profile prof = new Profile(name, dept, date);
                    Employee newEmp = null;
                    
                    if (components[0].equals("P")){
                    	double rate = 0;
                        try {
                            rate = Double.parseDouble(components[4]);
                        } catch(Exception e){
                            throw new Exception(": invalid pay rate");
                        }
                        if (rate < 0){
                            throw new Exception(": negative pay rate");
                        }
                        newEmp = new Parttime(prof, rate);
                    } else if (components[0].equals("F")){
                        int salary;
                        try {
                            salary = Integer.parseInt(components[4]);
                        
                        } catch(Exception e){
                            throw new NumberFormatException(": invalid salary");
                        }
                        if (salary < 0){
                            throw new NumberFormatException(": negative salary");
                        }
                        newEmp = new Fulltime(prof, salary);
                    } else {
                        int salary;
                        try {
                            salary = Integer.parseInt(components[4]);
                        
                        } catch(Exception e){
                            throw new NumberFormatException(": invalid salary");
                        }
                        if (salary < 0){
                            throw new NumberFormatException(": negative salary");
                        }
                        int code;
                        try {
                            code = Integer.parseInt(components[5]);
                        } catch(Exception e){
                            throw new Exception(": invalid management code");
                        }
                        if (code < MIN_MANAGEMENT_CODE || code > MAX_MANAGEMENT_CODE){
                            throw new Exception(": invalid management code");
                        }
                        newEmp = new Management(prof, salary, code);
                    }

                    if (!company.add(newEmp)){
                        throw new Exception(": invalid employee");
                    }
                    
                    command = "";
                } else {
                    command += database[i];
                }
            }
        } catch(IOException ioe) {
            output_area.appendText(ioe.getMessage() + "\n");

        } catch(Exception e){
            output_area.appendText("Improperly formatted input file" + e.getMessage() + "\n");
        }
    }

    @FXML
    void export_onclick(MouseEvent ev){
        if (company.getNumEmployees() == 0){
            output_area.appendText("Employee database is empty.");
        } else{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Target File for Export");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                    new ExtensionFilter("All Files", "*.*"));
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);

            String database = company.exportDatabase();
            if (file != null){
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(database);
                    fileWriter.close();
                } catch (IOException ex) {
                    output_area.appendText("Saving has gone wrong\n");
                }
            } else{
                output_area.appendText("No file selected\n");
            }
        }
    }
}
