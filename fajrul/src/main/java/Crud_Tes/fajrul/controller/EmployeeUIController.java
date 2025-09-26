package Crud_Tes.fajrul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeUIController {

    @GetMapping("/employees")
    public String employeePage() {
        return "employee";
    }
}
