package com.example.tp.controller;
import com.example.tp.model.Student;
import com.example.tp.model.University;
import com.example.tp.repository.UniversityRepository;
import com.example.tp.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {
     private final StudentService studentService;
    private final UniversityRepository universityRepo;

    public StudentController(StudentService studentService, UniversityRepository universityRepo) {
        this.studentService = studentService;
        this.universityRepo = universityRepo;
    }

  
    @PostMapping
    public Student create(@RequestBody Student s) {
        if (s.getUniversity() != null && s.getUniversity().getId() != null) {
            universityRepo.findById(s.getUniversity().getId()).ifPresent(s::setUniversity);
        }
        return studentService.create(s);
    }

    
    @GetMapping
    public List<Student> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long universityId
    ) {
        if (name != null) return studentService.searchByName(name);
        if (universityId != null) return studentService.filterByUniversity(universityId);
        return studentService.getAll();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return studentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student s) {
        Student updated = studentService.update(id, s);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

 
    @PostMapping("/universities")
    public University createUniversity(@RequestBody University u) {
        return universityRepo.save(u);
    }

    @GetMapping("/universities")
    public List<University> getUniversities() {
        return universityRepo.findAll();
    }
}
