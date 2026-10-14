package udSah.techGroup.DTO_Operation.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udSah.techGroup.DTO_Operation.dto.CreatedStudentRequestDto;
import udSah.techGroup.DTO_Operation.dto.CreatedStudentResponseDto;
import udSah.techGroup.DTO_Operation.dto.UpdateStudentRequestDto;
import udSah.techGroup.DTO_Operation.dto.UpdateStudentResponseDto;
import udSah.techGroup.DTO_Operation.entity.Student;
import udSah.techGroup.DTO_Operation.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")

public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping

    public ResponseEntity<CreatedStudentResponseDto> createStudent(
           @Valid @RequestBody CreatedStudentRequestDto studentRequestDto){

        CreatedStudentResponseDto createdStudentRecord =
                studentService.createStudent(studentRequestDto);
        return  ResponseEntity.

                status(HttpStatus.CREATED)
                .body(createdStudentRecord);
    }

    @GetMapping("{id}")
    public ResponseEntity<CreatedStudentResponseDto> getOneStudent(@PathVariable Long id){
        CreatedStudentResponseDto studentResp = studentService.getOneStudent(id);
        if(studentResp == null){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreatedStudentResponseDto>> getAllStudent(){
        List<CreatedStudentResponseDto> studentList = studentService.getAllStudent();
        return ResponseEntity.ok(studentList);
    }


    @PutMapping("{id}")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(
            @PathVariable Long id,
            @RequestBody UpdateStudentRequestDto studentReq) {

        UpdateStudentResponseDto updatedStudent =
                studentService.updateStudent(id, studentReq);

        return ResponseEntity.ok(updatedStudent);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
        studentService.studentToBeDeletedSoftly (id);

        return ResponseEntity.noContent().build();
    }



}
