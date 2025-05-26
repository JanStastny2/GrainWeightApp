package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.WeightRecord;
import java.util.List;

public interface WeighingRecordService {
    WeightRecord findById(Long id);

}
