package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.*;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee management operations.
 * All endpoints require JWT authentication except for the ones explicitly permitted in SecurityConfig.
 */
@Api(tags = "Employee Management", description = "APIs for managing employees")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieves all employees from the system.
     * Response is cached for better performance.
     *
     * @return List of all employees
     */
    @ApiOperation(value = "Get all employees", notes = "Returns a list of all employees. Response is cached for better performance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved all employees"),
        @ApiResponse(code = 403, message = "Access denied - JWT token is missing or invalid")
    })
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Retrieves a specific employee by their ID.
     * Response is cached for better performance.
     *
     * @param employeeId The ID of the employee to retrieve
     * @return The employee if found, null otherwise
     */
    @ApiOperation(value = "Get employee by ID", notes = "Returns a single employee by their ID. Response is cached for better performance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the employee"),
        @ApiResponse(code = 403, message = "Access denied - JWT token is missing or invalid"),
        @ApiResponse(code = 404, message = "Employee not found")
    })
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(
            @ApiParam(value = "ID of the employee to retrieve", required = true)
            @PathVariable(name="employeeId") Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new employee in the system.
     * Invalidates the employees list cache.
     *
     * @param employee The employee data to create
     */
    @ApiOperation(value = "Create a new employee", notes = "Creates a new employee in the system")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully created the employee"),
        @ApiResponse(code = 403, message = "Access denied - JWT token is missing or invalid")
    })
    @PostMapping("/employees")
    public ResponseEntity<Void> saveEmployee(
            @ApiParam(value = "Employee object to be created", required = true)
            @RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes an employee from the system.
     * Invalidates both the employees list cache and the individual employee cache.
     *
     * @param employeeId The ID of the employee to delete
     */
    @ApiOperation(value = "Delete an employee", notes = "Deletes an employee from the system")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted the employee"),
        @ApiResponse(code = 403, message = "Access denied - JWT token is missing or invalid")
    })
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @ApiParam(value = "ID of the employee to delete", required = true)
            @PathVariable(name="employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing employee's information.
     * Invalidates both the employees list cache and the individual employee cache.
     *
     * @param employee The updated employee data
     * @param employeeId The ID of the employee to update
     */
    @ApiOperation(value = "Update an employee", notes = "Updates an existing employee's information")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated the employee"),
        @ApiResponse(code = 403, message = "Access denied - JWT token is missing or invalid"),
        @ApiResponse(code = 404, message = "Employee not found")
    })
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Void> updateEmployee(
            @ApiParam(value = "Updated employee object", required = true)
            @RequestBody Employee employee,
            @ApiParam(value = "ID of the employee to update", required = true)
            @PathVariable(name="employeeId") Long employeeId) {
        Employee existingEmployee = employeeService.getEmployee(employeeId);
        if (existingEmployee != null) {
            employeeService.updateEmployee(employee);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
