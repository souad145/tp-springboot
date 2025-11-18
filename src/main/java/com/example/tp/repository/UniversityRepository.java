package com.example.tp.repository;
import com.example.tp.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UniversityRepository extends JpaRepository<University, Long>{
    
}
