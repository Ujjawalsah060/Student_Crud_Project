package udSah.techGroup.DTO_Operation.service;

import org.springframework.stereotype.Service;
import udSah.techGroup.DTO_Operation.dto.CreatedStudentRequestDto;
import udSah.techGroup.DTO_Operation.dto.CreatedStudentResponseDto;
import udSah.techGroup.DTO_Operation.dto.UpdateStudentRequestDto;
import udSah.techGroup.DTO_Operation.dto.UpdateStudentResponseDto;
import udSah.techGroup.DTO_Operation.entity.Student;
import udSah.techGroup.DTO_Operation.exception.DuplicateResourceException;
import udSah.techGroup.DTO_Operation.exception.ResourceNotFoundException;
import udSah.techGroup.DTO_Operation.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class StudentService {


        private StudentRepository studentRepository;

        public StudentService(StudentRepository studentRepository) {

            this.studentRepository = studentRepository;
        }
// creating a student
        public CreatedStudentResponseDto createStudent(
                CreatedStudentRequestDto studentRequestDto) {
            Student student = mapToEntity(studentRequestDto);



            if(emailExists(student)){
                throw  new DuplicateResourceException("Student with email" + Student.getEmail() + " already exists");
            }


            Student studentResp = studentRepository.save(student);

            return mapToDto(studentResp);
        }


        // get student of one record
    public CreatedStudentResponseDto getOneStudent(Long id) {
         Student studentResp= studentRepository.findById(id)
                 .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + "Not found" ));


        return mapToDto(studentResp);
    }

    // get all record of the student

    public List<CreatedStudentResponseDto> getAllStudent(){
            List<Student> studentList = studentRepository.findByDeletedIsFalse();

            return studentList.stream()
                    .map(this::mapToDto)
                    .toList();

        }


        // update the Student Record


    public UpdateStudentResponseDto updateStudent(Long id ,UpdateStudentRequestDto studentReq) {
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));




        existingStudent.setName(studentReq.getName());

        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(existingStudent);

        return mapToUpdateDto(savedStudent);
    }

        public void  deleteStudent(Long id){
            Student studentToBeDeleted = studentRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Student with id " + id + " not found"));

            studentRepository.delete(studentToBeDeleted);


        }

        public void  studentToBeDeletedSoftly (Long id){
             Student studentToBeDeletedSoftly = studentRepository
                     .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Student with id " + id + " not found"));



            studentToBeDeletedSoftly.setDeleted(true);
            studentRepository.save(studentToBeDeletedSoftly);


        }

        private Student mapToEntity(CreatedStudentRequestDto studentRequestDto){
            Student student = new Student();
            student.setName(studentRequestDto.getName());
            student.setAge(studentRequestDto.getAge());
            student.setEmail(studentRequestDto.getEmail());

            student.setSubject(studentRequestDto.getSubject());
            student.setCreatedAt(LocalDateTime.now());
            student.setUpdatedAt(LocalDateTime.now());

            student.setDeleted(false);

            return student;
        }

        private CreatedStudentResponseDto mapToDto(Student student){
            CreatedStudentResponseDto responseDto = new CreatedStudentResponseDto();



            responseDto.setId(student.getId());
            responseDto.setName(student.getName());
            responseDto.setAge(student.getAge());
            responseDto.setEmail(student.getEmail());

            responseDto.setSubject(student.getSubject());
            responseDto.setMessage("Student saved successfully");
            responseDto.setCreatedAt(student.getCreatedAt());
            responseDto.setUpdatedAt(student.getUpdatedAt());

            return responseDto;
        }
    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setSubject(student.getSubject());
        responseDto.setMessage("Student updated successfully");
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private Boolean emailExists(Student student){
            return studentRepository.existsByEmail(Student.getEmail());
    }



}



