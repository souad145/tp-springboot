package com.example.tp;

import com.example.tp.model.Student;
import com.example.tp.repository.StudentRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  
@ActiveProfiles("test")  
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void cleanDatabase() {
        studentRepository.deleteAll();  // Nettoie la base avant chaque test
    }

    @Test
    @Order(1)
    void shouldSaveStudent() {
        // Crée un étudiant
        Student student = new Student();
        student.setFirstName("Charlie");
        student.setLastName("Brown");
        student.setEmail("charlie@example.com");

        // Sauvegarde dans la base H2
        studentRepository.save(student);

        // Vérifie qu'il y a exactement 1 enregistrement
        assertThat(studentRepository.count()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void shouldFindAllStudents() {
        // Ajoute l'étudiant (recréé à chaque test)
        Student student = new Student();
        student.setFirstName("Charlie");
        student.setLastName("Brown");
        student.setEmail("charlie@example.com");
        studentRepository.save(student);

        // Récupère tous les étudiants
        List<Student> students = studentRepository.findAll();

        // Vérifie qu'il y a exactement 1 étudiant
        assertThat(students).hasSize(1);

        // Vérifie les champs
        Student saved = students.get(0);
        assertThat(saved.getFirstName()).isEqualTo("Charlie");
        assertThat(saved.getLastName()).isEqualTo("Brown");
        assertThat(saved.getEmail()).isEqualTo("charlie@example.com");
    }
}
