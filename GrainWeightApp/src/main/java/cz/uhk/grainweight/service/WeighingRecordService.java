package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeightRecord;
import java.util.List;

public interface WeighingRecordService {
    List<WeightRecord> getAllWeighingRecords();
    WeightRecord getWeighingRecordById(Long id);
    WeightRecord saveWeighingRecord(WeightRecord weighingRecord);
    void deleteWeightRecord(long id);

    List<WeightRecord> getAllWeightRecords();

    WeightRecord getWeightRecord(Long id);

    WeightRecord saveWeightRecord(WeightRecord weightRecord);

    void deleteWeightRecord(Long id);
}
