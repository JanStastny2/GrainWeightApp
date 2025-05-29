package cz.uhk.grainweight.repository;

import cz.uhk.grainweight.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> { }
