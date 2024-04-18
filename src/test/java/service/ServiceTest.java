package service;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaFileRepository;
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
    private Tema tema1;


    @BeforeEach
    void setUp(){
        tema1 = new Tema("","",0,0);
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

        assertNotEquals(student1, null);


        Student student2 = service.addStudent(student);
        assertNotEquals(student2, null);

    }

    @Test
    void addTema() {
        try{
            service.addTema(tema1);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Numar tema invalid!");
        }
        tema1 = new Tema("20","",0,0);
        try{
            service.addTema(tema1);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Descriere invalida!");
        }
        tema1 = new Tema("20","sdao",1000,0);
        try {
            service.addTema(tema1);
        }catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
        tema1 = new Tema("20","dasa",12,10000000);
        try {
            service.addTema(tema1);
        }catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
        tema1 =  new Tema("20","ada",11,11);
        try{
            Tema tema2 = service.addTema(tema1);
            assertEquals(tema2.getID(),tema1.getID());
        }catch (Exception e){
            assertEquals(e.getMessage(), "");
        }
    }
}