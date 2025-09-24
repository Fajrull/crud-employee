package Crud_Tes.fajrul.service;

import Crud_Tes.fajrul.model.dto.request.EmployeeRequest;
import Crud_Tes.fajrul.model.dto.request.SearchRequest;
import Crud_Tes.fajrul.model.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);
    Page<EmployeeResponse> getAll(SearchRequest request);
    EmployeeResponse getById(String id);
    EmployeeResponse update(EmployeeRequest request);
    EmployeeResponse deleteById(String id);
}
