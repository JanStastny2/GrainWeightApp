package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.repository.WeightRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeightRecordServiceImpl implements WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    @Override
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    @Override
    public Optional<WeightRecord> getWeightRecord(Long id) {
        return weightRecordRepository.findById(id);
    }

    @Override
    public WeightRecord saveWeightRecord(WeightRecord weightRecord) {
        return weightRecordRepository.save(weightRecord);
    }

    @Override
    public void deleteWeightRecord(Long id) {
        weightRecordRepository.deleteById(id);
    }

}
