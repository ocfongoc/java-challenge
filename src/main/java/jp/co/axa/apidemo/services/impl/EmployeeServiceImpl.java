package jp.co.axa.apidemo.services.impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the EmployeeService interface.
 * Includes caching for better performance:
 * - List of employees is cached under "employees"
 * - Individual employees are cached under "employee" with their ID as key
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees with caching.
     * First call will hit the database, subsequent calls will use cache.
     */
    @Cacheable(value = "employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    /**
     * Retrieves a single employee by ID with caching.
     * First call will hit the database, subsequent calls will use cache.
     */
    @Cacheable(value = "employee", key = "#employeeId")
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    /**
     * Saves a new employee.
     * Invalidates both the individual employee cache and the list cache.
     */
    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * Deletes an employee.
     * Invalidates both the individual employee cache and the list cache.
     */
    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Updates an existing employee.
     * Invalidates both the individual employee cache and the list cache.
     */
    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
} 