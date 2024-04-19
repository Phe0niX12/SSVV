
package org.example;
import domain.Nota;
import domain.Student;

import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private Nota nota;
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
    void saveTest_addStudent() {

        Student student1 = service.addStudent(student);

        assertNotEquals(student1, null);


        Student student2 = service.addStudent(student);
        assertNotEquals(student2, null);

    }

    @Test
    void validatorTest_addTema_numberInvalid() {
        try{
            service.addTema(tema1);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Numar tema invalid!");
        }




    }
    @Test
    void validatorTest_addTema_descriptionInvalid(){
        tema1 = new Tema("20","",0,0);
        try{
            service.addTema(tema1);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Descriere invalida!");
        }
    }

    @Test
    void validatorTest_addTema_deadlineInvalid(){
        tema1 = new Tema("20","sdao",1000,0);
        try {
            service.addTema(tema1);
        }catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
    }
    @Test
    void validatorTest_addTema_primireInvalida(){
        tema1 = new Tema("20","dasa",12,10000000);
        try {
            service.addTema(tema1);
        }catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

    @Test
    void validatorTest_addTema_validTema(){
        tema1 = new Tema("20","ada",11,11);
        try{
            Tema tema2 = service.addTema(tema1);
            assertEquals(tema2.getID(),tema1.getID());
        }catch (Exception e){
            assertEquals(e.getMessage(), "");
        }
    }

    @Test
    void validatorTest_addNota_validStudent() {
        student = new Student("10", "Adi", 916, "sdasdas@gmail.com");


        try {
            Student student1 = service.addStudent(student);
            assertEquals(student1.getNume(), student.getNume());
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Studentul valid!");
        }
    }


    @Test
    void validatorTest_addNota_validNota() {
        nota = new Nota("", "10", "20", 6, LocalDate.of(2015, Month.FEBRUARY, 11));

        try {
            double nota2 = service.addNota(nota,"Bun");
            assertNull(nota2);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Studentul nu mai poate preda aceasta tema!");
        }
    }

    @Test
    void inegrationTest_addNota_addTema_addStudent(){
        tema1 = new Tema("200","dasa",1,4);
        student = new Student("100", "Adi", 916, "sdasdas@gmail.com");
        nota = new Nota("10", "100", "200", 6, LocalDate.of(2018, Month.OCTOBER, 15));

        try{
            service.addStudent(student);
            service.addTema(tema1);
            double nota2 = service.addNota(nota,"Esti Bun");
            assertEquals(nota2, 3.5);
        }catch (Exception e){
            assertEquals(e.getMessage(), "");
        }
    }

}
