package edu.cit.lapure.jessie.campusequipmentloan.repository;

import edu.cit.lapure.jessie.campusequipmentloan.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByAvailabilityTrue();
}
