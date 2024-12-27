package com.example.CodeDemoUntiTest.controller;

import com.example.CodeDemoUntiTest.entity.Employee;
import com.example.CodeDemoUntiTest.service.Impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1)
                .firstName("John")
                .lastName("Cena")
                .email("john@gmail.com")
                .build();

    }

    @Test
    @Order(1)
    public void saveEmployeeTest() throws Exception {
        // precondition
        given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

        // action
//        ResultActions response = mockMvc.perform(post("/api/employees")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(employee)));

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // verify
//        response.andDo(print()).
//                andExpect(status().isCreated())
//                .andExpect(jsonPath("$.firstName",
//                        is(employee.getFirstName())))
//                .andExpect(jsonPath("$.lastName",
//                        is(employee.getLastName())))
//                .andExpect(jsonPath("$.email",
//                        is(employee.getEmail())));
    }

    @Test
    @Order(2)
    public void getEmployeeTest() throws Exception{
        // precondition
        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(employee);
        employeesList.add(Employee.builder().id(1).firstName("Sam").lastName("Curran").email("sam@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(employeesList);

        // action
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // verify the output
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.size()",
//                        is(employeesList.size())));

    }

    @Test
    @Order(3)
    public void getByIdEmployeeTest() throws Exception{
        // precondition
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));

        // action
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // verify
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
//                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }


    //Update employee
    @Test
    @Order(4)
    public void updateEmployeeTest() throws Exception{
        // precondition
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        employee.setFirstName("Max");
        employee.setEmail("max@gmail.com");
        given(employeeService.updateEmployee(employee,employee.getId())).willReturn(employee);

        // action
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // verify
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
//                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }


    // delete employee
//    @Test
//    public void deleteEmployeeTest() throws Exception{
//        // precondition
//        willDoNothing().given(employeeService).deleteEmployee(employee.getId());
//
//        // action
//        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
//
//        // then - verify the output
//        response.andExpect(status().isOk())
//                .andDo(print());
//    }
}
