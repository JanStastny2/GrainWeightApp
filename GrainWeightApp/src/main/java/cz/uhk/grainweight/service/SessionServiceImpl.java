package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Override
    public void startSession(Session session) {
    }

    @Override
    public List<Session> getAllSessions() {
        return List.of();
    }

    @Override
    public Session getSession(Long id) {
        return null;
    }

}
