package cz.uhk.grainweight.rest;

import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.service.WeightRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class WeightRecordRestController {

    private final WeightRecordService weightRecordService;

    @Autowired
    public WeightRecordRestController(WeightRecordService weightRecordService) {
        this.weightRecordService = weightRecordService;
    }

    @GetMapping("/getall")
    public List<WeightRecord> getAllRecords() {
        return weightRecordService.getAllWeightRecords();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WeightRecord> getRecord(@PathVariable long id) {
        return weightRecordService.getWeightRecord(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public WeightRecord createOrUpdateRecord(@RequestBody WeightRecord weightRecord) {
        return weightRecordService.saveWeightRecord(weightRecord);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<WeightRecord> deleteRecord(@PathVariable long id) {
        return weightRecordService.getWeightRecord(id)
                .map(record -> {
                    weightRecordService.deleteWeightRecord(id);
                    return ResponseEntity.ok(record);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
