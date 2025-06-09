package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @Before
    public void setup() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(50000);
        employee.setDepartment("IT");
    }

    @Test
    public void testRetrieveEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        
        List<Employee> employees = employeeService.retrieveEmployees();
        
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(employee.getName(), employees.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        
        Employee found = employeeService.getEmployee(1L);
        
        assertNotNull(found);
        assertEquals(employee.getId(), found.getId());
        assertEquals(employee.getName(), found.getName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveEmployee() {
        employeeService.saveEmployee(employee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {
        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateEmployee() {
        employeeService.updateEmployee(employee);
        verify(employeeRepository, times(1)).save(employee);
    }
} 