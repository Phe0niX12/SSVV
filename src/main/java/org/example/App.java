package org.example;

import curent.Curent;
import repository.NotaFileRepository;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import repository.NotaXMLRepo;
import repository.TemaFileRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import view.UI;
import domain.*;
import java.time.LocalDate;



public class App {

    public static void main(String[] args) {
        // Creating instances of repositories, validators, and services
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/Studenti.xml");
        StudentValidator studentValidator = new StudentValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo("fisiere/Teme.xml");
        TemaValidator temaValidator = new TemaValidator();
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo("fisiere/Note.xml");
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Student newStudent = new Student("S123", "Mircea Augustin", 123, "mircea.augustin@example.com");
        service.addStudent(newStudent);

        Tema newTema = new Tema("T2", "Lab Assignment 12", 5, 2); // Assuming current week is 2, deadline in week 5
        service.addTema(newTema);

        Nota newNota = new Nota("N1", "S123", "T2", 9.5, LocalDate.of(2024, 3, 10)); // Adjust the date to fit within the deadline or permissible late period
        String feedback = "Good job!";
        service.addNota(newNota, feedback);
    }
}