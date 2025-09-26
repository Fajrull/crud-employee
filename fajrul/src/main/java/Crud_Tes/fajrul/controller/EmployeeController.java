package Crud_Tes.fajrul.controller;

import Crud_Tes.fajrul.model.dto.request.EmployeeRequest;
import Crud_Tes.fajrul.model.dto.request.SearchRequest;
import Crud_Tes.fajrul.model.dto.response.CommonResponse;
import Crud_Tes.fajrul.model.dto.response.EmployeeResponse;
import Crud_Tes.fajrul.model.dto.response.PagingResponse;
import Crud_Tes.fajrul.service.EmployeeService;
import Crud_Tes.fajrul.utils.validate.PagingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> addNewEmploye(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse employee = employeeService.create(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Employee created successfully")
                .data(employee)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<EmployeeResponse>>> getAllEmployee(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ){
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchRequest request = SearchRequest.builder()
                .size(size)
                .page(Math.max(page -1 ,0))
                .query(search)
                .sort(sort)
                .direction(direction)
                .build();
        Page<EmployeeResponse> employees = employeeService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .totalPages(employees.getTotalPages())
                .totalElements(employees.getTotalElements())
                .page(page)
                .size(size)
                .hasNext(employees.hasNext())
                .hasPrevious(employees.hasPrevious())
                .build();

        CommonResponse<List<EmployeeResponse>> response = CommonResponse.<List<EmployeeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee retrieved successfully")
                .data(employees.getContent())
                .paging(paging)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<EmployeeResponse>> getEmployeeById(@PathVariable String id){
        EmployeeResponse employee = employeeService.getById(id);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee retrieved successfully")
                .data(employee)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<EmployeeResponse>> updateEmployee(
            @PathVariable String id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse employee = employeeService.update(id, request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee updated successfully")
                .data(employee)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id) {
        employeeService.deleteById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee with id " + id + " has been deleted.")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
