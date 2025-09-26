package Crud_Tes.fajrul.service;

import Crud_Tes.fajrul.constant.Gender;
import Crud_Tes.fajrul.model.dto.request.EmployeeRequest;
import Crud_Tes.fajrul.model.dto.request.SearchRequest;
import Crud_Tes.fajrul.model.dto.response.EmployeeResponse;
import Crud_Tes.fajrul.model.entity.Employee;
import Crud_Tes.fajrul.repository.EmployeeRepository;
import Crud_Tes.fajrul.service.impl.EmployeeServiceImpl;
import Crud_Tes.fajrul.utils.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeRequest employeeRequest;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRequest = EmployeeRequest.builder()
                .id("fj-1")
                .firstName("fajrul")
                .lastName("khaq")
                .birthDate(new Date())
                .gender(Gender.M)
                .hireDate(new Date())
                .build();

        employee = Employee.builder()
                .id("fj-1")
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .birthDate(employeeRequest.getBirthDate())
                .gender(employeeRequest.getGender())
                .hireDate(employeeRequest.getHireDate())
                .build();
    }

    @Test
    void testCreateEmployeeSuccess() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponse response = employeeService.create(employeeRequest);

        assertNotNull(response);
        assertEquals(employeeRequest.getFirstName(), response.getFirstName());
    }

    @Test
    void testCreateEmployeeFail() {
        when(employeeRepository.save(any(Employee.class)))
                .thenThrow(new DataIntegrityViolationException("Invalid data"));

        assertThrows(DataIntegrityViolationException.class,
                () -> employeeService.create(employeeRequest));
    }

    @Test
    void testGetAllEmployeeSuccess() {
        // Mock data employee
        List<Employee> employees = List.of(employee);
        Page<Employee> employeePage = new PageImpl<>(employees);

        // Search request
        SearchRequest searchRequest = SearchRequest.builder()
                .query("fajrul")
                .page(0)
                .size(5)
                .direction("asc")
                .sort("firstName")
                .build();

        // Mock repository method yang sesuai dengan service
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                eq("fajrul"), eq("fajrul"), any(Pageable.class))
        ).thenReturn(employeePage);

        // Call service
        Page<EmployeeResponse> response = employeeService.getAll(searchRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("fajrul", response.getContent().get(0).getFirstName());
    }


    @Test
    void testGetByIdSuccess() {
        when(employeeRepository.findById("fj-1")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getById("fj-1");

        assertNotNull(response);
        assertEquals("fajrul", response.getFirstName());
    }

    @Test
    void testGetByIdNotFound() {
        when(employeeRepository.findById("emp-99")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getById("emp-99"));
    }

    @Test
    void testUpdateEmployeeSuccess() {
        String id = "fj-1";
        employeeRequest.setFirstName("mfajrul");

        when(employeeRepository.findById("fj-1")).thenReturn(Optional.of(employee));
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(
                Employee.builder()
                        .id("fj-1")
                        .firstName("mfajrul")
                        .lastName(employee.getLastName())
                        .birthDate(employee.getBirthDate())
                        .gender(employee.getGender())
                        .hireDate(employee.getHireDate())
                        .build()
        );

        EmployeeResponse response = employeeService.update(id, employeeRequest);

        assertNotNull(response);
        assertEquals("mfajrul", response.getFirstName());
    }

    @Test
    void testDeleteEmployeeSuccess() {
        when(employeeRepository.findById("fj-1")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.deleteById("fj-1");

        assertNull(response);
        Mockito.verify(employeeRepository).delete(employee);
    }
}
