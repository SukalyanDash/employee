package com.employee.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Entity.Employee;
import com.employee.Entity.Tax;
import com.employee.Service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
    @Autowired
    private EmployeeService employeeService;

    
    @PostMapping("/addemployee")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
        	ResponseEntity<String> createdEmployee = employeeService.newEmployee(employee);
            return createdEmployee;
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @GetMapping("/taxdeduction")
    public ResponseEntity<List<Tax>> calculateTaxDeduction() {
        List<Employee> employees = employeeService.getAllEmployees(); // You need to implement this method
        System.out.println(employees);
        List<Tax> taxDeductions = new ArrayList<>();
        
        for (Employee employee : employees) {
            Tax taxDeduction = employeeService.calculateTaxDeductionForEmployee(employee);
            taxDeductions.add(taxDeduction);
        }
        
        return ResponseEntity.ok(taxDeductions);
    }
}