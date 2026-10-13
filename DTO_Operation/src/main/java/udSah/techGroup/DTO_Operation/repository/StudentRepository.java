package udSah.techGroup.DTO_Operation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udSah.techGroup.DTO_Operation.dto.CreatedStudentResponseDto;
import udSah.techGroup.DTO_Operation.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository

public  interface StudentRepository extends JpaRepository<Student,Long>{
    public CreatedStudentResponseDto findByIdAndDeletedFalse(Long id);
    public List<Student>findAllByDeletedFalse();


    List<Student> findByDeletedIsFalse();

    Student findByIdAndDeletedIsFalse(Long id);
}