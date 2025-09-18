package edu.cit.lapure.jessie.campusequipmentloan.config;

import edu.cit.lapure.jessie.campusequipmentloan.model.Equipment;
import edu.cit.lapure.jessie.campusequipmentloan.model.Student;
import edu.cit.lapure.jessie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.lapure.jessie.campusequipmentloan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EquipmentRepository equipmentRepository, 
                                     StudentRepository studentRepository) {
        return args -> {
            // Create and save equipment
            Equipment laptop = new Equipment("Dell Laptop", "Computer", "DL-2023-001", true);
            Equipment projector = new Equipment("Epson Projector", "Visual Aid", "EP-2023-001", true);
            Equipment camera = new Equipment("Canon DSLR", "Photography", "CD-2023-001", true);
            Equipment microscope = new Equipment("Laboratory Microscope", "Science", "LM-2023-001", true);
            Equipment tablet = new Equipment("iPad Pro", "Tablet", "IP-2023-001", true);
            
            equipmentRepository.save(laptop);
            equipmentRepository.save(projector);
            equipmentRepository.save(camera);
            equipmentRepository.save(microscope);
            equipmentRepository.save(tablet);
            
            // Create and save students
            Student student1 = new Student("2023-0001", "Juan Dela Cruz", "juan.delacruz@example.com");
            Student student2 = new Student("2023-0002", "Maria Santos", "maria.santos@example.com");
            Student student3 = new Student("2023-0003", "Pedro Reyes", "pedro.reyes@example.com");
            
            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
        };
    }
}
