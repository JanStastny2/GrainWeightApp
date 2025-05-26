package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeighingSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeighingSessionServiceImpl implements WeighingSessionService {
    @Override
    public WeighingSession startSession(WeighingSession session) {
        return null;
    }

    @Override
    public List<WeighingSession> getAllSessions() {
        return List.of();
    }

    @Override
    public WeighingSession getSession(Long id) {
        return null;
    }

}
