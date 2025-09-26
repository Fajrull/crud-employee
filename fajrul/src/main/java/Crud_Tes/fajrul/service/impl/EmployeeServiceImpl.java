package Crud_Tes.fajrul.service.impl;

import Crud_Tes.fajrul.model.dto.request.EmployeeRequest;
import Crud_Tes.fajrul.model.dto.request.SearchRequest;
import Crud_Tes.fajrul.model.dto.response.EmployeeResponse;
import Crud_Tes.fajrul.model.entity.Employee;
import Crud_Tes.fajrul.repository.EmployeeRepository;
import Crud_Tes.fajrul.service.EmployeeService;
import Crud_Tes.fajrul.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = mapToEntity((request));
        employee = employeeRepository.save(employee);
        return mapToResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(SearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        String query = request.getQuery() != null ? request.getQuery() : "";
        Page<Employee> employeePage = employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);

        return employeePage.map(this::mapToResponse);
    }


    @Override
    public EmployeeResponse getById(String id) {
        return mapToResponse(findByIdOrThrowNotFound(id));
    }

    @Override
    public EmployeeResponse update(String id, EmployeeRequest request) {
        Employee existingEmployee = findByIdOrThrowNotFound(id);

        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setGender(request.getGender());
        existingEmployee.setBirthDate(request.getBirthDate());
        existingEmployee.setHireDate(request.getHireDate());

        Employee updatedEmployee = employeeRepository.saveAndFlush(existingEmployee);
        return mapToResponse(updatedEmployee);
    }

    @Override
    public EmployeeResponse deleteById(String id) {
        Employee existingEmployee = findByIdOrThrowNotFound(id);
        employeeRepository.delete(existingEmployee);
        return null;
    }

    private Employee findByIdOrThrowNotFound(String id){
        return  employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found",new RuntimeException("Employee not found"))
        );
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .birthDate(employee.getBirthDate())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender())
                .hireDate(employee.getHireDate())
                .build();
    }

    private Employee mapToEntity(EmployeeRequest request) {
        return Employee.builder()
                .birthDate(request.getBirthDate())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .hireDate(request.getHireDate())
                .build();
    }

}
