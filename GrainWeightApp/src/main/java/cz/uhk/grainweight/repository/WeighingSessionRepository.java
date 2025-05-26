package cz.uhk.grainweight.repository;

import cz.uhk.grainweight.model.WeighingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeighingSessionRepository extends JpaRepository<WeighingSession, Long> { }
