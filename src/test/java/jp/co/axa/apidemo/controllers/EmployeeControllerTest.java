package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(50000);
        employee.setDepartment("IT");
    }

    @Test
    public void testGetEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeService.retrieveEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(employee.getName())))
                .andExpect(jsonPath("$[0].salary", is(employee.getSalary())))
                .andExpect(jsonPath("$[0].department", is(employee.getDepartment())));

        verify(employeeService, times(1)).retrieveEmployees();
    }

    @Test
    public void testGetEmployee() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employee.getId().intValue())))
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())));

        verify(employeeService, times(1)).getEmployee(1L);
    }

    @Test
    public void testSaveEmployee() throws Exception {
        doNothing().when(employeeService).saveEmployee(any(Employee.class));

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John2\",\"salary\":500000,\"department\":\"IT\"}"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(employee);
        doNothing().when(employeeService).updateEmployee(any(Employee.class));

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\",\"salary\":60000,\"department\":\"HR\"}"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).getEmployee(1L);
        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(null);

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\",\"salary\":60000,\"department\":\"HR\"}"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployee(1L);
        verify(employeeService, never()).updateEmployee(any(Employee.class));
    }
} 