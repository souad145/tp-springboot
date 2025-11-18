package com.example.tp.service;
import com.example.tp.model.Student;
import com.example.tp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() { return repo.findAll(); }

    public Optional<Student> getById(Long id) { return repo.findById(id); }

    public Student create(Student s) { return repo.save(s); }

    public Student update(Long id, Student s) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(s.getFirstName());
            existing.setLastName(s.getLastName());
            existing.setEmail(s.getEmail());
            existing.setUniversity(s.getUniversity());
            return repo.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) { repo.deleteById(id); }

    public List<Student> searchByName(String name) {
        return repo.findByFirstNameContainingIgnoreCase(name);
    }

    public List<Student> filterByUniversity(Long uniId) {
        return repo.findByUniversityId(uniId);
    }
}
