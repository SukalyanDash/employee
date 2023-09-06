package com.employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.employee.Entity.Employee;
import com.employee.Entity.Tax;

public interface EmployeeService {


	ResponseEntity<String> newEmployee(Employee employee);

	Tax calculateTaxDeductionForEmployee(Employee employee);

	List<Employee> getAllEmployees();

}
