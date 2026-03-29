package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeightRecord;

import java.util.List;
import java.util.Optional;

public interface WeightRecordService {

    List<WeightRecord> getAllWeightRecords();

    Optional<WeightRecord> getWeightRecord(Long id);

    WeightRecord saveWeightRecord(WeightRecord weightRecord);

    void deleteWeightRecord(Long id);
}
