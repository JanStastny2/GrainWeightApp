package cz.uhk.grainweight.repository;

import cz.uhk.grainweight.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

}
