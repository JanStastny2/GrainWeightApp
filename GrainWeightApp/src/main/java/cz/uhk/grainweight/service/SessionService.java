package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Session;
import java.util.List;

public interface SessionService {
    void startSession(Session session);
    List<Session> getAllSessions();
    Session getSession(Long id);
}
