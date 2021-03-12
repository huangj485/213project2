package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


import java.util.InputMismatchException;

public class Controller {
    private static final int MAX_HOURS = 100;
    
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
	
    @FXML
    void on_fulltime_select(MouseEvent ev){
        manager.setDisable(true);
        dept_head.setDisable(true);
        director.setDisable(true);
        hours_field.setDisable(true);
        rate_field.setDisable(true);
        salary_field.setDisable(false);
    }

    @FXML
    void on_parttime_select(MouseEvent ev){
        manager.setDisable(true);
        dept_head.setDisable(true);
        director.setDisable(true);
        hours_field.setDisable(false);
        rate_field.setDisable(false);
        salary_field.setDisable(true);
    }

    @FXML
    void on_mgmt_select(MouseEvent ev){
        manager.setDisable(false);
        dept_head.setDisable(false);
        director.setDisable(false);
        ManagementType.setDisable(false);
        hours_field.setDisable(true);
        rate_field.setDisable(true);
        salary_field.setDisable(false);
    }

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
	
	@FXML
	void add_onclick(MouseEvent ev) {
        //Common elements for all add commands.
        String name = name_field.getText();
        
        RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
        String dept = selectedRadioButton.getText();
        
        Date date = new Date(date_field.getText());            

        if (name_field.getText().trim().isEmpty() || 
            dept_field.getText().trim().isEmpty() ||
            date_field.getText().trim().isEmpty() ||
        ){
            throw new InputMismatchException("Wrong number of arguments provided.");
        }
        
        try{
            Date date = new Date(date_field.getText());
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
            double rate = Double.parseDouble(rate_field.getText());
            if (rate < 0){
                throw new NumberFormatException("Pay rate cannot be negative.");
            }
            newEmp = new Parttime(prof, rate);
        }

        else if (type.equals("Full Time")){
            int salary = Integer.parseInt(salary_field.getText());
            if (salary < 0){
                throw new NumberFormatException("Salary cannot be negative.");
            }
            newEmp = new Fulltime(prof, salary);
        }
        else {
            int salary = Integer.parseInt(salary_field.getText());
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
    void remove_onclick(){
        try{
            if(company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }
            if (name_field.getText().trim().isEmpty() || 
                dept_field.getText().trim().isEmpty() ||
                date_field.getText().trim().isEmpty() ||
            ){
                throw new InputMismatchException("Wrong number of arguments provided.");
            }
            
            String name = name_field.getText();
            
            RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
            String dept = selectedRadioButton.getText();

            try {
                Date date = new Date(date_field.getText());
            } catch (Exception ex){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }
            
            if (!date.isValid()){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }

            Profile prof = new Profile(name, dept, date);
            Employee toRemove = new Employee(prof);
            if(company.remove(toRemove)){
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
    void hours_onclick(){
        try{
            if (company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }

            if (name_field.getText().trim().isEmpty() || 
                dept_field.getText().trim().isEmpty() ||
                date_field.getText().trim().isEmpty() ||
            ){
                throw new InputMismatchException("Wrong number of arguments provided.");
            }
                
            String name = name_field.getText();
                
            RadioButton selectedRadioButton = (RadioButton) Department.getSelectedToggle();
            String dept = selectedRadioButton.getText();

            try {
                Date date = new Date(date_field.getText());
            } catch (Exception ex){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }
                
            if (!date.isValid()){
                throw new NumberFormatException(date_field.getText() + " is not a valid date!");
            }

            int hours = Integer.parseInt(hours_field.getText());
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
                output_area.appendText("Employee does not exist."\n);
            }
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
        catch (NumberFormatException nfe){
            output_area.appendText(nfe.getMessage() + "\n");
        }
    }

    @FXML
    void calculate_onclick(){
        try{
            if(company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }
            company.processPayments();
            output_area.appendText("Calculation of employee payments is done.");
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
    }

    @FXML
    void print_onclick(){
        try{
            if(company.getNumEmployees() == 0){
                throw new InputMismatchException("Employee database is empty.");
            }

            RadioButton selectedPrint = (RadioButton) Print.getSelectedToggle();
            String Print = selectedPrint.getText();

            if(Print.equals("Print All")){
                output_area.appendText("--Printing earning statements for all employees--\n");
                output_area.appendText(company.print());
            } else if (Print.equals("Print by Date Hired")){
                output_area.appendText("--Printing earning statements by date hired--\n");
                output_area.appendText(company.printByDate());
            } else if (Print.equals("Print by Department")){
                output_area.appendText("--Printing earning statements by department--\n");
                output_area.appendText(company.printByDepartment());
            } else{
                throw new InputMismatchException("Command \'" + Print + "\' not supported! (this should be impossible!)");
            }
        } catch (InputMismatchException ime){
            output_area.appendText(ime.getMessage() + "\n");
        }
    }
}
