package ca.tetervak.studentdata.errors;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Integer id) {
        super("Student not found: id=" + id);
    }
}
