package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeighingSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeighingRecordServiceImpl implements WeighingSessionService {
    @Override
    public void startSession(WeighingSession session) {
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
