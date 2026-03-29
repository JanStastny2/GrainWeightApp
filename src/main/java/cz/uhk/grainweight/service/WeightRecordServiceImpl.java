package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.repository.WeightRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeightRecordServiceImpl implements WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    @Autowired
    public WeightRecordServiceImpl(WeightRecordRepository weightRecordRepository) {
        this.weightRecordRepository = weightRecordRepository;
    }

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