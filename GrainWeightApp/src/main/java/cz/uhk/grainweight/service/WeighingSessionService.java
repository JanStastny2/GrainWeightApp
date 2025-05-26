package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeighingSession;
import java.util.List;

public interface WeighingSessionService {
    void startSession(WeighingSession session);
    List<WeighingSession> getAllSessions();
    WeighingSession getSession(Long id);
}
