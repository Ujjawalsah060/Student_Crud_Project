package udSah.techGroup.DTO_Operation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

public class CreatedStudentRequestDto {

    @NotBlank(message = "Name cannot be null/Empty or blank")
    @Size(min = 2, max = 50, message = " Student name must be within 2 to 50 character long ")
    private String name;

    @Email
    private String email;


    @NotBlank(message = "subject is necessary")
    private  String subject;


    @NotBlank(message = "Age is required")
    @NotNull(message = "age must be greater than equal to 18 years")
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
