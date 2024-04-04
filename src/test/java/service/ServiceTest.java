package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;
    private Student student;
    private StudentXMLRepo studentXMLRepository;
    private  StudentValidator studentValidator;
    private TemaXMLRepo temaXMLRepository;

    private TemaValidator temaValidator;
    private NotaXMLRepo notaXMLRepository;

    private NotaValidator notaValidator;
    private Student studentBad;

    @BeforeEach
    void setUp(){
        student = new Student("33", "Somesan Paul", 936, "paul.somesan@gmail.com");
        studentBad = new Student("", "Somesan Paul", 936, "paul.somesan@gmail.com");
        studentXMLRepository = new StudentXMLRepo("fisiere/Studenti.xml");
        studentValidator = new StudentValidator();
        temaXMLRepository = new TemaXMLRepo("fisiere/Teme.xml");
        temaValidator = new TemaValidator();
        notaXMLRepository = new NotaXMLRepo("fisiere/Note.xml");
        notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }
    @Test
    void addStudent() {

        Student student1 = service.addStudent(student);

        assertEquals(student1, null);


        Student student2 = service.addStudent(student);
        assertNotEquals(student2, null);

    }
}