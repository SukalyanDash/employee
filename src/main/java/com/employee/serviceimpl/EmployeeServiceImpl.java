package com.employee.serviceimpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.validation.ValidationException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employee.Entity.Employee;
import com.employee.Entity.Tax;
import com.employee.Repository.EmployeeRepository;
import com.employee.Service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository emprepo;


	@Override
	public ResponseEntity<String> newEmployee(Employee employee) {
		// TODO Auto-generated method stub
		//JSONObject jo=new JSONObject();
		try {
	        validateEmployeeData(employee); 
	        Employee savedEmployee = emprepo.save(employee);
	        return ResponseEntity.ok("Save successfully");	        //jo.put("message", true);
	    } catch (ValidationException e) {
	       // jo.put("message", false);
	    	 return ResponseEntity.badRequest().body(e.getMessage());

	    }
		
		//return employee;
	}
	
	private void validateEmployeeData(Employee employee) {
	    if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
	        throw new ValidationException("Employee ID is required.");
	    }
	    if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
	        throw new ValidationException("First Name is required.");
	    }
	    if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
	        throw new ValidationException("Last Name is required.");
	    }
	    if (employee.getEmail() == null ) {
	        throw new ValidationException("Invalid Email address.");
	    }
	    if (employee.getPhoneNumbers() == null || employee.getPhoneNumbers().isEmpty()) {
	        throw new ValidationException("At least one phone number is required.");
	    }
	    if (employee.getDoj() == null) {
	        throw new ValidationException("Date of Joining is required.");
	    }
	    if (employee.getSalary() <= 0) {
	        throw new ValidationException("Salary must be greater than zero.");
	    }
	}


	
	@Override
	public Tax calculateTaxDeductionForEmployee(Employee employee) {
	    LocalDate today = LocalDate.now();
	    LocalDate doj = employee.getDoj();
	    double salary = employee.getSalary();
	    
	    int monthsWorked = (int) Period.between(doj, today).toTotalMonths();
	    
	    double totalSalary = salary * monthsWorked / 12.0;
	    System.out.println("totalSalary"+totalSalary);
	    
	    double tax = 0.0;
	    
	    if (totalSalary <= 250000) {
	        tax = 0.0;
	    } else if (totalSalary <= 500000) {
	        tax = (totalSalary - 250000) * 0.05;
	    } else if (totalSalary <= 1000000) {
	        tax = 250000 * 0.05 + (totalSalary - 500000) * 0.10;
	    } else {
	        tax = 250000 * 0.05 + 500000 * 0.10 + (totalSalary - 1000000) * 0.20;
	    }
	    
	    double cess = 0.0;
	    System.out.println("tax"+tax);
	    if (totalSalary > 2500000) {
	        cess = (totalSalary - 2500000) * 0.02;
	    }
	    System.out.println("cess"+cess);
	    
	    System.out.println(employee.getEmployeeId());
	    Tax taxDeduction = new Tax();
	    taxDeduction.setId(employee.getId());
	    taxDeduction.setEmployeeCode(employee.getEmployeeId());
	    taxDeduction.setFirstName(employee.getFirstName());
	    taxDeduction.setLastName(employee.getLastName());
	    taxDeduction.setYearlySalary(totalSalary);
	    taxDeduction.setTaxAmount(tax);
	    taxDeduction.setCessAmount(cess);
	    System.out.println("taxDeduction"+taxDeduction);
	    return taxDeduction;
	}


	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> emp=emprepo.findAll();
		return emp;
	}


}
