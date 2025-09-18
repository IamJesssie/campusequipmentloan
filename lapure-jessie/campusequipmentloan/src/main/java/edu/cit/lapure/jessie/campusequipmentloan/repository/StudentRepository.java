package edu.cit.lapure.jessie.campusequipmentloan.repository;

import edu.cit.lapure.jessie.campusequipmentloan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
