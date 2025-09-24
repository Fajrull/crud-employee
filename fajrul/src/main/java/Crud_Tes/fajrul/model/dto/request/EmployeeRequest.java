package Crud_Tes.fajrul.model.dto.request;

import Crud_Tes.fajrul.constant.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class EmployeeRequest {
    private String id;

    @NotNull(message = "Birth date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank(message = "First name is required")
    @Size(max = 14, message = "First name must not exceed 14 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 16, message = "Last name must not exceed 16 characters")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;


    @NotNull(message = "Hire date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date hireDate;
}
