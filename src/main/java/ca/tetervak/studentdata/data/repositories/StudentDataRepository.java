package ca.tetervak.studentdata.data.repositories;


import ca.tetervak.studentdata.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentDataRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s ORDER BY s.firstName, s.lastName")
    List<Student> findAllSorted();
}
